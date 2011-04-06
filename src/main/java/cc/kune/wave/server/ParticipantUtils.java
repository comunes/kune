package cc.kune.wave.server;

import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.wave.model.wave.InvalidParticipantAddress;
import org.waveprotocol.wave.model.wave.ParticipantId;

import cc.kune.core.client.errors.DefaultException;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class ParticipantUtils {

    private final String domain;

    @Inject
    public ParticipantUtils(@Named(CoreSettings.WAVE_SERVER_DOMAIN) final String domain) {
        this.domain = domain;
    }

    public ParticipantId of(final String username) {
        try {
            if (username.contains(ParticipantId.DOMAIN_PREFIX)) {
                return ParticipantId.of(username);
            } else {
                return ParticipantId.of(username + ParticipantId.DOMAIN_PREFIX + domain);
            }
        } catch (final InvalidParticipantAddress e) {
            throw new DefaultException("Error getting Wave participant Id");
        }
    }
}
