/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
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

// TODO: Auto-generated Javadoc
/**
 * The Class FoldableAbstractClientTool.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class FoldableAbstractClientTool extends AbstractClientTool {

  /** The Constant EMPTY. */
  protected static final String EMPTY = "This folder is empty";

  /** The content capabilities registry. */
  protected final ContentCapabilitiesRegistry contentCapabilitiesRegistry;

  /** The i18n. */
  protected final I18nTranslationService i18n;

  /**
   * Instantiates a new foldable abstract client tool.
   * 
   * @param shortName
   *          the short name
   * @param longName
   *          the long name
   * @param tooltip
   *          the tooltip
   * @param icon
   *          the icon
   * @param visibleForRol
   *          the visible for rol
   * @param toolSelector
   *          the tool selector
   * @param contentCapabilitiesRegistry
   *          the content capabilities registry
   * @param i18n
   *          the i18n
   * @param history
   *          the history
   */
  public FoldableAbstractClientTool(final String shortName, final String longName, final String tooltip,
      final KuneIcon icon, final AccessRolDTO visibleForRol, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry contentCapabilitiesRegistry, final I18nTranslationService i18n,
      final HistoryWrapper history) {
    super(shortName, longName, tooltip, icon, visibleForRol, toolSelector, history);
    this.contentCapabilitiesRegistry = contentCapabilitiesRegistry;
    this.i18n = i18n;
  }

  /**
   * Register acl editable types.
   * 
   * @param typeIds
   *          the type ids
   */
  protected void registerAclEditableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getAclEditable().register(typeIds);
  }

  /**
   * Register authorable types.
   * 
   * @param typeIds
   *          the type ids
   */
  protected void registerAuthorableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getAuthorable().register(typeIds);
  }

  /**
   * Register comentable types.
   * 
   * @param typeIds
   *          the type ids
   */
  protected void registerComentableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getComentable().register(typeIds);
  }

  /**
   * Register content type icon.
   * 
   * @param contentTypeId
   *          the content type id
   * @param contentStatus
   *          the content status
   * @param icon
   *          the icon
   */
  public void registerContentTypeIcon(final String contentTypeId, final ContentStatus contentStatus,
      final KuneIcon icon) {
    contentCapabilitiesRegistry.getIconsRegistry().registerContentTypeIcon(contentTypeId, contentStatus,
        icon);
  }

  /**
   * Register content type icon.
   * 
   * @param contentTypeId
   *          the content type id
   * @param icon
   *          the icon
   */
  public void registerContentTypeIcon(final String contentTypeId, final KuneIcon icon) {
    contentCapabilitiesRegistry.getIconsRegistry().registerContentTypeIcon(contentTypeId, icon);
  }

  /**
   * Register dragable types.
   * 
   * @param typeIds
   *          the type ids
   */
  protected void registerDragableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getDragable().register(typeIds);
  }

  /**
   * Register dropable types.
   * 
   * @param typeIds
   *          the type ids
   */
  protected void registerDropableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getDropable().register(typeIds);
  }

  /**
   * Register email subscribe able types.
   * 
   * @param typeIds
   *          the type ids
   */
  protected void registerEmailSubscribeAbleTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getEmailSubscribeAble().register(typeIds);
  }

  /**
   * Register empty folder tutorial.
   * 
   * @param contentTypeId
   *          the content type id
   */
  public void registerEmptyFolderTutorial(final String contentTypeId) {
    contentCapabilitiesRegistry.getEmptyFolderTutorialRegistry().register(contentTypeId);
  }

  /**
   * Register empty messages.
   * 
   * @param contentTypeId
   *          the content type id
   * @param message
   *          the message
   */
  public void registerEmptyMessages(final String contentTypeId, final String message) {
    contentCapabilitiesRegistry.getEmptyMessagesRegistry().register(contentTypeId, message);
  }

  /**
   * Register empty messages not logged.
   * 
   * @param contentTypeId
   *          the content type id
   * @param message
   *          the message
   */
  public void registerEmptyMessagesNotLogged(final String contentTypeId, final String message) {
    contentCapabilitiesRegistry.getEmptyMessagesRegistryNotLogged().register(contentTypeId, message);
  }

  /**
   * Register licensable types.
   * 
   * @param typeIds
   *          the type ids
   */
  protected void registerLicensableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getLicensable().register(typeIds);
  }

  /**
   * Register new menu.
   * 
   * @param typeId
   *          the type id
   * @param menu
   *          the menu
   */
  public void registerNewMenu(final String typeId, final MenuDescriptor menu) {
    contentCapabilitiesRegistry.getNewMenus().register(typeId, menu);
  }

  /**
   * Register publish moderable types.
   * 
   * @param typeIds
   *          the type ids
   */
  protected void registerPublishModerableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getPublishModerable().register(typeIds);
  }

  /**
   * Register rateable types.
   * 
   * @param typeIds
   *          the type ids
   */
  protected void registerRateableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getRateable().register(typeIds);
  }

  /**
   * Register renamable types.
   * 
   * @param typeIds
   *          the type ids
   */
  protected void registerRenamableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getRenamable().register(typeIds);
  }

  /**
   * Register show deleted.
   * 
   * @param typeIds
   *          the type ids
   */
  protected void registerShowDeleted(final String... typeIds) {
    contentCapabilitiesRegistry.getShowDeleted().register(typeIds);
  }

  /**
   * Register tageable types.
   * 
   * @param typeIds
   *          the type ids
   */
  protected void registerTageableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getTageable().register(typeIds);
  }

  /**
   * Register translatable types.
   * 
   * @param typeIds
   *          the type ids
   */
  protected void registerTranslatableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getTranslatable().register(typeIds);
  }

  /**
   * Register versionable types.
   * 
   * @param typeIds
   *          the type ids
   */
  protected void registerVersionableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getVersionable().register(typeIds);
  }

  /**
   * Register xmpp comentable types.
   * 
   * @param typeIds
   *          the type ids
   */
  protected void registerXmppComentableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getXmppComentable().register(typeIds);
  }

  /**
   * Register xmpp notify capable types.
   * 
   * @param typeIds
   *          the type ids
   */
  protected void registerXmppNotifyCapableTypes(final String... typeIds) {
    contentCapabilitiesRegistry.getXmppNotificyCapable().register(typeIds);
  }
}
