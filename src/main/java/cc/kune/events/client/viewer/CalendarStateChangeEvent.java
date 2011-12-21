package cc.kune.events.client.viewer;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class CalendarStateChangeEvent extends
    GwtEvent<CalendarStateChangeEvent.CalendarStateChangeHandler> {

  public interface CalendarStateChangeHandler extends EventHandler {
    public void onCalendarStateChange(CalendarStateChangeEvent event);
  }

  public interface HasCalendarStateChangeHandlers extends HasHandlers {
    HandlerRegistration addCalendarStateChangeHandler(CalendarStateChangeHandler handler);
  }

  private static final Type<CalendarStateChangeHandler> TYPE = new Type<CalendarStateChangeHandler>();

  public static void fire(final HasHandlers source) {
    source.fireEvent(new CalendarStateChangeEvent());
  }

  public static Type<CalendarStateChangeHandler> getType() {
    return TYPE;
  }

  public CalendarStateChangeEvent() {
  }

  @Override
  protected void dispatch(final CalendarStateChangeHandler handler) {
    handler.onCalendarStateChange(this);
  }

  @Override
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }

  @Override
  public Type<CalendarStateChangeHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "CalendarStateChangeEvent[" + "]";
  }
}
