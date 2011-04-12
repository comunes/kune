package cc.kune.wave.server;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.integration.IntegrationTest;
import cc.kune.core.server.integration.IntegrationTestHelper;

import com.google.inject.Inject;

public class KuneWaveManagerDefaultTest extends IntegrationTest {

    @Inject
    KuneWaveManagerDefault manager;

    @Before
    public void before() {
        new IntegrationTestHelper(this);

    }

    @Test
    public void createWave() throws DefaultException, IOException {
        doLogin();
        manager.createWave(getSiteAdminShortName(), "testing");
    }
}
