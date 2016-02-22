package org.ydautremay.ouist.application;

import org.seedstack.business.Service;

/**
 * Created by dautremayy on 03/02/2016.
 */
@Service
public interface ApplicationStatusMonitor {

    ApplicationStatus getStatus();

    void stop();
}
