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
 \*/
package cc.kune.core.server.manager.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.waveprotocol.box.server.CoreSettings;

import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Group;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class EntityLogoDownloadManager extends HttpServlet {

  public static final Log LOG = LogFactory.getLog(EntityLogoDownloadManager.class);

  private static final long serialVersionUID = -1958945058088446881L;
  private final InputStream groupLogo;
  GroupManager groupManager;
  private final InputStream personLogo;
  private final InputStream unknownLogo;

  @Inject
  public EntityLogoDownloadManager(@Named(CoreSettings.RESOURCE_BASES) final List<String> resourceBases,
      final GroupManager groupManager) {
    this.groupManager = groupManager;
    personLogo = FileDownloadManagerUtils.searchFileInResourcBases(resourceBases,
        FileConstants.PERSON_NO_AVATAR_IMAGE);
    groupLogo = FileDownloadManagerUtils.searchFileInResourcBases(resourceBases,
        FileConstants.GROUP_NO_AVATAR_IMAGE);
    unknownLogo = FileDownloadManagerUtils.searchFileInResourcBases(resourceBases,
        FileConstants.NO_RESULT_AVATAR_IMAGE);
  }

  @Override
  protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
      throws ServletException, IOException {

    final StateToken stateToken = new StateToken(req.getParameter(FileConstants.TOKEN));
    final String onlyUserS = req.getParameter(FileConstants.ONLY_USERS);
    final boolean onlyUsers = Boolean.parseBoolean(onlyUserS);
    Group group = Group.NO_GROUP;
    try {
      group = groupManager.findByShortName(stateToken.getGroup());
      if (group == Group.NO_GROUP) {
        unknownResult(resp);
        return;
      }
      if (onlyUsers && !group.isPersonal()) {
        unknownResult(resp);
        return;
      }
      if (!group.hasLogo()) {
        FileDownloadManagerUtils.returnFile((group.isPersonal() ? personLogo : groupLogo),
            resp.getOutputStream());
      } else {
        // Has logo
        final byte[] logo = group.getLogo();

        resp.setContentLength(logo.length);
        resp.setContentType(group.getLogoMime().toString());
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + group.getShortName()
            + "-logo\"");
        resp.getOutputStream().write(logo);
      }
    } catch (final NoResultException e) {
      unknownResult(resp);
    }
  }

  private void unknownResult(final HttpServletResponse resp) throws IOException {
    FileDownloadManagerUtils.returnFile(unknownLogo, resp.getOutputStream());
  }

}
