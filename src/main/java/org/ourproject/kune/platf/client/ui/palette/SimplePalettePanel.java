/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.client.ui.palette;


import cc.kune.core.shared.i18n.I18nTranslationService;

import com.gwtext.client.widgets.ColorPalette;
import com.gwtext.client.widgets.event.ColorPaletteListenerAdapter;

public class SimplePalettePanel extends AbstractPalettePanel implements SimplePaletteView {

    private final SimplePalettePresenter presenter;
    private final I18nTranslationService i18n;

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
