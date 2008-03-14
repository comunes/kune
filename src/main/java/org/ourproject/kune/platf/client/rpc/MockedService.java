/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.rpc;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MockedService {
    public static boolean isTest;

    public static interface Delayer {
        void run();
    }

    protected void answer(final Object response, final AsyncCallback callback) {
        delay(new Delayer() {
            public void run() {
                callback.onSuccess(response);
            }
        });
    }

    protected void delay(final Delayer timer) {
        if (isTest) {
            timer.run();
        } else {
            schedule(timer);
        }
    }

    private void schedule(final Delayer delayer) {
        Timer timer = new Timer() {
            public void run() {
                delayer.run();
            }
        };
        timer.schedule(1500);
    }

}
