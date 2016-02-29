package org.ydautremay.ouist.domain.model.scoresheet;

import java.util.UUID;

import javax.persistence.Embeddable;
import javax.persistence.OrderColumn;

import org.seedstack.business.domain.BaseValueObject;

/**
 * Created by dautremayy on 19/02/2016.
 */
@Embeddable
public class ScoreLineId extends BaseValueObject {

    private UUID scoreSheetId;

    @OrderColumn(name="linNb")
    private int lineNb;

    ScoreLineId() {
    }

    ScoreLineId(UUID scoreSheetId, int lineNb) {
        this.scoreSheetId = scoreSheetId;
        this.lineNb = lineNb;
    }

    public UUID getScoreSheetId() {
        return scoreSheetId;
    }

    private void setScoreSheetId(UUID roundId) {
        this.scoreSheetId = roundId;
    }

    public int getLineNb() {
        return lineNb;
    }

    public void setRoundId(int lineNb) {
        this.lineNb = lineNb;
    }
}
