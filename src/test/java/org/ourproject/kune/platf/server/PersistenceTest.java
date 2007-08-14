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

    public PersistenceTest(final String persistenceUnit) {
	this.persistenceUnit = persistenceUnit;
    }

    public PersistenceTest() {
	this("test");
    }

    @Before
    public void prepare() {
	Injector injector = TestHelper.create(new KunePlatformModule(), persistenceUnit);
	PersistenceService persistence = injector.getInstance(PersistenceService.class);
	// To Debug insert breakpoint here
	persistence.start();
	injector.injectMembers(this);
    }

    public EntityManager openTransaction() {
	EntityManager manager = getManager();
	EntityTransaction transaction = manager.getTransaction();
	transaction.begin();
	return manager;
    }

    public void closeTransaction() {
	getManager().getTransaction().commit();
    }

    public void rollbackTransaction() {
	getManager().getTransaction().rollback();
    }

    public void persist(final Object entity) {
	getManager().persist(entity);
    }

    private EntityManager getManager() {
	return provider.get();
    }

    public EntityTransaction getTransaction() {
	return getManager().getTransaction();
    }

}
