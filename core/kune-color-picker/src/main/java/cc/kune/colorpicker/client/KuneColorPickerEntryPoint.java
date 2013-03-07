package cc.kune.colorpicker.client;

import net.auroris.ColorPicker.client.ColorPicker;

import com.google.gwt.core.client.EntryPoint;
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
//    ColorWebSafePalettePresenter pres = new ColorWebSafePalettePresenter();
//    ColorWebSafePalettePanel panel = new ColorWebSafePalettePanel(pres);
   // panel.show(0, 0);

   RootPanel.get().add(new ColorPalettePanel());
   RootPanel.get().add(new ColorPicker());
  }
}
