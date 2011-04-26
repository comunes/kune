package cc.kune.blogs.client;

import cc.kune.core.client.state.Session;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BlogsParts {

    @Inject
    public BlogsParts(final Session session, final Provider<BlogsClientTool> clientTool) {
        clientTool.get();
    }
}