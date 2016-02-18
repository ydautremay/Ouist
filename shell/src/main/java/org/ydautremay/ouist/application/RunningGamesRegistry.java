package org.ydautremay.ouist.application;

import java.util.Collection;
import java.util.UUID;

import org.seedstack.business.Service;
import org.ydautremay.ouist.domain.model.game.Game;

/**
 * Created by dautremayy on 03/02/2016.
 */
@Service
public interface RunningGamesRegistry {

    Game getRunningGame(UUID gameId);

    Collection<Game> listRunningGames();

    void addRunningGame(Game game);

    void removeRunningGame(UUID gameId);
}
