package cc.kune.gspace.client.ui.footer.license;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class LicenseChangedEvent extends GwtEvent<LicenseChangedEvent.LicenseChangedHandler> {

  public interface HasLicenseChangedHandlers extends HasHandlers {
    HandlerRegistration addLicenseChangedHandler(LicenseChangedHandler handler);
  }

  public interface LicenseChangedHandler extends EventHandler {
    public void onLicenseChanged(LicenseChangedEvent event);
  }

  private static final Type<LicenseChangedHandler> TYPE = new Type<LicenseChangedHandler>();

  public static void fire(final HasHandlers source) {
    source.fireEvent(new LicenseChangedEvent());
  }

  public static Type<LicenseChangedHandler> getType() {
    return TYPE;
  }

  public LicenseChangedEvent() {
  }

  @Override
  protected void dispatch(final LicenseChangedHandler handler) {
    handler.onLicenseChanged(this);
  }

  @Override
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }

  @Override
  public Type<LicenseChangedHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "LicenseChangedEvent[" + "]";
  }
}
