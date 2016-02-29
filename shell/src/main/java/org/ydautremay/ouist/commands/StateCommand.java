package org.ydautremay.ouist.commands;

import java.util.UUID;

import javax.inject.Inject;

import org.seedstack.business.domain.Repository;
import org.seedstack.jpa.Jpa;
import org.seedstack.seed.spi.command.Command;
import org.seedstack.seed.spi.command.CommandDefinition;
import org.ydautremay.ouist.application.Session;
import org.ydautremay.ouist.domain.model.game.Game;

/**
 * Created by dautremayy on 22/02/2016.
 */
@CommandDefinition(scope = "ouist", name = "state", description = "Show state")
public class StateCommand implements Command<String> {

    @Inject
    private Session session;

    @Inject
    @Jpa
    private Repository<Game, UUID> gameRepository;

    @Override
    public String execute(Object object) throws Exception {
        UUID gameId = session.getCurrentGameId();
        if(gameId == null){
            return "You need to join a game. Please use the join command";
        }
        Game game = gameRepository.load(gameId);
        if(game == null){
            return "The game you joined does not exist anymore";
        }
        return "";
    }
}
