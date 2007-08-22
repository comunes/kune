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

package org.ourproject.kune.platf.client.tool;

import org.ourproject.kune.platf.client.dto.StateToken;

public class ToolTriggerDefault implements ToolTrigger {
    private final String toolName;
    private final String label;
    private TriggerListener listener;

    public ToolTriggerDefault(final String toolName, final String caption) {
	this.toolName = toolName;
	this.label = caption;
    }

    public String getLabel() {
	return label;
    }

    public String getName() {
	return toolName;
    }

    public void setListener(final TriggerListener listener) {
	this.listener = listener;
    }

    public void setState(final String encoded) {
	listener.onStateChanged(encoded);
    }

    public void setState(final StateToken stateToken) {
	setState(stateToken.getEncoded());
    }

}
