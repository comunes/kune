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
/*
 *
 * ((e)) emite: A pure gwt (Google Web Toolkit) xmpp (jabber) library
 *
 * (c) 2008-2009 The emite development team (see CREDITS for details)
 * This file is part of emite.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.client.ui.gridmenu;

import com.calclab.suco.client.events.Event;
import com.calclab.suco.client.events.Listener;

public class GridDropConfiguration {

    private final String ddGroupId;
    private final Event<String> onDrop;

    public GridDropConfiguration(final String ddGroupId, final Listener<String> listener) {
        this.ddGroupId = ddGroupId;
        this.onDrop = new Event<String>("onDrop");
        this.onDropImpl(listener);
    }

    public void fire(final String id) {
        onDrop.fire(id);
    }

    public String getDdGroupId() {
        return ddGroupId;
    }

    public void onDrop(final Listener<String> listener) {
        onDropImpl(listener);
    }

    private void onDropImpl(final Listener<String> listener) {
        onDrop.add(listener);
    }

}
