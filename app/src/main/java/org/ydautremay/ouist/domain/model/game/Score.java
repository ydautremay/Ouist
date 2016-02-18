package org.ydautremay.ouist.domain.model.game;

import org.ydautremay.ouist.domain.model.player.PlayerNickName;

/**
 * Created by dautremayy on 22/01/2016.
 */

public class Score {

    private PlayerNickName player;

    private int value;

    Score() {
    }

    public Score(PlayerNickName player) {
        this.player = player;
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
