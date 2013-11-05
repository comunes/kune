/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.gspace.client.tool;

import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.res.KuneIcon;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.tool.selector.ToolSelector;

public abstract class FoldableAbstractClientTool extends AbstractClientTool {

  protected static final String EMPTY = "This folder is empty";
  protected final ContentCapabilitiesRegistry contentCapabilitiesRegistry;
  protected final I18nTranslationService i18n;

  public FoldableAbstractClientTool(final String shortName, final String longName, final String tooltip,
      final KuneIcon icon, final AccessRolDTO visibleForRol, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry contentCapabilitiesRegistry, final I18nTranslationService i18n,
      final HistoryWrapper history) {
    super(shortName, longName, tooltip, icon, visibleForRol, toolSelector, history);
    this.contentCapabilitiesRegistry = contentCapabilitiesRegistry;
    this.i18n = i18n;
  }

  protected void registerAclEditableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getAclEditable().register(typeIds);
  }

  protected void registerAuthorableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getAuthorable().register(typeIds);
  }

  protected void registerComentableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getComentable().register(typeIds);
  }

  public void registerContentTypeIcon(final String contentTypeId, final ContentStatus contentStatus,
      final KuneIcon icon) {
    contentCapabilitiesRegistry.getIconsRegistry().registerContentTypeIcon(contentTypeId, contentStatus,
        icon);
  }

  public void registerContentTypeIcon(final String contentTypeId, final KuneIcon icon) {
    contentCapabilitiesRegistry.getIconsRegistry().registerContentTypeIcon(contentTypeId, icon);
  }

  protected void registerDragableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getDragable().register(typeIds);
  }

  protected void registerDropableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getDropable().register(typeIds);
  }

  protected void registerEmailSubscribeAbleTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getEmailSubscribeAble().register(typeIds);
  }

  public void registerEmptyFolderTutorial(final String contentTypeId) {
    contentCapabilitiesRegistry.getEmptyFolderTutorialRegistry().register(contentTypeId);
  }

  public void registerEmptyMessages(final String contentTypeId, final String message) {
    contentCapabilitiesRegistry.getEmptyMessagesRegistry().register(contentTypeId, message);
  }

  public void registerEmptyMessagesNotLogged(final String contentTypeId, final String message) {
    contentCapabilitiesRegistry.getEmptyMessagesRegistryNotLogged().register(contentTypeId, message);
  }

  protected void registerLicensableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getLicensable().register(typeIds);
  }

  public void registerNewMenu(final String typeId, final MenuDescriptor menu) {
    contentCapabilitiesRegistry.getNewMenus().register(typeId, menu);
  }

  protected void registerPublishModerableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getPublishModerable().register(typeIds);
  }

  protected void registerRateableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getRateable().register(typeIds);
  }

  protected void registerRenamableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getRenamable().register(typeIds);
  }

  protected void registerShowDeleted(final String... typeIds) {
    contentCapabilitiesRegistry.getShowDeleted().register(typeIds);
  }

  protected void registerTageableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getTageable().register(typeIds);
  }

  protected void registerTranslatableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getTranslatable().register(typeIds);
  }

  protected void registerVersionableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getVersionable().register(typeIds);
  }

  protected void registerXmppComentableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getXmppComentable().register(typeIds);
  }

  protected void registerXmppNotifyCapableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getXmppNotificyCapable().register(typeIds);
  }
}
