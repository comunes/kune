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
 */package org.ourproject.kune.workspace.client.sitebar.siteoptions;

import org.ourproject.kune.workspace.client.i18n.I18nTranslator;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;

public class SiteOptionsPanel implements SiteOptionsView {

    public SiteOptionsPanel(final SiteOptionsPresenter presenter, final WorkspaceSkeleton ws,
            final I18nUITranslationService i18n, final Provider<I18nTranslator> translatorProvider) {
        final PushButton optionsButton = new PushButton("");
        // optionsButton.setText(i18n.t("Options"));
        optionsButton.setHTML(i18n.t("Options")
                + "<img style=\"vertical-align: middle;\" src=\"images/arrowdown.png\" />");
        optionsButton.setStyleName("k-sitebar-labellink");
        ws.getSiteBar().addSeparator();
        ws.getSiteBar().add(optionsButton);
        ws.getSiteBar().addSpacer();
        ws.getSiteBar().addSpacer();
        final Menu optionsMenu = new Menu();
        optionsButton.addClickListener(new ClickListener() {
            public void onClick(final Widget sender) {
                optionsMenu.showAt(sender.getAbsoluteLeft(), sender.getAbsoluteTop() + 10);
            }
        });
        final Item linkHelpInTrans = new Item(i18n.t("Help with the translation"), new BaseItemListenerAdapter() {
            @Override
            public void onClick(BaseItem item, EventObject e) {
                super.onClick(item, e);
                translatorProvider.get().doShowTranslator();
            }
        }, "images/language.gif");

        final Item linkKuneBugs = new Item(i18n.t("Report kune bugs"), new BaseItemListenerAdapter() {
            @Override
            public void onClick(BaseItem item, EventObject e) {
                super.onClick(item, e);
                Window.open("http://code.google.com/p/kune/issues/list", "_blank", null);
            }
        }, "images/kuneicon16.gif");
        optionsMenu.addItem(linkHelpInTrans);
        optionsMenu.addItem(linkKuneBugs);

    }
}
