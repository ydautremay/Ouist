package org.ydautremay.ouist.internal;

import javax.inject.Inject;

import org.seedstack.seed.Logging;
import org.seedstack.seed.cli.CliCommand;
import org.seedstack.seed.cli.CommandLineHandler;
import org.slf4j.Logger;
import org.ydautremay.ouist.application.ApplicationStatus;

import org.ydautremay.ouist.application.ApplicationStatusMonitor;

/**
 * Created by dautremayy on 03/02/2016.
 */
@CliCommand("dummy")
public class CommandLineHandlerImpl implements CommandLineHandler {

    @Logging
    private Logger logger;

    @Inject
    ApplicationStatusMonitor applicationStatusMonitor;

    public Integer call() throws Exception {
        while(!applicationStatusMonitor.getStatus().equals(ApplicationStatus.STOPPED)){
            Thread.sleep(1000);
        }
        return 0;
    }
}
