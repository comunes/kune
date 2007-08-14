package org.ourproject.kune.platf.server.properties;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Sugar
 */
@Singleton
public class DatabaseProperties {
    private final KuneProperties properties;

    @Inject
    public DatabaseProperties(final KuneProperties properties) {
	this.properties = properties;
    }

    public String getDefaultSiteShortName() {
	return properties.get(KuneProperties.DEFAULT_SITE_SHORT_NAME);
    }

    public String getDefaultSiteName() {
	return properties.get("kune.default.site.name");
    }

    public String getDefaultSiteAdminEmail() {
	return properties.get("kune.default.site.admin.email");
    }

    public String getDefaultSiteAdminPassword() {
	return properties.get("kune.default.site.admin.password");
    }

    public String getAdminUserName() {
	return properties.get("kune.admin.name");
    }

    public String getAdminShortName() {
	return properties.get("kune.admin.shortName");
    }

    public String getAdminEmail() {
	return properties.get("kune.admin.email");
    }

    public String getAdminPassword() {
	return properties.get("kune.admin.password");
    }

}
