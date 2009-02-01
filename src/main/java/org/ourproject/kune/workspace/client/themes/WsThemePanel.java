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
 \*/
package org.ourproject.kune.workspace.client.themes;

import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.events.Listener2;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.MenuItem;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;

public class WsThemePanel extends ToolbarButton implements WsThemeView {

    private final Menu menu;
    private final WsThemePresenter presenter;
    private final I18nUITranslationService i18n;

    public WsThemePanel(final WorkspaceSkeleton ws, final WsThemePresenter presenter,
            final I18nUITranslationService i18n) {
        this.presenter = presenter;
        this.i18n = i18n;
        menu = new Menu();

        menu.setDefaultAlign("br-tr");
        super.setMenu(menu);
        super.setIcon("images/colors.gif");
        // super.setTooltip(i18n.t("Select Workspace theme for this group"));
        // ws.getSiteTraybar().addButton(this);
        presenter.onThemeChanged(new Listener2<WsTheme, WsTheme>() {
            public void onEvent(final WsTheme oldTheme, final WsTheme newTheme) {
                ws.setTheme(oldTheme, newTheme);
            }
        });
    }

    public void setThemes(final String[] themes) {
        for (String theme2 : themes) {
            final WsTheme theme = new WsTheme(theme2);
            final MenuItem item = new MenuItem();
            final String name = theme.getName();
            item.setIconCls("k-wstheme-icon-" + name);
            item.setText(i18n.t(name));
            menu.addItem(item);
            item.addListener(new BaseItemListenerAdapter() {
                @Override
                public void onClick(final BaseItem item, final EventObject e) {
                    presenter.onChangeGroupWsTheme(theme);
                }
            });
        }
    }

    @Override
    public void setVisible(final boolean visible) {
        super.setVisible(visible);
    }

}
