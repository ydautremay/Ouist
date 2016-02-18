package org.ydautremay.ouist.domain.model.game.exceptions;

/**
 * Created by dautremayy on 18/02/2016.
 */
public class GameStateChangeException extends Exception {
    public GameStateChangeException() {
    }

    public GameStateChangeException(String message) {
        super(message);
    }

    public GameStateChangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameStateChangeException(Throwable cause) {
        super(cause);
    }

    public GameStateChangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
