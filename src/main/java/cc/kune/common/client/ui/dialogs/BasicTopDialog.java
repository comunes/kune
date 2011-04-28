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
package cc.kune.common.client.ui.dialogs;

import cc.kune.common.client.ui.PopupTopPanel;
import cc.kune.common.client.utils.TextUtils;

import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.UIObject;

public class BasicTopDialog extends BasicDialog {

    public static class Builder {
        private final boolean autohide;
        private boolean autoscroll = false;
        private final String dialogId;
        private String firstButtonId;
        private String firstButtonTitle;
        private String height = "70%";
        private String icon;
        private final boolean modal;
        private String sndButtonId;
        private String sndButtonTitle;
        private int tabIndexStart = 0;
        private String title;
        private String width = "400px";

        public Builder(final String dialogId, final boolean autohide, final boolean modal) {
            // Required params
            this.autohide = autohide;
            this.modal = modal;
            this.dialogId = dialogId;
        }

        public Builder autoscroll(final boolean autoscroll) {
            // Not used for now
            this.autoscroll = autoscroll;
            return this;
        }

        public BasicTopDialog build() {
            return new BasicTopDialog(this);
        }

        public Builder firstButtonId(final String firstButtonId) {
            this.firstButtonId = firstButtonId;
            return this;
        }

        public Builder firstButtonTitle(final String firstButtonTitle) {
            this.firstButtonTitle = firstButtonTitle;
            return this;
        }

        public Builder height(final int height) {
            this.height = String.valueOf(height);
            return this;
        }

        public Builder height(final String height) {
            this.height = height;
            return this;
        }

        public Builder icon(final String icon) {
            this.icon = icon;
            return this;
        }

        public Builder sndButtonId(final String sndButtonId) {
            this.sndButtonId = sndButtonId;
            return this;
        }

        public Builder sndButtonTitle(final String sndButtonTitle) {
            this.sndButtonTitle = sndButtonTitle;
            return this;
        }

        public Builder tabIndexStart(final int tabIndexStart) {
            this.tabIndexStart = tabIndexStart;
            return this;
        }

        public Builder title(final String title) {
            this.title = title;
            return this;
        }

        public Builder width(final int width) {
            this.width = String.valueOf(width);
            return this;
        }

        public Builder width(final String width) {
            this.width = width;
            return this;
        }
    }

    private final PopupTopPanel popup;

    private BasicTopDialog(final Builder builder) {
        popup = new PopupTopPanel(builder.autohide, builder.modal);
        popup.add(this);
        popup.ensureDebugId(builder.dialogId);
        super.getTitleText().setText(builder.title);
        if (TextUtils.notEmpty(builder.icon)) {
            super.setTitleIcon(builder.icon);
        }
        if (TextUtils.empty(builder.firstButtonTitle)) {
            super.setFirstBtnVisible(false);
        } else {
            super.getFirstBtnText().setText(builder.firstButtonTitle);
            if (TextUtils.notEmpty(builder.firstButtonId)) {
                super.setFirstBtnId(builder.firstButtonId);
            }
            super.setFirstBtnTabIndex(builder.tabIndexStart);
        }
        if (TextUtils.empty(builder.sndButtonTitle)) {
            super.setSecondBtnVisible(false);
        } else {
            super.getSecondBtnText().setText(builder.sndButtonTitle);
            if (TextUtils.notEmpty(builder.sndButtonId)) {
                super.setSecondBtnId(builder.sndButtonId);
            }
            super.setSecondBtnTabIndex(builder.tabIndexStart + 1);
        }
        super.setSize(builder.width, builder.height);
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
