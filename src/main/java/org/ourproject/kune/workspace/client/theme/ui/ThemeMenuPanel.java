/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.workspace.client.theme.ui;

import org.ourproject.kune.platf.client.services.ColorTheme;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.workspace.client.theme.ThemeMenuPresenter;
import org.ourproject.kune.workspace.client.theme.ThemeMenuView;
import org.ourproject.kune.workspace.client.workspace.ui.BottomTrayIcon;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;

public class ThemeMenuPanel extends BottomTrayIcon implements ThemeMenuView {

    private final MenuBar themesMB;
    private final ThemeMenuPresenter presenter;

    public ThemeMenuPanel(final ThemeMenuPresenter presenter) {
        super(Kune.I18N.t("Select Workspace theme for this group"));
        this.presenter = presenter;
        themesMB = new MenuBar(true);
        themesMB.setStyleName("kune-ThemeMenu-offset");
        this.addItem(Images.App.getInstance().themeChoose().getHTML(), true, themesMB);
    }

    public void setThemes(final String[] themes) {
        for (int i = 0; i < themes.length; i++) {
            final String theme = themes[i];
            final ColorTheme colorTheme = Kune.getInstance().theme;
            colorTheme.setTheme(theme);
            String themeName = colorTheme.getThemeName();
            String mainColor = colorTheme.getContentMainBorder();
            themesMB.addItem("<span style=\"color: " + mainColor + ";\">" + themeName + "</span>", true, new Command() {
                public void execute() {
                    presenter.chooseTheme(theme);
                }
            });
        }
    }

    public void setVisible(final boolean visible) {
        super.setVisible(visible);
    }
}
