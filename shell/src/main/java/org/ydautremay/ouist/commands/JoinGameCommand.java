package org.ydautremay.ouist.commands;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.shiro.SecurityUtils;
import org.seedstack.business.domain.Repository;
import org.seedstack.jpa.Jpa;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.security.principals.SimplePrincipalProvider;
import org.seedstack.seed.spi.command.Argument;
import org.seedstack.seed.spi.command.Command;
import org.seedstack.seed.spi.command.CommandDefinition;
import org.seedstack.seed.spi.command.Option;
import org.seedstack.seed.transaction.Transactional;
import org.ydautremay.ouist.domain.model.game.Game;

import org.ydautremay.ouist.application.Session;
import org.ydautremay.ouist.domain.model.game.GameState;
import org.ydautremay.ouist.domain.model.game.exceptions.GameActionException;
import org.ydautremay.ouist.domain.model.player.PlayerNickName;

/**
 * Created by dautremayy on 03/02/2016.
 */
@CommandDefinition(scope = "ouist", name = "join", description = "join a Game")
public class JoinGameCommand implements Command<String> {

    @Argument(index = 0, description = "ID of the game to join", mandatory = false)
    private String id;

    @Option(name = "l", longName = "list", description = "lists all games")
    private boolean list;
    @Inject
    private Session session;

    @Inject
    private EntityManager entityManager;

    @Jpa
    @Inject
    private Repository<Game, UUID> gameRepository;

    @Transactional
    @JpaUnit("ouist-jpa-unit")
    public String execute(Object object) throws Exception {
        UUID id = session.getCurrentGameId();
        if(list){
            List<Game> games = entityManager.createQuery("SELECT g from Game g WHERE g.gameState <> :state",
                    Game.class).setParameter("state", GameState.FINISHED).getResultList();
            String toReturn = "";
            for(Game game : games){
                toReturn += game.getEntityId() + "\n";
            }
            return toReturn;
        }
        if (id == null) {
            return displayCurrentGame(id);
        }
        return joinGame(id);
    }

    private String joinGame(UUID id) {
        Game game = gameRepository.load(id);
        if (game == null) {
            return "The game with id " + id + " does not exist or is not running";
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
