package org.ydautremay.ouist.domain.model.player;

import org.seedstack.business.domain.BaseFactory;

/**
 * Created by dautremayy on 25/01/2016.
 */
public class PlayerFactoryDefault extends BaseFactory<Player> implements PlayerFactory {

    @Override
    public Player createPlayer(String nickname) {
        PlayerNickName playerNickName = new PlayerNickName(nickname);
        return new Player(playerNickName);

    }
}
