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
package org.ourproject.kune.workspace.client.sitebar.siteusermenu;

import org.ourproject.kune.platf.client.PlatfMessages;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.ui.MenuItemCollection;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.skel.SimpleToolbar;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.MenuItem;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;

public class SiteUserMenuPanel implements SiteUserMenuView {

    public static final String LOGGED_USER_MENU = "kune-sump-lum";
    public static final String USER_PREFERENCES_MENU_ITEM = "kune-sump-uprmi";
    public static final String USER_HOME_PAGE_MENU_ITEM = "kune-sump-hpmi";
    public static final String USER_PARTICIPATION_MENU_ITEM = "kune-sump-upami";
    private final PushButton loggedUserMenu;
    private final Widget separator;
    private final Menu userMenu;
    private final Menu userParticipation;

    public SiteUserMenuPanel(final SiteUserMenuPresenter presenter, final WorkspaceSkeleton ws,
            final I18nUITranslationService i18n) {
        loggedUserMenu = new PushButton("");
        loggedUserMenu.ensureDebugId(LOGGED_USER_MENU);
        loggedUserMenu.setStyleName("k-sitebar-labellink");
        SimpleToolbar siteBar = ws.getSiteBar();
        siteBar.add(loggedUserMenu);
        separator = siteBar.addSeparator();
        userMenu = new Menu();
        loggedUserMenu.addClickListener(new ClickListener() {
            public void onClick(final Widget sender) {
                userMenu.showAt(sender.getAbsoluteLeft(), sender.getAbsoluteTop() + 10);
            }
        });
        final Item userHomePage = new Item(i18n.t(PlatfMessages.YOUR_HOMEPAGE));
        userHomePage.setId(USER_HOME_PAGE_MENU_ITEM);
        userHomePage.setIcon("images/group-home.gif");
        userHomePage.addListener(new BaseItemListenerAdapter() {
            @Override
            public void onClick(final BaseItem item, final EventObject e) {
                super.onClick(item, e);
                presenter.onUserHomePage();
            }
        });
        userMenu.addItem(userHomePage);
        final Item userPreferences = new Item(i18n.t("Your preferences"));
        userPreferences.setId(USER_PREFERENCES_MENU_ITEM);
        userPreferences.setIcon("images/emblem-system.png");
        userPreferences.addListener(new BaseItemListenerAdapter() {
            @Override
            public void onClick(final BaseItem item, final EventObject e) {
                super.onClick(item, e);
                presenter.onUserPreferences();
            }
        });
        userMenu.addItem(userPreferences);
        userParticipation = new Menu();
        userParticipation.setId(USER_PARTICIPATION_MENU_ITEM);
        final MenuItem userParticipationItem = new MenuItem(i18n.t("Your groups"), userParticipation);
        userParticipationItem.setIcon("");
        userMenu.addItem(userParticipationItem);
    }

    public void setLoggedUserName(final String name) {
        loggedUserMenu.setHTML(name + "<img style=\"vertical-align: middle;\" src=\"images/arrowdown.png\" />");
    }

    public void setParticipation(final MenuItemCollection<GroupDTO> participateInGroups) {
        userParticipation.removeAll();
        for (final org.ourproject.kune.platf.client.ui.MenuItem<GroupDTO> groupItem : participateInGroups) {
            final Item item = new Item(groupItem.getTitle(), new BaseItemListenerAdapter() {
                @Override
                public void onClick(BaseItem item, EventObject e) {
                    super.onClick(item, e);
                    groupItem.fire(null);
                }
            });
            item.setIcon(groupItem.getIcon());
            userParticipation.addItem(item);
        }
    }

    public void setVisible(final boolean visible) {
        loggedUserMenu.setVisible(visible);
        separator.setVisible(visible);
    }
}
