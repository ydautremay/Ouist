package org.ydautremay.ouist.application;

import javax.inject.Singleton;

/**
 * Created by dautremayy on 03/02/2016.
 */
@Singleton
public class ApplicationStatusMonitorImpl implements ApplicationStatusMonitor {

    private ApplicationStatus status;

    public ApplicationStatusMonitorImpl() {
        this.status = ApplicationStatus.STARTED;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void stop() {
        this.status = ApplicationStatus.STOPPED;
    }
}
