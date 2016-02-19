package org.ydautremay.ouist.domain.model.game;

import javax.persistence.Embeddable;

import org.seedstack.business.domain.BaseValueObject;
import org.ydautremay.ouist.domain.model.player.PlayerNickName;

/**
 * Created by dautremayy on 19/02/2016.
 */
@Embeddable
public class SimpleTrick extends BaseValueObject {

    private RoundId roundId;

    private PlayerNickName leader;

    SimpleTrick() {
    }

    public SimpleTrick(RoundId roundId, PlayerNickName playerNickName) {
        this.roundId = roundId;
        this.leader = playerNickName;
    }

    public RoundId getRoundId() {
        return roundId;
    }

    public PlayerNickName getLeader() {
        return leader;
    }
}
