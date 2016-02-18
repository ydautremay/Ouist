package org.ydautremay.ouist.domain.model.game;

import javax.persistence.Embeddable;

import org.seedstack.business.domain.BaseEntity;

import org.ydautremay.ouist.domain.model.player.PlayerNickName;

/**
 * Created by dautremayy on 17/02/2016.
 */
@Embeddable
public class Chair extends BaseEntity<PlayerNickName> {

    private PlayerNickName player;

    Chair(){}

    Chair(PlayerNickName player) {
        this.player = player;
    }

    @Override
    public PlayerNickName getEntityId() {
        return player;
    }

    public PlayerNickName getPlayer() {
        return player;
    }

}
