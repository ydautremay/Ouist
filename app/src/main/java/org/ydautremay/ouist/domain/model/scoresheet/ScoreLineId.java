package org.ydautremay.ouist.domain.model.scoresheet;

import java.util.UUID;

import javax.persistence.Embeddable;

import org.seedstack.business.domain.BaseValueObject;
import org.ydautremay.ouist.domain.model.game.RoundId;

/**
 * Created by dautremayy on 19/02/2016.
 */
@Embeddable
public class ScoreLineId extends BaseValueObject {

    private UUID scoreSheetId;

    private RoundId roundId;

    ScoreLineId() {
    }

    ScoreLineId(UUID scoreSheetId, RoundId roundId) {
        this.scoreSheetId = scoreSheetId;
        this.roundId = roundId;
    }

    public UUID getScoreSheetId() {
        return scoreSheetId;
    }

    private void setScoreSheetId(UUID roundId) {
        this.scoreSheetId = roundId;
    }

    public RoundId getRoundId() {
        return roundId;
    }

    public void setRoundId(RoundId roundId) {
        this.roundId = roundId;
    }
}
