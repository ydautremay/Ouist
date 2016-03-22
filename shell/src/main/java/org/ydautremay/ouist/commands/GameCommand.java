package org.ydautremay.ouist.commands;

import java.util.UUID;

import javax.inject.Inject;

import org.seedstack.business.domain.Repository;
import org.seedstack.jpa.Jpa;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.spi.command.Command;
import org.seedstack.seed.spi.command.CommandDefinition;
import org.seedstack.seed.spi.command.Option;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.ydautremay.ouist.application.Session;
import org.ydautremay.ouist.domain.model.game.Chair;
import org.ydautremay.ouist.domain.model.game.Game;
import org.ydautremay.ouist.domain.model.game.GameFactory;
import org.ydautremay.ouist.domain.model.game.GameState;
import org.ydautremay.ouist.domain.model.player.PlayerNickName;
import org.ydautremay.ouist.domain.model.scoresheet.ScoreSheet;
import org.ydautremay.ouist.domain.model.scoresheet.ScoreSheetFactory;

/**
 * Created by dautremayy on 29/01/2016.
 */
@CommandDefinition(scope = "ouist", name = "game", description = "Create a new Game")
public class GameCommand implements Command<String> {

    @Logging
    private Logger logger;

    @Option(name = "a", longName = "add-player", hasArgument = true, description = "Players can be added "
            + "separated by ','")
    private String playersToAdd;

    @Option(name = "c", longName = "create", description = "Creates a new game. No game id can be given "
            + "in this mode")
    private boolean create;

    @Option(name = "g", longName = "game", hasArgument = true, description = "Id of the game. Must be given "
            + "if not created")
    private String gameId;

    @Option(name = "s", longName = "start", description = "starts the game with given id or the created "
            + "name")
    private boolean start;

    @Option(name = "f", longName = "finish", description = "finishes the game with given id or the created "
            + "name")
    private boolean finish;

    @Option(name = "d", longName = "describe", description = "Describes the current game")
    private boolean describe;

    @Inject
    private GameFactory gameFactory;

    @Inject
    private Session session;

    @Inject
    private ScoreSheetFactory scoreSheetFactory;

    @Inject
    @Jpa
    private Repository<ScoreSheet, UUID> scoreRepository;

    @Inject
    @Jpa
    private Repository<Game, UUID> gameRepository;

    @Transactional
    @JpaUnit("ouist-jpa-unit")
    public String execute(Object object) throws Exception {
        try {
            if (!create && playersToAdd == null && !start && !describe && !finish) {
                return "No option given";
            }
            if (create && gameId != null) {
                return "Cannot create new game and specify game id at the same time";
            }
            String toReturn = "";
            Game game;
            if (create) {
                game = createGame();
                toReturn += "Game created with id " + game.getGameId() + "\n";
            } else {
                if(gameId == null){
                    UUID gameId = session.getCurrentGameId();
                    if (gameId == null) {
                        return "No game id specified, or option c or game in session";
                    }
                }
                game = gameRepository.load(UUID.fromString(gameId));
                if (game == null) {
                    return "No game with id " + gameId + " exists";
                }
            }
            session.setCurrentGameId(game.getGameId());
            if (playersToAdd != null) {
                String[] players;
                players = playersToAdd.split(",");
                for (String p : players) {
                    game.addPlayer(new PlayerNickName(p.trim()));
                    toReturn += "Added player " + p + "\n";
                }
            }
            if (start) {
                game.startGame();
                toReturn += "Game started\n";
                toReturn += game.getCurrentRound().getDealer() + " : please deal " + game.getCurrentTrickAmount
                        () + " cards to each player\n";
                toReturn += "First player to bet : " + game.getPlayerToBet(game.getCurrentRound()) + "\n";
            }
            if (describe) {
                toReturn += "players :\n";
                for (Chair chair : game.getChairs()) {
                    toReturn += chair.getPlayer().getNickname() + " ";
                }
                toReturn += "\n";
                GameState state = game.getGameState();
                if(state == GameState.READY || state == GameState.BETTING || state == GameState.LAST_BET){
                    toReturn += "Dealer is " + game.getCurrentRound().getDealer() + "\n";
                    toReturn += "Player to bet : " + game.getPlayerToBet(game.getCurrentRound()) + "\n";
                }else if(state == GameState.FIRST_PLAY || state == GameState.PLAYING){
                    toReturn += "Dealer is " + game.getCurrentRound().getDealer() + "\n";
                    toReturn += "Playing trinck nb " + game.getCurrentRound().getPlayedTricks().size() + " "
                            + "out of " + game.getCurrentTrickAmount();
                }else if(state == GameState.NEW){
                    toReturn += "Game has not started\n";
                }else{
                    toReturn += "Game is finished";
                }
            }
            if(finish){
                game.finish();
                gameRepository.save(game);
            }
            return toReturn;
        }catch(Exception e){
            logger.error("OMG !!", e);
            throw e;
        }
    }

    private Game createGame() {
        Game game = gameFactory.createGame();
        ScoreSheet sheet = scoreSheetFactory.createScoreSheet();
        game.setScoreSheetId(sheet.getEntityId());
        gameRepository.persist(game);
        scoreRepository.persist(sheet);
        return game;
    }
}
