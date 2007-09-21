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

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.AdmissionType;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupListMode;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
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
    private final License licenseFinder;

    @Inject
    public GroupManagerDefault(final Provider<EntityManager> provider, final Group finder,
	    final DatabaseProperties properties, final ToolRegistry registry, final License licenseFinder) {
	super(provider, Group.class);
	this.finder = finder;
	this.properties = properties;
	this.registry = registry;
	this.licenseFinder = licenseFinder;
    }

    public Group getDefaultGroup() {
	final String shortName = properties.getDefaultSiteShortName();
	return findByShortName(shortName);
    }

    public Group findByShortName(final String shortName) {
	return finder.findByShortName(shortName);
    }

    public Group createGroup(final String shortName, final String longName, final User user)
	    throws SerializableException {
	final Group group = new Group(shortName, longName);
	return createGroup(group, user);
    }

    public Group createUserGroup(final User user) {
	final Group group = new Group(user.getShortName(), user.getName());
	final String licenseDefId = properties.getDefaultLicense();
	final License licenseDef = licenseFinder.findByShortName(licenseDefId);
	group.setDefaultLicense(licenseDef);
	group.setAdmissionType(AdmissionType.Closed);
	user.setUserGroup(group);
	initSocialNetwork(group, group);
	user.getAdminInGroups().add(group);
	initGroup(user, group);
	super.persist(user, User.class);
	return group;
    }

    public Group createGroup(final Group group, final User user) throws SerializableException {
	if (User.isKownUser(user)) {
	    try {
		initSocialNetwork(group, user.getUserGroup());
		user.getAdminInGroups().add(group);
		initGroup(user, group);
		return group;
	    } catch (final EntityExistsException e) {
		// i18n
		throw new SerializableException("Already exist a group with this name");
	    }
	} else {
	    // i18n
	    throw new SerializableException("Must be logged");
	}

    }

    private void initSocialNetwork(final Group group, final Group userGroup) {
	final SocialNetwork network = group.getSocialNetwork();
	final AccessLists lists = network.getAccessLists();
	lists.getEditors().setMode(GroupListMode.NOBODY);
	lists.getViewers().setMode(GroupListMode.EVERYONE);
	if (group.getAdmissionType() == null) {
	    group.setAdmissionType(AdmissionType.Moderated);
	}
	network.addAdmin(userGroup);
    }

    private void initGroup(final User user, final Group group) {
	for (final ServerTool tool : registry.all()) {
	    tool.initGroup(user, group);
	}
	persist(group);
    }

    public Group getGroupOfUserWithId(final Long userId) {
	return userId != null ? find(User.class, userId).getUserGroup() : null;
    }
}
