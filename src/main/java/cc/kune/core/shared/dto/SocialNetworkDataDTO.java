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
package cc.kune.core.shared.dto;

import cc.kune.core.shared.domain.SocialNetworkVisibility;
import cc.kune.core.shared.domain.UserSNetVisibility;
import cc.kune.core.shared.domain.utils.AccessRights;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class SocialNetworkDataDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SocialNetworkDataDTO implements IsSerializable {

  /** The group members. */
  private SocialNetworkDTO groupMembers;

  /** The group rights. */
  private AccessRights groupRights;

  /** The is buddies visible. */
  private boolean isBuddiesVisible;

  /** The is members visible. */
  private boolean isMembersVisible;

  /** The social network visibility. */
  private SocialNetworkVisibility socialNetworkVisibility;

  /** The user buddies. */
  private UserBuddiesDataDTO userBuddies;

  /** The user buddies visibility. */
  private UserSNetVisibility userBuddiesVisibility;

  /** The user participation. */
  private ParticipationDataDTO userParticipation;

  /**
   * Instantiates a new social network data dto.
   */
  public SocialNetworkDataDTO() {
    this(null, null, null, null, null, null, false, false);
  }

  /**
   * Instantiates a new social network data dto.
   * 
   * @param socialNetworkVisibility
   *          the social network visibility
   * @param groupMembers
   *          the group members
   * @param userParticipation
   *          the user participation
   * @param userBuddiesVisibility
   *          the user buddies visibility
   * @param userBuddies
   *          the user buddies
   * @param groupRights
   *          the group rights
   * @param isBuddiesVisible
   *          the is buddies visible
   * @param isMembersVisible
   *          the is members visible
   */
  public SocialNetworkDataDTO(final SocialNetworkVisibility socialNetworkVisibility,
      final SocialNetworkDTO groupMembers, final ParticipationDataDTO userParticipation,
      final UserSNetVisibility userBuddiesVisibility, final UserBuddiesDataDTO userBuddies,
      final AccessRights groupRights, final boolean isBuddiesVisible, final boolean isMembersVisible) {
    this.socialNetworkVisibility = socialNetworkVisibility;
    this.groupMembers = groupMembers;
    this.userParticipation = userParticipation;
    this.userBuddiesVisibility = userBuddiesVisibility;
    this.userBuddies = userBuddies;
    this.groupRights = groupRights;
    this.isBuddiesVisible = isBuddiesVisible;
    this.isMembersVisible = isMembersVisible;
  }

  /**
   * Gets the group members.
   * 
   * @return the group members
   */
  public SocialNetworkDTO getGroupMembers() {
    return groupMembers;
  }

  /**
   * Gets the group rights.
   * 
   * @return the group rights
   */
  public AccessRights getGroupRights() {
    return groupRights;
  }

  /**
   * Gets the checks if is buddies visible.
   * 
   * @return the checks if is buddies visible
   */
  public boolean getIsBuddiesVisible() {
    return isBuddiesVisible;
  }

  /**
   * Gets the checks if is members visible.
   * 
   * @return the checks if is members visible
   */
  public boolean getIsMembersVisible() {
    return isMembersVisible;
  }

  /**
   * Gets the social network visibility.
   * 
   * @return the social network visibility
   */
  public SocialNetworkVisibility getSocialNetworkVisibility() {
    return socialNetworkVisibility;
  }

  /**
   * Gets the user buddies.
   * 
   * @return the user buddies
   */
  public UserBuddiesDataDTO getUserBuddies() {
    return userBuddies;
  }

  /**
   * Gets the user buddies visibility.
   * 
   * @return the user buddies visibility
   */
  public UserSNetVisibility getUserBuddiesVisibility() {
    return userBuddiesVisibility;
  }

  /**
   * Gets the user participation.
   * 
   * @return the user participation
   */
  public ParticipationDataDTO getUserParticipation() {
    return userParticipation;
  }

  /**
   * Checks if is buddies visible.
   * 
   * @return true, if is buddies visible
   */
  public boolean isBuddiesVisible() {
    return isBuddiesVisible;
  }

  /**
   * Checks if is members visible.
   * 
   * @return true, if is members visible
   */
  public boolean isMembersVisible() {
    return isMembersVisible;
  }

  /**
   * Sets the buddies visible.
   * 
   * @param isBuddiesVisible
   *          the new buddies visible
   */
  public void setBuddiesVisible(final boolean isBuddiesVisible) {
    this.isBuddiesVisible = isBuddiesVisible;
  }

  /**
   * Sets the group members.
   * 
   * @param groupMembers
   *          the new group members
   */
  public void setGroupMembers(final SocialNetworkDTO groupMembers) {
    this.groupMembers = groupMembers;
  }

  /**
   * Sets the group rights.
   * 
   * @param groupRights
   *          the new group rights
   */
  public void setGroupRights(final AccessRights groupRights) {
    this.groupRights = groupRights;
  }

  /**
   * Sets the checks if is buddies visible.
   * 
   * @param isBuddiesVisible
   *          the new checks if is buddies visible
   */
  public void setIsBuddiesVisible(final boolean isBuddiesVisible) {
    setBuddiesVisible(isBuddiesVisible);
  }

  /**
   * Sets the checks if is members visible.
   * 
   * @param isMembersVisible
   *          the new checks if is members visible
   */
  public void setIsMembersVisible(final boolean isMembersVisible) {
    setMembersVisible(isMembersVisible);
  }

  /**
   * Sets the members visible.
   * 
   * @param isMembersVisible
   *          the new members visible
   */
  public void setMembersVisible(final boolean isMembersVisible) {
    this.isMembersVisible = isMembersVisible;
  }

  /**
   * Sets the social network visibility.
   * 
   * @param socialNetworkVisibility
   *          the new social network visibility
   */
  public void setSocialNetworkVisibility(final SocialNetworkVisibility socialNetworkVisibility) {
    this.socialNetworkVisibility = socialNetworkVisibility;
  }

  /**
   * Sets the user buddies.
   * 
   * @param userBuddies
   *          the new user buddies
   */
  public void setUserBuddies(final UserBuddiesDataDTO userBuddies) {
    this.userBuddies = userBuddies;
  }

  /**
   * Sets the user buddies visibility.
   * 
   * @param userBuddiesVisibility
   *          the new user buddies visibility
   */
  public void setUserBuddiesVisibility(final UserSNetVisibility userBuddiesVisibility) {
    this.userBuddiesVisibility = userBuddiesVisibility;
  }

  /**
   * Sets the user participation.
   * 
   * @param userParticipation
   *          the new user participation
   */
  public void setUserParticipation(final ParticipationDataDTO userParticipation) {
    this.userParticipation = userParticipation;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "SocialNetworkResultDTO[members: " + groupMembers + "; participation: " + userParticipation
        + "; buddiesvisibility: " + userBuddiesVisibility + "]";
  }
}
