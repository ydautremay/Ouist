package org.ydautremay.ouist.application;

import java.util.UUID;

import org.seedstack.business.Service;

/**
 * Created by dautremayy on 06/02/2016.
 */
@Service
public interface Session {

    UUID getCurrentGameId();

    void setCurrentGameId(UUID gameId);
}
