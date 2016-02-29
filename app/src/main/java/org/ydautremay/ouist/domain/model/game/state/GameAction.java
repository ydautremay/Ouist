package org.ydautremay.ouist.domain.model.game.state;

import org.ydautremay.ouist.domain.model.game.Game;
import org.ydautremay.ouist.domain.model.game.GameState;
import org.ydautremay.ouist.domain.model.game.exceptions.CannotChangePlayersException;
import org.ydautremay.ouist.domain.model.game.exceptions.GameActionException;

/**
 * Created by dautremayy on 18/02/2016.
 */
public enum GameAction {
    CHANGE_PLAYERS {
        @Override
        public void checkActionState(Game game) throws CannotChangePlayersException {
            if(game.getGameState() != GameState.NEW){
                throw new CannotChangePlayersException("Cannot change players because game is started");
            }
        }
    },
    NEW_CONTRACT {
        @Override
        public void checkActionState(Game game) throws GameActionException {
            if(game.getGameState() != GameState.READY && game.getGameState() != GameState.LAST_BET){
                throw new GameActionException("Cannot get in a new round");
            }
        }
    },
    NEW_TRICK {
        @Override
        public void checkActionState(Game game) throws GameActionException {
            if(game.getGameState() != GameState.BETS_DONE){
                throw new GameActionException("A new trick can only go when game is dealt");
            }
        }
    };

    public abstract void checkActionState(Game game) throws GameActionException;
}
