package org.ydautremay.ouist.domain.model.game.state;

import org.ydautremay.ouist.domain.model.game.Game;
import org.ydautremay.ouist.domain.model.game.exceptions.CannotChangePlayersException;
import org.ydautremay.ouist.domain.model.game.exceptions.GameActionException;
import org.ydautremay.ouist.domain.model.game.GameState;

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
    NEW_ROUND {
        @Override
        public void checkActionState(Game game) throws GameActionException {
            if(game.getGameState() != GameState.READY){
                throw new CannotChangePlayersException("Cannot change players because game is started");
            }
        }
    },
    NEW_CONTRACT {
        @Override
        public void checkActionState(Game game) throws GameActionException {

        }
    },
    NEW_DEAL {
        @Override
        public void checkActionState(Game game) throws GameActionException {

        }
    },
    NEW_TRICK {
        @Override
        public void checkActionState(Game game) throws GameActionException {

        }
    };

    public abstract void checkActionState(Game game) throws GameActionException;
}
