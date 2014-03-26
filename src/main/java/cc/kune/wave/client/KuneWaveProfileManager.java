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
package cc.kune.wave.client;

import org.waveprotocol.box.profile.ProfileResponse;
import org.waveprotocol.box.profile.ProfileResponse.FetchedProfile;
import org.waveprotocol.box.webclient.client.Session;
import org.waveprotocol.box.webclient.profile.FetchProfilesService;
import org.waveprotocol.box.webclient.profile.FetchProfilesServiceImpl;
import org.waveprotocol.wave.client.account.impl.AbstractProfileManager;
import org.waveprotocol.wave.client.account.impl.ProfileImpl;
import org.waveprotocol.wave.client.debug.logger.DomLogger;
import org.waveprotocol.wave.common.logging.LoggerBundle;
import org.waveprotocol.wave.model.wave.InvalidParticipantAddress;
import org.waveprotocol.wave.model.wave.ParticipantId;

import cc.kune.chat.client.LastConnectedManager;
import cc.kune.common.client.log.Log;

import com.google.inject.Inject;

/**
 * The Class KuneWaveProfileManager is a workaround to show avatars in kune
 * while the Wave part is more mature (see in the future
 * RemoteProfileManagerImpl)
 * 
 */
public class KuneWaveProfileManager extends AbstractProfileManager<ProfileImpl> implements
    FetchProfilesService.Callback {

  private final static LoggerBundle LOG = new DomLogger("fetchProfiles");

  static private void deserializeAndUpdateProfile(final KuneWaveProfileManager manager,
      final FetchedProfile fetchedProfile) {
    final ParticipantId participantId = ParticipantId.ofUnsafe(fetchedProfile.getAddress());
    final ProfileImpl profile = manager.getProfile(participantId);
    // Profiles already exist for all profiles that have been requested.
    assert profile != null;
    // Updates profiles - this also notifies listeners.
    profile.update(fetchedProfile.getName(), fetchedProfile.getName(), fetchedProfile.getImageUrl());
  }

  /**
   * Deserializes {@link ProfileResponse} and updates the profiles.
   */
  static void deserializeResponseAndUpdateProfiles(final KuneWaveProfileManager manager,
      final ProfileResponse profileResponse) {
    int i = 0;
    for (final FetchedProfile fetchedProfile : profileResponse.getProfiles()) {
      deserializeAndUpdateProfile(manager, fetchedProfile);
      i++;
    }
  }

  private final FetchProfilesServiceImpl fetchProfilesService;

  private final LastConnectedManager lastConnectedManager;

  @Inject
  public KuneWaveProfileManager(final LastConnectedManager lastConnectedManager) {
    this.lastConnectedManager = lastConnectedManager;
    fetchProfilesService = FetchProfilesServiceImpl.create();
  }

  public String getAddress(final String kuneUsername) {
    return kuneUsername + "@" + Session.get().getDomain();
  }

  @Override
  public ProfileImpl getProfile(final ParticipantId participantId) {
    return refreshProfile(participantId, true, false);
  }

  public String getUsername(final String address) {
    return address.split("@")[0];
  }

  public boolean isLocal(final String address) {
    return Session.get().getDomain() != null && address.contains(Session.get().getDomain());
  }

  @Override
  public void onFailure(final String message) {
    LOG.error().log(message);
    // TODO (user) Try to re-fetch the profile.
  }

  @Override
  public void onSuccess(final ProfileResponse profileResponse) {
    deserializeResponseAndUpdateProfiles(this, profileResponse);
  }

  public void refreshAddress(final String address, final boolean noCache) {
    try {
      refreshProfile(ParticipantId.of(address), true, noCache);
    } catch (final InvalidParticipantAddress e) {
      Log.error("Invalid participant address: " + address, e);
    }
  }

  private ProfileImpl refreshProfile(final ParticipantId participantId, final boolean refresh,
      final boolean noCache) {
    final String address = participantId.getAddress();
    ProfileImpl profile = profiles.get(address);
    if (profile == null) {
      profile = new ProfileImpl(this, participantId);
      profiles.put(address, profile);
      LOG.trace().log("Fetching profile: " + address);
      fetchProfilesService.fetch(this, address);

      updateStatus(profile);

      profiles.put(address, profile);
    } else if (refresh) {
      // FIXME This hangs weblclient in stage two
      // updateStatus(profile);
      // checkAvatar(profile, noCache);
      // profiles.put(address, profile);
    }
    return profile;
  }

  private void updateStatus(final ProfileImpl profile) {
    final String full = profile.getFullName();
    final String address = profile.getAddress();
    if (isLocal(address)) {
      profile.update(profile.getFirstName(),
          full + lastConnectedManager.get(getUsername(address), false), profile.getImageUrl());
    }
  }

}
