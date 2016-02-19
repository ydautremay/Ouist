package org.ydautremay.ouist.domain.model.scoresheet;

import org.seedstack.business.domain.GenericFactory;

/**
 * Created by dautremayy on 19/02/2016.
 */
public interface ScoreSheetFactory extends GenericFactory<ScoreSheet> {

    ScoreSheet createScoreSheet();
}
