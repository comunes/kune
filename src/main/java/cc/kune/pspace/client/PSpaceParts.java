package cc.kune.pspace.client;

import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class PSpaceParts {

    @Inject
    public PSpaceParts(final Session session, final Provider<PSpacePresenter> pspacePresenter) {
        session.onAppStart(true, new AppStartHandler() {
            @Override
            public void onAppStart(final AppStartEvent event) {
                pspacePresenter.get();
            }
        });
    }
}
