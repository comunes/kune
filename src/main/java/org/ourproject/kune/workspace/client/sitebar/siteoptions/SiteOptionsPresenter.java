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
 \*/
package org.ourproject.kune.workspace.client.sitebar.siteoptions;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.AbstractExtendedAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.ui.AbstractActionExtensiblePresenter;
import org.ourproject.kune.platf.client.actions.ui.GuiActionDescrip;
import org.ourproject.kune.platf.client.actions.ui.MenuDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuItemDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuSeparatorDescriptor;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.KuneWindowUtils;
import org.ourproject.kune.platf.client.ui.img.ImgResources;

public class SiteOptionsPresenter extends AbstractActionExtensiblePresenter implements SiteOptions {

    private SiteOptionsView view;
    private final MenuDescriptor menuDescriptor;
    private final I18nTranslationService i18n;
    private final ImgResources img;

    public SiteOptionsPresenter(final I18nTranslationService i18n, final ImgResources img) {
        super();
        this.i18n = i18n;
        this.img = img;
        menuDescriptor = new MenuDescriptor();
        menuDescriptor.setStandalone(true);
    }

    @Override
    public void addAction(final GuiActionDescrip descriptor) {
        descriptor.setParent(menuDescriptor);
        view.addAction(descriptor);
    }

    public View getView() {
        return view;
    }

    public void init(final SiteOptionsView view) {
        this.view = view;
        createActions();
    }

    private void createActions() {
        view.addAction(menuDescriptor);
        view.setMenu(menuDescriptor);
        final MenuSeparatorDescriptor sep = new MenuSeparatorDescriptor(menuDescriptor);
        view.addAction(sep);

        final AbstractExtendedAction bugsAction = new AbstractExtendedAction() {
            public void actionPerformed(final ActionEvent event) {
                KuneWindowUtils.open("http://ourproject.org/tracker/?group_id=407");
            }
        };
        bugsAction.putValue(Action.NAME, i18n.t("Report Kune issues/problems"));
        bugsAction.putValue(Action.SMALL_ICON, img.bug());
        final MenuItemDescriptor item = new MenuItemDescriptor(bugsAction);
        addAction(item);

        // final KeyStroke shortcut = Shortcut.getShortcut(true, true, false,
        // false, Character.valueOf('K'));
        final AbstractExtendedAction aboutAction = new AbstractExtendedAction() {
            public void actionPerformed(final ActionEvent event) {
                view.showAboutDialog();
            }
        };
        aboutAction.putValue(Action.NAME, i18n.t("About kune"));
        aboutAction.putValue(Action.SMALL_ICON, img.kuneIcon16());
        // aboutAction.setShortcut(shortcut);
        // shortcutReg.put(shortcut, aboutAction);
        addAction(new MenuItemDescriptor(aboutAction));
    }
}
