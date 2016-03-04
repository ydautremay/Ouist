package org.ydautremay.ouist.domain.model.game.state;

import org.ydautremay.ouist.domain.model.game.Game;
import org.ydautremay.ouist.domain.model.game.GameState;
import org.ydautremay.ouist.domain.model.game.exceptions.GameStateChangeException;

/**
 * Created by dautremayy on 18/02/2016.
 */
public class ReadyGso implements GameStateOperations {

    @Override
    public GameState ready(Game game) throws GameStateChangeException {
        throw new GameStateChangeException();
    }

    @Override
    public GameState bet(Game game) throws GameStateChangeException {
        return GameState.BETTING;
    }

    @Override
    public GameState cancelBet(Game game) throws GameStateChangeException {
        throw new GameStateChangeException();
    }

    @Override
    public GameState play(Game game) throws GameStateChangeException {
        throw new GameStateChangeException();
    }

    @Override
    public GameState cancelPlay(Game game) throws GameStateChangeException {
        if(game.getLastRound() == null){
            throw new GameStateChangeException();
        }
        return GameState.PLAYING;

    }

    @Override
    public GameState finish(Game game) throws GameStateChangeException {
        return GameState.FINISHED;
    }
}
