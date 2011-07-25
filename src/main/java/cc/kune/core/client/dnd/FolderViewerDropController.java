package cc.kune.core.client.dnd;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.gspace.client.viewers.FolderItemWidget;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.ui.Widget;

public class FolderViewerDropController extends SimpleDropController {

  public FolderViewerDropController(final Widget dropTarget, final KuneDragController dragController) {
    super(dropTarget);
    dropTarget.addStyleName("k-drop-allowed");
    dropTarget.addAttachHandler(new Handler() {
      @Override
      public void onAttachOrDetach(final AttachEvent event) {
        if (!event.isAttached()) {
          dragController.unregisterDropController(FolderViewerDropController.this);
        } else {
          dragController.registerDropController(FolderViewerDropController.this);
        }
      }
    });
  }

  @Override
  public void onDrop(final DragContext context) {
    for (final Widget widget : context.selectedWidgets) {
      if (widget instanceof FolderItemWidget) {
        getDropTarget().removeStyleName("k-drop-allowed-hover");
        if (getDropTarget() instanceof FolderItemWidget) {
          // NotifyUser.info("" + ((FolderItemWidget)
          // getDropTarget()).getToken().toString() + " receive: "
          // + ((FolderItemWidget) widget).getToken().toString());
        }
        NotifyUser.info(TextUtils.IN_DEVELOPMENT);
      }
    }
    super.onDrop(context);
  }

  @Override
  public void onEnter(final DragContext context) {
    super.onEnter(context);
    for (final Widget widget : context.selectedWidgets) {
      if (widget instanceof FolderItemWidget) {
        getDropTarget().addStyleName("k-drop-allowed-hover");
      }
    }
  }

  @Override
  public void onLeave(final DragContext context) {
    super.onLeave(context);
    for (final Widget widget : context.selectedWidgets) {
      if (widget instanceof FolderItemWidget) {
        getDropTarget().removeStyleName("k-drop-allowed-hover");
      }
    }
  }

  @Override
  public void onPreviewDrop(final DragContext context) throws VetoDragException {
    for (final Widget widget : context.selectedWidgets) {
      if (widget instanceof FolderItemWidget) {
        getDropTarget().removeStyleName("k-drop-allowed-hover");
      } else {
        throw new VetoDragException();
      }
    }
    super.onPreviewDrop(context);
  }

}
