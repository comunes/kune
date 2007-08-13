package org.ourproject.kune.platf.server;

import org.ourproject.kune.platf.server.init.DatabaseInitializer;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.PersistenceService;

@Singleton
public class KunePersistenceService {
    @Inject
    DatabaseInitializer databaseInitializer;
    @Inject
    PersistenceService persistenceService;

    public void start() {
	persistenceService.start();
	databaseInitializer.initConditional();
    }

}
