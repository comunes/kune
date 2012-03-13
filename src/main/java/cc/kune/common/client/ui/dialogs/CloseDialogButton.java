package cc.kune.common.client.ui.dialogs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

public class CloseDialogButton extends Composite implements HasClickHandlers {

  interface CloseDialogButtonUiBinder extends UiBinder<Widget, CloseDialogButton> {
  }

  private static CloseDialogButtonUiBinder uiBinder = GWT.create(CloseDialogButtonUiBinder.class);

  @UiField
  PushButton closeBtn;

  public CloseDialogButton() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public HandlerRegistration addClickHandler(final ClickHandler handler) {
    return closeBtn.addClickHandler(handler);
  }

  @Override
  public void setVisible(final boolean visible) {
    closeBtn.setVisible(visible);
  }

}
