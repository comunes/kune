/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client.options;

import org.ourproject.kune.platf.client.actions.AbstractExtendedAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.ui.MenuItemDescriptor;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;
import org.ourproject.kune.platf.client.ui.img.ImgResources;
import org.ourproject.kune.workspace.client.sitebar.siteusermenu.SiteUserOptions;

import com.calclab.suco.client.events.Listener0;

public class UserOptionsPresenter extends AbstractTabbedDialogPresenter implements UserOptions {

    private UserOptionsView view;
    private final ImgResources img;
    private final I18nTranslationService i18n;
    private final SiteUserOptions userOptions;
    private final Session session;
    private final StateManager stateManager;

    public UserOptionsPresenter(final Session session, final StateManager stateManager,
            final I18nTranslationService i18n, final ImgResources img, final SiteUserOptions userOptions) {
        this.session = session;
        this.stateManager = stateManager;
        this.i18n = i18n;
        this.img = img;
        this.userOptions = userOptions;
        session.onUserSignOut(new Listener0() {
            public void onEvent() {
                view.hide();
            }
        });
    }

    public void init(final UserOptionsView view) {
        super.init(view);
        this.view = view;
        createActions();
    }

    private void createActions() {
        final AbstractExtendedAction userPrefsAction = new AbstractExtendedAction() {
            public void actionPerformed(final ActionEvent event) {
                if (!session.isInCurrentUserSpace()) {
                    stateManager.gotoToken(session.getCurrentUser().getStateToken());
                }
                show();
            }
        };
        userPrefsAction.putValue(Action.NAME, i18n.t("Your preferences"));
        userPrefsAction.putValue(Action.SMALL_ICON, img.prefs());
        final MenuItemDescriptor prefsItem = new MenuItemDescriptor(userPrefsAction);
        prefsItem.setPosition(1);
        userOptions.addAction(prefsItem);
    }
}
