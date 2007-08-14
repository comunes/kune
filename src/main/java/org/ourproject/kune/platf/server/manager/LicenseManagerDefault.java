package org.ourproject.kune.platf.server.manager;

import java.util.List;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.License;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class LicenseManagerDefault extends DefaultManager<License, Long> implements LicenseManager {
    private License licenseFinder;

    @Inject
    public LicenseManagerDefault(final Provider<EntityManager> provider) {
	super(provider, License.class);
    }

    public List<License> getAll() {
	return licenseFinder.getAll();
    }

    public License createLicense(final License license) {
	return persist(license);
    }

    @Inject
    public void setLicenseFinder(final License licenseFinder) {
	this.licenseFinder = licenseFinder;
    }

}
