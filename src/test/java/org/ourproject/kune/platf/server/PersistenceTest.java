package org.ourproject.kune.platf.server;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Before;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.wideplay.warp.persist.PersistenceService;

public abstract class PersistenceTest {
    @Inject
    Provider<EntityManager> provider;
    private final String persistenceUnit;
    private final String propetiesFileName;

    public PersistenceTest(final String persistenceUnit, final String propetiesFileName) {
	this.persistenceUnit = persistenceUnit;
	this.propetiesFileName = propetiesFileName;
    }

    public PersistenceTest() {
	// test: use memory
	// test_db: use mysql
	this("test", "kune.properties");
    }

    @Before
    public void prepare() {
	final Injector injector = TestHelper.create(new PlatformServerModule(), persistenceUnit, propetiesFileName);
	final PersistenceService persistence = injector.getInstance(PersistenceService.class);
	// To Debug insert breakpoint here
	persistence.start();
	injector.injectMembers(this);
    }

    public EntityManager openTransaction() {
	final EntityManager manager = getManager();
	final EntityTransaction transaction = manager.getTransaction();
	transaction.begin();
	return manager;
    }

    public void closeTransaction() {
	getManager().getTransaction().commit();
    }

    public void rollbackTransaction() {
	getManager().getTransaction().rollback();
    }

    public void persist(final Object... entities) {
	for (final Object entity : entities) {
	    getManager().persist(entity);
	}
    }

    private EntityManager getManager() {
	return provider.get();
    }

    public EntityTransaction getTransaction() {
	return getManager().getTransaction();
    }

}
