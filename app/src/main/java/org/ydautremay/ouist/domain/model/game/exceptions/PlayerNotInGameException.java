package org.ydautremay.ouist.domain.model.game.exceptions;

/**
 * Created by dautremayy on 15/02/2016.
 */
public class PlayerNotInGameException extends GameActionException {
    public PlayerNotInGameException() {
    }

    public PlayerNotInGameException(String message) {
        super(message);
    }

    public PlayerNotInGameException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayerNotInGameException(Throwable cause) {
        super(cause);
    }

    public PlayerNotInGameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
