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
public class ReadyGsoTest {

    private GameStateOperations gso = new ReadyGso();

    private Game game = Mockito.mock(Game.class);

    @Test
    public void bets_done() throws GameStateChangeException {
        assertThat(gso.betsDone(game)).isEqualTo(GameState.DEALT);
    }

    @Test
    public void finish() throws GameStateChangeException {
        assertThat(gso.finish(game)).isEqualTo(GameState.FINISHED);
    }

    @Test(expected = GameStateChangeException.class)
    public void deal_played() throws GameStateChangeException {
        gso.dealPlayed(game);
    }

    @Test(expected = GameStateChangeException.class)
    public void ready() throws GameStateChangeException {
        gso.ready(game);
    }
}
