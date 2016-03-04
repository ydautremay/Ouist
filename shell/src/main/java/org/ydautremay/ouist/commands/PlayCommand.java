package org.ydautremay.ouist.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.seedstack.business.domain.Repository;
import org.seedstack.jpa.Jpa;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.spi.command.Argument;
import org.seedstack.seed.spi.command.Command;
import org.seedstack.seed.spi.command.CommandDefinition;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.ydautremay.ouist.application.Session;
import org.ydautremay.ouist.domain.model.game.Chair;
import org.ydautremay.ouist.domain.model.game.Game;
import org.ydautremay.ouist.domain.model.game.GameState;
import org.ydautremay.ouist.domain.model.game.Round;
import org.ydautremay.ouist.domain.model.game.exceptions.GameActionException;
import org.ydautremay.ouist.domain.model.player.PlayerNickName;
import org.ydautremay.ouist.domain.model.scoresheet.Score;
import org.ydautremay.ouist.domain.model.scoresheet.ScoreSheet;
import org.ydautremay.ouist.domain.service.ScoreService;

/**
 * Created by dautremayy on 06/02/2016.
 */
@CommandDefinition(scope = "ouist", name = "play", description = "Register the leader of the played trick")
public class PlayCommand implements Command<String> {
    @Logging
    private Logger logger;

    @Argument(name = "leader", index = 0, description = "Nickname of the leader")
    private String leader;

    @Inject
    private Session session;

    @Inject
    @Jpa
    private Repository<Game, UUID> gameRepository;

    @Inject
    @Jpa
    private Repository<ScoreSheet, UUID> scoreSheetRepository;

    @Inject
    private ScoreService scoreService;

    @Transactional
    @JpaUnit("ouist-jpa-unit")
    public String execute(Object object) throws Exception {
        UUID gameId = session.getCurrentGameId();
        if (gameId == null) {
            return "You need to join a game. Please use the join command";
        }
        Game game = gameRepository.load(gameId);
        if (game == null) {
            return "The game you joined does not exist anymore";
        }
        GameState state;
        try {
            state = game.nextTrick(new PlayerNickName(leader));
        }
        catch (GameActionException e) {
            return "Player " + leader + " is not playing at this table";
        }
        gameRepository.save(game);
        if (state == GameState.READY) {
            ScoreSheet scoreSheet = scoreSheetRepository.load(game.getScoreSheetId());
            Round lastRound = game.getLastRound();
            scoreService.computeScores(scoreSheet, lastRound);
            scoreSheetRepository.save(scoreSheet);
            String toReturn = "Round over !\n";
            for (Chair chair : game.getChairs()) {
                PlayerNickName player = chair.getPlayer();
                Score score = getScore(scoreSheet, lastRound, player);
                toReturn += player + " : " + score.getValue() + "\n";
            }
            toReturn += "\n";
            toReturn += "Game totals \n";
            List<Score> totals = new ArrayList<>();
            for (Chair chair : game.getChairs()) {
                PlayerNickName player = chair.getPlayer();
                int total = scoreSheet.getTotal(player);
                totals.add(new Score(player, total));
            }
            Collections.sort(totals, Comparator.comparing(score -> score.getValue()));
            for (Score score:totals) {
                toReturn += score.getPlayer() + " : " + score.getValue() + "\n";
            }
            toReturn += "New Round\n";
            toReturn += game.getCurrentRound().getDealer() + " : please deal " + game.getCurrentTrickAmount
                    () + " cards to each player\n";
            toReturn += "First player to bet : " + game.getPlayerToBet(game.getCurrentRound()) + "\n";
            return toReturn;
        } else {
            return leader + " takes the trick.";
        }
    }

    private Score getScore(ScoreSheet scoreSheet, Round lastRound, PlayerNickName player) {

        Collection<Score> roundScores = scoreSheet.getScoreLines().get(lastRound.getRoundId().getRoundNb())
                .getRoundScores();
        for(Score score:roundScores){
            if(score.getPlayer().equals(player)){
                return score;
            }
        }
        return null;
    }
}
