package org.ydautremay.ouist.domain.model.game.state;

import org.ydautremay.ouist.domain.model.game.Game;
import org.ydautremay.ouist.domain.model.game.GameState;
import org.ydautremay.ouist.domain.model.game.exceptions.CannotStartGameException;
import org.ydautremay.ouist.domain.model.game.exceptions.GameStateChangeException;

/**
 * Created by dautremayy on 18/02/2016.
 */
public class NewGso implements GameStateOperations {

    @Override
    public GameState ready(Game game) throws GameStateChangeException {
        if(game.getChairs().isEmpty()){
            throw new CannotStartGameException("Cannot start a game with no players");
        }
        return GameState.READY;
    }

    @Override
    public GameState betsDone(Game game) throws GameStateChangeException {
        throw new GameStateChangeException();
    }

    @Override
    public GameState dealPlayed(Game game) throws GameStateChangeException {
        throw new GameStateChangeException();
    }

    @Override
    public GameState finish(Game game) throws GameStateChangeException {
        throw new GameStateChangeException();
    }

}
