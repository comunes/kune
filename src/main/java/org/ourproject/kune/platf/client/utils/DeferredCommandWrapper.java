package org.ourproject.kune.platf.client.utils;

import com.calclab.suco.client.listener.Listener0;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;

/**
 * The Class DeferredCommandWrapper is a wrapper of the GWT DeferredCommand
 * (used for testing classes without GWT dependencies).
 */
public class DeferredCommandWrapper {

    /**
     * Adds the command.
     * 
     * @param command
     *            the listener
     */
    public void addCommand(final Listener0 command) {
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                command.onEvent();
            }
        });
    }
}
