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
package org.ourproject.kune.testhelper.ctx;

import java.util.HashMap;
import java.util.TimeZone;


import cc.kune.core.shared.dto.GroupType;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Group;
import cc.kune.domain.I18nCountry;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.SocialNetwork;
import cc.kune.domain.User;

public class DomainContext {
    private final HashMap<String, User> users;
    private final HashMap<String, Group> groups;

    public DomainContext() {
        this.users = new HashMap<String, User>();
        this.groups = new HashMap<String, Group>();
    }

    public void createGroups(final String... groupNames) {
        Group group;
        for (String name : groupNames) {
            group = new Group("name", "Some group: " + name);
            groups.put(name, group);
        }
    }

    public void createOrphanGroup(final String... groupNames) {
        Group group;
        for (String name : groupNames) {
            group = new Group("name", "Some group: " + name);
            group.setGroupType(GroupType.ORPHANED_PROJECT);
            groups.put(name, group);
        }
    }

    public void createUsers(final String... userNames) {
        User user;
        for (String name : userNames) {
            user = new User(name, "long" + name, name + "@email.com", "password" + name, new I18nLanguage(),
                    new I18nCountry(), TimeZone.getDefault());
            user.setUserGroup(new Group(name, "groupLong" + name));
            users.put(name, user);
        }
    }

    public AccessLists getDefaultAccessListOf(final String userName) {
        return getSocialNetworkOf(userName).getAccessLists();
    }

    public Group getGroup(final String groupName) {
        return groups.get(groupName);
    }

    public Group getGroupOf(final String userName) {
        User user = getUser(userName);
        Group userGroup = user.getUserGroup();
        return userGroup;
    }

    public User getUser(final String userName) {
        return users.get(userName);
    }

    public SocialNetworkOperator inSocialNetworkOf(final String userName) {
        return new SocialNetworkOperator(this, getSocialNetworkOf(userName));
    }

    private SocialNetwork getSocialNetworkOf(final String userName) {
        Group userGroup = getGroupOf(userName);
        SocialNetwork socialNetwork = userGroup.getSocialNetwork();
        return socialNetwork;
    }

}
