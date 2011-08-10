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
package cc.kune.gspace.client.viewers;

import javax.annotation.Nonnull;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.ui.EditEvent;
import cc.kune.common.client.ui.EditEvent.EditHandler;
import cc.kune.common.client.ui.HasEditHandler;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.registry.IconsRegistry;
import cc.kune.core.client.services.FileDownloadUtils;
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
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.actions.ActionGroups;
import cc.kune.gspace.client.actions.RenameAction;
import cc.kune.gspace.client.actions.RenameListener;
import cc.kune.gspace.client.tool.ContentViewer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class FolderViewerPresenter extends
    Presenter<FolderViewerPresenter.FolderViewerView, FolderViewerPresenter.FolderViewerProxy> implements
    ContentViewer {

  @ProxyCodeSplit
  public interface FolderViewerProxy extends Proxy<FolderViewerPresenter> {
  }

  public interface FolderViewerView extends View {

    long NO_DATE = 0;

    void addItem(FolderItemDescriptor item, ClickHandler clickHandler,
        DoubleClickHandler doubleClickHandler);

    void attach();

    void clear();

    void detach();

    void editTitle();

    HasEditHandler getEditTitle();

    void highlightTitle();

    void setActions(GuiActionDescCollection actions);

    void setContainer(StateContainerDTO state);

    void setEditableTitle(String title);

    void showEmptyMsg(String contentTypeId);
  }

  private final ActionRegistryByType actionsRegistry;
  private final ContentCapabilitiesRegistry capabilitiesRegistry;
  private final Provider<FileDownloadUtils> downloadUtilsProvider;
  private HandlerRegistration editHandler;
  private final I18nTranslationService i18n;
  private final IconsRegistry iconsRegistry;
  private final PathToolbarUtils pathToolbarUtils;
  private final Provider<RenameAction> renameAction;
  private final Session session;
  private final StateManager stateManager;
  private final boolean useGenericImageIcon;

  @Inject
  public FolderViewerPresenter(final EventBus eventBus, final FolderViewerView view,
      final FolderViewerProxy proxy, final Session session, final StateManager stateManager,
      final I18nTranslationService i18n, final ActionRegistryByType actionsRegistry,
      final Provider<FileDownloadUtils> downloadUtilsProvider,
      final Provider<RenameAction> renameAction, final ContentCapabilitiesRegistry capabilitiesRegistry,
      final PathToolbarUtils pathToolbarUtils) {
    super(eventBus, view, proxy);
    this.session = session;
    this.stateManager = stateManager;
    this.i18n = i18n;
    this.actionsRegistry = actionsRegistry;
    this.downloadUtilsProvider = downloadUtilsProvider;
    this.capabilitiesRegistry = capabilitiesRegistry;
    this.pathToolbarUtils = pathToolbarUtils;
    iconsRegistry = capabilitiesRegistry.getIconsRegistry();
    useGenericImageIcon = false;
    this.renameAction = renameAction;
  }

  private void addItem(final AbstractContentSimpleDTO content, final BasicMimeTypeDTO mimeType,
      final ContentStatus status, final StateToken parentStateToken, final AccessRights rights,
      final long modifiedOn) {
    final StateToken stateToken = content.getStateToken();
    final String typeId = content.getTypeId();
    final String name = content.getName();
    final Object icon = mimeType != null ? getIcon(stateToken, typeId, mimeType) : getIcon(stateToken,
        typeId, status);
    final String tooltip = getTooltip(stateToken, mimeType);
    if (status.equals(ContentStatus.inTheDustbin)
        && (!capabilitiesRegistry.showDeleted(typeId) && !session.getShowDeletedContent())) {
      // Don't show
      // NotifyUser.info("Deleted, don't show");
    } else {
      final FolderItemDescriptor item = new FolderItemDescriptor(genId(stateToken),
          genId(parentStateToken), icon, name, tooltip, status, stateToken, modifiedOn,
          capabilitiesRegistry.isDragable(typeId) && rights.isAdministrable(),
          capabilitiesRegistry.isDropable(typeId) && rights.isAdministrable(),
          actionsRegistry.getCurrentActions(content, typeId, status, session.isLogged(), rights,
              ActionGroups.ITEM_MENU));
      getView().addItem(item, new ClickHandler() {
        @Override
        public void onClick(final ClickEvent event) {
          stateManager.gotoStateToken(stateToken);
        }
      }, new DoubleClickHandler() {
        @Override
        public void onDoubleClick(final DoubleClickEvent event) {
          stateManager.gotoStateToken(stateToken);
        }
      });
    }
  }

  @Override
  public void attach() {
    getView().attach();
    if (editHandler == null) {
      createEditHandler();
    }
  }

  private void createChildItems(final ContainerDTO container, final AccessRights containerRights) {
    if (container.getContents().size() + container.getChilds().size() == 0) {
      getView().showEmptyMsg(container.getTypeId());
    } else {
      // Folders
      for (final ContainerSimpleDTO childFolder : container.getChilds()) {
        addItem(childFolder, null, ContentStatus.publishedOnline,
            childFolder.getStateToken().copy().setFolder(childFolder.getParentFolderId()),
            containerRights, FolderViewerView.NO_DATE);
      }
      // Other contents (docs, etc)
      for (final ContentSimpleDTO content : container.getContents()) {
        assert content != null;
        addItem(content, content.getMimeType(), content.getStatus(),
            content.getStateToken().copy().clearDocument(), content.getRights(), content.getModifiedOn());
      }
    }
  }

  private void createEditHandler() {
    // Duplicate in DocViewerPresenter
    editHandler = getView().getEditTitle().addEditHandler(new EditHandler() {
      @Override
      public void fire(final EditEvent event) {
        renameAction.get().rename(session.getCurrentStateToken(), session.getCurrentState().getTitle(),
            event.getText(), new RenameListener() {
              @Override
              public void onFail(final StateToken token, final String oldTitle) {
                getView().setEditableTitle(oldTitle);
              }

              @Override
              public void onSuccess(final StateToken token, final String title) {
                getView().setEditableTitle(title);
              }
            });
      }
    });
  }

  @Override
  public void detach() {
    getView().detach();
  }

  public void editTitle() {
    getView().editTitle();
  }

  private String genId(final StateToken token) {
    return "k-cnav-" + token.toString().replace(StateToken.SEPARATOR, "-");
  }

  private Object getIcon(final StateToken token, final String contentTypeId,
      final BasicMimeTypeDTO mimeType) {
    if (!useGenericImageIcon && mimeType != null && mimeType.getType().equals("image")) {
      return downloadUtilsProvider.get().getImageResizedUrl(token, ImageSize.ico);
    } else {
      return iconsRegistry.getContentTypeIcon(contentTypeId, mimeType);
    }
  }

  private Object getIcon(final StateToken stateToken, final String typeId, final ContentStatus status) {
    return iconsRegistry.getContentTypeIcon(typeId, status);
  }

  private String getTooltip(final StateToken token, final BasicMimeTypeDTO mimeType) {
    if (mimeType != null && (mimeType.isImage() || mimeType.isPdf())) {
      // Used for previews
      return null;
    } else {
      return i18n.t("Double click to open");
    }
  }

  public void highlightTitle() {
    getView().highlightTitle();
  }

  public void refreshState() {
    setContent((HasContent) session.getCurrentState());
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  @Override
  public void setContent(@Nonnull final HasContent state) {
    getView().clear();
    final StateContainerDTO stateContainer = (StateContainerDTO) state;
    getView().setContainer(stateContainer);
    final AccessRights rights = stateContainer.getContainerRights();
    final GuiActionDescCollection actions = actionsRegistry.getCurrentActions(stateContainer.getGroup(),
        stateContainer.getTypeId(), session.isLogged(), rights, ActionGroups.TOOLBAR);
    final ContainerDTO container = stateContainer.getContainer();
    pathToolbarUtils.createPath(container, actions, true);
    getView().setActions(actions);
    createChildItems(container, stateContainer.getContainerRights());
  }
}