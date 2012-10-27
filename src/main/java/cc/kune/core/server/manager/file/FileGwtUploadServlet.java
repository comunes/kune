/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

import javax.inject.Inject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.domain.utils.StateToken;

public class FileGwtUploadServlet extends FileGwtUploadAbstractServlet {

  public static final Log LOG = LogFactory.getLog(FileGwtUploadServlet.class);
  private static final long serialVersionUID = 1L;

  @Inject
  public FileGwtUploadServlet(final KuneProperties kuneProperties) {
    super(kuneProperties);
  }

  @Override
  protected String createUploadedFile(final String userHash, final StateToken stateToken,
      final String fileName, final FileItem file, final String typeId) {
    LOG.info("ok");
    return "";
  }

}
