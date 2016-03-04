package org.ydautremay.ouist.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.seedstack.business.domain.Repository;
import org.seedstack.jpa.Jpa;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.spi.command.Argument;
import org.seedstack.seed.spi.command.Command;
import org.seedstack.seed.spi.command.CommandDefinition;
import org.seedstack.seed.transaction.Transactional;
import org.ydautremay.ouist.application.Session;
import org.ydautremay.ouist.domain.model.game.Chair;
import org.ydautremay.ouist.domain.model.game.Game;
import org.ydautremay.ouist.domain.model.game.GameState;
import org.ydautremay.ouist.domain.model.game.exceptions.CannotBetException;

/**
 * Created by dautremayy on 06/02/2016.
 */
@CommandDefinition(scope = "ouist", name = "bet", description = "Register the bets")
public class BetCommand implements Command<String> {

    @Argument(name = "bets", index = 0, description = "Place bets in player order separated by ','")
    private String sBets;

    @Inject
    private Session session;

    @Inject
    @Jpa
    private Repository<Game, UUID> gameRepository;

    @Transactional
    @JpaUnit("ouist-jpa-unit")
    public String execute(Object object) throws Exception {
        UUID gameId = session.getCurrentGameId();
        if(gameId == null){
            return "You need to join a game. Please use the join command";
        }
        Game game = gameRepository.load(gameId);
        if(game == null){
            return "The game you joined does not exist anymore";
        }
        List<Integer> bets = new ArrayList<>();
        for (String sBet: sBets.split(",")) {
            Integer bet;
            try {
                bet = Integer.decode(sBet);
            }catch (NumberFormatException e){
                return "Cannot add bets. Only numbers separated by ',' accepted";
            }
            if(bet < 0){
                return "Bets can only be positive";
            }
            bets.add(bet);
        }
        List<Chair> chairs = game.getChairs();
        String toReturn = "";
        GameState state = game.getGameState();
        for (int i = 0; i <bets.size();  i++) {
            try {
                toReturn += game.getPlayerToBet(game.getCurrentRound()).getNickname() + " bets : ";
                state = game.nextContract(bets.get(i));
                 toReturn += bets.get(i) + "\n";
                if (state == GameState.LAST_BET) {
                    int forbiddenBet = game.getNextForbiddenBet();
                    toReturn += "Last player (" + game.getPlayerToBet(game.getCurrentRound()) + ") can bet anything";
                    if(forbiddenBet >= 0){
                        toReturn += " but " + forbiddenBet;
                    }
                    toReturn += "\n";
                }
                if (state == GameState.FIRST_PLAY) {
                    toReturn += "Every player has bet. Play time !";
                    break;
                }
            } catch(CannotBetException e){
                toReturn += "Last player cannot bet " + bets.get(i);
                return toReturn;
            }
        }
        if(state == GameState.BETTING){
            toReturn += "Next player to bet : " + game.getPlayerToBet(game.getCurrentRound());
        }
        gameRepository.save(game);
        return toReturn;
    }
}
