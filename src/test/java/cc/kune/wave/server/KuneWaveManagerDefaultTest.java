package cc.kune.wave.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.waveprotocol.wave.model.waveref.WaveRef;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.integration.IntegrationTest;
import cc.kune.core.server.integration.IntegrationTestHelper;

import com.google.inject.Inject;
import com.google.wave.api.Wavelet;

public class KuneWaveManagerDefaultTest extends IntegrationTest {

    private static final String MESSAGE = "testing";
    private static final String NEW_PARTICIPANT = "newparti";
    private static final String TITLE = "title";
    private static final String TITLENEW = "titleNew";
    @Inject
    KuneWaveManagerDefault manager;
    @Inject
    ParticipantUtils participantUtils;

    private void addParticipant(final String whoAdds) throws IOException {
        doLogin();
        final WaveRef waveletName = manager.createWave(TITLE, MESSAGE, participantUtils.of(getSiteAdminShortName()));
        assertNotNull(waveletName);
        manager.addParticipant(waveletName, getSiteAdminShortName(), whoAdds, NEW_PARTICIPANT);
        final Wavelet fetchWavelet = manager.fetchWavelet(waveletName, getSiteAdminShortName());
        assertNotNull(fetchWavelet);
        assertEquals(2, fetchWavelet.getParticipants().size());
        manager.isParticipant(fetchWavelet, NEW_PARTICIPANT);
    }

    @Test
    public void authorAddParticipant() throws DefaultException, IOException {
        final String whoAdds = getSiteAdminShortName();
        addParticipant(whoAdds);
    }

    @Before
    public void before() {
        new IntegrationTestHelper(this);

    }

    @Test
    public void createWave() throws DefaultException, IOException {
        doLogin();
        final WaveRef waveletName = manager.createWave(MESSAGE, participantUtils.of(getSiteAdminShortName()));
        assertNotNull(waveletName);
        final Wavelet fetchWavelet = manager.fetchWavelet(waveletName, getSiteAdminShortName());
        assertNotNull(fetchWavelet);
        assertTrue(fetchWavelet.getRootBlip().getContent().contains(MESSAGE));
    }

    @Test
    public void createWaveWithTitle() throws DefaultException, IOException {
        doLogin();
        final WaveRef waveletName = manager.createWave(TITLE, MESSAGE, participantUtils.of(getSiteAdminShortName()));
        assertNotNull(waveletName);
        final Wavelet fetchWavelet = manager.fetchWavelet(waveletName, getSiteAdminShortName());
        assertNotNull(fetchWavelet);
        assertTrue(fetchWavelet.getRootBlip().getContent().contains(MESSAGE));
        assertEquals(TITLE, fetchWavelet.getTitle());
    }

    @Test
    public void otherMemberAddParticipant() throws DefaultException, IOException {
        final String whoAdds = "otherMember";
        addParticipant(whoAdds);
    }

    @Test
    public void setTitle() throws DefaultException, IOException {
        doLogin();
        final WaveRef waveletName = manager.createWave(TITLE, MESSAGE, participantUtils.of(getSiteAdminShortName()));
        assertNotNull(waveletName);
        manager.setTitle(waveletName, TITLENEW, getSiteAdminShortName());
        final Wavelet fetchWavelet = manager.fetchWavelet(waveletName, getSiteAdminShortName());
        assertNotNull(fetchWavelet);
        assertTrue(fetchWavelet.getRootBlip().getContent().contains(MESSAGE));
        assertEquals(TITLENEW, fetchWavelet.getTitle());
    }

}
