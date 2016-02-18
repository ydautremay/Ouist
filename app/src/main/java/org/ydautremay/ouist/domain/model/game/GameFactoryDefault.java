package org.ydautremay.ouist.domain.model.game;

import java.util.Date;

import javax.inject.Inject;

import org.seedstack.business.domain.BaseFactory;
import org.seedstack.business.domain.identity.IdentityService;

/**
 * Created by dautremayy on 25/01/2016.
 */
public class GameFactoryDefault extends BaseFactory<Game> implements GameFactory {

    @Inject
    IdentityService identityService;

    @Override
    public Game createGame() {
        Game game = new Game(new Date());
        identityService.identify(game);
        return game;
    }
}
