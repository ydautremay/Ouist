package org.ydautremay.ouist.domain.model.game;

import org.ydautremay.ouist.domain.model.game.exceptions.GameStateChangeException;
import org.ydautremay.ouist.domain.model.game.state.BettingGso;
import org.ydautremay.ouist.domain.model.game.state.FinishedGso;
import org.ydautremay.ouist.domain.model.game.state.FirstPlayGso;
import org.ydautremay.ouist.domain.model.game.state.GameStateOperations;
import org.ydautremay.ouist.domain.model.game.state.LastBetGso;
import org.ydautremay.ouist.domain.model.game.state.NewGso;
import org.ydautremay.ouist.domain.model.game.state.PlayingGso;
import org.ydautremay.ouist.domain.model.game.state.ReadyGso;

/**
 * Created by dautremayy on 17/02/2016.
 */
public enum GameState implements GameStateOperations {
    NEW(new NewGso()),
    READY(new ReadyGso()),
    BETTING(new BettingGso()),
    LAST_BET(new LastBetGso()),
    FIRST_PLAY(new FirstPlayGso()),
    PLAYING(new PlayingGso()),
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
    public GameState bet(Game game) throws GameStateChangeException {
        return operations.bet(game);
    }

    @Override
    public GameState cancelBet(Game game) throws GameStateChangeException {
        return operations.cancelBet(game);
    }

    @Override
    public GameState play(Game game) throws GameStateChangeException {
        return operations.play(game);
    }

    @Override
    public GameState cancelPlay(Game game) throws GameStateChangeException {
        return operations.cancelPlay(game);
    }

    @Override
    public GameState finish(Game game) throws GameStateChangeException {
        return operations.finish(game);
    }
}