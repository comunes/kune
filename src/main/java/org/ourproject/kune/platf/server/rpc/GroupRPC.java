/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.server.rpc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.DefaultException;
import org.ourproject.kune.platf.client.rpc.GroupService;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.access.AccessRol;
import org.ourproject.kune.platf.server.auth.ActionLevel;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.auth.Authorizated;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.content.ContentUtils;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.mapper.Mapper;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

@Singleton
public class GroupRPC implements RPC, GroupService {
    private static final Log log = LogFactory.getLog(GroupRPC.class);
    private final Mapper mapper;
    private final GroupManager groupManager;
    private final Provider<UserSession> userSessionProvider;
    private final ContentManager contentManager;

    @Inject
    public GroupRPC(final Provider<UserSession> userSessionProvider, final GroupManager groupManager,
	    final ContentManager contentManager, final Mapper mapper) {
	this.userSessionProvider = userSessionProvider;
	this.groupManager = groupManager;
	this.contentManager = contentManager;
	this.mapper = mapper;
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public void changeGroupWsTheme(final String userHash, final StateToken groupToken, final String theme)
	    throws DefaultException {
	final UserSession userSession = getUserSession();
	final User user = userSession.getUser();
	final Group group = groupManager.findByShortName(groupToken.getGroup());
	groupManager.changeWsTheme(user, group, theme);
    }

    @Authenticated
    @Transactional(type = TransactionType.READ_WRITE, rollbackOn = DefaultException.class)
    public StateToken createNewGroup(final String userHash, final GroupDTO groupDTO) throws DefaultException {
	log.debug(groupDTO.getShortName() + groupDTO.getLongName() + groupDTO.getPublicDesc()
		+ groupDTO.getDefaultLicense() + groupDTO.getType());
	final UserSession userSession = getUserSession();
	final User user = userSession.getUser();
	final Group group = mapper.map(groupDTO, Group.class);
	final Group newGroup = groupManager.createGroup(group, user);
	final Long defContentId = newGroup.getDefaultContent().getId();
	contentManager.setTags(user, defContentId, groupDTO.getTags());
	return newGroup.getDefaultContent().getStateToken();
    }

    @Authenticated
    @Authorizated(actionLevel = ActionLevel.group, accessRolRequired = AccessRol.Administrator)
    @Transactional(type = TransactionType.READ_WRITE)
    public GroupDTO setGroupLogo(final String userHash, final StateToken token) {
	final Group group = groupManager.findByShortName(token.getGroup());
	final Content content = contentManager.find(ContentUtils.parseId(token.getDocument()));
	groupManager.setGroupLogo(group, content);
	return mapper.map(group, GroupDTO.class);
    }

    private UserSession getUserSession() {
	return userSessionProvider.get();
    }

}
