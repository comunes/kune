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
import cc.kune.gspace.client.viewers.items.FolderItemDescriptor;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class FolderViewerUtils {

  private final ActionRegistryByType actionsRegistry;
  private final ContentCapabilitiesRegistry capabReg;
  private final Provider<ClientFileDownloadUtils> downloadUtilsProvider;
  private final I18nTranslationService i18n;
  private final IconsRegistry iconsRegistry;
  private final PathToolbarUtils pathToolbarUtils;
  private final Session session;
  private final StateManager stateManager;
  private AbstractFolderViewerView view;

  @Inject
  public FolderViewerUtils(final ContentCapabilitiesRegistry capabilitiesRegistry,
      final EventBus eventBus, final Session session,
      final Provider<ClientFileDownloadUtils> downloadUtilsProvider, final I18nTranslationService i18n,
      final ActionRegistryByType actionsRegistry, final StateManager stateManager,
      final PathToolbarUtils pathToolbarUtils) {
    this.capabReg = capabilitiesRegistry;
    this.session = session;
    this.downloadUtilsProvider = downloadUtilsProvider;
    this.i18n = i18n;
    this.actionsRegistry = actionsRegistry;
    this.stateManager = stateManager;
    this.iconsRegistry = capabilitiesRegistry.getIconsRegistry();
    this.pathToolbarUtils = pathToolbarUtils;
    eventBus.addHandler(ShowHelpContainerEvent.getType(),
        new ShowHelpContainerEvent.ShowHelpContainerHandler() {
          @Override
          public void onShowHelpContainer(final ShowHelpContainerEvent event) {
            getView().showTutorial(event.getTool());
          }
        });
  }

  private void addItem(final AbstractContentSimpleDTO content, final BasicMimeTypeDTO mimeType,
      final ContentStatus status, final StateToken parentStateToken, final AccessRights rights,
      final long modifiedOn, final boolean isContainer) {
    final StateToken stateToken = content.getStateToken();
    final String typeId = content.getTypeId();
    final String name = content.getName();
    final Object icon = mimeType != null ? getIcon(stateToken, typeId, mimeType) : getIcon(stateToken,
        typeId, status);
    final String tooltip = getTooltip(stateToken, mimeType,
        capabReg.isDragable(typeId) && rights.isAdministrable());
    if (status.equals(ContentStatus.inTheDustbin)
        && (!capabReg.showDeleted(typeId) && !session.getShowDeletedContent())) {
      // Don't show
      // NotifyUser.info("Deleted, don't show");
    } else {
      final FolderItemDescriptor item = new FolderItemDescriptor(genId(stateToken),
          genId(parentStateToken), icon, name, tooltip, status, stateToken, modifiedOn,
          capabReg.isDragable(typeId) && rights.isAdministrable(), capabReg.isDropable(typeId)
              && rights.isAdministrable(), actionsRegistry.getCurrentActions(content, typeId, status,
              session.isLogged(), rights, ActionGroups.ITEM_MENU), isContainer);
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

  private void createChildItems(final ContainerDTO container, final AccessRights containerRights) {
    if (container.getContents().size() + container.getChilds().size() == 0) {
      final String typeId = container.getTypeId();
      // No elements here, so, we show a empty message (or a tutorial)
      if (session.isLogged() && capabReg.getTutorialRegistry().hasTutorial(typeId)) {
        // If we have a tutorial, we show it.
        getView().showTutorial(container.getStateToken().getTool());
      } else {
        // If not, we show the empty message
        // msg is already translated
        final String msg = session.isLogged() ? capabReg.getEmptyMessagesRegistry().getContentTypeIcon(
            typeId) : capabReg.getEmptyMessagesRegistryNotLogged().getContentTypeIcon(typeId);
        final String emptyMessage = TextUtils.empty(msg) ? i18n.t("This is empty.") : msg;
        getView().showEmptyMsg(emptyMessage);
      }
    } else {
      // Folders
      for (final ContainerSimpleDTO childFolder : container.getChilds()) {
        addItem(childFolder, null, ContentStatus.publishedOnline,
            childFolder.getStateToken().copy().setFolder(childFolder.getParentFolderId()),
            containerRights, FolderViewerView.NO_DATE, true);
      }
      // Other contents (docs, etc)
      for (final ContentSimpleDTO content : container.getContents()) {
        assert content != null;
        addItem(content, content.getMimeType(), content.getStatus(),
            content.getStateToken().copy().clearDocument(), content.getRights(),
            content.getModifiedOn(), false);
      }
    }
  }

  private String genId(final StateToken token) {
    return "k-cnav-" + token.toString().replace(StateToken.SEPARATOR, "-");
  }

  private Object getIcon(final StateToken token, final String contentTypeId,
      final BasicMimeTypeDTO mimeType) {
    if (mimeType != null && mimeType.getType().equals("image")) {
      return downloadUtilsProvider.get().getImageResizedUrl(token, ImageSize.ico);
    } else {
      return iconsRegistry.getContentTypeIcon(contentTypeId, mimeType);
    }
  }

  private Object getIcon(final StateToken stateToken, final String typeId, final ContentStatus status) {
    return iconsRegistry.getContentTypeIcon(typeId, status);
  }

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

  private AbstractFolderViewerView getView() {
    return view;
  }

  public void setContent(final AbstractFolderViewerView view, @Nonnull final HasContent state) {
    this.view = view;
    getView().clear();
    final StateContainerDTO stateContainer = (StateContainerDTO) state;
    getView().setContainer(stateContainer);
    final AccessRights rights = stateContainer.getContainerRights();
    // NotifyUser.info("Rights: " + rights, true);
    final GuiActionDescCollection actions = actionsRegistry.getCurrentActions(stateContainer.getGroup(),
        stateContainer.getTypeId(), session.isLogged(), rights, ActionGroups.TOOLBAR);
    final ContainerDTO container = stateContainer.getContainer();
    final GuiActionDescCollection pathActions = pathToolbarUtils.createPath(container, true);
    getView().setSubheaderActions(actions);
    getView().setFooterActions(pathActions);
    createChildItems(container, stateContainer.getContainerRights());
  }
}
