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
package org.ourproject.kune.platf.server.manager.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import magick.MagickException;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.server.access.AccessRol;
import org.ourproject.kune.platf.server.auth.ActionLevel;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.auth.Authorizated;
import org.ourproject.kune.platf.server.domain.BasicMimeType;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogoView;

import com.google.inject.Inject;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

public class EntityLogoUploadManager extends FileJsonUploadManagerAbstract {

    private static final long serialVersionUID = 1L;

    @Inject
    GroupManager groupManager;

    @Override
    protected JSONObject createJsonResponse(final boolean success, final String message) {
        /**
         * Expect result with this format:
         * {"success":false,"errors":[{"id":"email","msg":"Already exists"},
         * {"id":"username","msg":"Already taken"}]}
         */

        JSONObject response = null;
        try {
            response = new JSONObject();
            JSONObject[] jsonError = new JSONObject[1];
            jsonError[0] = new JSONObject();
            if (!success) {
                jsonError[0].put("id", EntityLogoView.LOGO_FORM_FIELD);
                jsonError[0].put("msg", message);
            }
            response.put("errors", jsonError);
            response.put("success", success);
        } catch (final Exception e) {
            log.error("Error building response");
        }
        return response;
    }

    protected void createUploadedFile(StateToken stateToken, String mimeTypeS, File origFile) throws Exception,
            IOException, MagickException, FileNotFoundException {
        BasicMimeType mimeType = new BasicMimeType(mimeTypeS);
        if (!mimeType.getType().equals("image")) {
            throw new Exception("Trying to set a non image (" + mimeTypeS + ") as group logo");
        }
        Group group = groupManager.findByShortName(stateToken.getGroup());

        if (group == Group.NO_GROUP) {
            throw new Exception("Group not found trying to set the logo");
        }

        File tmpDestFile = File.createTempFile("logoDest", "");

        boolean result = ImageUtilsDefault.scaleImageToMax(origFile.getAbsolutePath(), tmpDestFile.getAbsolutePath(),
                EntityLogoView.LOGO_ICON_DEFAULT_HEIGHT);
        if (result) {
            group.setLogo(FileUtils.getBytesFromFile(tmpDestFile));
            group.setLogoMime(mimeType);
            tmpDestFile.delete();
        } else {
            tmpDestFile.delete();
            throw new Exception("Cannot create group logo thumb");
        }
    }

    @Override
    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
    @Transactional(type = TransactionType.READ_WRITE)
    protected void createUploadedFile(String userHash, StateToken stateToken, String fileName, FileItem file)
            throws Exception {
        String mimeTypeS = file.getContentType();
        File tmpOrigFile = File.createTempFile("logoOrig", "");
        file.write(tmpOrigFile);
        createUploadedFile(stateToken, mimeTypeS, tmpOrigFile);
        tmpOrigFile.delete();
    }

}
