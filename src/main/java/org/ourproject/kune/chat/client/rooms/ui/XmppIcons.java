/*
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

package org.ourproject.kune.chat.client.rooms.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface XmppIcons extends ImageBundle {

    public static class App {
	private static XmppIcons ourInstance = null;

	public static synchronized XmppIcons getInstance() {
	    if (ourInstance == null) {
		ourInstance = (XmppIcons) GWT.create(XmppIcons.class);
	    }
	    return ourInstance;
	}
    }

    /**
     * @gwt.resource away.png
     */
    AbstractImagePrototype away();

    /**
     * @gwt.resource busy.png
     */
    AbstractImagePrototype busy();

    /**
     * @gwt.resource message.png
     */
    AbstractImagePrototype message();

    /**
     * @gwt.resource invisible.png
     */
    AbstractImagePrototype invisible();

    /**
     * @gwt.resource xa.png
     */
    AbstractImagePrototype extendedAway();

    /**
     * @gwt.resource offline.png
     */
    AbstractImagePrototype offline();

    /**
     * @gwt.resource online.png
     */
    AbstractImagePrototype online();

}
