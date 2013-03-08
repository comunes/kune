package cc.kune.colorpicker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class KuneColorPickerEntryPoint implements EntryPoint {

  /**
   * This is the entry point method.
   */
  @Override
  public void onModuleLoad() {
   RootPanel.get().add(new ComplexColorPicker(new OnColorSelectedListener() {

    @Override
    public void onColorChoose(String color) {
      GWT.log("Color: " + color);
    }
  }));
  }
}
