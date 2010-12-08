/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.client.utils;

import com.calclab.suco.client.events.Listener0;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

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
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            public void execute() {
                command.onEvent();
            }
        });
    }
}
