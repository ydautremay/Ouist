package org.ydautremay.ouist.domain.model.game.state;

import org.ydautremay.ouist.domain.model.game.Game;
import org.ydautremay.ouist.domain.model.game.GameState;
import org.ydautremay.ouist.domain.model.game.Round;
import org.ydautremay.ouist.domain.model.game.exceptions.GameStateChangeException;

/**
 * Created by dautremayy on 18/02/2016.
 */
public class BettingGso implements GameStateOperations {
    @Override
    public GameState ready(Game game) throws GameStateChangeException {
        throw new GameStateChangeException();
   }

    @Override
    public GameState bet(Game game) throws GameStateChangeException {
        Round round = game.getCurrentRound();
        int nbContracts = round.getContracts().size();
        if(nbContracts == game.getChairs().size() - 2){
            return GameState.LAST_BET;
        }else if (nbContracts < game.getChairs().size() - 2){
            return GameState.BETTING;
        }else{
            throw new GameStateChangeException();
        }
    }

    @Override
    public GameState cancelBet(Game game) throws GameStateChangeException {
        Round round = game.getCurrentRound();
        int nbContracts = round.getContracts().size();
        if(nbContracts == 1){
            return GameState.READY;
        }else if (nbContracts > 1){
            return GameState.BETTING;
        }else{
            throw new GameStateChangeException();
        }
    }

    @Override
    public GameState play(Game game) throws GameStateChangeException {
        throw new GameStateChangeException();
    }

    @Override
    public GameState cancelPlay(Game game) throws GameStateChangeException {
        throw new GameStateChangeException();
    }

    @Override
    public GameState finish(Game game) throws GameStateChangeException {
        throw new GameStateChangeException();
    }
}
