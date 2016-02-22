package org.ydautremay.ouist.domain.model.scoresheet;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import javax.inject.Inject;

import org.junit.Test;
import org.seedstack.business.domain.Repository;
import org.seedstack.jpa.Jpa;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.it.AbstractSeedIT;
import org.seedstack.seed.transaction.Transactional;
import org.ydautremay.ouist.domain.model.game.RoundId;
import org.ydautremay.ouist.domain.model.player.PlayerNickName;

/**
 * Created by dautremayy on 19/02/2016.
 */
public class ScoreSheetRepositoryTest extends AbstractSeedIT {

    @Inject
    @Jpa
    private Repository<ScoreSheet, UUID> scoreRepository;

    @Inject
    private ScoreSheetFactory scoreSheetFactory;

    @Test
    @Transactional
    @JpaUnit("ouist-jpa-unit")
    public void testRepository(){
        ScoreSheet sheet = scoreSheetFactory.createScoreSheet();
        assertThat(sheet.getEntityId()).isNotNull();

        scoreRepository.persist(sheet);

        Score score = new Score(new PlayerNickName("1"), 10);
        Score score2 = new Score(new PlayerNickName("2"), 11);
        Score score3 = new Score(new PlayerNickName("3"), 12);

        ScoreLine scoreLine = sheet.newLine(new RoundId(UUID.randomUUID(), 1));
        scoreLine.getRoundScores().add(score);
        scoreLine.getRoundScores().add(score2);
        scoreLine.getRoundScores().add(score3);

        sheet.getScoreLines().add(scoreLine);

        scoreRepository.save(sheet);

        ScoreLine scoreLine2 = sheet.newLine(new RoundId(UUID.randomUUID(), 2));
        scoreLine2.getRoundScores().add(score);
        scoreLine2.getRoundScores().add(score2);
        scoreLine2.getRoundScores().add(score3);

        sheet.getScoreLines().add(scoreLine2);

        scoreRepository.save(sheet);

        ScoreSheet sheet2 = scoreRepository.load(sheet.getEntityId());
        assertThat(sheet2).isNotNull();
    }
}
