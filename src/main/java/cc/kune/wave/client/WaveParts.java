package cc.kune.wave.client;

import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class WaveParts {

    @Inject
    public WaveParts(final Session session, final Provider<WaveClientManager> waveClientManager,
            final Provider<SitebarWaveStatus> waveStatus) {
        session.onInitDataReceived(true, new AppStartHandler() {
            @Override
            public void onAppStart(final AppStartEvent event) {
                waveClientManager.get();
                waveStatus.get();
            }
        });
    }
}
