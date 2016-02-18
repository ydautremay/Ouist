package org.ydautremay.ouist.domain.policies;

import org.seedstack.business.domain.DomainPolicy;

/**
 * Created by dautremayy on 22/01/2016.
 */
@DomainPolicy
public interface ScorePolicy {

    int scoreForNotLoosing();

    int bonusPerTrick();

    int minusPerTrick();
}
