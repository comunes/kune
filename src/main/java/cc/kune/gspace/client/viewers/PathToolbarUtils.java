package cc.kune.gspace.client.viewers;

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.dnd.FolderViewerDropController;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.registry.IconsRegistry;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.ContainerDTO;
import cc.kune.core.shared.dto.ContainerSimpleDTO;
import cc.kune.gspace.client.actions.GotoTokenAction;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class PathToolbarUtils {

  protected static final String CSSBTN = "k-button, k-fr";
  protected static final String CSSBTNC = "k-button, k-button-center, k-fr";
  protected static final String CSSBTNL = "k-button, k-button-left, k-fr";
  protected static final String CSSBTNR = "k-button, k-button-right, k-fr";

  private final Provider<FolderViewerDropController> dropController;
  private final EventBus eventBus;
  private final IconsRegistry iconsRegistry;
  private final StateManager stateManager;

  @Inject
  public PathToolbarUtils(final Provider<FolderViewerDropController> dropController,
      final StateManager stateManager, final ContentCapabilitiesRegistry capabilitiesRegistry,
      final EventBus eventBus) {
    this.dropController = dropController;
    this.stateManager = stateManager;
    this.eventBus = eventBus;
    iconsRegistry = capabilitiesRegistry.getIconsRegistry();
  }

  String calculateStyle(final int pos, final int length) {
    if (length == 1) {
      return CSSBTN;
    }
    if (pos == 0) {
      return CSSBTNL;
    }
    if (pos == length - 1) {
      return CSSBTNR;
    }
    return CSSBTNC;
  }

  public void createPath(final ContainerDTO container, final GuiActionDescCollection actions,
      final boolean withDrop) {
    final ContainerSimpleDTO[] path = container.getAbsolutePath();
    final int pathLength = path.length;
    if (pathLength > 0) {
      for (int i = pathLength - 1; i >= 0; i--) {
        final ButtonDescriptor btn = createPathButton(path[i], pathLength, i);
        if (withDrop) {
          if (i != pathLength - 1) {
            final FolderViewerDropController dropTarget = dropController.get();
            dropTarget.setTarget(path[i].getStateToken());
            btn.setDropTarget(dropTarget);
          }
        }
        actions.add(btn);
      }
    }
  }

  private ButtonDescriptor createPathButton(final ContainerSimpleDTO container, final int length,
      final int pos) {
    final String style = calculateStyle(pos, length);
    final String title = container.getName();
    final ButtonDescriptor btn = new ButtonDescriptor(new GotoTokenAction(
        iconsRegistry.getContentTypeIcon(container.getTypeId()), TextUtils.ellipsis(title, 15),
        container.getStateToken(), style, stateManager, eventBus));
    if (title.length() > 15) {
      btn.withToolTip(title);
    }
    return btn;
  }
}
