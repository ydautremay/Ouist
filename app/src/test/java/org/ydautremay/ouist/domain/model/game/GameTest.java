package org.ydautremay.ouist.domain.model.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import org.ydautremay.ouist.domain.model.game.exceptions.GameActionException;
import org.ydautremay.ouist.domain.model.game.exceptions.GameStateChangeException;
import org.ydautremay.ouist.domain.model.player.PlayerNickName;

/**
 * Created by dautremayy on 22/02/2016.
 */
public class GameTest {

    @Test
    public void nominalGame()throws Exception{
        Game underTest = new Game();
        UUID id = UUID.randomUUID();
        Whitebox.setInternalState(underTest, "gameId", id);
        assertThat(underTest.getGameId()).isEqualTo(id);
        assertThat(underTest.getEntityId()).isEqualTo(id);
        assertThat(underTest.getGameState()).isEqualTo(GameState.NEW);
        assertThat(Whitebox.getInternalState(underTest, "ascending")).isEqualTo(false);

        PlayerNickName player1 = new PlayerNickName("toto");
        PlayerNickName player2 = new PlayerNickName("titi");
        PlayerNickName player3 = new PlayerNickName("tata");
        PlayerNickName player4 = new PlayerNickName("tutu");
        PlayerNickName player5 = new PlayerNickName("toRemove");
        underTest.addPlayer(player1);
        underTest.addPlayer(player2);
        underTest.addPlayer(player3);
        underTest.addPlayer(player4);
        underTest.addPlayer(player5);
        underTest.removePlayer(player5);
        assertThat(underTest.getChairs().stream().noneMatch(c -> c.getPlayer().equals(player5))).isTrue();
        underTest.startGame();
        assertThat(underTest.getRounds()).hasSize(1);

        assertThat(Whitebox.getInternalState(underTest, "ascending")).isEqualTo(false);
        assertThat(Whitebox.getInternalState(underTest, "maxTricks")).isEqualTo(5);
        assertThat(underTest.getCurrentTrickAmount()).isEqualTo(5);

        GameState gameState = underTest.getGameState();

        assertThat(gameState).isEqualTo( GameState.READY);

        gameState = underTest.nextContract(0);
        assertThat(gameState).isEqualTo( GameState.READY);
        gameState = underTest.nextContract(0);
        assertThat(gameState).isEqualTo( GameState.READY);
        gameState = underTest.nextContract(0);
        assertThat(gameState).isEqualTo( GameState.READY);
        gameState = underTest.nextContract(0);
        assertThat(gameState).isEqualTo( GameState.DEALT);
        assertThat(underTest.getRounds()).hasSize(1);

        gameState = underTest.nextTrick(player1);
        assertThat(gameState).isEqualTo( GameState.DEALT);
        gameState = underTest.nextTrick(player2);
        assertThat(gameState).isEqualTo( GameState.DEALT);
        gameState = underTest.nextTrick(player1);
        assertThat(gameState).isEqualTo( GameState.DEALT);
        gameState =  underTest.nextTrick(player3);
        assertThat(gameState).isEqualTo( GameState.DEALT);
        gameState = underTest.nextTrick(player4);
        assertThat(gameState).isEqualTo( GameState.READY);
        assertThat(underTest.getRounds()).hasSize(2);

        assertThat(underTest.getCurrentTrickAmount()).isEqualTo(4);

        Whitebox.setInternalState(underTest, "currentTrickAmount", 1);

        gameState = underTest.nextContract(0);
        assertThat(gameState).isEqualTo( GameState.READY);
        gameState = underTest.nextContract(0);
        assertThat(gameState).isEqualTo( GameState.READY);
        gameState = underTest.nextContract(0);
        assertThat(gameState).isEqualTo( GameState.READY);
        gameState = underTest.nextContract(0);
        assertThat(gameState).isEqualTo( GameState.DEALT);

        gameState = underTest.nextTrick(player1);
        assertThat(gameState).isEqualTo( GameState.READY);
        assertThat(underTest.getRounds()).hasSize(3);

        assertThat(underTest.getCurrentTrickAmount()).isEqualTo(2);
        assertThat(Whitebox.getInternalState(underTest, "ascending")).isEqualTo(true);

        gameState = underTest.finish();
        assertThat(gameState).isEqualTo(GameState.FINISHED);
    }

    @Test(expected = GameActionException.class)
    public void addPlayer_throws_exception_in_wrong_state() throws GameActionException {
        Game underTest = new Game();
        Whitebox.setInternalState(underTest, "gameId", UUID.randomUUID());
        Whitebox.setInternalState(underTest, "gameState", GameState.READY);
        underTest.addPlayer(new PlayerNickName("player"));
    }

    @Test(expected = GameActionException.class)
    public void removePlayer_throws_exception_in_wrong_state() throws GameActionException {
        Game underTest = new Game();
        Whitebox.setInternalState(underTest, "gameId", UUID.randomUUID());
        underTest.addPlayer(new PlayerNickName("player"));
        Whitebox.setInternalState(underTest, "gameState", GameState.READY);
        underTest.removePlayer(new PlayerNickName("player"));
    }

    @Test(expected = GameActionException.class)
    public void nextContract_throws_exception_in_wrong_state()
            throws GameActionException, GameStateChangeException {
        Game underTest = new Game();
        Whitebox.setInternalState(underTest, "gameId", UUID.randomUUID());
        underTest.nextContract(0);
    }

    @Test(expected = GameActionException.class)
    public void nextContract_throws_exception_in_wrong_state2()
            throws GameActionException, GameStateChangeException {
        Game underTest = new Game();
        Whitebox.setInternalState(underTest, "gameId", UUID.randomUUID());
        underTest.addPlayer(new PlayerNickName("player"));
        underTest.startGame();
        underTest.nextContract(0);
        underTest.nextContract(0);
    }

    @Test(expected = GameActionException.class)
    public void nextContract_throws_exception_if_last_bet_equals_nb_tricks()
            throws GameActionException, GameStateChangeException {
        Game underTest = new Game();
        Whitebox.setInternalState(underTest, "gameId", UUID.randomUUID());
        underTest.addPlayer(new PlayerNickName("player"));
        underTest.startGame();
        underTest.nextContract(22);
    }

    @Test(expected = GameActionException.class)
    public void nextTrick_throws_exception_in_wrong_state()
            throws GameActionException, GameStateChangeException {
        Game underTest = new Game();
        Whitebox.setInternalState(underTest, "gameId", UUID.randomUUID());
        underTest.nextTrick(new PlayerNickName(""));
    }

    @Test(expected = GameActionException.class)
    public void nextTrick_throws_exception_in_wrong_state2()
            throws GameActionException, GameStateChangeException {
        Game underTest = new Game();
        Whitebox.setInternalState(underTest, "gameId", UUID.randomUUID());
        underTest.addPlayer(new PlayerNickName("player"));
        underTest.startGame();
        underTest.nextContract(0);
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
        underTest.nextTrick(new PlayerNickName("player"));
    }

}
