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
package cc.kune.core.client.auth;

import cc.kune.common.client.noti.NotifyLevel;
import cc.kune.common.client.noti.NotifyLevelImages;
import cc.kune.core.client.ui.dialogs.BasicTopDialog;
import cc.kune.core.client.ui.dialogs.MessageToolbar;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public abstract class SignInAbstractPanel extends ViewImpl {

    private final BasicTopDialog dialog;
    private final String errorLabelId;
    protected final I18nTranslationService i18n;
    private final NotifyLevelImages images;
    private MessageToolbar messageErrorBar;

    public SignInAbstractPanel(final String dialogId, final I18nTranslationService i18n, final String title,
            final boolean autohide, final boolean modal, final boolean autoscroll, final int width, final int heigth,
            final String icon, final String firstButtonTitle, final String firstButtonId,
            final String cancelButtonTitle, final String cancelButtonId, final NotifyLevelImages images,
            final String errorLabelId, final int tabIndexStart) {
        dialog = new BasicTopDialog(dialogId, title, autohide, modal, autoscroll, width, heigth, icon,
                firstButtonTitle, firstButtonId, cancelButtonTitle, cancelButtonId, tabIndexStart);
        this.i18n = i18n;
        this.images = images;
        this.errorLabelId = errorLabelId;
    }

    @Override
    public Widget asWidget() {
        return dialog;
    }

    private void createMessageErrorIfNeeded() {
        if (messageErrorBar == null) {
            messageErrorBar = new MessageToolbar(images, errorLabelId);
        }
    }

    public HasCloseHandlers<PopupPanel> getClose() {
        return dialog.getClose();
    }

    public HasClickHandlers getFirstBtn() {
        return dialog.getFirstBtn();
    }

    public ForIsWidget getInnerPanel() {
        return dialog.getInnerPanel();
    }

    public HasClickHandlers getSecondBtn() {
        return dialog.getSecondBtn();
    }

    public void hide() {
        if (dialog.isVisible()) {
            dialog.hide();
        }
    }

    public void hideMessages() {
        createMessageErrorIfNeeded();
        messageErrorBar.hideErrorMessage();
    }

    public void mask(final String message) {
        GWT.log("Mask not implemented");

    }

    public void maskProcessing() {
        GWT.log("Mask not implemented");
        // mask(i18n.t("Processing"));
    }

    public void setErrorMessage(final String message, final NotifyLevel level) {
        createMessageErrorIfNeeded();
        messageErrorBar.setErrorMessage(message, level);
    }

    public void show() {
        dialog.show();
    }

    public void unMask() {
        GWT.log("Unask not implemented");
    }
}
