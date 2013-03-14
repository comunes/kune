package cc.kune.colorpicker.client;


import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class KuneColorPickerEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        ColorWebSafePalettePresenter presenter = new ColorWebSafePalettePresenter();
        ColorWebSafePalettePanel panel = new ColorWebSafePalettePanel(presenter);
        panel.show(10, 10);
    }
}
