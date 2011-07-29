package cc.kune.core.client.dnd;

import cc.kune.gspace.client.GSpaceArmor;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class KuneDragController extends PickupDragController {

  private final Widget mainPanel;

  @Inject
  public KuneDragController(final GSpaceArmor armor) {
    super(RootPanel.get(), false);
    mainPanel = (Widget) armor.getMainpanel();
    setBehaviorDragProxy(true);
    setBehaviorMultipleSelection(false);
    setBehaviorScrollIntoView(false);
    setBehaviorDragStartSensitivity(5);
    Window.addResizeHandler(new ResizeHandler() {

      @Override
      public void onResize(final ResizeEvent event) {
        setRootPanelSize();
      }

    });
    setRootPanelSize();
  }

  @Override
  public void dragEnd() {
    super.dragEnd();
    clearSelection();
  }

  private void setRootPanelSize() {
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        // - 100 because of problems in chrommium (issue #76), not needed in ff
        RootPanel.get().setPixelSize(mainPanel.getOffsetWidth(), mainPanel.getOffsetHeight() - 100);
      }
    });
  }

}
