package org.ourproject.kune.platf.server.init;

import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.LicenseManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

public class DatabaseInitializer {
    private final LicenseManager licenseManager;
    private final UserManager userManager;
    private final DatabaseProperties properties;

    @Inject
    public DatabaseInitializer(final DatabaseProperties properties, final UserManager userManager,
	    final LicenseManager licenseManager) {
	this.properties = properties;
	this.userManager = userManager;
	this.licenseManager = licenseManager;
    }

    public void initConditional() throws SerializableException {
	String shortName = properties.getDefaultSiteShortName();
	User user = userManager.getByShortName(shortName);
	if (user == null) {
	    initDatabase();
	}
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void initDatabase() throws SerializableException {
	createUsers();
	createLicenses();
    }

    private void createUsers() throws SerializableException {
	String adminName = properties.getAdminUserName();
	String adminShortName = properties.getAdminShortName();
	String adminEmail = properties.getAdminEmail();
	String adminPassword = properties.getAdminPassword();
	User user = new User(adminName, adminShortName, adminEmail, adminPassword);
	userManager.createUser(user);

	String siteName = properties.getDefaultSiteName();
	String siteShortName = properties.getDefaultSiteShortName();
	String siteEmail = properties.getDefaultSiteAdminEmail();
	String sitePassword = properties.getDefaultSiteAdminPassword();
	user = new User(siteName, siteShortName, siteEmail, sitePassword);
	userManager.createUser(user);
    }

    private void createLicenses() {
	License license = new License("by", "Creative Commons Attribution", "",
		"http://creativecommons.org/licenses/by/3.0/", true, false, false, "", "");
	licenseManager.persist(license);
	license = new License("by-sa", "Creative Commons Attribution-ShareAlike", "",
		"http://creativecommons.org/licenses/by-sa/3.0/", true, true, false, "", "");
	licenseManager.persist(license);
	license = new License("by-nd", "Creative Commons Attribution-NoDerivs", "",
		"http://creativecommons.org/licenses/by-nd/3.0/", true, false, false, "", "");
	licenseManager.persist(license);
	license = new License("by-nc", "Creative Commons Attribution-NonCommercial", "",
		"http://creativecommons.org/licenses/by-nc/3.0/", true, false, false, "", "");
	licenseManager.persist(license);
	license = new License("by-nc-sa", "Creative Commons Attribution-NonCommercial-ShareAlike", "",
		"http://creativecommons.org/licenses/by-nc-sa/3.0/", true, false, false, "", "");
	licenseManager.persist(license);
	license = new License("by-nc-nd", "Creative Commons Attribution-NonCommercial-NoDerivs", "",
		"http://creativecommons.org/licenses/by-nc-nd/3.0/", true, false, false, "", "");
	licenseManager.persist(license);
	license = new License("gfdl", "GNU Free Documentation License", "", "http://www.gnu.org/copyleft/fdl.html",
		false, true, false, "", "");
	licenseManager.persist(license);
    }

}
