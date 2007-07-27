package org.ourproject.kune.platf.server.finders;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.ourproject.kune.GuiceTestHelper;
import org.ourproject.kune.platf.server.KunePlatformModule;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

public abstract class PersistenceTest {
    Provider<EntityManager> provider;

    @Before
    public void injectAndOpenTransaction() {
        Injector create = GuiceTestHelper.create(new KunePlatformModule());
        create.injectMembers(this);
        getManager().getTransaction().begin();
    }

    @After
    public void closeTransaction() {
        getManager().getTransaction().commit();
    }

    public void persist(Object entity) {
        getManager().persist(entity);
    }

    private EntityManager getManager() {
        return provider.get();
    }

    @Inject
    public void setProvider(Provider<EntityManager> provider) {
        this.provider = provider;
    }
}
