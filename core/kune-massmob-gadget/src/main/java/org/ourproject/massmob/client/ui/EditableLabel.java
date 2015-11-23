package org.ourproject.massmob.client.ui;

import org.ourproject.massmob.client.ui.EditEvent.EditHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import cc.kune.common.client.tooltip.Tooltip;

public class EditableLabel extends Composite {

  interface EditableLabelUiBinder extends UiBinder<Widget, EditableLabel> {
  }

  private static EditableLabelUiBinder uiBinder = GWT.create(EditableLabelUiBinder.class);

  @UiField
  Label label;
  @UiField
  TextBox textbox;

  public EditableLabel() {
    initWidget(uiBinder.createAndBindUi(this));
    label.setStylePrimaryName("editableLabel");
    Tooltip.to(label, "Click to edit");
  }

  public HandlerRegistration addEditHandler(final EditHandler handler) {
    return addHandler(handler, EditEvent.getType());
  }

  private void finishEdit() {
    label.setText(textbox.getText());
    label.setVisible(true);
    textbox.setVisible(false);
    fireEvent(new EditEvent(textbox.getValue()));
  }

  @UiHandler("textbox")
  void handleKeys(final KeyDownEvent event) {
    if (event.getNativeKeyCode() == 13) {
      finishEdit();
    }
  }

  @UiHandler("textbox")
  void onBlur(final BlurEvent event) {
    finishEdit();
  }

  @UiHandler("label")
  void onClick(final ClickEvent e) {
    label.setVisible(false);
    textbox.setVisible(true);
    textbox.setFocus(true);
    textbox.selectAll();
  }

  @UiHandler("label")
  void onMouseOut(final MouseOutEvent event) {
    label.removeStyleDependentName("high");
  }

  @UiHandler("label")
  void onMouseOver(final MouseOverEvent event) {
    label.addStyleDependentName("high");
  }

  public void setEnabled(final boolean enabled) {
    textbox.setEnabled(enabled);
  }

  public void setText(final String text) {
    label.setText(text);
    textbox.setText(text);
  }

}
