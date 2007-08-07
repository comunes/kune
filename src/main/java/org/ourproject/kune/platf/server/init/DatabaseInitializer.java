package org.ourproject.kune.platf.server.init;

import org.ourproject.kune.platf.server.KunePlatformModule;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.LicenseManager;
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
    UserManager userManager;
    @Inject
    LicenseManager licenseManager;

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
	userManager.createUser(user);
	user = new User("site", "site", "kune_site@localhost", "site");
	userManager.createUser(user);
    }

    private void createLicenses() {
	License license = new License("by", "Creative Commons Attribution", "",
		"http://creativecommons.org/licenses/by/3.0/", true, false, false, "", "");
	licenseManager.createLicense(license);
	license = new License("by-sa", "Creative Commons Attribution-ShareAlike", "",
		"http://creativecommons.org/licenses/by-sa/3.0/", true, true, false, "", "");
	licenseManager.createLicense(license);
	license = new License("by-nd", "Creative Commons Attribution-NoDerivs", "",
		"http://creativecommons.org/licenses/by-nd/3.0/", true, false, false, "", "");
	licenseManager.createLicense(license);
	license = new License("by-nc", "Creative Commons Attribution-NonCommercial", "",
		"http://creativecommons.org/licenses/by-nc/3.0/", true, false, false, "", "");
	licenseManager.createLicense(license);
	license = new License("by-nc-sa", "Creative Commons Attribution-NonCommercial-ShareAlike", "",
		"http://creativecommons.org/licenses/by-nc-sa/3.0/", true, false, false, "", "");
	licenseManager.createLicense(license);
	license = new License("by-nc-nd", "Creative Commons Attribution-NonCommercial-NoDerivs", "",
		"http://creativecommons.org/licenses/by-nc-nd/3.0/", true, false, false, "", "");
	licenseManager.createLicense(license);
	license = new License("gfdl", "GNU Free Documentation License", "", "http://www.gnu.org/copyleft/fdl.html",
		false, true, false, "", "");
	licenseManager.createLicense(license);
    }

}
