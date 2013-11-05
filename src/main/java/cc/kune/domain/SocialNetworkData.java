/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
package cc.kune.domain;

import cc.kune.core.shared.domain.SocialNetworkVisibility;
import cc.kune.core.shared.domain.UserSNetVisibility;
import cc.kune.core.shared.domain.utils.AccessRights;

public class SocialNetworkData {
  private SocialNetworkVisibility socialNetworkVisibility;
  private SocialNetwork groupMembers;
  private ParticipationData userParticipation;
  private UserSNetVisibility userBuddiesVisibility;
  private UserBuddiesData userBuddies;
  private AccessRights groupRights;
  private boolean isBuddiesVisible;
  private boolean isMembersVisible;

  public SocialNetworkData() {
    this(null, null, null, null, null, null, false, false);
  }

  public SocialNetworkData(SocialNetworkVisibility socialNetworkVisibility, SocialNetwork groupMembers,
      ParticipationData userParticipation, UserSNetVisibility userBuddiesVisibility,
      UserBuddiesData userBuddies, AccessRights groupRights, boolean isBuddiesVisible,
      boolean isMembersVisible) {
    this.socialNetworkVisibility = socialNetworkVisibility;
    this.groupMembers = groupMembers;
    this.userParticipation = userParticipation;
    this.userBuddiesVisibility = userBuddiesVisibility;
    this.userBuddies = userBuddies;
    this.groupRights = groupRights;
    this.isBuddiesVisible = isBuddiesVisible;
    this.isMembersVisible = isMembersVisible;
  }

  public SocialNetwork getGroupMembers() {
    return groupMembers;
  }

  public AccessRights getGroupRights() {
    return groupRights;
  }

  public boolean getIsBuddiesVisible() {
    return isBuddiesVisible;
  }

  public boolean getIsMembersVisible() {
    return isMembersVisible;
  }

  public SocialNetworkVisibility getSocialNetworkVisibility() {
    return socialNetworkVisibility;
  }

  public UserBuddiesData getUserBuddies() {
    return userBuddies;
  }

  public UserSNetVisibility getUserBuddiesVisibility() {
    return userBuddiesVisibility;
  }

  public ParticipationData getUserParticipation() {
    return userParticipation;
  }

  public boolean isBuddiesVisible() {
    return isBuddiesVisible;
  }

  public boolean isMembersVisible() {
    return isMembersVisible;
  }

  public void setBuddiesVisible(boolean isBuddiesVisible) {
    this.isBuddiesVisible = isBuddiesVisible;
  }

  public void setGroupMembers(SocialNetwork groupMembers) {
    this.groupMembers = groupMembers;
  }

  public void setGroupRights(AccessRights groupRights) {
    this.groupRights = groupRights;
  }

  public void setIsBuddiesVisible(boolean isBuddiesVisible) {
    setBuddiesVisible(isBuddiesVisible);
  }

  public void setIsMembersVisible(boolean isMembersVisible) {
    setMembersVisible(isMembersVisible);
  }

  public void setMembersVisible(boolean isMembersVisible) {
    this.isMembersVisible = isMembersVisible;
  }

  public void setSocialNetworkVisibility(SocialNetworkVisibility socialNetworkVisibility) {
    this.socialNetworkVisibility = socialNetworkVisibility;
  }

  public void setUserBuddies(UserBuddiesData userBuddies) {
    this.userBuddies = userBuddies;
  }

  public void setUserBuddiesVisibility(UserSNetVisibility userBuddiesVisibility) {
    this.userBuddiesVisibility = userBuddiesVisibility;
  }

  public void setUserParticipation(ParticipationData userParticipation) {
    this.userParticipation = userParticipation;
  }

}
