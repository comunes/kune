package org.ourproject.kune.platf.server.init;

import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.LicenseManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.properties.KuneProperties;

import com.google.inject.Inject;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

public class DatabaseInitializer {
    @Inject
    UserManager userManager;
    @Inject
    LicenseManager licenseManager;
    @Inject
    KuneProperties properties;

    public void initConditional() {
	properties.get(KuneProperties.DEFAULT_SITE_SHORT_NAME);
	User user = userManager.getByShortName("site");
	if (user == null) {
	    initDatabase();
	}
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void initDatabase() {
	createUsers();
	createLicenses();
    }

    private void createUsers() {
	String adminName = "administrator";
	String adminShortName = "admin";
	String adminEmail = "kune_admin@localhost";
	String adminPassword = "psw4admin";
	User user = new User(adminName, adminShortName, adminEmail, adminPassword);
	userManager.createUser(user);

	String sitePassword = "site";
	String siteName = "default";
	String siteShortName = properties.get(KuneProperties.DEFAULT_SITE_SHORT_NAME);
	String siteEmail = "kune_site@localhost";
	user = new User(siteName, siteShortName, siteEmail, sitePassword);
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
