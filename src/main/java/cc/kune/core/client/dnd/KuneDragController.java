package cc.kune.core.client.dnd;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class KuneDragController extends PickupDragController {

  public KuneDragController() {
    super(RootPanel.get(), false);
    setBehaviorDragProxy(true);
    setBehaviorMultipleSelection(false);
    setBehaviorScrollIntoView(false);
    setBehaviorDragStartSensitivity(5);
    Window.addResizeHandler(new ResizeHandler() {

      @Override
      public void onResize(final ResizeEvent event) {
        final int width = event.getWidth();
        final int height = event.getHeight();
        setRootPanelSize(width, height);
      }

    });
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        setRootPanelSize(Window.getClientWidth(), Window.getClientHeight());
      }
    });
  }

  @Override
  public void dragEnd() {
    super.dragEnd();
    clearSelection();
  }

  private void setRootPanelSize(final int width, final int height) {
    RootPanel.get().setPixelSize(width, height);
  }

}
