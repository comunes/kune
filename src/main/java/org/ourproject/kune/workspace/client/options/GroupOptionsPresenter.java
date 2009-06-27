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
import org.ourproject.kune.platf.client.actions.ui.ButtonDescriptor;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;
import org.ourproject.kune.platf.client.ui.img.ImgConstants;
import org.ourproject.kune.platf.client.ui.img.ImgResources;

import com.calclab.suco.client.events.Listener;

public class GroupOptionsPresenter extends AbstractTabbedDialogPresenter implements GroupOptions {
    public static final String GROUP_OPTIONS_ICON = "k-eop-icon";
    private GroupOptionsView view;
    private final I18nTranslationService i18n;
    private final ImgResources img;
    private ButtonDescriptor prefsItem;

    public GroupOptionsPresenter(final StateManager stateManager, final I18nTranslationService i18n,
            final ImgResources img) {
        this.i18n = i18n;
        this.img = img;
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                if (!state.getGroup().isPersonal() && state.getGroupRights().isAdministrable()) {
                    prefsItem.setVisible(true);
                } else {
                    view.hide();
                    prefsItem.setVisible(false);
                }
            }
        });
    }

    public void init(final GroupOptionsView view) {
        super.init(view);
        this.view = view;
        createActions();
    }

    private void createActions() {
        final AbstractExtendedAction groupPrefsAction = new AbstractExtendedAction() {
            public void actionPerformed(final ActionEvent event) {
                show();
            }
        };
        groupPrefsAction.putValue(Action.NAME, i18n.t("Group options"));
        groupPrefsAction.putValue(Action.SMALL_ICON, ImgConstants.toPath(img.prefs()));
        prefsItem = new ButtonDescriptor(groupPrefsAction);
        prefsItem.setId(GROUP_OPTIONS_ICON);
        view.addAction(prefsItem);
    }
}
