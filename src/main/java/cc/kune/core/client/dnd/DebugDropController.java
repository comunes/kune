package cc.kune.core.client.dnd;

import cc.kune.common.client.notify.NotifyUser;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.ui.Widget;

public class DebugDropController extends SimpleDropController {

  public DebugDropController(final Widget dropTarget) {
    super(dropTarget);
  }

  @Override
  public void onEnter(final DragContext context) {
    NotifyUser.info("On enter in drop area");
    super.onEnter(context);
  }

  @Override
  public void onLeave(final DragContext context) {
    NotifyUser.info("On leave drop area");
    super.onLeave(context);
  }

  @Override
  public void onMove(final DragContext context) {
    // NotifyUser.info("On move over drop area");
    super.onMove(context);
  };

  @Override
  public void onPreviewDrop(final DragContext context) throws VetoDragException {
    NotifyUser.info("On preview drop");
    throw new VetoDragException();
  }
}
