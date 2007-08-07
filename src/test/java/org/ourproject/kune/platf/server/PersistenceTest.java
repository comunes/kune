package org.ourproject.kune.platf.server;

import javax.persistence.EntityManager;

import org.junit.Before;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.wideplay.warp.persist.PersistenceService;

public abstract class PersistenceTest {
    @Inject
    Provider<EntityManager> provider;

    @Before
    public void prepare() {
	Injector injector = TestHelper.create(new KunePlatformModule());
	PersistenceService persistence = injector.getInstance(PersistenceService.class);
	persistence.start();
	injector.injectMembers(this);
    }

    public void openTransaction() {
	getManager().getTransaction().begin();
    }

    public void closeTransaction() {
	getManager().getTransaction().rollback();
    }

    public void persist(final Object entity) {
	getManager().persist(entity);
    }

    private EntityManager getManager() {
	return provider.get();
    }

}
