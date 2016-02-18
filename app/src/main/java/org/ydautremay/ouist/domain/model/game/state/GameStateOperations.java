package org.ydautremay.ouist.domain.model.game.state;

import org.ydautremay.ouist.domain.model.game.Game;
import org.ydautremay.ouist.domain.model.game.exceptions.GameStateChangeException;
import org.ydautremay.ouist.domain.model.game.GameState;

/**
 * Created by dautremayy on 18/02/2016.
 */
public interface GameStateOperations {

    GameState ready(Game game) throws GameStateChangeException;

    GameState betsDone(Game game) throws GameStateChangeException;

    GameState dealPlayed(Game game) throws GameStateChangeException;

    GameState finish(Game game) throws GameStateChangeException;
}
