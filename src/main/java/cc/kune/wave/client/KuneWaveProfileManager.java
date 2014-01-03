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

import org.waveprotocol.box.webclient.client.Session;
import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.account.impl.AbstractProfileManager;
import org.waveprotocol.wave.client.account.impl.ProfileImpl;
import org.waveprotocol.wave.model.wave.InvalidParticipantAddress;
import org.waveprotocol.wave.model.wave.ParticipantId;

import cc.kune.chat.client.LastConnectedManager;
import cc.kune.common.client.log.Log;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.shared.FileConstants;

import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.google.inject.Inject;

/**
 * The Class KuneWaveProfileManager is a workaround to show avatars in kune
 * while the Wave part is more mature (see in the future
 * RemoteProfileManagerImpl)
 * 
 */
public class KuneWaveProfileManager extends AbstractProfileManager<ProfileImpl> implements
    ProfileManager {
  // TODO implement remote part of RemoteProfileManagerImpl

  private final ClientFileDownloadUtils downloadUtils;
  private final LastConnectedManager lastConnectedManager;
  private String localDomain;

  @Inject
  public KuneWaveProfileManager(final ClientFileDownloadUtils downloadUtils,
      final LastConnectedManager lastConnectedManager) {
    this.downloadUtils = downloadUtils;
    this.lastConnectedManager = lastConnectedManager;
  }

  private void checkAvatar(final ProfileImpl profile, final boolean noCache) {
    if (localDomain == null) {
      localDomain = "@" + Session.get().getDomain();
    }
    final String address = profile.getAddress();
    if (address.equals(localDomain) || address.equals("@")) {
      updateProfileAvatar(profile, FileConstants.WORLD_AVATAR_IMAGE);

    } else if (isLocal(address)) {
      updateProfileAvatar(profile, downloadUtils.getUserAvatar(getUsername(address), noCache));
    }
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
      updateStatus(profile);
      checkAvatar(profile, noCache);
      profiles.put(address, profile);
    } else if (refresh) {
      // FIXME This hangs weblclient in stage two
      // updateStatus(profile);
      // checkAvatar(profile, noCache);
      // profiles.put(address, profile);
    }
    return profile;
  }

  private void refreshRosterItem(final XmppURI uri, final boolean noCache) {
    refreshAddress(uri.toString(), noCache);
  }

  private void updateProfileAvatar(final ProfileImpl profile, final String avatar) {
    profile.update(profile.getFirstName(), profile.getFullName(), avatar);
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
