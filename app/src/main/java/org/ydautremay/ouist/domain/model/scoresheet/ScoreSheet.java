package org.ydautremay.ouist.domain.model.scoresheet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.domain.Identity;
import org.seedstack.business.domain.identity.UUIDHandler;
import org.ydautremay.ouist.domain.model.game.RoundId;

/**
 * Created by dautremayy on 19/02/2016.
 */
@Entity
public class ScoreSheet extends BaseAggregateRoot<UUID> {

    @Id
    @Identity(handler = UUIDHandler.class)
    private UUID sheetId;

    @OneToMany(mappedBy = "scoreLineId.scoreSheetId", cascade = CascadeType.ALL)
    @OrderColumn(name = "lineNb")
    private List<ScoreLine> scoreLines;

    ScoreSheet() {
        scoreLines = new ArrayList<>();
    }

    ScoreSheet(UUID sheetId) {
        this();
        this.sheetId = sheetId;
    }

    @Override
    public UUID getEntityId() {
        return sheetId;
    }

    public List<ScoreLine> getScoreLines() {
        return scoreLines;
    }

    public ScoreLine newLine(RoundId roundId){
        ScoreLineId lineId = new ScoreLineId(sheetId, roundId);
        return new ScoreLine(lineId);
    }
}
