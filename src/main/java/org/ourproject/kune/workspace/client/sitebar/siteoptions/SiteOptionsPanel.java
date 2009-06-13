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

import org.ourproject.kune.platf.client.actions.ui.AbstractComposedGuiItem;
import org.ourproject.kune.platf.client.actions.ui.AbstractGuiItem;
import org.ourproject.kune.platf.client.actions.ui.AbstractMenuGui;
import org.ourproject.kune.platf.client.actions.ui.GuiBindingsRegister;
import org.ourproject.kune.platf.client.actions.ui.MenuBinding;
import org.ourproject.kune.platf.client.actions.ui.MenuDescriptor;
import org.ourproject.kune.platf.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PushButton;

public class SiteOptionsPanel extends AbstractComposedGuiItem implements SiteOptionsView {

    private static final String BTN_ID = "k-sopts-btn-id";
    private AbstractMenuGui menu;

    public SiteOptionsPanel(final WorkspaceSkeleton wspace, final I18nUITranslationService i18n,
            final GuiBindingsRegister bindings) {
        super(bindings);
        final PushButton optionsButton = new PushButton("");
        // optionsButton.setText(i18n.t("Options"));
        optionsButton.setHTML(i18n.t("Options")
                + "<img style=\"vertical-align: middle;\" src=\"images/arrowdown.png\" />");
        optionsButton.setStyleName("k-sitebar-labellink");
        optionsButton.ensureDebugId(BTN_ID);
        wspace.getSiteBar().addSeparator();
        wspace.getSiteBar().add(optionsButton);
        wspace.getSiteBar().addSpacer();
        wspace.getSiteBar().addSpacer();

        optionsButton.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
                menu.show(optionsButton.getElement().getId());
            }
        });
    }

    public void setMenu(final MenuDescriptor menuDescriptor) {
        menu = (AbstractMenuGui) menuDescriptor.getValue(MenuBinding.UI_MENU);
    }

    @Override
    protected void addWidget(final AbstractGuiItem item, final int position, final boolean visible) {
        // Do nothing (menu items are attached automatically to its menu
    }

}
