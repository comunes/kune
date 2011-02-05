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
package org.ourproject.kune.workspace.client.entityheader.maxmin;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.AbstractExtendedAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.KeyStroke;
import org.ourproject.kune.platf.client.actions.Shortcut;
import org.ourproject.kune.platf.client.actions.ui.MenuItemDescriptor;
import org.ourproject.kune.platf.client.shortcuts.GlobalShortcutRegister;
import org.ourproject.kune.workspace.client.sitebar.siteoptions.SiteOptions;

import cc.kune.core.client.resources.icons.IconResources;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.resources.client.ImageResource;

public class MaxMinWorkspacePresenter implements MaxMinWorkspace {
    public class MaximizeAction extends AbstractExtendedAction {
        public MaximizeAction(final String name, final ImageResource img) {
            super();
            putValue(Action.NAME, name);
            putValue(Action.SMALL_ICON, img);
        }

        public void actionPerformed(final ActionEvent event) {
            showMaximized(true);
        }
    }
    public class MinimizeAction extends AbstractExtendedAction {
        public MinimizeAction(final String name, final ImageResource img) {
            super();
            putValue(Action.NAME, name);
            putValue(Action.SMALL_ICON, img);
        }

        public void actionPerformed(final ActionEvent event) {
            showMaximized(false);
        }
    }

    public static final String MIN_ICON = "mmwp-min_bt";
    public static final String MAX_ICON = "mmwp-max_bt";
    private MaxMinWorkspaceView view;

    private boolean maximized;

    private final IconResources images;

    private final I18nTranslationService i18n;

    private final GlobalShortcutRegister shortcutReg;

    private MenuItemDescriptor maximizeButton;

    private MenuItemDescriptor minimizeButton;
    private final SiteOptions siteOptions;

    public MaxMinWorkspacePresenter(final GlobalShortcutRegister shortcutReg, final IconResources images,
            final I18nTranslationService i18n, final SiteOptions siteOptions) {
        this.shortcutReg = shortcutReg;
        this.images = images;
        this.i18n = i18n;
        this.siteOptions = siteOptions;
        maximized = false;

    }

    public View getView() {
        return view;
    }

    public void init(final MaxMinWorkspaceView view) {
        this.view = view;
        createActions();
    }

    public void maximize() {
        showMaximized(true);
    }

    public void minimize() {
        showMaximized(false);
    }

    private void createActions() {
        final KeyStroke shortcut = Shortcut.getShortcut(true, true, false, false, Character.valueOf('F'));

        final MaximizeAction maximizeAction = new MaximizeAction(i18n.t("Maximize the workspace"), images.maximize());
        maximizeAction.setShortcut(shortcut);
        maximizeButton = new MenuItemDescriptor(maximizeAction);
        maximizeButton.setPosition(0);
        maximizeButton.setId(MAX_ICON);

        final MinimizeAction minimizeAction = new MinimizeAction(i18n.t("Minimize the workspace"), images.minimize());
        minimizeAction.setShortcut(shortcut);
        minimizeButton = new MenuItemDescriptor(minimizeAction);
        minimizeButton.setPosition(1);
        minimizeButton.setVisible(false);
        minimizeButton.setId(MIN_ICON);

        shortcutReg.put(shortcut, new AbstractExtendedAction() {
            public void actionPerformed(final ActionEvent event) {
                showMaximized(!maximized);
            }
        });

        siteOptions.addAction(maximizeButton);
        siteOptions.addAction(minimizeButton);
    }

    private void showMaximized(final boolean maximized) {
        maximizeButton.setVisible(!maximized);
        minimizeButton.setVisible(maximized);
        this.maximized = maximized;
        view.setMaximized(maximized);
    }
}
