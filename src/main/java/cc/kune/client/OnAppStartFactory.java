package cc.kune.client;

import cc.kune.blogs.client.BlogsClientTool;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
import cc.kune.core.client.state.Session;
import cc.kune.docs.client.DocsClientTool;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class OnAppStartFactory {

    @Inject
    public OnAppStartFactory(final Session session, final Provider<DocsClientTool> docsClientTool,
            final Provider<BlogsClientTool> blogsClientTool) {
        session.onAppStart(true, new AppStartHandler() {
            @Override
            public void onAppStart(final AppStartEvent event) {
                docsClientTool.get();
                blogsClientTool.get();
            }
        });
    }
}
