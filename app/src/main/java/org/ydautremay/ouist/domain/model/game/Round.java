package org.ydautremay.ouist.domain.model.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;

import org.seedstack.business.domain.BaseEntity;
import org.ydautremay.ouist.domain.model.Deal;
import org.ydautremay.ouist.domain.model.player.PlayerNickName;

/**
 * Created by dautremayy on 22/01/2016.
 */
@Entity
public class Round extends BaseEntity<RoundId> {

    @EmbeddedId
    private RoundId roundId;

    private PlayerNickName dealer;

    @Transient
    private Deal deal;

    @ElementCollection
    @OrderColumn(name = "trickNb")
    private List<SimpleTrick> playedTricks;

    @OneToMany(mappedBy = "contractId.roundId", cascade = CascadeType.ALL)
    private Collection<Contract> contracts;

    Round() {
    }

    Round(RoundId roundId) {
        this.roundId = roundId;
        this.contracts = new ArrayList<>();
        this.playedTricks = new ArrayList<>();
    }

    Round(UUID gameId, int roundNb) {
        this(new RoundId(gameId, roundNb));
    }

    public Collection<Contract> getContracts() {
        return contracts;
    }


    public Deal getDeal() {
        return deal;
    }

    public RoundId getRoundId() {
        return roundId;
    }

    public List<SimpleTrick> getPlayedTricks() {
        return playedTricks;
    }

    @Override
    public RoundId getEntityId() {
        return roundId;
    }

    public PlayerNickName getDealer() {
        return dealer;
    }

    public void setDealer(PlayerNickName dealer) {
        this.dealer = dealer;
    }
}
