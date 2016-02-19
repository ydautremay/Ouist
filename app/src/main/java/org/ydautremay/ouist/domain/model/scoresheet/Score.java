package org.ydautremay.ouist.domain.model.scoresheet;

import javax.persistence.Embeddable;

import org.seedstack.business.domain.BaseValueObject;
import org.ydautremay.ouist.domain.model.player.PlayerNickName;

/**
 * Created by dautremayy on 22/01/2016.
 */
@Embeddable
public class Score extends BaseValueObject {

    private PlayerNickName player;

    private int value;

    Score() {
    }

    public Score(PlayerNickName player, int value) {
        this.player = player;
        this.value = value;
    }

    public PlayerNickName getPlayer() {
        return player;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
