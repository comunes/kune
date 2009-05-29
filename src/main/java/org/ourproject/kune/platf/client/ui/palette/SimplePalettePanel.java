package org.ourproject.kune.platf.client.ui.palette;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;

import com.gwtext.client.widgets.ColorPalette;
import com.gwtext.client.widgets.event.ColorPaletteListenerAdapter;

public class SimplePalettePanel extends AbstractPalettePanel implements SimplePaletteView {

    private transient final SimplePalettePresenter presenter;
    private transient final I18nTranslationService i18n;

    public SimplePalettePanel(final SimplePalettePresenter presenter, final I18nTranslationService i18n) {
        super();
        this.presenter = presenter;
        this.i18n = i18n;
    }

    @Override
    protected void createWidget() {
        final ColorPalette colorPalette = new ColorPalette();
        colorPalette.setTitle(i18n.t("Pick a color"));
        colorPalette.addListener(new ColorPaletteListenerAdapter() {
            @Override
            public void onSelect(final ColorPalette colorPalette, final String color) {
                presenter.onColorSelected(color);
            }
        });
        widget = colorPalette;
    }
}
