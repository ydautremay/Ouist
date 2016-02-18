package org.ydautremay.ouist.infrastructure.policies;

import org.seedstack.seed.Configuration;

import org.ydautremay.ouist.domain.policies.ScorePolicy;

/**
 * Created by dautremayy on 22/01/2016.
 */
public class PropsScorePolicy implements ScorePolicy {

    @Configuration("score.base")
    private int baseScore;

    @Configuration("score.bonus")
    private int bonus;

    @Configuration("score.minus")
    private int minus;

    public int scoreForNotLoosing() {
        return baseScore;
    }

    public int bonusPerTrick() {
        return bonus;
    }

    public int minusPerTrick() {
        return baseScore;
    }
}
