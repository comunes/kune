package org.ourproject.kune.platf.server.finders;

import javax.persistence.EntityManager;

import org.junit.After;
import org.ourproject.kune.platf.server.KunePlatformModule;
import org.ourproject.kune.platf.server.properties.PropertiesFileName;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.wideplay.warp.jpa.JpaUnit;
import com.wideplay.warp.persist.PersistenceService;

public abstract class PersistenceTest {
    Provider<EntityManager> provider;

    public void prepare() {
        Injector create = create(new KunePlatformModule());
        create.injectMembers(this);
        getManager().getTransaction().begin();
    }

    public static Injector create(Module module) {
        Injector injector = Guice.createInjector(module , new Module () {
            public void configure(Binder binder) {
                binder.bindConstant().annotatedWith(JpaUnit.class).to("test");
                binder.bindConstant().annotatedWith(PropertiesFileName.class).to("kune.test.properties");
            }
        });
        PersistenceService persistence = injector.getInstance(PersistenceService.class);
        persistence.start();
        return injector;
    }


    @After
    public void closeTransaction() {
        getManager().getTransaction().rollback();
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
