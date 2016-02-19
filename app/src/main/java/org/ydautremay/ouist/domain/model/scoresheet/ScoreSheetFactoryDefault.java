package org.ydautremay.ouist.domain.model.scoresheet;

import javax.inject.Inject;

import org.seedstack.business.domain.BaseFactory;
import org.seedstack.business.domain.identity.IdentityService;

/**
 * Created by dautremayy on 19/02/2016.
 */
public class ScoreSheetFactoryDefault extends BaseFactory<ScoreSheet> implements ScoreSheetFactory {

    @Inject
    IdentityService identityService;

    @Override
    public ScoreSheet createScoreSheet() {
        ScoreSheet scoreSheet = new ScoreSheet();
        identityService.identify(scoreSheet);
        return scoreSheet;
    }
}
