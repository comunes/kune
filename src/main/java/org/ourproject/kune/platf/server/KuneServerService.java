package org.ourproject.kune.platf.server;

import java.util.ArrayList;
import java.util.List;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.LicenseManager;
import org.ourproject.kune.platf.server.mapper.Mapper;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class KuneServerService implements KuneService {
    private final Mapper mapper;
    private final GroupManager groupManager;
    private final UserSession session;
    private final LicenseManager licenseManager;

    @Inject
    public KuneServerService(final UserSession session, final GroupManager groupManager,
	    final LicenseManager licenseManager, final Mapper mapper) {
	this.session = session;
	this.groupManager = groupManager;
	this.licenseManager = licenseManager;
	this.mapper = mapper;
    }

    public void createNewGroup(final GroupDTO group) throws SerializableException {
	groupManager.create(session.getUser(), group);
    }

    public List getAllLicenses() throws SerializableException {
	return mapper.map(licenseManager.getAll(), ArrayList.class);
    }

    public List getNotCCLicenses() throws SerializableException {
	// TODO: see if is necessary;
	return null;
    }

}
