package org.ydautremay.ouist.domain.model.game;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.seedstack.business.domain.BaseEntity;

/**
 * Created by dautremayy on 22/01/2016.
 */
@Entity
public class Contract extends BaseEntity<ContractId>{

    @EmbeddedId
    private ContractId contractId;

    private int nbTricks;

    Contract(){
    }

    Contract(ContractId contractId, int nbTricks) {
        this.contractId = contractId;
        this.nbTricks = nbTricks;
    }

    public ContractId getContractId() {
        return contractId;
    }

    public int getNbTricks() {
        return nbTricks;
    }

    @Override
    public ContractId getEntityId() {
        return contractId;
    }
}
