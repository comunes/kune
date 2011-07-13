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
package cc.kune.wave.client;

import org.waveprotocol.box.webclient.client.Session;
import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.account.impl.AbstractProfileManager;
import org.waveprotocol.wave.client.account.impl.ProfileImpl;
import org.waveprotocol.wave.model.wave.ParticipantId;

import cc.kune.core.client.services.FileDownloadUtils;

import com.google.inject.Inject;

/**
 * The Class KuneWaveProfileManager is a workaround to show avatars in kune
 * while the Wave part is more mature
 * 
 */
public class KuneWaveProfileManager extends AbstractProfileManager<ProfileImpl> implements
    ProfileManager {

  private final FileDownloadUtils downloadUtils;

  @Inject
  public KuneWaveProfileManager(final FileDownloadUtils downloadUtils) {
    this.downloadUtils = downloadUtils;
  }

  private void checkAvatar(final ProfileImpl profile) {
    final String address = profile.getAddress();
    if (address.contains(Session.get().getDomain())) {
      profile.update(profile.getFirstName(), profile.getFullName(),
          downloadUtils.getUserAvatar(address.split("@")[0]));
    }
  }

  @Override
  public ProfileImpl getProfile(final ParticipantId participantId) {
    ProfileImpl profile = profiles.get(participantId.getAddress());

    if (profile == null) {
      profile = new ProfileImpl(this, participantId);
      checkAvatar(profile);
      profiles.put(participantId.getAddress(), profile);
    }
    return profile;
  }

}