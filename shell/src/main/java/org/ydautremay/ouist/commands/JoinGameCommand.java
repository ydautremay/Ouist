package org.ydautremay.ouist.commands;

import java.util.UUID;

import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.seedstack.seed.security.principals.SimplePrincipalProvider;
import org.seedstack.seed.spi.command.Argument;
import org.seedstack.seed.spi.command.Command;
import org.seedstack.seed.spi.command.CommandDefinition;
import org.ydautremay.ouist.domain.model.game.Game;

import org.ydautremay.ouist.application.RunningGamesRegistry;
import org.ydautremay.ouist.application.Session;
import org.ydautremay.ouist.domain.model.game.exceptions.GameActionException;
import org.ydautremay.ouist.domain.model.player.PlayerNickName;

/**
 * Created by dautremayy on 03/02/2016.
 */
@CommandDefinition(scope = "ouist", name = "join", description = "join a Game")
public class JoinGameCommand implements Command<String> {

    @Argument(index = 0, description = "ID of the game to join", mandatory = false)
    private String id;

    @Inject
    private Session session;

    @Inject
    private RunningGamesRegistry runningGamesRegistry;

    public String execute(Object object) throws Exception {
        UUID id = session.getCurrentGameId();
        if (id == null) {
            return displayCurrentGame(id);
        }
        return joinGame(id);
    }

    private String joinGame(UUID id) {
        Game game = runningGamesRegistry.getRunningGame(id);
        if (game == null) {
            return "The game with id " + id + " does not exist or is not running";
        }
        if (game.getGameId().equals(id)) {
            return "Already playing on game " + id;
        }
        PlayerNickName nick = new PlayerNickName(((SimplePrincipalProvider) SecurityUtils.getSubject()
                .getPrincipal()).getValue());
        if (game.getChairs().contains(nick)) {
            return "Already on game " + id;
        }
        try {
            game.addPlayer(nick);
        }
        catch (GameActionException e) {
            return "Cannot join game " + id + " because it has already started";
        }
        session.setCurrentGameId(id);
        return "Joined game " + id;
    }

    private String displayCurrentGame(UUID sessionGameId) {
        if (sessionGameId == null) {
            return "Not playing on any game";
        } else {
            return "Playing on game " + sessionGameId;
        }
    }
}
