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

package org.ourproject.kune.platf.server.content;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.manager.impl.DefaultManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ContainerManagerDefault extends DefaultManager<Container, Long> implements ContainerManager {

    @Inject
    public ContainerManagerDefault(final Provider<EntityManager> provider) {
	super(provider, Container.class);
    }

    public Container createRootFolder(final Group group, final String toolName, final String name, final String type) {
	Container container = new Container("", name, group, toolName);
	container.setTypeId(type);
	return persist(container);
    }

    public Container createFolder(final Group group, final Container parent, final String name) {
	Container child = new Container(parent.getAbsolutePath(), name, group, parent.getToolName());
	parent.addChild(child);
	child.setParent(parent);
	persist(child);
	return child;
    }
}
