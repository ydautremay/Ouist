package org.ydautremay.ouist.domain.model.game.exceptions;

/**
 * Created by dautremayy on 26/02/2016.
 */
public class CannotBetException extends GameActionException {
    public CannotBetException() {
    }

    public CannotBetException(String message) {
        super(message);
    }

    public CannotBetException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotBetException(Throwable cause) {
        super(cause);
    }

    public CannotBetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
