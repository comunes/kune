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