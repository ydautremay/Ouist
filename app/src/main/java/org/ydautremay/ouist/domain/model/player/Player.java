package org.ydautremay.ouist.domain.model.player;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.seedstack.business.domain.BaseAggregateRoot;

/**
 * Created by dautremayy on 22/01/2016.
 */
@Entity
public class Player extends BaseAggregateRoot<PlayerNickName> {

    @EmbeddedId
    private PlayerNickName nickname;

    private String firstName;

    private String lastName;

    Player() {
    }

    Player(PlayerNickName nickname) {
        this.nickname = nickname;
    }

    public PlayerNickName getNickname() {
        return nickname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public PlayerNickName getEntityId() {
        return nickname;
    }

}
