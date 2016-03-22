package org.ydautremay.ouist.domain.model.game.state;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ydautremay.ouist.domain.model.game.Chair;
import org.ydautremay.ouist.domain.model.game.Game;
import org.ydautremay.ouist.domain.model.game.GameState;
import org.ydautremay.ouist.domain.model.game.exceptions.GameStateChangeException;

/**
 * Created by dautremayy on 22/02/2016.
 */
public class NewGsoTest {

    private GameStateOperations gso;

    private Game game;

    @Before
    public void before()throws Exception{
        gso = new NewGso();
        Constructor<Game> cons = Game.class.getDeclaredConstructor();
        cons.setAccessible(true);
        game = cons.newInstance();
    }

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
        gso.play(game);
    }

    @Test
    public void ready() throws GameStateChangeException {
        List<Chair> chairs = new ArrayList<>();
        chairs.add(mock(Chair.class));
        setInternalState(game, "chairs", chairs);
        assertThat(gso.ready(game)).isEqualTo(GameState.READY);
    }

    @Test(expected = GameStateChangeException.class)
    public void ready_fails_if_no_chair_in_game() throws GameStateChangeException {
        gso.ready(game);
    }
}
