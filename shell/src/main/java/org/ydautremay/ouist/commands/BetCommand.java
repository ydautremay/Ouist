package org.ydautremay.ouist.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.seedstack.seed.spi.command.Argument;
import org.seedstack.seed.spi.command.Command;
import org.seedstack.seed.spi.command.CommandDefinition;
import org.ydautremay.ouist.application.Session;
import org.ydautremay.ouist.domain.model.game.Game;

import org.ydautremay.ouist.application.RunningGamesRegistry;

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
    private RunningGamesRegistry runningGamesRegistry;

    public String execute(Object object) throws Exception {
        UUID gameId = session.getCurrentGameId();
        if(gameId == null){
            return "You need to join a game. Please use the join command";
        }
        Game game = runningGamesRegistry.getRunningGame(gameId);
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
        }
        int sum = bets.stream().reduce(0, Integer::sum);
        return null;
    }
}
