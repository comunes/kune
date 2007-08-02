package org.ourproject.kune.platf.server;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import com.wideplay.warp.persist.PersistenceService;

public class KunePlatformModule extends AbstractModule {
    @Override
    protected void configure() {
        install(PersistenceService.usingJpa().buildModule());
        bindInterceptor(Matchers.any(), Matchers.any(), new LoggerMethodInterceptor());
    }
}
