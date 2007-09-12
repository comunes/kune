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

package org.ourproject.kune.platf.server.state;

import org.ourproject.kune.platf.server.access.Access;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Data;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.License;

import com.google.inject.Singleton;

@Singleton
public class StateServiceDefault implements StateService {
    public State create(final Access access) {
	Content content = access.getContent();
	Container container = content.getFolder();
	State state = new State();

	Long documentId = content.getId();
	if (documentId != null) {
	    state.setTypeId(content.getTypeId());
	    state.setDocumentId(documentId.toString());
	} else {
	    state.setTypeId(container.getTypeId());
	    state.setDocumentId(null);
	}
	Data data = content.getLastRevision().getData();
	char[] text = data.getContent();
	state.setContent(text == null ? null : new String(text));
	if (documentId != null) {
	    state.setTitle(data.getTitle());
	} else {
	    state.setTitle(container.getName());
	}
	state.setToolName(container.getToolName());
	state.setGroup(container.getOwner());
	state.setFolder(container);
	state.setSocialNetwork(container.getOwner().getSocialNetwork());
	state.setAccessLists(access.getContentAccessLists());
	state.setContentRights(access.getContentRights());
	state.setFolderRights(access.getFolderRights());
	state.setRate(content.calculateRate(content));
	state.setRateByUsers(content.calculateRateNumberOfUsers(content));
	License contentLicense = content.getLicense();
	if (contentLicense == null) {
	    contentLicense = container.getOwner().getDefaultLicense();
	}
	state.setLicense(contentLicense);
	return state;
    }
}
