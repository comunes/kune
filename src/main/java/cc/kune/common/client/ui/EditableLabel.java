package cc.kune.common.client.ui;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.EditEvent.EditHandler;

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
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditableLabel extends Composite implements HasEditHandler {

  interface EditableLabelUiBinder extends UiBinder<Widget, EditableLabel> {
  }

  private static EditableLabelUiBinder uiBinder = GWT.create(EditableLabelUiBinder.class);

  private boolean editable;
  @UiField
  InlineLabel label;
  @UiField
  TextBox textbox;
  private String tooltip;

  public EditableLabel() {
    initWidget(uiBinder.createAndBindUi(this));
    label.setStylePrimaryName("k-editableLabel");
    editable = false;
    tooltip = "";
  }

  @Override
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
    if (editable) {
      label.setVisible(false);
      textbox.setVisible(true);
      textbox.setFocus(true);
      textbox.selectAll();
    }
  }

  @UiHandler("label")
  void onMouseOut(final MouseOutEvent event) {
    label.removeStyleDependentName("high");
  }

  @UiHandler("label")
  void onMouseOver(final MouseOverEvent event) {
    label.addStyleDependentName("high");
  }

  public void setEditable(final boolean editable) {
    this.editable = editable;
    Tooltip.to(label, editable ? tooltip : "");
  }

  public void setText(final String text) {
    label.setText(text);
    textbox.setText(text);
  }

  public void setTooltip(final String tooltip) {
    this.tooltip = tooltip;
  }

}
