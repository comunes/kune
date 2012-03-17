/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.manager.file;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.domain.utils.StateToken;

import com.google.inject.servlet.RequestScoped;

@RequestScoped
public abstract class FileGwtUploadAbstractServlet extends UploadAction {

  public static final Log LOG = LogFactory.getLog(FileGwtUploadAbstractServlet.class);
  private static final long serialVersionUID = 1L;
  protected static final String UTF8 = "UTF-8";
  protected final KuneProperties kuneProperties;

  public FileGwtUploadAbstractServlet(final KuneProperties kuneProperties) {
    this.kuneProperties = kuneProperties;
    this.maxSize = kuneProperties.getInteger(KuneProperties.UPLOAD_MAX_FILE_SIZE_IN_KS);
    this.uploadDelay = kuneProperties.getInteger(KuneProperties.UPLOAD_DELAY_FOR_TEST);
  }

  protected abstract String createUploadedFile(final String userHash, final StateToken stateToken,
      final String fileName, final FileItem file, final String typeId);

  @Override
  public String executeAction(final HttpServletRequest request, final List<FileItem> fileItems)
      throws UploadActionException {
    String userHash = null;
    StateToken stateToken = null;
    String typeId = null;
    String fileName = null;
    FileItem file = null;
    for (final Object element : fileItems) {
      final FileItem item = (FileItem) element;
      if (item.isFormField()) {
        final String name = item.getFieldName();
        final String value = item.getString();
        LOG.info("name: " + name + " value: " + value);
        if (name.equals(FileConstants.HASH)) {
          userHash = value;
        }
        if (name.equals(FileConstants.TOKEN)) {
          stateToken = new StateToken(value);
        }
        if (name.equals(FileConstants.TYPE_ID)) {
          typeId = value;
        }
      } else {
        fileName = item.getName();
        LOG.info("file: " + fileName + " fieldName: " + item.getFieldName() + " size: " + item.getSize()
            + " typeId: " + typeId);
        file = item;
      }
    }
    final String response = createUploadedFile(userHash, stateToken, fileName, file, typeId);

    // Remove files from session because we have a copy of them
    removeSessionFileItems(request);

    return response;

  }

  protected void logFileDel(final boolean delResult) {
    if (!delResult) {
      LOG.error("Cannot delete file");
    }
  }

}
