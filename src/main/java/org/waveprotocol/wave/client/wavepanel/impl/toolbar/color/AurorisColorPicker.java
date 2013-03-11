package org.waveprotocol.wave.client.wavepanel.impl.toolbar.color;

import net.auroris.ColorPicker.client.ColorPicker;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.inject.Singleton;

/**
 * The Class AurorisColorPicker enables the auroris color picker in wave editor.
 */
@Singleton
public class AurorisColorPicker extends AbstractColorPicker {

  /**
   * Instantiates a new sample custom color picker.
   * 
   * @param colorPicker
   *          the color picker
   */
  public AurorisColorPicker() {
    super(ComplexColorPicker.getInstance());

    final FlowPanel auPickerPanel = new FlowPanel();
    auPickerPanel.setWidth("155px");
    auPickerPanel.addStyleName("k-aurorisColorPicker");
    final ColorPicker auPicker = new ColorPicker();
    final Button okBtn = new Button("Ok");
    final Button cancelBtn = new Button("Cancel");
    okBtn.addStyleName("k-fl");
    okBtn.addStyleName("k-aurorisColorPicker-btn");
    cancelBtn.addStyleName("k-fr");
    cancelBtn.addStyleName("k-aurorisColorPicker-btn");
    auPickerPanel.add(auPicker);
    auPickerPanel.add(okBtn);
    auPickerPanel.add(cancelBtn);
    okBtn.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        onColorChoose("#" + auPicker.getHexColor());
        colorPicker.showWidget(0);
      }
    });
    cancelBtn.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        colorPicker.showWidget(0);
      }
    });

    final PushButton custom = new PushButton("Custom...");
    custom.addStyleName(ComplexColorPicker.style.margins());
    custom.setStylePrimaryName(ComplexColorPicker.style.customColorPushbutton());
    custom.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        // ComplexColorPicker is a DeckPanel, so we show our widget
        colorPicker.showWidget(1);
      }
    });

    initWidget(auPickerPanel);

    // We add the button and the panel to the ComplexColorPicker (the button
    // opens the panel)
    colorPicker.addToBottom(custom);
    colorPicker.addColorPicker(this);
  }

}
