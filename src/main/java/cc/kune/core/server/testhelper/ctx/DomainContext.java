/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.core.server.testhelper.ctx;

import java.util.HashMap;
import java.util.TimeZone;

import cc.kune.core.shared.dto.GroupType;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Group;
import cc.kune.domain.I18nCountry;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.SocialNetwork;
import cc.kune.domain.User;

// TODO: Auto-generated Javadoc
/**
 * The Class DomainContext.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class DomainContext {

  /** The groups. */
  private final HashMap<String, Group> groups;

  /** The users. */
  private final HashMap<String, User> users;

  /**
   * Instantiates a new domain context.
   */
  public DomainContext() {
    this.users = new HashMap<String, User>();
    this.groups = new HashMap<String, Group>();
  }

  /**
   * Creates the groups.
   * 
   * @param groupNames
   *          the group names
   */
  public void createGroups(final String... groupNames) {
    Group group;
    for (final String name : groupNames) {
      group = new Group("name", "Some group: " + name);
      groups.put(name, group);
    }
  }

  /**
   * Creates the orphan group.
   * 
   * @param groupNames
   *          the group names
   */
  public void createOrphanGroup(final String... groupNames) {
    Group group;
    for (final String name : groupNames) {
      group = new Group("name", "Some group: " + name);
      group.setGroupType(GroupType.ORPHANED_PROJECT);
      groups.put(name, group);
    }
  }

  /**
   * Creates the users.
   * 
   * @param userNames
   *          the user names
   */
  public void createUsers(final String... userNames) {
    User user;
    for (final String name : userNames) {
      user = new User(name, "long" + name, name + "@email.com", "diggest".getBytes(), "salt".getBytes(),
          new I18nLanguage(), new I18nCountry(), TimeZone.getDefault());
      user.setUserGroup(new Group(name, "groupLong" + name));
      users.put(name, user);
    }
  }

  /**
   * Gets the default access list of.
   * 
   * @param userName
   *          the user name
   * @return the default access list of
   */
  public AccessLists getDefaultAccessListOf(final String userName) {
    return getSocialNetworkOf(userName).getAccessLists();
  }

  /**
   * Gets the group.
   * 
   * @param groupName
   *          the group name
   * @return the group
   */
  public Group getGroup(final String groupName) {
    return groups.get(groupName);
  }

  /**
   * Gets the group of.
   * 
   * @param userName
   *          the user name
   * @return the group of
   */
  public Group getGroupOf(final String userName) {
    final User user = getUser(userName);
    final Group userGroup = user.getUserGroup();
    return userGroup;
  }

  /**
   * Gets the social network of.
   * 
   * @param userName
   *          the user name
   * @return the social network of
   */
  private SocialNetwork getSocialNetworkOf(final String userName) {
    final Group userGroup = getGroupOf(userName);
    final SocialNetwork socialNetwork = userGroup.getSocialNetwork();
    return socialNetwork;
  }

  /**
   * Gets the user.
   * 
   * @param userName
   *          the user name
   * @return the user
   */
  public User getUser(final String userName) {
    return users.get(userName);
  }

  /**
   * In social network of.
   * 
   * @param userName
   *          the user name
   * @return the social network operator
   */
  public SocialNetworkOperator inSocialNetworkOf(final String userName) {
    return new SocialNetworkOperator(this, getSocialNetworkOf(userName));
  }

}
