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
 */
package cc.kune.docs.server;

import static cc.kune.docs.shared.DocsConstants.NAME;
import static cc.kune.docs.shared.DocsConstants.ROOT_NAME;
import static cc.kune.docs.shared.DocsConstants.TYPE_DOCUMENT;
import static cc.kune.docs.shared.DocsConstants.TYPE_FOLDER;
import static cc.kune.docs.shared.DocsConstants.TYPE_ROOT;
import static cc.kune.docs.shared.DocsConstants.TYPE_UPLOADEDFILE;

import java.util.Arrays;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.server.AbstractServerTool;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.content.CreationService;
import cc.kune.core.server.manager.ToolConfigurationManager;
import cc.kune.core.server.tool.ServerToolTarget;
import cc.kune.docs.shared.DocsConstants;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;

public class DocumentServerTool extends AbstractServerTool {

  @Inject
  public DocumentServerTool(final ContentManager contentManager,
      final ContainerManager containerManager, final ToolConfigurationManager configurationManager,
      final I18nTranslationService i18n, final CreationService creationService) {
    super(NAME, ROOT_NAME, TYPE_ROOT, Arrays.asList(TYPE_DOCUMENT, TYPE_UPLOADEDFILE), Arrays.asList(
        TYPE_ROOT, TYPE_FOLDER), Arrays.asList(TYPE_FOLDER), Arrays.asList(TYPE_ROOT, TYPE_FOLDER),
        contentManager, containerManager, creationService, configurationManager, i18n,
        ServerToolTarget.forBoth);
  }

  @Override
  public Group initGroup(final User user, final Group group, final Object... otherVars) {
    final Container rootFolder = createRoot(group);

    final boolean hasVars = otherVars.length >= 2;
    final String title = hasVars ? (String) otherVars[0] : i18n.t("Document sample");
    final String body = hasVars ? (String) otherVars[1] : i18n.t("This is only a sample of document");
    final String contentType = DocsConstants.TYPE_DOCUMENT;

    final Content content = createInitialContent(user, group, rootFolder, title, body, contentType);
    group.setDefaultContent(content);
    return group;
  }

}
