package cc.kune.wave.server;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.waveprotocol.box.server.persistence.PersistenceException;

import com.google.inject.Injector;
import com.google.inject.servlet.ServletModule;

public class WaveModule extends ServletModule {

    private static final Log LOG = LogFactory.getLog(WaveModule.class);

    @Override
    protected void configureServlets() {
        super.configureServlets();

    }

    public void runWaveServer(Injector settingsInjector) throws IOException, PersistenceException,
            ConfigurationException {

    }

}
