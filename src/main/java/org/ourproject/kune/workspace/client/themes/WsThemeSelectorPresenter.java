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
package org.ourproject.kune.workspace.client.themes;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.AbstractExtendedAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.ui.ActionExtensibleView;
import org.ourproject.kune.platf.client.actions.ui.CssStyleDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuItemDescriptor;

import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.InitDataDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener;

public class WsThemeSelectorPresenter implements WsThemeSelector {

    class ThemeAction extends AbstractExtendedAction {

        private final String theme;

        public ThemeAction(final String theme) {
            super();
            this.theme = theme;
            putValue(NAME, i18n.t(theme));
            putValue(SMALL_ICON, CssStyleDescriptor.create("k-wstheme-icon-" + theme));
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            // themeSelected.fire(new WsTheme(theme));
            menu.setText(i18n.t(theme));
        }
    }

    // private final Event<WsTheme> themeSelected;
    // private final Event<String> selectItemInUI;
    private final I18nTranslationService i18n;
    private MenuDescriptor menu;
    private final Session session;
    private ActionExtensibleView view;

    public WsThemeSelectorPresenter(final Session session, final I18nTranslationService i18n) {
        this.session = session;
        this.i18n = i18n;
        // this.themeSelected = new Event<WsTheme>("themeSelected");
        // this.selectItemInUI = new Event<String>("selectItemInUI");
    }

    @Override
    public void addThemeSelected(final Listener<WsTheme> listener) {
        // themeSelected.add(listener);
    }

    private void createActions() {
        createMenu();
        final InitDataDTO initData = session.getInitData();
        if (initData == null) {
            session.onInitDataReceived(new Listener<InitDataDTO>() {
                @Override
                public void onEvent(final InitDataDTO initData) {
                    setThemes(initData);
                }
            });
        } else {
            setThemes(initData);
        }
    }

    private void createMenu() {
        menu = new MenuDescriptor("&nbsp;");
        menu.putValue(Action.SMALL_ICON, "images/colors.gif");
    }

    private void createTheme(final String theme) {
        final ThemeAction action = new ThemeAction(theme);
        final MenuItemDescriptor themeItem = new MenuItemDescriptor(menu, action);
        // selectItemInUI.add(new Listener<String>() {
        // public void onEvent(final String themeToSelect) {
        // if (theme.equals(themeToSelect)) {
        // menu.setText(i18n.t(theme));
        // // themeItem.setChecked(true);
        // }
        // }
        // });
        view.addAction(themeItem);
    }

    @Override
    public View getView() {
        return view;
    }

    public void init(final ActionExtensibleView view) {
        this.view = view;
        createActions();
    }

    @Override
    public void select(final String theme) {
        // selectItemInUI.fire(theme);
    }

    private void setThemes(final InitDataDTO initData) {
        view.addAction(menu);
        for (final String theme : initData.getWsThemes()) {
            createTheme(theme);
        }
    }

}
