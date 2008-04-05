package org.ourproject.kune.blogs.server;

import com.google.inject.Binder;
import com.google.inject.Module;

public class BlogServerModule implements Module {
    public void configure(final Binder binder) {
	binder.bind(BlogServerTool.class).asEagerSingleton();
    }
}
