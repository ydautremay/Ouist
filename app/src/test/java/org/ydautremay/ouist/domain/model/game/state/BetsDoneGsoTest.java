package org.ydautremay.ouist.domain.model.game.state;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.mockito.Mockito;
import org.ydautremay.ouist.domain.model.game.Game;
import org.ydautremay.ouist.domain.model.game.GameState;
import org.ydautremay.ouist.domain.model.game.exceptions.GameStateChangeException;

/**
 * Created by dautremayy on 22/02/2016.
 */
public class BetsDoneGsoTest {

    private GameStateOperations gso = new BetsDoneGso();

    private Game game = Mockito.mock(Game.class);

    @Test(expected = GameStateChangeException.class)
    public void bets_done() throws GameStateChangeException {
        gso.betsDone(game);
    }

    @Test(expected = GameStateChangeException.class)
    public void finish() throws GameStateChangeException {
        gso.finish(game);
    }

    @Test
    public void deal_played() throws GameStateChangeException {
        assertThat(gso.dealPlayed(game)).isEqualTo(GameState.READY);
    }

    @Test(expected = GameStateChangeException.class)
    public void ready() throws GameStateChangeException {
        gso.ready(game);
    }
}
