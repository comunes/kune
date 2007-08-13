package org.ourproject.kune.platf.server.manager;

import java.util.List;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.License;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class LicenseManager extends DefaultManager<License, Long> {
    private License licenseFinder;

    @Inject
    public LicenseManager(final Provider<EntityManager> provider) {
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
