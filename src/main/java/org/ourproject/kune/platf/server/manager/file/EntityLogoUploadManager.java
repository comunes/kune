/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
 \*/
package org.ourproject.kune.platf.server.manager.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import magick.MagickException;

import org.apache.commons.fileupload.FileItem;
import org.ourproject.kune.platf.client.ui.download.FileConstants;
import org.ourproject.kune.platf.server.auth.ActionLevel;
import org.ourproject.kune.platf.server.auth.Authenticated;
import org.ourproject.kune.platf.server.auth.Authorizated;
import org.ourproject.kune.platf.server.manager.GroupManager;

import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.domain.BasicMimeType;
import cc.kune.domain.Group;

import com.google.inject.Inject;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

public class EntityLogoUploadManager extends FileUploadManagerAbstract {

    private static final long serialVersionUID = -4281058427935636238L;

    @Inject
    transient GroupManager groupManager;

    @Inject
    transient I18nTranslationService i18n;

    @Override
    protected void beforePostStart() {
    }

    @Override
    protected void beforeRespond(final HttpServletResponse response, final Writer w) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/xml");
    }

    protected void createUploadedFile(final StateToken stateToken, final String mimeTypeS, final File origFile)
            throws Exception, IOException, MagickException, FileNotFoundException {
        final BasicMimeType mimeType = new BasicMimeType(mimeTypeS);
        if (!mimeType.getType().equals("image")) {
            throw new Exception("Trying to set a non image (" + mimeTypeS + ") as group logo");
        }
        final Group group = groupManager.findByShortName(stateToken.getGroup());

        if (group == Group.NO_GROUP) {
            throw new Exception("Group not found trying to set the logo");
        }

        final File tmpDestFile = File.createTempFile("logoDest", "");

        final boolean result = ImageUtilsDefault.scaleImageToMax(origFile.getAbsolutePath(),
                tmpDestFile.getAbsolutePath(), FileConstants.LOGO_DEF_HEIGHT);

        if (result) {
            group.setLogo(FileUtils.getBytesFromFile(tmpDestFile));
            group.setLogoMime(mimeType);
            logFileDel(tmpDestFile.delete());
        } else {
            logFileDel(tmpDestFile.delete());
            throw new Exception("Cannot create group logo thumb");
        }
    }

    @Override
    @Authenticated
    @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.group)
    @Transactional(type = TransactionType.READ_WRITE)
    protected void createUploadedFile(final String userHash, final StateToken stateToken, final String fileName,
            final FileItem file, final String typeId) throws Exception {
        final String mimeTypeS = file.getContentType();
        final File tmpOrigFile = File.createTempFile("logoOrig", "");
        file.write(tmpOrigFile);
        createUploadedFile(stateToken, mimeTypeS, tmpOrigFile);
        logFileDel(tmpOrigFile.delete());
    }

    /**
     * Expect result with this format:
     * 
     * <pre>
     *  &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
     *  &lt;response success=&quot;false&quot;&gt;
     *     &lt;errors&gt;
     *         &lt;field&gt;
     *             &lt;id&gt;first&lt;/id&gt;
     *             &lt;msg&gt;&lt;![CDATA[
     *             Invalid name. &lt;br /&gt;&lt;i&gt;This is a test validation message from the server &lt;/i&gt;
     *          ]]&gt;&lt;/msg&gt;
     *         &lt;/field&gt;
     *         &lt;field&gt;
     *             &lt;id&gt;dob&lt;/id&gt;
     *             &lt;msg&gt;&lt;![CDATA[
     *             Invalid Date of Birth. &lt;br /&gt;&lt;i&gt;This is a test validation message from the server &lt;/i&gt;
     *          ]]&gt;&lt;/msg&gt;
     *         &lt;/field&gt;
     *     &lt;/errors&gt;
     * &lt;/response&gt;
     * </pre>
     **/
    protected String createXmlResponse(final boolean success, final String message) {
        String error = "";
        // FIXME: Use Group/User field when necessary
        if (!success) {
            error = "<errors><field><id>" + FileConstants.GROUP_LOGO_FIELD + "</id><msg><![CDATA[" + message
                    + "]]></msg></field></errors>";
        }
        return "<response success=\"" + success + "\">" + error + "</response>";
    }

    @Override
    protected void onFileUploadException(final HttpServletResponse response) throws IOException {
        doResponse(response, createXmlResponse(false, i18n.t("Error: File too large")).toString(),
                HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void onOtherException(final HttpServletResponse response, final Exception e) throws IOException {
        super.onOtherException(response, e);
        LOG.info("Exception: " + e.getCause());
        // e.printStackTrace();
        doResponse(response, createXmlResponse(false, i18n.t("Error uploading file")).toString(),
                HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void onSuccess(final HttpServletResponse response) throws IOException {
        doResponse(response, createXmlResponse(true, i18n.t("Success uploading")).toString());
    }

}
