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
package cc.kune.meets.server;

import static cc.kune.meets.shared.MeetingsConstants.NAME;
import static cc.kune.meets.shared.MeetingsConstants.ROOT_NAME;
import static cc.kune.meets.shared.MeetingsConstants.TYPE_MEETING;
import static cc.kune.meets.shared.MeetingsConstants.TYPE_ROOT;

import java.net.URL;
import java.util.Date;

import cc.kune.core.client.errors.ContainerNotPermittedException;
import cc.kune.core.client.errors.ContentNotPermittedException;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.manager.ToolConfigurationManager;
import cc.kune.core.server.tool.ServerToolRegistry;
import cc.kune.core.server.tool.ServerToolTarget;
import cc.kune.core.server.tool.ServerWaveTool;
import cc.kune.core.server.utils.UrlUtils;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.GroupListMode;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.ToolConfiguration;
import cc.kune.domain.User;

import com.google.inject.Inject;

public class MeetingServerTool implements ServerWaveTool {

  private static final String MEETING_GADGET = "http://mass-mob.appspot.com/massmob/org.ourproject.massmob.client.MassmobGadget.gadget.xml";
  private final ToolConfigurationManager configurationManager;
  private final ContainerManager containerManager;
  private final ContentManager contentManager;
  private final URL gadgetUrl;
  private final I18nTranslationService i18n;

  @Inject
  public MeetingServerTool(final ContentManager contentManager, final ContainerManager containerManager,
      final ToolConfigurationManager configurationManager,
      final I18nTranslationService translationService) {
    this.contentManager = contentManager;
    this.containerManager = containerManager;
    this.configurationManager = configurationManager;
    this.i18n = translationService;
    gadgetUrl = UrlUtils.of(MEETING_GADGET);
  }

  void checkContainerTypeId(final String parentTypeId, final String typeId) {
    throw new ContainerNotPermittedException();
  }

  void checkContentTypeId(final String parentTypeId, final String typeId) {
    if (typeId.equals(TYPE_MEETING)) {
      // ok valid content
      final boolean parentIsFolderOrRoot = parentTypeId.equals(TYPE_ROOT);
      if ((typeId.equals(TYPE_MEETING) && parentIsFolderOrRoot)) {
        // ok
      } else {
        throw new ContentNotPermittedException();
      }
    } else {
      throw new ContentNotPermittedException();
    }
  }

  @Override
  public void checkTypesBeforeContainerCreation(final String parentTypeId, final String typeId) {
    checkContainerTypeId(parentTypeId, typeId);
  }

  @Override
  public void checkTypesBeforeContentCreation(final String parentTypeId, final String typeId) {
    checkContentTypeId(parentTypeId, typeId);
  }

  @Override
  public URL getGadgetUrl() {
    return gadgetUrl;
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getRootName() {
    return ROOT_NAME;
  }

  @Override
  public ServerToolTarget getTarget() {
    return ServerToolTarget.forGroups;
  }

  @Override
  public Group initGroup(final User user, final Group group, final Object... otherVars) {
    final ToolConfiguration config = new ToolConfiguration();
    final Container rootFolder = containerManager.createRootFolder(group, NAME, ROOT_NAME, TYPE_ROOT);
    setContainerMeetingsAcl(rootFolder);
    config.setRoot(rootFolder);
    group.setToolConfig(NAME, config);
    configurationManager.persist(config);
    final Content content = contentManager.createContent(
        i18n.t("Meeting sample"),
        i18n.t("This is only a meet sample. You can invite other participants to this meeting, but also publish to the general public allowing you to to help in the organization, call and speed-up of events."),
        user, rootFolder, TYPE_MEETING, gadgetUrl);
    content.addAuthor(user);
    content.setLanguage(user.getLanguage());
    content.setTypeId(TYPE_MEETING);
    content.setStatus(ContentStatus.publishedOnline);
    contentManager.save(content);
    return group;
  }

  @Override
  public void onCreateContainer(final Container container, final Container parent) {
    setContainerMeetingsAcl(container);
  }

  @Override
  public void onCreateContent(final Content content, final Container parent) {
    content.setStatus(ContentStatus.publishedOnline);
    content.setPublishedOn(new Date());
  }

  @Override
  @Inject
  public void register(final ServerToolRegistry registry) {
    registry.register(this);
  }

  private void setContainerMeetingsAcl(final Container container) {
    final AccessLists meetsAcl = new AccessLists();
    meetsAcl.getAdmins().setMode(GroupListMode.NORMAL);
    meetsAcl.getAdmins().add(container.getOwner());
    meetsAcl.getEditors().setMode(GroupListMode.NORMAL);
    meetsAcl.getViewers().setMode(GroupListMode.EVERYONE);
    containerManager.setAccessList(container, meetsAcl);
  }
}
