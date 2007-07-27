package org.ourproject.kune.app.server;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class KuneAppModule extends AbstractModule {
    @Override
    protected void configure() {
        bindInterceptor(Matchers.any(), Matchers.any(), new LoggerMethodInterceptor());
    }
}
