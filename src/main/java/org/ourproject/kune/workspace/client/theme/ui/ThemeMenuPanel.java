/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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

package org.ourproject.kune.workspace.client.theme.ui;

import org.ourproject.kune.platf.client.services.ColorTheme;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.BottomTrayIcon;
import org.ourproject.kune.workspace.client.theme.ThemeMenuPresenter;
import org.ourproject.kune.workspace.client.theme.ThemeMenuView;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;

public class ThemeMenuPanel extends BottomTrayIcon implements ThemeMenuView {

    private final MenuBar themesMB;
    private final ThemeMenuPresenter presenter;
    private final ColorTheme colorTheme;

    public ThemeMenuPanel(final ThemeMenuPresenter presenter, final I18nTranslationService i18n,
            final ColorTheme colorTheme) {
        super(i18n.t("Select Workspace theme for this group"));
        this.presenter = presenter;
        this.colorTheme = colorTheme;
        themesMB = new MenuBar(true);
        themesMB.addStyleDependentName("bottomMenu");
        this.addItem(Images.App.getInstance().themeChoose().getHTML(), true, themesMB);
    }

    public void setThemes(final String[] themes) {
        for (int i = 0; i < themes.length; i++) {
            final String theme = themes[i];
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
