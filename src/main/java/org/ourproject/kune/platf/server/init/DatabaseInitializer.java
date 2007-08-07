package org.ourproject.kune.platf.server.init;

import org.ourproject.kune.platf.server.KunePlatformModule;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.properties.PropertiesFileName;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.wideplay.warp.jpa.JpaUnit;
import com.wideplay.warp.persist.PersistenceService;

public class DatabaseInitializer {
    @Inject
    UserManager manager;

    public static void main(final String[] args) {
	DatabaseInitializer databaseInitializer = new DatabaseInitializer();
	Injector injector = Guice.createInjector(new KunePlatformModule(), new Module() {
	    public void configure(Binder binder) {
		binder.bindConstant().annotatedWith(JpaUnit.class).to("development");
		binder.bindConstant().annotatedWith(PropertiesFileName.class).to("kune.dev.properties");
	    }
	});
	injector.injectMembers(databaseInitializer);
	injector.getInstance(PersistenceService.class).start();
	databaseInitializer.start();
    }

    // TODO: sacar al properties
    void start() {
	createUsers();
	createLicenses();
    }

    private void createUsers() {
	User user = new User("administrator", "admin", "kune_admin@localhost", "admin");
	manager.createUser(user);
	user = new User("site", "site", "kune_site@localhost", "site");
	manager.createUser(user);
    }

    private void createLicenses() {
    }

}
