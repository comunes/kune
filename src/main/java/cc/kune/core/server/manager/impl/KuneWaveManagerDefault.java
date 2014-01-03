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
package cc.kune.core.server.manager.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;
import org.waveprotocol.wave.util.escapers.jvm.JavaWaverefEncoder;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.access.AccessRightsUtils;
import cc.kune.core.server.manager.KuneWaveManager;
import cc.kune.core.server.manager.UserManager;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;
import cc.kune.domain.Group;
import cc.kune.domain.User;
import cc.kune.domain.UserBuddiesData;
import cc.kune.domain.finders.GroupFinder;
import cc.kune.domain.finders.UserFinder;
import cc.kune.wave.server.kspecific.KuneWaveServerUtils;
import cc.kune.wave.server.kspecific.KuneWaveService;
import cc.kune.wave.server.kspecific.ParticipantUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.wave.api.Participants;

// TODO: Auto-generated Javadoc
/**
 * The Class KuneWaveManagerDefault.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class KuneWaveManagerDefault implements KuneWaveManager {

  /** The group finder. */
  private final GroupFinder groupFinder;

  /** The kune properties. */
  private final KuneProperties kuneProperties;

  /** The participant utils. */
  private final ParticipantUtils participantUtils;

  /** The user finder. */
  private final UserFinder userFinder;

  /** The user manager. */
  private final UserManager userManager;

  /** The wave service. */
  private final KuneWaveService waveService;

  /**
   * Instantiates a new kune wave manager default.
   * 
   * @param waveService
   *          the wave service
   * @param participantUtils
   *          the participant utils
   * @param kuneProperties
   *          the kune properties
   * @param groupFinder
   *          the group finder
   * @param userFinder
   *          the user finder
   * @param userManager
   *          the user manager
   */
  @Inject
  public KuneWaveManagerDefault(final KuneWaveService waveService,
      final ParticipantUtils participantUtils, final KuneProperties kuneProperties,
      final GroupFinder groupFinder, final UserFinder userFinder, final UserManager userManager) {
    this.waveService = waveService;
    this.participantUtils = participantUtils;
    this.kuneProperties = kuneProperties;
    this.groupFinder = groupFinder;
    this.userFinder = userFinder;
    this.userManager = userManager;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.KuneWaveManager#sendFeedback(cc.kune.domain
   * .User, java.lang.String, java.lang.String)
   */
  @Override
  public String sendFeedback(final User user, final String title, final String body) {
    final List<String> participants = kuneProperties.getList(KuneProperties.FEEDBACK_TO);
    participants.add(0, user.getShortName());
    final List<ParticipantId> partIds = new ArrayList<ParticipantId>();
    for (final String part : participants) {
      partIds.add(participantUtils.of(part));
    }
    return KuneWaveServerUtils.getUrl(waveService.createWave(title, body,
        KuneWaveService.DO_NOTHING_CBACK, partIds.toArray(new ParticipantId[0])));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.KuneWaveManager#writeTo(cc.kune.domain.User,
   * java.lang.String, boolean)
   */
  @Override
  public String writeTo(final User user, final String groupName, final boolean onlyToAdmins) {
    return writeTo(user, groupName, onlyToAdmins, KuneWaveService.NO_TITLE, KuneWaveService.NO_MESSAGE);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.KuneWaveManager#writeTo(cc.kune.domain.User,
   * java.lang.String, boolean, java.lang.String, java.lang.String)
   */
  @Override
  public String writeTo(final User user, final String groupName, final boolean onlyToAdmins,
      final String title, final String message) {
    final Group group = groupFinder.findByShortName(groupName);
    final Set<User> toList = new LinkedHashSet<User>();
    toList.add(user);
    if (group.isPersonal()) {
      final UserBuddiesData userBuddies = userManager.getUserBuddies(groupName);
      if (userBuddies.contains(user.getShortName())) {
        toList.add(userFinder.findByShortName(group.getShortName()));
      } else {
        throw new AccessViolationException("You cannot write to non buddies");
      }
    } else {
      if (!AccessRightsUtils.correctMember(user, group, AccessRol.Editor)) {
        throw new AccessViolationException("You cannot write because you are not a member");
      }
      GroupServerUtils.getAllUserMembers(toList, group, onlyToAdmins ? SocialNetworkSubGroup.ADMINS
          : SocialNetworkSubGroup.ALL_GROUP_MEMBERS);
    }
    return KuneWaveServerUtils.getUrl(waveService.createWave(title, message,
        KuneWaveService.DO_NOTHING_CBACK, participantUtils.listFrom(toList)));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.KuneWaveManager#writeToParticipants(java.lang
   * .String, java.lang.String, java.lang.String)
   */
  @Override
  public String writeToParticipants(final String author, final String from, final String waveId) {
    try {
      final Participants parts = waveService.getParticipants(
          JavaWaverefEncoder.decodeWaveRefFromPath(waveId), author);
      final List<String> list = new ArrayList<String>();
      // From the first in the list
      list.add(from);
      list.addAll(Arrays.asList(parts.toArray(new String[parts.size()])));
      return KuneWaveServerUtils.getUrl(waveService.createWave("", KuneWaveService.DO_NOTHING_CBACK,
          participantUtils.listFrom(list)));
    } catch (final InvalidWaveRefException e) {
      throw new DefaultException("Cannot access to the wave to copy");
    }
  }
}
