package org.ydautremay.ouist.domain.model.game.state;

import org.ydautremay.ouist.domain.model.game.Game;
import org.ydautremay.ouist.domain.model.game.GameState;
import org.ydautremay.ouist.domain.model.game.Round;
import org.ydautremay.ouist.domain.model.game.exceptions.GameStateChangeException;

/**
 * Created by dautremayy on 18/02/2016.
 */
public class PlayingGso implements GameStateOperations {
    @Override
    public GameState ready(Game game) throws GameStateChangeException {
        throw new GameStateChangeException();
   }

    @Override
    public GameState bet(Game game) throws GameStateChangeException {
        throw new GameStateChangeException();
    }

    @Override
    public GameState cancelBet(Game game) throws GameStateChangeException {
        throw new GameStateChangeException();
    }

    @Override
    public GameState play(Game game) throws GameStateChangeException {
        Round round = game.getCurrentRound();
        int nbPlays = round.getPlayedTricks().size();
        if(nbPlays == game.getCurrentTrickAmount() - 1) {
            return GameState.READY;
        }
        return GameState.PLAYING;

    }

    @Override
    public GameState cancelPlay(Game game) throws GameStateChangeException {
        Round round = game.getCurrentRound();
        int nbPlays = round.getPlayedTricks().size();
        if(nbPlays == 1) {
            return GameState.FIRST_PLAY;
        }
        return GameState.PLAYING;
    }


    @Override
    public GameState finish(Game game) throws GameStateChangeException {
        throw new GameStateChangeException();
    }
}
