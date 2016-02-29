package org.ydautremay.ouist.domain.model.game;

import org.ydautremay.ouist.domain.model.game.exceptions.GameStateChangeException;
import org.ydautremay.ouist.domain.model.game.state.BetsDoneGso;
import org.ydautremay.ouist.domain.model.game.state.FinishedGso;
import org.ydautremay.ouist.domain.model.game.state.GameStateOperations;
import org.ydautremay.ouist.domain.model.game.state.LastBetGso;
import org.ydautremay.ouist.domain.model.game.state.NewGso;
import org.ydautremay.ouist.domain.model.game.state.ReadyGso;

/**
 * Created by dautremayy on 17/02/2016.
 */
public enum GameState implements GameStateOperations {
    NEW(new NewGso()),
    READY(new ReadyGso()),
    LAST_BET(new LastBetGso()),
    BETS_DONE(new BetsDoneGso()),
    FINISHED(new FinishedGso());

    private final GameStateOperations operations;

   GameState(GameStateOperations operations) {
        this.operations = operations;
    }

    @Override
    public GameState ready(Game game) throws GameStateChangeException {
        return operations.ready(game);
    }

    @Override
    public GameState lastBet(Game game) throws GameStateChangeException{
        return operations.lastBet(game);
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