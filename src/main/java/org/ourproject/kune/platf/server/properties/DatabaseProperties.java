
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
        return properties.get("kune.default.site.shortName");
    }

    public String getDefaultSiteName() {
        return properties.get("kune.default.site.name");
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

    public String getDefaultLicense() {
        return properties.get("kune.default.license");
    }
}
