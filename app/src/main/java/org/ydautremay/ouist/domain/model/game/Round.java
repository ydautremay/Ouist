package org.ydautremay.ouist.domain.model.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;

import org.seedstack.business.domain.BaseEntity;

import org.ydautremay.ouist.domain.model.Deal;

/**
 * Created by dautremayy on 22/01/2016.
 */
@Entity
public class Round extends BaseEntity<RoundId> {

    @EmbeddedId
    private RoundId roundId;

    @Transient
    private Deal deal;

    @OneToMany(mappedBy = "contractId.roundId", cascade= CascadeType.ALL)
    @OrderColumn(name = "contractNb")
    private List<Contract> contracts;

    @Transient
    private Collection<Score> scores;

    Round() {
    }

    Round(RoundId roundId) {
        this.roundId = roundId;
        this.contracts = new ArrayList<>();
        this.scores = new ArrayList<Score>();
    }

    Round(UUID gameId, int roundNb) {
        this(new RoundId(gameId, roundNb));
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public Collection<Score> getScores() {
        return scores;
    }

    public Deal getDeal() {
        return deal;
    }

    public RoundId getRoundId() {
        return roundId;
    }

    @Override
    public RoundId getEntityId() {
        return roundId;
    }
}
