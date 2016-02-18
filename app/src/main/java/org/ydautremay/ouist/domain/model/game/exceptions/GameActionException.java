package org.ydautremay.ouist.domain.model.game.exceptions;

/**
 * Created by dautremayy on 18/02/2016.
 */
public class GameActionException extends Exception {
    public GameActionException() {
    }

    public GameActionException(String message) {
        super(message);
    }

    public GameActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameActionException(Throwable cause) {
        super(cause);
    }

    public GameActionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
