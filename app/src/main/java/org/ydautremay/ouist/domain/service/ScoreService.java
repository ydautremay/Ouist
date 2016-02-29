package org.ydautremay.ouist.domain.service;

import org.seedstack.business.Service;
import org.ydautremay.ouist.domain.model.game.Round;
import org.ydautremay.ouist.domain.model.scoresheet.ScoreSheet;

/**
 * Created by dautremayy on 22/01/2016.
 */
@Service
public interface ScoreService {

    void computeScores (ScoreSheet scoreSheet, Round round);
}
