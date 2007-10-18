/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.server.rpc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.rpc.GroupService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.AdmissionType;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.mapper.Mapper;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

@Singleton
public class GroupRPC implements RPC, GroupService {
    private final Mapper mapper;
    private final GroupManager groupManager;
    private final UserSession session;
    private static final Log log = LogFactory.getLog(GroupRPC.class);

    @Inject
    public GroupRPC(final UserSession session, final GroupManager groupManager, final Mapper mapper) {
	this.session = session;
	this.groupManager = groupManager;
	this.mapper = mapper;
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public StateToken createNewGroup(final String userHash, final GroupDTO group) throws SerializableException {
	log.debug(group.getShortName() + group.getLongName() + group.getPublicDesc() + group.getDefaultLicense()
		+ group.getType());
	final User user = session.getUser();
	final Group newGroup = groupManager.createGroup(mapper.map(group, Group.class), user);
	if (group.getType() == GroupDTO.COMMUNITY) {
	    newGroup.setAdmissionType(AdmissionType.Open);
	}
	if (group.getType() == GroupDTO.ORGANIZATION) {
	    newGroup.setAdmissionType(AdmissionType.Moderated);
	}
	if (group.getType() == GroupDTO.PROJECT) {
	    newGroup.setAdmissionType(AdmissionType.Moderated);
	}
	return new StateToken(newGroup.getDefaultContent().getStateToken());
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void changeGroupWsTheme(final String userHash, final String groupShortName, final String theme)
	    throws AccessViolationException {
	// TODO Auto-generated method stub
	final User user = session.getUser();
	Group group = groupManager.findByShortName(groupShortName);
	groupManager.changeWsTheme(user, group, theme);
    }

}
