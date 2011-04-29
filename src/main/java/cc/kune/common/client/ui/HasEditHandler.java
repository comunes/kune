package cc.kune.common.client.ui;

import cc.kune.common.client.ui.EditEvent.EditHandler;

import com.google.gwt.event.shared.HandlerRegistration;

public interface HasEditHandler {

  HandlerRegistration addEditHandler(EditHandler handler);

}
