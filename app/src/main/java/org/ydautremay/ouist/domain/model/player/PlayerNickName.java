package org.ydautremay.ouist.domain.model.player;

import javax.persistence.Embeddable;

import org.seedstack.business.domain.BaseValueObject;

/**
 * Created by dautremayy on 25/01/2016.
 */
@Embeddable
public class PlayerNickName extends BaseValueObject {

    private String nickname;

    PlayerNickName() {
    }

    public PlayerNickName(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    private void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String toString(){
        return nickname;
    }
}
