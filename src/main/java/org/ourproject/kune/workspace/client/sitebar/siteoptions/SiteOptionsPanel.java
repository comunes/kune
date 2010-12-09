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

import org.ourproject.kune.platf.client.actions.ui.GuiBindingsRegister;
import org.ourproject.kune.platf.client.ui.AbstractToolbar;
import org.ourproject.kune.platf.client.ui.dialogs.InfoDialog;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import cc.kune.core.client.i18n.I18nUITranslationService;

import com.google.gwt.user.client.ui.Frame;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.layout.FitLayout;

public class SiteOptionsPanel extends AbstractSiteOptionsPanel implements SiteOptionsView {

    public static final String SITE_OPTIONS_MENU = "kune-sop-om";
    public static final String ABOUT_KUNE_ID = "kune-about-diag";
    public static final String ABOUT_KUNE_BTN_ID = "kune-about-button-diag";
    private final I18nUITranslationService i18n;
    private InfoDialog infoDialog;

    public SiteOptionsPanel(final WorkspaceSkeleton wspace, final I18nUITranslationService i18n,
            final GuiBindingsRegister bindings) {
        super(bindings, SITE_OPTIONS_MENU);
        this.i18n = i18n;
        setBtnText(i18n.t("Options"));
        final AbstractToolbar siteBar = wspace.getSiteBar();
        siteBar.addSeparator();
        siteBar.add(btn);
        siteBar.addSpacer();
        siteBar.addSpacer();
    }

    public void showAboutDialog() {
        if (infoDialog == null) {
            final Frame aboutFrame = new Frame("/ws/about.html");
            final Panel aboutPanel = new Panel();
            aboutPanel.setCls("k-plain-iframe");
            aboutPanel.setLayout(new FitLayout());
            aboutPanel.add(aboutFrame);
            aboutPanel.setBorder(false);
            aboutPanel.setHeight(300);
            infoDialog = new InfoDialog(ABOUT_KUNE_ID, i18n.t("About Kune"), i18n.t("Ok"), ABOUT_KUNE_BTN_ID, true,
                    true, 400, 400);
            infoDialog.add(aboutPanel);
            infoDialog.setIconCls("k-newgroup-icon");
            infoDialog.setMainPanelPaddings(10);
        }
        infoDialog.show();
    }
}
