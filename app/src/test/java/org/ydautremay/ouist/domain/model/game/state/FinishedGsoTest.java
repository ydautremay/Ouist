package org.ydautremay.ouist.domain.model.game.state;

import org.junit.Test;
import org.mockito.Mockito;
import org.ydautremay.ouist.domain.model.game.Game;
import org.ydautremay.ouist.domain.model.game.exceptions.GameStateChangeException;

/**
 * Created by dautremayy on 22/02/2016.
 */
public class FinishedGsoTest {

    private GameStateOperations gso = new FinishedGso();

    private Game game = Mockito.mock(Game.class);

    @Test(expected = GameStateChangeException.class)
    public void bets_done() throws GameStateChangeException {
        gso.play(game);
    }

    @Test(expected = GameStateChangeException.class)
    public void finish() throws GameStateChangeException {
        gso.finish(game);
    }

    @Test(expected = GameStateChangeException.class)
    public void deal_played() throws GameStateChangeException {
        gso.finish(game);
    }

    @Test(expected = GameStateChangeException.class)
    public void ready() throws GameStateChangeException {
        gso.ready(game);
    }
}
