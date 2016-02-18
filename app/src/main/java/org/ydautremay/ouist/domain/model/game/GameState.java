package org.ydautremay.ouist.domain.model.game;

import org.ydautremay.ouist.domain.model.game.exceptions.GameStateChangeException;
import org.ydautremay.ouist.domain.model.game.state.DealtRso;
import org.ydautremay.ouist.domain.model.game.state.FinishedRso;
import org.ydautremay.ouist.domain.model.game.state.GameStateOperations;
import org.ydautremay.ouist.domain.model.game.state.NewRso;
import org.ydautremay.ouist.domain.model.game.state.ReadyRso;

/**
 * Created by dautremayy on 17/02/2016.
 */
public enum GameState implements GameStateOperations {
    NEW(new NewRso()),
    READY(new ReadyRso()),
    DEALT(new DealtRso()),
    FINISHED(new FinishedRso());

    private final GameStateOperations operations;

   GameState(GameStateOperations operations) {
        this.operations = operations;
    }

    @Override
    public GameState ready(Game game) throws GameStateChangeException {
        return operations.ready(game);
    }

    @Override
    public GameState betsDone(Game game) throws GameStateChangeException{
        return operations.betsDone(game);
    }

    @Override
    public GameState dealPlayed(Game game) throws GameStateChangeException{
        return operations.dealPlayed(game);
    }

    @Override
    public GameState finish(Game game) throws GameStateChangeException{
        return operations.finish(game);
    }

}