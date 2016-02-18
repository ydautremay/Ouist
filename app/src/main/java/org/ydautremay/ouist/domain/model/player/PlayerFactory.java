package org.ydautremay.ouist.domain.model.player;

import org.seedstack.business.domain.GenericFactory;

/**
 * Created by dautremayy on 25/01/2016.
 */
public interface PlayerFactory extends GenericFactory<Player> {

    Player createPlayer(String nickname);
}
