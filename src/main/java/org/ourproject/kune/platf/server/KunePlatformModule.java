package org.ourproject.kune.platf.server;

import com.google.inject.AbstractModule;
import com.wideplay.warp.persist.PersistenceService;

public class KunePlatformModule extends AbstractModule {
    @Override
    protected void configure() {
        install(PersistenceService.usingJpa().buildModule());
    }
}
