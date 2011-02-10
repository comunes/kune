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
package cc.kune.core.client.ui.dialogs;

import cc.kune.common.client.ui.PopupTopPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.UIObject;

public class BasicTopDialog extends BasicDialog {

    private final PopupTopPanel popup;

    public BasicTopDialog(final String dialogId, final String title, final boolean autohide, final boolean modal,
            final boolean autoscroll, final int width, final int height, final String icon,
            final String firstButtonTitle, final String firstButtonId, final String cancelButtonTitle,
            final String cancelButtonId, final int tabIndexStart) {
        popup = new PopupTopPanel(autohide, modal);
        popup.add(this);
        popup.ensureDebugId(dialogId);
        super.getTitleText().setText(title);
        // super.setAutoscroll(autoscroll);
        // super.setSize(String.valueOf(width), String.valueOf(height));
        GWT.log("Not setting size of dialog to: " + String.valueOf(width) + "/" + String.valueOf(height));
        super.setTitleIcon(icon);
        super.getFirstBtnText().setText(firstButtonTitle);
        super.getSecondBtnText().setText(cancelButtonTitle);
        super.setFirstBtnId(firstButtonId);
        super.setSecondBtnId(cancelButtonId);
        super.setFirstBtnTabIndex(tabIndexStart);
        super.setSecondBtnTabIndex(tabIndexStart + 1);
    }

    public HasCloseHandlers<PopupPanel> getClose() {
        return popup;
    }

    public void hide() {
        popup.hide();

    }

    public void showCentered() {
        popup.showCentered();
    }

    public void showRelativeTo(final UIObject object) {
        popup.showRelativeTo(object);
    }
}
