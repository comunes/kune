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

package org.ourproject.kune.platf.server.manager.impl;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;
import org.ourproject.kune.platf.server.tool.ServerTool;
import org.ourproject.kune.platf.server.tool.ToolRegistry;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class GroupManagerDefault extends DefaultManager<Group, Long> implements GroupManager {

    private final Group finder;
    private final ToolRegistry registry;
    private final DatabaseProperties properties;

    @Inject
    public GroupManagerDefault(final Provider<EntityManager> provider, final Group finder,
	    final DatabaseProperties properties, final ToolRegistry registry) {
	super(provider, Group.class);
	this.finder = finder;
	this.properties = properties;
	this.registry = registry;
    }

    public Group getDefaultGroup() {
	String shortName = properties.getDefaultSiteShortName();
	return findByShortName(shortName);
    }

    public Group findByShortName(final String shortName) {
	return finder.findByShortName(shortName);
    }

    public Group createGroup(final String shortName, final String longName, final User user)
	    throws SerializableException {
	Group group = new Group(shortName, longName);
	return createGroup(group, user);
    }

    public Group createUserGroup(final User user) {
	Group group = new Group(user.getShortName(), user.getName());
	user.setUserGroup(group);
	initSocialNetwork(group, group);
	initGroup(user, group);
	super.persist(user, User.class);
	return group;
    }

    public Group createGroup(final Group group, final User user) throws SerializableException {
	try {
	    initSocialNetwork(group, user.getUserGroup());
	    initGroup(user, group);
	    return group;
	} catch (EntityExistsException e) {
	    throw new SerializableException("Already exist a group with this name");
	}
    }

    private void initSocialNetwork(final Group group, final Group userGroup) {
	group.getSocialNetwork().addAdmin(userGroup);
    }

    private void initGroup(final User user, final Group group) {
	for (ServerTool tool : registry.all()) {
	    tool.initGroup(user, group);
	}
	persist(group);
    }
}
