package org.ydautremay.ouist.commands;

import javax.inject.Inject;

import org.seedstack.seed.spi.command.Command;
import org.seedstack.seed.spi.command.CommandDefinition;
import org.ydautremay.ouist.application.ApplicationStatus;

import org.ydautremay.ouist.application.ApplicationStatusMonitor;

/**
 * Created by dautremayy on 29/01/2016.
 */
@CommandDefinition(scope = "", name = "stop", description="Stops the application")
public class StopApplicationCommand implements Command<String> {

    @Inject
    ApplicationStatusMonitor applicationStatusMonitor;

    public String execute(Object object) throws Exception {
        applicationStatusMonitor.setStatus(ApplicationStatus.STOPPED);
        return "Stopping application...";
    }
}
