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
package cc.kune.wiki.server;

import static cc.kune.wiki.shared.WikiToolConstants.ROOT_NAME;
import static cc.kune.wiki.shared.WikiToolConstants.TOOL_NAME;
import static cc.kune.wiki.shared.WikiToolConstants.TYPE_FOLDER;
import static cc.kune.wiki.shared.WikiToolConstants.TYPE_ROOT;
import static cc.kune.wiki.shared.WikiToolConstants.TYPE_UPLOADEDFILE;
import static cc.kune.wiki.shared.WikiToolConstants.TYPE_WIKIPAGE;

import java.util.Arrays;
import java.util.Date;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.server.AbstractWaveBasedServerTool;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.content.CreationService;
import cc.kune.core.server.manager.ToolConfigurationManager;
import cc.kune.core.server.tool.ServerToolTarget;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.GroupListMode;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.User;
import cc.kune.wave.server.ParticipantUtils;

import com.google.inject.Inject;

public class WikiServerTool extends AbstractWaveBasedServerTool {

  private final String[] publicArray;

  @Inject
  public WikiServerTool(final ContentManager contentManager, final ContainerManager containerManager,
      final ToolConfigurationManager configurationManager, final I18nTranslationService i18n,
      final CreationService creationService, final ParticipantUtils participantUtils) {
    super(TOOL_NAME, ROOT_NAME, TYPE_ROOT, Arrays.asList(TYPE_WIKIPAGE, TYPE_UPLOADEDFILE),
        Arrays.asList(TYPE_ROOT, TYPE_FOLDER), Arrays.asList(TYPE_FOLDER), Arrays.asList(TYPE_ROOT,
            TYPE_FOLDER), contentManager, containerManager, creationService, configurationManager, i18n,
        ServerToolTarget.forBoth);
    publicArray = new String[1];
    publicArray[0] = participantUtils.getPublicParticipantId().toString();
  }

  @Override
  public String[] getNewContentAdditionalParts(final Container containerParent) {
    return publicArray;
  }

  @Override
  public Group initGroup(final User user, final Group group, final Object... otherVars) {
    createRoot(group);
    return group;
  }

  @Override
  public void onCreateContainer(final Container container, final Container parent) {
    setContainerAcl(container);
  }

  @Override
  public void onCreateContent(final Content content, final Container parent) {
    content.setStatus(ContentStatus.publishedOnline);
    content.setPublishedOn(new Date());
  }

  @Override
  protected void setContainerAcl(final Container container) {
    final AccessLists wikiAcl = new AccessLists();
    wikiAcl.getAdmins().setMode(GroupListMode.NORMAL);
    wikiAcl.getAdmins().add(container.getOwner());
    wikiAcl.getEditors().setMode(GroupListMode.EVERYONE);
    wikiAcl.getViewers().setMode(GroupListMode.EVERYONE);
    setAccessList(container, wikiAcl);
  }
}
