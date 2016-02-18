package org.ydautremay.ouist.domain.model.game;

import javax.persistence.Embeddable;

import org.seedstack.business.domain.BaseValueObject;

import org.ydautremay.ouist.domain.model.player.PlayerNickName;

/**
 * Created by dautremayy on 08/02/2016.
 */
@Embeddable
public class ContractId extends BaseValueObject {

    public ContractId(RoundId roundId, PlayerNickName player) {
        this.roundId = roundId;
        this.player = player;
    }

    private RoundId roundId;

    private PlayerNickName player;

    public RoundId getRoundId() {
        return roundId;
    }

    public PlayerNickName getPlayer() {
        return player;
    }
}
