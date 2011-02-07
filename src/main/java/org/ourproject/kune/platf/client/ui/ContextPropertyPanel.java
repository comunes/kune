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
package org.ourproject.kune.platf.client.ui;

import org.ourproject.kune.platf.client.services.Images;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DisclosurePanelImages;
import com.google.gwt.user.client.ui.Widget;

public class ContextPropertyPanel extends Composite {

    public ContextPropertyPanel(AbstractImagePrototype headerIcon, String tooltip, String headerText, boolean isOpen,
            String debugId, Widget content) {
        this(new IconLabel(headerIcon, headerText), tooltip, isOpen, debugId, content);
    }

    public ContextPropertyPanel(String headerText, String tooltip, boolean isOpen, String debugId, Widget content) {
        DisclosurePanelImages images = new DisclosurePanelImages() {
            public AbstractImagePrototype disclosurePanelClosed() {
                return Images.App.getInstance().arrowRightWhite();
            }

            public AbstractImagePrototype disclosurePanelOpen() {
                return Images.App.getInstance().arrowDownWhite();
            }
        };
        DisclosurePanel disclosure = new DisclosurePanel(images, headerText, isOpen);
        init(disclosure, tooltip, content, debugId);
    }

    public ContextPropertyPanel(Widget header, String tooltip, boolean isOpen, String debugId, Widget content) {
        DisclosurePanel disclosure = new DisclosurePanel(header, isOpen);
        init(disclosure, tooltip, content, debugId);
    }

    private void init(DisclosurePanel disclosure, String tooltip, Widget content, String debugId) {
        disclosure.setAnimationEnabled(true);
        disclosure.ensureDebugId(debugId);
        disclosure.setContent(content);
        KuneUiUtils.setQuickTip(disclosure, tooltip);
        initWidget(disclosure);
    }
}
