package org.ydautremay.ouist.commands;

import java.util.UUID;

import javax.inject.Inject;

import org.seedstack.seed.spi.command.Command;
import org.seedstack.seed.spi.command.CommandDefinition;
import org.seedstack.seed.spi.command.Option;

import org.ydautremay.ouist.application.RunningGamesRegistry;
import org.ydautremay.ouist.application.Session;
import org.ydautremay.ouist.domain.model.game.Chair;
import org.ydautremay.ouist.domain.model.game.Game;
import org.ydautremay.ouist.domain.model.game.GameFactory;
import org.ydautremay.ouist.domain.model.player.PlayerNickName;

/**
 * Created by dautremayy on 29/01/2016.
 */
@CommandDefinition(scope = "ouist", name = "game", description = "Create a new Game")
public class GameCommand implements Command<String> {

    @Option(name = "a", longName = "add-player", hasArgument = true, description = "Players can be added "
            + "separated by ','")
    private String playersToAdd;

    @Option(name = "c", longName = "create", description = "Creates a new game. No game id can be given "
            + "in this mode")
    private boolean create;

    @Option(name = "g", longName = "game", hasArgument = true, description = "Id of the game. Must be given "
            + "if not created")
    private String gameId;

    @Option(name = "s", longName = "start", description = "starts the game with given id or the created "
            + "name")
    private boolean start;

    @Option(name = "d", longName = "describe", description = "Describes the current game")
    private boolean describe;

    @Inject
    private RunningGamesRegistry runningGamesRegistry;

    @Inject
    private GameFactory gameFactory;

    @Inject
    private Session session;

    public String execute(Object object) throws Exception {
        if (!create && playersToAdd == null && !start) {
            return "No option given";
        }
        if (create && gameId != null) {
            return "Cannot create new game and specify game id at the same time";
        }
        String toReturn = "";
        Game game;
        if (create) {
            game = createGame();
            toReturn += "Game created with id " + game.getGameId() + "\n";
        } else {
            game = runningGamesRegistry.getRunningGame(UUID.fromString(gameId));
            if (game == null) {
                return "No game with id " + gameId + " is running";
            }
        }
        session.setCurrentGameId(game.getGameId());
        if (playersToAdd != null) {
            String[] players;
            players = playersToAdd.split(",");
            for (String p:players) {
                game.addPlayer(new PlayerNickName(p.trim()));
                toReturn += "Added player " + p + "\n";
            }
        }
        if (start) {
            game.startGame();
            toReturn += "Game started\n";
        }
        if(describe){
            for (Chair chair:game.getChairs()) {
                toReturn += chair.getPlayer().getNickname() + " ";
            }
        }
        return toReturn;
    }

    private Game createGame() {
        Game game = gameFactory.createGame();
        runningGamesRegistry.addRunningGame(game);
        return game;
    }
}
