package org.ydautremay.ouist.domain.model.game;

import java.util.UUID;

import javax.persistence.Embeddable;
import javax.persistence.OrderColumn;

import org.seedstack.business.domain.BaseValueObject;

/**
 * Created by dautremayy on 25/01/2016.
 */
@Embeddable
public class RoundId extends BaseValueObject {

    private UUID gameId;

    @OrderColumn(name="roundNb")
    private int roundNb;

    RoundId() {
    }

    public RoundId(UUID gameId, int roundNb) {
        this.gameId = gameId;
        this.roundNb = roundNb;
    }

    public UUID getGameId() {
        return gameId;
    }

    public int getRoundNb() {
        return roundNb;
    }

    private void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    private void setRoundNb(int roundNb) {
        this.roundNb = roundNb;
    }
}
