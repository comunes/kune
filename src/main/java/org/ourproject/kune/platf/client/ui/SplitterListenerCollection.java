/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.ui;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.user.client.ui.Widget;

public class SplitterListenerCollection extends ArrayList<SplitterListener> {

    private static final long serialVersionUID = 1L;

    /**
     * Fires a event to all listeners.
     * 
     * @param sender
     *            the widget sending the event.
     */
    public void fireStartResizing(final Widget sender) {
        for (Iterator<SplitterListener> it = iterator(); it.hasNext();) {
            SplitterListener listener = it.next();
            listener.onStartResizing(sender);
        }
    }

    /**
     * Fires a event to all listeners.
     * 
     * @param sender
     *            the widget sending the event.
     */
    public void fireStopResizing(final Widget sender) {
        for (Iterator<SplitterListener> it = iterator(); it.hasNext();) {
            SplitterListener listener = it.next();
            listener.onStopResizing(sender);
        }
    }

}
