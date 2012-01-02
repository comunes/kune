package cc.kune.core.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class StackErrorEvent extends GwtEvent<StackErrorEvent.StackErrorHandler> { 

  public interface HasStackErrorHandlers extends HasHandlers {
    HandlerRegistration addStackErrorHandler(StackErrorHandler handler);
  }

  public interface StackErrorHandler extends EventHandler {
    public void onStackError(StackErrorEvent event);
  }

  private static final Type<StackErrorHandler> TYPE = new Type<StackErrorHandler>();

  public static void fire(HasHandlers source, java.lang.Throwable exception) {
    source.fireEvent(new StackErrorEvent(exception));
  }

  public static Type<StackErrorHandler> getType() {
    return TYPE;
  }

  java.lang.Throwable exception;

  public StackErrorEvent(java.lang.Throwable exception) {
    this.exception = exception;
  }

  protected StackErrorEvent() {
    // Possibly for serialization.
  }

  @Override
  public Type<StackErrorHandler> getAssociatedType() {
    return TYPE;
  }

  public java.lang.Throwable getException() {
    return exception;
  }

  @Override
  protected void dispatch(StackErrorHandler handler) {
    handler.onStackError(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    StackErrorEvent other = (StackErrorEvent) obj;
    if (exception == null) {
      if (other.exception != null)
        return false;
    } else if (!exception.equals(other.exception))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (exception == null ? 1 : exception.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "StackErrorEvent["
                 + exception
    + "]";
  }
}
