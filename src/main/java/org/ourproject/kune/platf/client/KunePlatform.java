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

package org.ourproject.kune.platf.client;

import java.util.ArrayList;
import java.util.List;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;
import org.ourproject.kune.platf.client.tool.ClientTool;

public class KunePlatform implements Register {
    private final ArrayList tools;
    private final ArrayList actions;

    public KunePlatform() {
	this.tools = new ArrayList();
	this.actions = new ArrayList();
    }

    public void addTool(final ClientTool clientTool) {
	tools.add(clientTool);
    }

    public void addAction(final Action action) {
	actions.add(action);
    }

    public void install(final ClientModule module) {
	module.configure(this);
    }

    public List getTools() {
	return tools;
    }

    public ArrayList getActions() {
	return actions;
    }

}
