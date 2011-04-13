/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.common.client.utils;

import com.google.gwt.user.client.Timer;

public class TimerWrapper {
    public interface Executer {
        /**
         * Invokes the execute.
         */
        void execute();
    }

    private boolean isScheduled;
    private Timer timer;

    public TimerWrapper() {
        isScheduled = false;
    }

    public void cancel() {
        isScheduled = false;
        timer.cancel();
    }

    public void configure(final Executer onTime) {
        timer = new Timer() {
            @Override
            public void run() {
                isScheduled = false;
                onTime.execute();
            }
        };
    }

    public boolean isScheduled() {
        return isScheduled;
    }

    public void run() {
        timer.run();
    }

    public void schedule(final int delayMillis) {
        isScheduled = true;
        timer.schedule(delayMillis);
    }

    public void scheduleRepeating(final int delayMillis) {
        isScheduled = true;
        timer.scheduleRepeating(delayMillis);
    }
}
