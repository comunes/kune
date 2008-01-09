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

package org.ourproject.kune.platf.client.rpc;

import java.util.Date;

import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.dto.StateDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ContentServiceMocked extends MockedService implements ContentServiceAsync {

    public void getContent(final String user, final String groupShortName, final StateToken newState,
            final AsyncCallback callback) {
        StateDTO content = new StateDTO();
        ContainerDTO folder = new ContainerDTO();
        folder.setId(new Long(1));
        content.setFolder(folder);

        GroupDTO group = new GroupDTO();
        group.setShortName("kune");
        content.setGroup(group);

        content.setToolName(DocumentClientTool.NAME);
        content.setContentRights(new AccessRightsDTO(false, true, true));

        content.setContent("this is the content");

        answer(content, callback);
    }

    public void save(final String user, final String groupShortName, final String documentId, final String content,
            final AsyncCallback asyncCallback) {
    }

    public void addContent(final String user, final String groupShortName, final Long parentFolderId,
            final String name, final AsyncCallback asyncCallback) {
    }

    public void addFolder(final String hash, final String groupShortName, final Long parentFolderId,
            final String title, final AsyncCallback callback) {
    }

    public void addRoom(final String user, final String groupShortName, final Long parentFolderId, final String name,
            final AsyncCallback callback) {
    }

    public void rateContent(final String userHash, final String groupShortName, final String documentId,
            final Double value, final AsyncCallback asyncCallback) {
    }

    public void addAuthor(final String userHash, final String groupShortName, final String documentId,
            final String authorShortName, final AsyncCallback asyncCallback) {
    }

    public void removeAuthor(final String userHash, final String groupShortName, final String documentId,
            final String authorShortName, final AsyncCallback asyncCallback) {
    }

    public void delContent(final String userHash, final String groupShortName, final String documentId,
            final AsyncCallback asyncCallback) {
    }

    public void setLanguage(final String userHash, final String groupShortName, final String documentId,
            final String languageCode, final AsyncCallback asyncCallback) {
    }

    public void setPublishedOn(final String userHash, final String groupShortName, final String documentId,
            final Date date, final AsyncCallback asyncCallback) {
    }

    public void setTags(final String userHash, final String groupShortName, final String documentId, final String tags,
            final AsyncCallback asyncCallback) {
    }

    public void renameContent(final String userHash, final String groupShortName, final String documentId,
            final String newTitle, final AsyncCallback asyncCallback) {
    }

    public void renameFolder(final String hash, final String groupShortName, final Long folderId, final String title,
            final AsyncCallback asyncCallback) {
    }

    public void rename(final String userHash, final String groupShortName, final String token, final String newName,
            final AsyncCallback asyncCallback) {
    }

    public void getSummaryTags(final String userHash, final String groupShortName, final AsyncCallback asyncCallback) {
    }

}
