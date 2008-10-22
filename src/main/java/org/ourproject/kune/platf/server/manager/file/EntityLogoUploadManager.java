package org.ourproject.kune.platf.server.manager.file;

import java.io.File;

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

    @Override
    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
    @Transactional(type = TransactionType.READ_WRITE)
    protected void createUploadedFile(String userHash, StateToken stateToken, String fileName, FileItem file)
            throws Exception {
        BasicMimeType mimeType = new BasicMimeType(file.getContentType());
        if (!mimeType.getType().equals("image")) {
            throw new Exception("Trying to set a non image (" + file.getContentType() + ") as group logo");
        }
        Group group = groupManager.findByShortName(stateToken.getGroup());

        if (group == Group.NO_GROUP) {
            throw new Exception("Group not found trying to set the logo");
        }

        File tmpOrigFile = File.createTempFile("logoOrig", "");
        file.write(tmpOrigFile);
        File tmpDestFile = File.createTempFile("logoDest", "");

        boolean result = ImageUtilsDefault.scaleImageToMax(tmpOrigFile.getAbsolutePath(),
                tmpDestFile.getAbsolutePath(), EntityLogoView.LOGO_ICON_DEFAULT_HEIGHT);
        if (result) {
            group.setLogo(FileUtils.getBytesFromFile(tmpDestFile));
            group.setLogoMime(mimeType);
        } else {
            throw new Exception("Cannot create group logo thumb");
        }
    }

}
