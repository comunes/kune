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
package cc.kune.core.client.registry;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ContentCapabilitiesRegistry {

  private final AclEditableRegistry aclEditable;
  private final AuthorableRegistry authorable;
  private final CanBeHomepageRegistry canBeHomepage;
  private final ComentableRegistry comentable;
  private final DragableRegistry dragable;
  private final DropableRegistry dropable;
  private final EmailSubscribeAbleRegistry emailSubscribeAble;
  private final EmptyFolderTutorialRegistry emptyFolderTutorialRegistry;
  private final EmptyMessagesRegistry emptyMessagesRegistry;
  private final EmptyMessagesRegistry emptyMessagesRegistryNotLogged;
  private final IconsRegistry iconsRegistry;
  private final IconsRegistry iconsRegistryLight;
  private final LicensableRegistry licensable;
  private final NewMenusForTypeIdsRegistry newMenus;
  private final PublishModerableRegistry publishModerable;
  private final RateableRegistry rateable;
  private final RenamableRegistry renamable;
  private final ShowDeletedRegistry showDeleted;
  private final TageableRegistry tageable;
  private final TranslatableRegistry translatable;
  private final VersionableRegistry versionable;
  private final XmppComentableRegistry xmppComentable;
  private final XmppNotifyCapableRegistry xmppNotifyCapable;

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
    this.iconsRegistryLight = iconsRegistry.get();
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

  public boolean canBeHomepage(final String typeId) {
    return canBeHomepage.contains(typeId);
  }

  public AclEditableRegistry getAclEditable() {
    return aclEditable;
  }

  public AuthorableRegistry getAuthorable() {
    return authorable;
  }

  public CanBeHomepageRegistry getCanBeHomepage() {
    return canBeHomepage;
  }

  public ComentableRegistry getComentable() {
    return comentable;
  }

  public DragableRegistry getDragable() {
    return dragable;
  }

  public DropableRegistry getDropable() {
    return dropable;
  }

  public EmailSubscribeAbleRegistry getEmailSubscribeAble() {
    return emailSubscribeAble;
  }

  public EmptyFolderTutorialRegistry getEmptyFolderTutorialRegistry() {
    return emptyFolderTutorialRegistry;
  }

  public EmptyMessagesRegistry getEmptyMessagesRegistry() {
    return emptyMessagesRegistry;
  }

  public EmptyMessagesRegistry getEmptyMessagesRegistryNotLogged() {
    return emptyMessagesRegistryNotLogged;
  }

  public IconsRegistry getIconsRegistry() {
    return iconsRegistry;
  }

  public IconsRegistry getIconsRegistryLight() {
    return iconsRegistryLight;
  }

  public LicensableRegistry getLicensable() {
    return licensable;
  }

  public NewMenusForTypeIdsRegistry getNewMenus() {
    return newMenus;
  }

  public PublishModerableRegistry getPublishModerable() {
    return publishModerable;
  }

  public RateableRegistry getRateable() {
    return rateable;
  }

  public RenamableRegistry getRenamable() {
    return renamable;
  }

  public ShowDeletedRegistry getShowDeleted() {
    return showDeleted;
  }

  public TageableRegistry getTageable() {
    return tageable;
  }

  public TranslatableRegistry getTranslatable() {
    return translatable;
  }

  public VersionableRegistry getVersionable() {
    return versionable;
  }

  public XmppComentableRegistry getXmppComentable() {
    return xmppComentable;
  }

  public XmppNotifyCapableRegistry getXmppNotificyCapable() {
    return xmppNotifyCapable;
  }

  public boolean isAclEditable(final String typeId) {
    return aclEditable.contains(typeId);
  }

  public boolean isAuthorable(final String typeId) {
    return authorable.contains(typeId);
  }

  public boolean isComentable(final String typeId) {
    return comentable.contains(typeId);
  }

  public boolean isDragable(final String typeId) {
    return dragable.contains(typeId);
  }

  public boolean isDropable(final String typeId) {
    return dropable.contains(typeId);
  }

  public boolean isEmailSubscribeAble(final String typeId) {
    return emailSubscribeAble.contains(typeId);
  }

  public boolean isLicensable(final String typeId) {
    return licensable.contains(typeId);
  }

  public boolean isPublishModerable(final String typeId) {
    return publishModerable.contains(typeId);
  }

  public boolean isRateable(final String typeId) {
    return rateable.contains(typeId);
  }

  public boolean isRenamable(final String typeId) {
    return renamable.contains(typeId);
  }

  public boolean isTageable(final String typeId) {
    return tageable.contains(typeId);
  }

  public boolean isTranslatable(final String typeId) {
    return translatable.contains(typeId);
  }

  public boolean isVersionable(final String typeId) {
    return versionable.contains(typeId);
  }

  public boolean isXmppComentable(final String typeId) {
    return xmppComentable.contains(typeId);
  }

  public boolean isXmppNotifyCapable(final String typeId) {
    return xmppNotifyCapable.contains(typeId);
  }

  public boolean showDeleted(final String typeId) {
    return showDeleted.contains(typeId);
  }

}
