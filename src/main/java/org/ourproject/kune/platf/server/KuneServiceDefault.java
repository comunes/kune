package org.ourproject.kune.platf.server;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.mapper.Mapper;

import com.wideplay.warp.persist.Transactional;

public class KuneServiceDefault implements KuneService {
    private Mapper mapper;
    private UserSession userSession;

    @Transactional
    public GroupDTO getDefaultGroup(final String userHash) {
	User user = userSession.getUser();
	return mapper.map(user.getUserGroup(), GroupDTO.class);
    }

    public void setUserSession(final UserSession userSession) {
	this.userSession = userSession;
    }

}
