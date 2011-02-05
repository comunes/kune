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
package org.ourproject.kune.platf.client.ui.noti;

import com.calclab.suco.client.events.Listener0;

@Deprecated
public class ConfirmationAsk {

    private final String title;
    private final String message;
    private final Listener0 onConfirmed;
    private final Listener0 onCancel;

    public ConfirmationAsk(final String title, final String message, final Listener0 onConfirmed,
            final Listener0 onCancel) {
        this.title = title;
        this.message = message;
        this.onConfirmed = onConfirmed;
        this.onCancel = onCancel;
    }

    public String getMessage() {
        return message;
    }

    public Listener0 getOnCancel() {
        return onCancel;
    }

    public Listener0 getOnConfirmed() {
        return onConfirmed;
    }

    public String getTitle() {
        return title;
    }
}
