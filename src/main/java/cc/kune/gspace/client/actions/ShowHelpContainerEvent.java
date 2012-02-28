package cc.kune.gspace.client.actions;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class ShowHelpContainerEvent extends GwtEvent<ShowHelpContainerEvent.ShowHelpContainerHandler> {

  public interface HasShowHelpContainerHandlers extends HasHandlers {
    HandlerRegistration addShowHelpContainerHandler(ShowHelpContainerHandler handler);
  }

  public interface ShowHelpContainerHandler extends EventHandler {
    public void onShowHelpContainer(ShowHelpContainerEvent event);
  }

  private static final Type<ShowHelpContainerHandler> TYPE = new Type<ShowHelpContainerHandler>();

  public static void fire(final HasHandlers source, final String tool) {
    source.fireEvent(new ShowHelpContainerEvent(tool));
  }

  public static Type<ShowHelpContainerHandler> getType() {
    return TYPE;
  }

  private final String tool;

  public ShowHelpContainerEvent(final String tool) {
    this.tool = tool;
  }

  @Override
  protected void dispatch(final ShowHelpContainerHandler handler) {
    handler.onShowHelpContainer(this);
  }

  @Override
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }

  @Override
  public Type<ShowHelpContainerHandler> getAssociatedType() {
    return TYPE;
  }

  public String getTool() {
    return tool;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "ShowHelpContainerEvent[" + "]";
  }
}
