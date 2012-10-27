/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.shared.FileConstants;

import com.google.inject.Inject;

/**
 * The Class KuneWaveProfileManager is a workaround to show avatars in kune
 * while the Wave part is more mature (see in the future
 * RemoteProfileManagerImpl)
 * 
 */
public class KuneWaveProfileManager extends AbstractProfileManager<ProfileImpl> implements
    ProfileManager {

  private final ClientFileDownloadUtils downloadUtils;
  private String localDomain;

  @Inject
  public KuneWaveProfileManager(final ClientFileDownloadUtils downloadUtils) {
    this.downloadUtils = downloadUtils;
  }

  private void checkAvatar(final ProfileImpl profile) {
    if (localDomain == null) {
      localDomain = "@" + Session.get().getDomain();
    }
    final String address = profile.getAddress();
    if (address.equals(localDomain) || address.equals("@")) {
      updateProfileAvatar(profile, FileConstants.WORLD_AVATAR_IMAGE);
    } else if (Session.get().getDomain() != null && address.contains(Session.get().getDomain())) {
      updateProfileAvatar(profile, downloadUtils.getUserAvatar(address.split("@")[0]));
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

  private void updateProfileAvatar(final ProfileImpl profile, final String avatar) {
    profile.update(profile.getFirstName(), profile.getFullName(), avatar);
  }

}
