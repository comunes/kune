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
package cc.kune.core.client.registry;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentCapabilitiesRegistry.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ContentCapabilitiesRegistry {

  /** The acl editable. */
  private final AclEditableRegistry aclEditable;

  /** The authorable. */
  private final AuthorableRegistry authorable;

  /** The can be homepage. */
  private final CanBeHomepageRegistry canBeHomepage;

  /** The comentable. */
  private final ComentableRegistry comentable;

  /** The dragable. */
  private final DragableRegistry dragable;

  /** The dropable. */
  private final DropableRegistry dropable;

  /** The email subscribe able. */
  private final EmailSubscribeAbleRegistry emailSubscribeAble;

  /** The empty folder tutorial registry. */
  private final EmptyFolderTutorialRegistry emptyFolderTutorialRegistry;

  /** The empty messages registry. */
  private final EmptyMessagesRegistry emptyMessagesRegistry;

  /** The empty messages registry not logged. */
  private final EmptyMessagesRegistry emptyMessagesRegistryNotLogged;

  /** The icons registry. */
  private final IconsRegistry iconsRegistry;

  /** The licensable. */
  private final LicensableRegistry licensable;

  /** The new menus. */
  private final NewMenusForTypeIdsRegistry newMenus;

  /** The publish moderable. */
  private final PublishModerableRegistry publishModerable;

  /** The rateable. */
  private final RateableRegistry rateable;

  /** The renamable. */
  private final RenamableRegistry renamable;

  /** The show deleted. */
  private final ShowDeletedRegistry showDeleted;

  /** The tageable. */
  private final TageableRegistry tageable;

  /** The translatable. */
  private final TranslatableRegistry translatable;

  /** The versionable. */
  private final VersionableRegistry versionable;

  /** The xmpp comentable. */
  private final XmppComentableRegistry xmppComentable;

  /** The xmpp notify capable. */
  private final XmppNotifyCapableRegistry xmppNotifyCapable;

  /**
   * Instantiates a new content capabilities registry.
   * 
   * @param authorableRegistry
   *          the authorable registry
   * @param newMenus
   *          the new menus
   * @param aclEditableRegistry
   *          the acl editable registry
   * @param iconsRegistry
   *          the icons registry
   * @param canBeHomepage
   *          the can be homepage
   * @param comentable
   *          the comentable
   * @param dragable
   *          the dragable
   * @param dropable
   *          the dropable
   * @param emailSubscribeAble
   *          the email subscribe able
   * @param licensable
   *          the licensable
   * @param publishModerable
   *          the publish moderable
   * @param rateable
   *          the rateable
   * @param tageable
   *          the tageable
   * @param renamable
   *          the renamable
   * @param translatable
   *          the translatable
   * @param versionable
   *          the versionable
   * @param emptyMessagesRegistry
   *          the empty messages registry
   * @param emptyMessagesRegistryNotLogged
   *          the empty messages registry not logged
   * @param xmppComentable
   *          the xmpp comentable
   * @param xmppNotifyCapable
   *          the xmpp notify capable
   * @param showDeleted
   *          the show deleted
   * @param emptyFolderTutorialRegistry
   *          the empty folder tutorial registry
   */
  @Inject
  public ContentCapabilitiesRegistry(final AuthorableRegistry authorableRegistry,
      final NewMenusForTypeIdsRegistry newMenus, final AclEditableRegistry aclEditableRegistry,
      final Provider<IconsRegistry> iconsRegistry, final CanBeHomepageRegistry canBeHomepage,
      final ComentableRegistry comentable, final DragableRegistry dragable,
      final DropableRegistry dropable, final EmailSubscribeAbleRegistry emailSubscribeAble,
      final LicensableRegistry licensable, final PublishModerableRegistry publishModerable,
      final RateableRegistry rateable, final TageableRegistry tageable,
      final RenamableRegistry renamable, final TranslatableRegistry translatable,
      final VersionableRegistry versionable, final EmptyMessagesRegistry emptyMessagesRegistry,
      final EmptyMessagesRegistry emptyMessagesRegistryNotLogged,
      final XmppComentableRegistry xmppComentable, final XmppNotifyCapableRegistry xmppNotifyCapable,
      final ShowDeletedRegistry showDeleted,
      final EmptyFolderTutorialRegistry emptyFolderTutorialRegistry) {
    this.authorable = authorableRegistry;
    this.newMenus = newMenus;
    this.aclEditable = aclEditableRegistry;
    this.iconsRegistry = iconsRegistry.get();
    this.canBeHomepage = canBeHomepage;
    this.comentable = comentable;
    this.dragable = dragable;
    this.dropable = dropable;
    this.emailSubscribeAble = emailSubscribeAble;
    this.licensable = licensable;
    this.publishModerable = publishModerable;
    this.rateable = rateable;
    this.tageable = tageable;
    this.renamable = renamable;
    this.translatable = translatable;
    this.versionable = versionable;
    this.emptyMessagesRegistry = emptyMessagesRegistry;
    this.emptyMessagesRegistryNotLogged = emptyMessagesRegistryNotLogged;
    this.xmppComentable = xmppComentable;
    this.xmppNotifyCapable = xmppNotifyCapable;
    this.showDeleted = showDeleted;
    this.emptyFolderTutorialRegistry = emptyFolderTutorialRegistry;
  }

  /**
   * Can be homepage.
   * 
   * @param typeId
   *          the type id
   * @return true, if successful
   */
  public boolean canBeHomepage(final String typeId) {
    return canBeHomepage.contains(typeId);
  }

  /**
   * Gets the acl editable.
   * 
   * @return the acl editable
   */
  public AclEditableRegistry getAclEditable() {
    return aclEditable;
  }

  /**
   * Gets the authorable.
   * 
   * @return the authorable
   */
  public AuthorableRegistry getAuthorable() {
    return authorable;
  }

  /**
   * Gets the can be homepage.
   * 
   * @return the can be homepage
   */
  public CanBeHomepageRegistry getCanBeHomepage() {
    return canBeHomepage;
  }

  /**
   * Gets the comentable.
   * 
   * @return the comentable
   */
  public ComentableRegistry getComentable() {
    return comentable;
  }

  /**
   * Gets the dragable.
   * 
   * @return the dragable
   */
  public DragableRegistry getDragable() {
    return dragable;
  }

  /**
   * Gets the dropable.
   * 
   * @return the dropable
   */
  public DropableRegistry getDropable() {
    return dropable;
  }

  /**
   * Gets the email subscribe able.
   * 
   * @return the email subscribe able
   */
  public EmailSubscribeAbleRegistry getEmailSubscribeAble() {
    return emailSubscribeAble;
  }

  /**
   * Gets the empty folder tutorial registry.
   * 
   * @return the empty folder tutorial registry
   */
  public EmptyFolderTutorialRegistry getEmptyFolderTutorialRegistry() {
    return emptyFolderTutorialRegistry;
  }

  /**
   * Gets the empty messages registry.
   * 
   * @return the empty messages registry
   */
  public EmptyMessagesRegistry getEmptyMessagesRegistry() {
    return emptyMessagesRegistry;
  }

  /**
   * Gets the empty messages registry not logged.
   * 
   * @return the empty messages registry not logged
   */
  public EmptyMessagesRegistry getEmptyMessagesRegistryNotLogged() {
    return emptyMessagesRegistryNotLogged;
  }

  /**
   * Gets the icons registry.
   * 
   * @return the icons registry
   */
  public IconsRegistry getIconsRegistry() {
    return iconsRegistry;
  }

  /**
   * Gets the licensable.
   * 
   * @return the licensable
   */
  public LicensableRegistry getLicensable() {
    return licensable;
  }

  /**
   * Gets the new menus.
   * 
   * @return the new menus
   */
  public NewMenusForTypeIdsRegistry getNewMenus() {
    return newMenus;
  }

  /**
   * Gets the publish moderable.
   * 
   * @return the publish moderable
   */
  public PublishModerableRegistry getPublishModerable() {
    return publishModerable;
  }

  /**
   * Gets the rateable.
   * 
   * @return the rateable
   */
  public RateableRegistry getRateable() {
    return rateable;
  }

  /**
   * Gets the renamable.
   * 
   * @return the renamable
   */
  public RenamableRegistry getRenamable() {
    return renamable;
  }

  /**
   * Gets the show deleted.
   * 
   * @return the show deleted
   */
  public ShowDeletedRegistry getShowDeleted() {
    return showDeleted;
  }

  /**
   * Gets the tageable.
   * 
   * @return the tageable
   */
  public TageableRegistry getTageable() {
    return tageable;
  }

  /**
   * Gets the translatable.
   * 
   * @return the translatable
   */
  public TranslatableRegistry getTranslatable() {
    return translatable;
  }

  /**
   * Gets the versionable.
   * 
   * @return the versionable
   */
  public VersionableRegistry getVersionable() {
    return versionable;
  }

  /**
   * Gets the xmpp comentable.
   * 
   * @return the xmpp comentable
   */
  public XmppComentableRegistry getXmppComentable() {
    return xmppComentable;
  }

  /**
   * Gets the xmpp notificy capable.
   * 
   * @return the xmpp notificy capable
   */
  public XmppNotifyCapableRegistry getXmppNotificyCapable() {
    return xmppNotifyCapable;
  }

  /**
   * Checks if is acl editable.
   * 
   * @param typeId
   *          the type id
   * @return true, if is acl editable
   */
  public boolean isAclEditable(final String typeId) {
    return aclEditable.contains(typeId);
  }

  /**
   * Checks if is authorable.
   * 
   * @param typeId
   *          the type id
   * @return true, if is authorable
   */
  public boolean isAuthorable(final String typeId) {
    return authorable.contains(typeId);
  }

  /**
   * Checks if is comentable.
   * 
   * @param typeId
   *          the type id
   * @return true, if is comentable
   */
  public boolean isComentable(final String typeId) {
    return comentable.contains(typeId);
  }

  /**
   * Checks if is dragable.
   * 
   * @param typeId
   *          the type id
   * @return true, if is dragable
   */
  public boolean isDragable(final String typeId) {
    return dragable.contains(typeId);
  }

  /**
   * Checks if is dropable.
   * 
   * @param typeId
   *          the type id
   * @return true, if is dropable
   */
  public boolean isDropable(final String typeId) {
    return dropable.contains(typeId);
  }

  /**
   * Checks if is email subscribe able.
   * 
   * @param typeId
   *          the type id
   * @return true, if is email subscribe able
   */
  public boolean isEmailSubscribeAble(final String typeId) {
    return emailSubscribeAble.contains(typeId);
  }

  /**
   * Checks if is licensable.
   * 
   * @param typeId
   *          the type id
   * @return true, if is licensable
   */
  public boolean isLicensable(final String typeId) {
    return licensable.contains(typeId);
  }

  /**
   * Checks if is publish moderable.
   * 
   * @param typeId
   *          the type id
   * @return true, if is publish moderable
   */
  public boolean isPublishModerable(final String typeId) {
    return publishModerable.contains(typeId);
  }

  /**
   * Checks if is rateable.
   * 
   * @param typeId
   *          the type id
   * @return true, if is rateable
   */
  public boolean isRateable(final String typeId) {
    return rateable.contains(typeId);
  }

  /**
   * Checks if is renamable.
   * 
   * @param typeId
   *          the type id
   * @return true, if is renamable
   */
  public boolean isRenamable(final String typeId) {
    return renamable.contains(typeId);
  }

  /**
   * Checks if is tageable.
   * 
   * @param typeId
   *          the type id
   * @return true, if is tageable
   */
  public boolean isTageable(final String typeId) {
    return tageable.contains(typeId);
  }

  /**
   * Checks if is translatable.
   * 
   * @param typeId
   *          the type id
   * @return true, if is translatable
   */
  public boolean isTranslatable(final String typeId) {
    return translatable.contains(typeId);
  }

  /**
   * Checks if is versionable.
   * 
   * @param typeId
   *          the type id
   * @return true, if is versionable
   */
  public boolean isVersionable(final String typeId) {
    return versionable.contains(typeId);
  }

  /**
   * Checks if is xmpp comentable.
   * 
   * @param typeId
   *          the type id
   * @return true, if is xmpp comentable
   */
  public boolean isXmppComentable(final String typeId) {
    return xmppComentable.contains(typeId);
  }

  /**
   * Checks if is xmpp notify capable.
   * 
   * @param typeId
   *          the type id
   * @return true, if is xmpp notify capable
   */
  public boolean isXmppNotifyCapable(final String typeId) {
    return xmppNotifyCapable.contains(typeId);
  }

  /**
   * Show deleted.
   * 
   * @param typeId
   *          the type id
   * @return true, if successful
   */
  public boolean showDeleted(final String typeId) {
    return showDeleted.contains(typeId);
  }

}
