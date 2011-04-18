package cc.kune.client;

import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
import cc.kune.core.client.state.Session;
import cc.kune.docs.client.DocsClientTool;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class OnAppStartFactory {

    @Inject
    public OnAppStartFactory(final Session session, final Provider<DocsClientTool> docClientTool) {
        session.onAppStart(true, new AppStartHandler() {
            @Override
            public void onAppStart(final AppStartEvent event) {
                docClientTool.get();
            }
        });
    }
}
