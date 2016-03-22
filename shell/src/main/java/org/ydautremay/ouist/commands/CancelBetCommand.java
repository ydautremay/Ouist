package org.ydautremay.ouist.commands;

import java.util.UUID;

import javax.inject.Inject;

import org.seedstack.business.domain.Repository;
import org.seedstack.jpa.Jpa;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.spi.command.Command;
import org.seedstack.seed.spi.command.CommandDefinition;
import org.seedstack.seed.spi.command.Option;
import org.seedstack.seed.transaction.Transactional;
import org.ydautremay.ouist.application.Session;
import org.ydautremay.ouist.domain.model.game.Game;
import org.ydautremay.ouist.domain.model.game.GameState;

/**
 * Created by dautremayy on 22/02/2016.
 */
@CommandDefinition(scope = "ouist", name = "cancel-bet", description = "Cancels bets")
public class CancelBetCommand implements Command<String> {

    @Option(name="a", longName = "all", description = "Cancels all the plays")
    private boolean all;

    @Inject
    private Session session;

    @Inject
    @Jpa
    private Repository<Game, UUID> gameRepository;

    @Override
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
        GameState state = game.getGameState();
        String toReturn = "";
        if(all){
            while(state != GameState.READY){
                state = game.cancelContract();
            }
        }else{
            state = game.cancelContract();
            toReturn = "Bet canceled\n";
        }
        gameRepository.save(game);
        if(state == GameState.READY){
            toReturn += "All Bets cancelled for this round\n";
        }
        toReturn += "Next player to bet : " + game.getPlayerToBet(game.getCurrentRound());
        return toReturn;
    }
}
