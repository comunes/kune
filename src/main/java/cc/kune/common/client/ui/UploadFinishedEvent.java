package cc.kune.common.client.ui;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class UploadFinishedEvent extends GwtEvent<UploadFinishedEvent.UploadFinishedHandler> {

  public interface HasUploadFinishedHandlers extends HasHandlers {
    HandlerRegistration addUploadFinishedHandler(UploadFinishedHandler handler);
  }

  public interface UploadFinishedHandler extends EventHandler {
    public void onUploadFinished(UploadFinishedEvent event);
  }

  private static final Type<UploadFinishedHandler> TYPE = new Type<UploadFinishedHandler>();

  public static void fire(final HasHandlers source, final cc.kune.common.shared.ui.UploadFile file) {
    final UploadFinishedEvent eventInstance = new UploadFinishedEvent(file);
    source.fireEvent(eventInstance);
  }

  public static void fire(final HasHandlers source, final UploadFinishedEvent eventInstance) {
    source.fireEvent(eventInstance);
  }

  public static Type<UploadFinishedHandler> getType() {
    return TYPE;
  }

  cc.kune.common.shared.ui.UploadFile file;

  protected UploadFinishedEvent() {
    // Possibly for serialization.
  }

  public UploadFinishedEvent(final cc.kune.common.shared.ui.UploadFile file) {
    this.file = file;
  }

  @Override
  protected void dispatch(final UploadFinishedHandler handler) {
    handler.onUploadFinished(this);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final UploadFinishedEvent other = (UploadFinishedEvent) obj;
    if (file == null) {
      if (other.file != null) {
        return false;
      }
    } else if (!file.equals(other.file)) {
      return false;
    }
    return true;
  }

  @Override
  public Type<UploadFinishedHandler> getAssociatedType() {
    return TYPE;
  }

  public cc.kune.common.shared.ui.UploadFile getFile() {
    return file;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (file == null ? 1 : file.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "UploadFinishedEvent[" + file + "]";
  }
}
