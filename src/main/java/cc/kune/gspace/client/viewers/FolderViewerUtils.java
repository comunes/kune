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
package cc.kune.gspace.client.viewers;

import javax.annotation.Nonnull;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.registry.IconsRegistry;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.services.ImageSize;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AbstractContentSimpleDTO;
import cc.kune.core.shared.dto.BasicMimeTypeDTO;
import cc.kune.core.shared.dto.ContainerDTO;
import cc.kune.core.shared.dto.ContainerSimpleDTO;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.HasContent;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.gspace.client.actions.ActionGroups;
import cc.kune.gspace.client.actions.ShowHelpContainerEvent;
import cc.kune.gspace.client.viewers.FolderViewerPresenter.FolderViewerView;
import cc.kune.gspace.client.viewers.TutorialViewer.OnTutorialClose;
import cc.kune.gspace.client.viewers.items.FolderItemDescriptor;
import cc.kune.trash.shared.TrashToolConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class FolderViewerUtils.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class FolderViewerUtils {

  /** The actions registry. */
  private final ActionRegistryByType actionsRegistry;

  /** The capab reg. */
  private final ContentCapabilitiesRegistry capabReg;

  /** The download utils provider. */
  private final Provider<ClientFileDownloadUtils> downloadUtilsProvider;

  /** The event bus. */
  private final EventBus eventBus;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The icons registry. */
  private final IconsRegistry iconsRegistry;

  /** The path toolbar utils. */
  private final PathToolbarUtils pathToolbarUtils;

  /** The session. */
  private final Session session;

  /** The state manager. */
  private final StateManager stateManager;

  /** The view. */
  private AbstractFolderViewerView view;

  /**
   * Instantiates a new folder viewer utils.
   * 
   * @param capabilitiesRegistry
   *          the capabilities registry
   * @param eventBus
   *          the event bus
   * @param session
   *          the session
   * @param downloadUtilsProvider
   *          the download utils provider
   * @param i18n
   *          the i18n
   * @param actionsRegistry
   *          the actions registry
   * @param stateManager
   *          the state manager
   * @param pathToolbarUtils
   *          the path toolbar utils
   */
  @Inject
  public FolderViewerUtils(final ContentCapabilitiesRegistry capabilitiesRegistry,
      final EventBus eventBus, final Session session,
      final Provider<ClientFileDownloadUtils> downloadUtilsProvider, final I18nTranslationService i18n,
      final ActionRegistryByType actionsRegistry, final StateManager stateManager,
      final PathToolbarUtils pathToolbarUtils) {
    this.capabReg = capabilitiesRegistry;
    this.eventBus = eventBus;
    this.session = session;
    this.downloadUtilsProvider = downloadUtilsProvider;
    this.i18n = i18n;
    this.actionsRegistry = actionsRegistry;
    this.stateManager = stateManager;
    this.iconsRegistry = capabilitiesRegistry.getIconsRegistry();
    this.pathToolbarUtils = pathToolbarUtils;
  }

  /**
   * Adds the item.
   * 
   * @param tool
   *          the tool
   * @param content
   *          the content
   * @param mimeType
   *          the mime type
   * @param status
   *          the status
   * @param parentStateToken
   *          the parent state token
   * @param rights
   *          the rights
   * @param modifiedOn
   *          the modified on
   * @param isContainer
   *          the is container
   */
  private void addItem(final String tool, final AbstractContentSimpleDTO content,
      final BasicMimeTypeDTO mimeType, final ContentStatus status, final StateToken parentStateToken,
      final AccessRights rights, final long modifiedOn, final boolean isContainer) {
    final StateToken stateToken = content.getStateToken();
    final String typeId = content.getTypeId();
    final String name = content.getName();
    final Object icon = mimeType != null ? getIcon(stateToken, typeId, mimeType) : getIcon(stateToken,
        typeId, status);
    final String tooltip = getTooltip(stateToken, mimeType,
        capabReg.isDragable(typeId) && rights.isAdministrable());
    final boolean thisTypeShouldShowDelete = capabReg.showDeleted(typeId)
        || parentStateToken.getTool().equals(TrashToolConstants.TOOL_NAME);
    final boolean showAllDeleted = session.getShowDeletedContent();
    if (status.equals(ContentStatus.inTheDustbin) && !(thisTypeShouldShowDelete || showAllDeleted)) {
      // Don't show
      // NotifyUser.info("Deleted, don't show: " + parentStateToken + "  " +
      // thisTypeShouldShowDelete);
    } else {
      final GuiActionDescCollection currentActions = actionsRegistry.getCurrentActions(tool, content,
          typeId, status, session.isLogged(), rights, ActionGroups.ITEM_MENU);
      final FolderItemDescriptor item = new FolderItemDescriptor(genId(stateToken),
          genId(parentStateToken), icon, name, tooltip, status, stateToken, modifiedOn,
          capabReg.isDragable(typeId) && rights.isAdministrable(), capabReg.isDropable(typeId)
              && rights.isAdministrable(), currentActions, isContainer);
      getView().addItem(item, new ClickHandler() {
        @Override
        public void onClick(final ClickEvent event) {
          event.stopPropagation();
          stateManager.gotoStateToken(stateToken);
        }
      }, new DoubleClickHandler() {
        @Override
        public void onDoubleClick(final DoubleClickEvent event) {
          event.stopPropagation();
          // stateManager.gotoStateToken(stateToken);
        }
      });
    }
  }

  /**
   * Creates the child items.
   * 
   * @param container
   *          the container
   * @param containerRights
   *          the container rights
   */
  private void createChildItems(final ContainerDTO container, final AccessRights containerRights) {
    if (container.getContents().size() + container.getChilds().size() == 0) {
      final String typeId = container.getTypeId();
      // No elements here, so, we show a empty message (or a tutorial)
      if (session.isLogged() && capabReg.getEmptyFolderTutorialRegistry().hasTutorial(typeId)) {
        // If we have a tutorial, we show it.
        ShowHelpContainerEvent.fire(eventBus, new OnTutorialClose() {
          @Override
          public void onClose() {
            showEmptyMsg(typeId);
          }
        });
      } else {
        // If not, we show the empty message
        showEmptyMsg(typeId);
      }
    } else {
      // Folders
      for (final ContainerSimpleDTO childFolder : container.getChilds()) {
        addItem(container.getAbsolutePath()[0].getStateToken().getTool(), childFolder, null,
            ContentStatus.publishedOnline, childFolder.getParentToken(), containerRights,
            FolderViewerView.NO_DATE, true);
      }
      // Other contents (docs, etc)
      for (final ContentSimpleDTO content : container.getContents()) {
        assert content != null;
        addItem(container.getAbsolutePath()[0].getStateToken().getTool(), content,
            content.getMimeType(), content.getStatus(), content.getStateToken().copy().clearDocument(),
            content.getRights(), content.getModifiedOn(), false);
      }
      getView().showFolder();
    }
  }

  /**
   * Gen id.
   * 
   * @param token
   *          the token
   * @return the string
   */
  private String genId(final StateToken token) {
    return "k-cnav-" + token.toString().replace(StateToken.SEPARATOR, "-");
  }

  /**
   * Gets the icon.
   * 
   * @param token
   *          the token
   * @param contentTypeId
   *          the content type id
   * @param mimeType
   *          the mime type
   * @return the icon
   */
  private Object getIcon(final StateToken token, final String contentTypeId,
      final BasicMimeTypeDTO mimeType) {
    if (mimeType != null && mimeType.getType().equals("image")) {
      return downloadUtilsProvider.get().getImageResizedUrl(token, ImageSize.ico);
    } else {
      return iconsRegistry.getContentTypeIcon(contentTypeId, mimeType);
    }
  }

  /**
   * Gets the icon.
   * 
   * @param stateToken
   *          the state token
   * @param typeId
   *          the type id
   * @param status
   *          the status
   * @return the icon
   */
  private Object getIcon(final StateToken stateToken, final String typeId, final ContentStatus status) {
    return iconsRegistry.getContentTypeIcon(typeId, status);
  }

  /**
   * Gets the tooltip.
   * 
   * @param token
   *          the token
   * @param mimeType
   *          the mime type
   * @param draggable
   *          the draggable
   * @return the tooltip
   */
  private String getTooltip(final StateToken token, final BasicMimeTypeDTO mimeType,
      final boolean draggable) {
    if (mimeType != null && (mimeType.isImage() || mimeType.isPdf())) {
      // Used for previews
      return null;
    } else {
      return draggable ? i18n.t("Click to open. Drag and drop to move this to another place")
          : i18n.t("Click to open");
    }
  }

  /**
   * Gets the view.
   * 
   * @return the view
   */
  private AbstractFolderViewerView getView() {
    return view;
  }

  /**
   * Sets the content.
   * 
   * @param view
   *          the view
   * @param state
   *          the state
   */
  public void setContent(final AbstractFolderViewerView view, @Nonnull final HasContent state) {
    this.view = view;
    getView().clear();
    final StateContainerDTO stateContainer = (StateContainerDTO) state;
    getView().setContainer(stateContainer);
    final AccessRights rights = stateContainer.getContainerRights();
    // NotifyUser.info("Rights: " + rights, true);
    final GuiActionDescCollection topActions = actionsRegistry.getCurrentActions(
        stateContainer.getToolName(), stateContainer.getGroup(), stateContainer.getTypeId(),
        session.isLogged(), rights, ActionGroups.TOPBAR);
    final GuiActionDescCollection bottomActions = actionsRegistry.getCurrentActions(
        stateContainer.getToolName(), stateContainer.getGroup(), stateContainer.getTypeId(),
        session.isLogged(), rights, ActionGroups.BOTTOMBAR);
    final ContainerDTO container = stateContainer.getContainer();
    final GuiActionDescCollection pathActions = pathToolbarUtils.createPath(stateContainer.getGroup(),
        container, true, false);
    bottomActions.addAll(pathActions);
    getView().setSubheaderActions(topActions);
    getView().setFooterActions(bottomActions);
    createChildItems(container, stateContainer.getContainerRights());
  }

  /**
   * Show empty msg.
   * 
   * @param typeId
   *          the type id
   */
  private void showEmptyMsg(final String typeId) {
    // msg is already translated
    final String msg = session.isLogged() ? capabReg.getEmptyMessagesRegistry().getContentTypeIcon(
        typeId) : capabReg.getEmptyMessagesRegistryNotLogged().getContentTypeIcon(typeId);
    final String emptyMessage = TextUtils.empty(msg) ? i18n.t("This is empty.") : msg;
    getView().showEmptyMsg(emptyMessage);
  }
}
