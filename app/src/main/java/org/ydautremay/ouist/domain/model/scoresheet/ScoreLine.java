package org.ydautremay.ouist.domain.model.scoresheet;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.seedstack.business.domain.BaseEntity;

/**
 * Created by dautremayy on 19/02/2016.
 */
@Entity
public class ScoreLine extends BaseEntity<ScoreLineId> {

    @EmbeddedId
    private ScoreLineId scoreLineId;

    @ElementCollection
    private Collection<Score> roundScores;

    ScoreLine(){
        roundScores = new ArrayList<>();
    }

    ScoreLine(ScoreLineId lineId){
        this();
        this.scoreLineId = lineId;
    }

    public Collection<Score> getRoundScores() {
        return roundScores;
    }

    public ScoreLineId getScoreLineId() {
        return scoreLineId;
    }

    @Override
    public ScoreLineId getEntityId() {
        return scoreLineId;
    }
}
