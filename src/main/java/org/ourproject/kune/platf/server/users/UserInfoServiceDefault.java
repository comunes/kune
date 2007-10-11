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

package org.ourproject.kune.platf.server.users;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserInfoServiceDefault implements UserInfoService {

    private final GroupManager groupFinder;

    @Inject
    public UserInfoServiceDefault(final GroupManager groupFinder) {
	this.groupFinder = groupFinder;
    }

    public UserInfo buildInfo(final User user) {
	UserInfo info = null;
	if (User.isKownUser(user)) {
	    info = new UserInfo();

	    info.setName(user.getName());
	    info.setChatName(user.getShortName());
	    info.setChatPassword(user.getPassword());

	    Long userGroupId = user.getUserGroup().getId();
	    List<Group> adminInGroups = groupFinder.findAdminInGroups(userGroupId);
	    List<Group> collabInGroups = groupFinder.findCollabInGroups(userGroupId);

	    List<Link> groupsIsAdmin = new ArrayList();
	    List<Link> groupsIsCollab = new ArrayList();
	    ;
	    Iterator iter = adminInGroups.iterator();
	    while (iter.hasNext()) {
		Group g = (Group) iter.next();
		groupsIsAdmin
			.add(new Link(g.getShortName(), g.getLongName(), "", g.getDefaultContent().getStateToken()));

	    }
	    iter = collabInGroups.iterator();
	    while (iter.hasNext()) {
		Group g = (Group) iter.next();
		groupsIsCollab.add(new Link(g.getShortName(), g.getLongName(), "", g.getDefaultContent()
			.getStateToken()));
	    }
	    info.setGroupsIsAdmin(groupsIsAdmin);
	    info.setGroupsIsCollab(groupsIsCollab);

	    Group userGroup = user.getUserGroup();
	    Content defaultContent = userGroup.getDefaultContent();
	    if (defaultContent != null) {
		info.setHomePage(defaultContent.getStateToken());
	    }
	}
	return info;
    }
}
