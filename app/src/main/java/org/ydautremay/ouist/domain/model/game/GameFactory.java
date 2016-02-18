package org.ydautremay.ouist.domain.model.game;

import org.seedstack.business.domain.GenericFactory;

/**
 * Created by dautremayy on 25/01/2016.
 */
public interface GameFactory extends GenericFactory<Game> {

    Game createGame();
}
