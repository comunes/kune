package org.ourproject.kune.platf.client.ui.palette;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;

import com.gwtext.client.widgets.ColorPalette;
import com.gwtext.client.widgets.event.ColorPaletteListenerAdapter;

public class SimplePalettePanel extends AbstractPalettePanel implements SimplePaletteView {

    private final SimplePalettePresenter presenter;
    private final I18nTranslationService i18n;

    public SimplePalettePanel(final SimplePalettePresenter presenter, I18nTranslationService i18n) {
        this.presenter = presenter;
        this.i18n = i18n;
    }

    @Override
    protected void createPalette() {
        ColorPalette colorPalette = new ColorPalette();
        colorPalette.setTitle(i18n.t("Pick a color"));
        colorPalette.addListener(new ColorPaletteListenerAdapter() {
            @Override
            public void onSelect(ColorPalette colorPalette, String color) {
                presenter.onColorSelected(color);
            }
        });
        widget = colorPalette;
    }
}
