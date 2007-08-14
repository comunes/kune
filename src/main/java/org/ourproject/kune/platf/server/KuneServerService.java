package org.ourproject.kune.platf.server;

import java.util.List;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.server.manager.GroupManagerDefault;
import org.ourproject.kune.platf.server.mapper.Mapper;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class KuneServerService implements KuneService {
    private final Mapper mapper;
    private final GroupManagerDefault groupManager;
    private final UserSession session;

    @Inject
    public KuneServerService(final UserSession session, final GroupManagerDefault groupManager, final Mapper mapper) {
	this.session = session;
	this.groupManager = groupManager;
	this.mapper = mapper;
    }

    public void createNewGroup(final GroupDTO group) throws SerializableException {
	groupManager.create(session.getUser(), group);
    }

    public List getAllLicenses() throws SerializableException {
	// TODO Auto-generated method stub
	return null;
    }

    public List getNotCCLicenses() throws SerializableException {
	// TODO Auto-generated method stub
	return null;
    }

}
