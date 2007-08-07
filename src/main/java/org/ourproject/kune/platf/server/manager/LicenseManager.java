package org.ourproject.kune.platf.server.manager;

import java.util.List;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.License;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

public class LicenseManager extends DefaultManager {
    private License licenseFinder;

    @Inject
    public LicenseManager(final Provider<EntityManager> provider) {
	super(provider);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List<License> getAll() {
	return licenseFinder.getAll();
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public License createLicense(final License license) {
	getEntityManager().persist(license);
	return license;
    }

    @Inject
    public void setLicenseFinder(final License licenseFinder) {
	this.licenseFinder = licenseFinder;
    }

}
