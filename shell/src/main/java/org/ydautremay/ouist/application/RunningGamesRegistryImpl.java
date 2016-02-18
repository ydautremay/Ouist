package org.ydautremay.ouist.application;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;

import org.ydautremay.ouist.domain.model.game.Game;

/**
 * Created by dautremayy on 03/02/2016.
 */
@Singleton
public class RunningGamesRegistryImpl implements RunningGamesRegistry {

    private Map<UUID, Game> runningGames = new ConcurrentHashMap<UUID, Game>();

    public Game getRunningGame(UUID gameId) {
        return runningGames.get(gameId);
    }

    public Collection<Game> listRunningGames() {
        return Collections.unmodifiableCollection(runningGames.values());
    }

    public void addRunningGame(Game game) {
        runningGames.put(game.getGameId(), game);
    }

    public void removeRunningGame(UUID gameId) {
        runningGames.remove(gameId);
    }
}