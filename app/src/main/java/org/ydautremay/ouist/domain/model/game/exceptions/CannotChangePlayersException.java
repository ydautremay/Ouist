package org.ydautremay.ouist.domain.model.game.exceptions;

/**
 * Created by dautremayy on 04/02/2016.
 */
public class CannotChangePlayersException extends GameActionException {
    public CannotChangePlayersException() {
    }

    public CannotChangePlayersException(String message) {
        super(message);
    }

    public CannotChangePlayersException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotChangePlayersException(Throwable cause) {
        super(cause);
    }

    public CannotChangePlayersException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
