package org.ydautremay.ouist.domain.model.game.exceptions;

/**
 * Created by dautremayy on 04/02/2016.
 */
public class CannotStartGameException extends GameStateChangeException {
    public CannotStartGameException() {
    }

    public CannotStartGameException(String message) {
        super(message);
    }

    public CannotStartGameException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotStartGameException(Throwable cause) {
        super(cause);
    }

    public CannotStartGameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
