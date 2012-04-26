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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.waveprotocol.box.server.CoreSettings;

import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Group;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class EntityLogoDownloadManager extends HttpServlet {

  private static final long serialVersionUID = -1958945058088446881L;
  private final byte[] groupLogo;
  GroupManager groupManager;
  private final String groupMime;
  private final byte[] personLogo;
  private final String personMime;
  private final byte[] unknownLogo;
  private final String unknownMime;

  @Inject
  public EntityLogoDownloadManager(@Named(CoreSettings.RESOURCE_BASES) final List<String> resourceBases,
      final GroupManager groupManager) throws IOException {
    this.groupManager = groupManager;

    final File personFile = getFile(resourceBases, FileConstants.PERSON_NO_AVATAR_IMAGE);
    personMime = getMime(personFile);
    personLogo = getBy(personFile);

    final File groupFile = getFile(resourceBases, FileConstants.GROUP_NO_AVATAR_IMAGE);
    groupMime = getMime(groupFile);
    groupLogo = getBy(groupFile);

    final File unknownFile = getFile(resourceBases, FileConstants.NO_RESULT_AVATAR_IMAGE);
    unknownMime = getMime(unknownFile);
    unknownLogo = getBy(unknownFile);
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
        if (group.isPersonal()) {
          reply(resp, personLogo, personMime);
        } else {
          reply(resp, groupLogo, groupMime);
        }

      } else {
        // Has logo
        reply(resp, group.getLogo(), group.getLogoMime().toString());
      }
    } catch (final NoResultException e) {
      unknownResult(resp);
    }
  }

  private byte[] getBy(final File file) throws IOException {
    final BufferedInputStream is = new BufferedInputStream(new FileInputStream(file));
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    org.apache.commons.io.IOUtils.copy(is, baos);
    return baos.toByteArray();
  }

  private File getFile(final List<String> resourceBases, final String location) {
    final File file = FileDownloadManagerUtils.searchFileInResourceBases(resourceBases, location);
    return file;
  }

  private String getMime(final File file) {
    return new MimetypesFileTypeMap().getContentType(file);
  }

  private void reply(final HttpServletResponse resp, final byte[] logo, final String mime)
      throws IOException {
    resp.setContentLength(logo.length);
    resp.setContentType(mime);
    resp.getOutputStream().write(logo);
  }

  private void unknownResult(final HttpServletResponse resp) throws IOException {
    reply(resp, unknownLogo, unknownMime);
  }

}
