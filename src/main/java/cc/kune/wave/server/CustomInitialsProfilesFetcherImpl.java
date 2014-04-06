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

package cc.kune.wave.server;

import org.waveprotocol.box.server.robots.operations.FetchProfilesService.ProfilesFetcher;

import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.utils.SharedFileDownloadUtils;
import cc.kune.domain.Group;
import cc.kune.domain.finders.GroupFinder;
import cc.kune.wave.server.kspecific.ParticipantUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.wave.api.ParticipantProfile;

/**
 * A {@link ProfilesFetcher} implementation that assigns a default image URL for
 * the user avatar using it's initial and a random color
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class CustomInitialsProfilesFetcherImpl implements ProfilesFetcher {
  @Inject
  private SharedFileDownloadUtils downUtils;
  @Inject
  private GroupFinder groupFinder;
  @Inject
  private ParticipantUtils partUtils;

  @KuneTransactional
  @Override
  public ParticipantProfile fetchProfile(final String email) {
    ParticipantProfile pTemp = null;
    pTemp = ProfilesFetcher.SIMPLE_PROFILES_FETCHER.fetchProfile(email);
    String name = pTemp.getName();
    String imageUrl = getImageUrl(email);
    if (partUtils.isLocal(email)) {
      if (partUtils.getAtDomain().equals(email)) {
        // @localhost participant
        imageUrl = FileConstants.WORLD_AVATAR_IMAGE;
        // FIXME i18n
        name = "Anyone";
      } else {
        final String shortName = partUtils.getAddressName(email);
        final Group group = groupFinder.findByShortName(shortName);
        if (group != Group.NO_GROUP && group.hasLogo()) {
          // Know group and have a configured logo
          imageUrl = downUtils.getLogoImageUrl(shortName);
          name = group.getLongName();
        }
      }
    }
    final ParticipantProfile profile = new ParticipantProfile(email, name, imageUrl,
        pTemp.getProfileUrl());
    return profile;
  }

  /**
   * Returns the avatar URL for the given email address.
   */
  public String getImageUrl(final String email) {
    return "/iniavatars/100x100/" + email;
  }

}
