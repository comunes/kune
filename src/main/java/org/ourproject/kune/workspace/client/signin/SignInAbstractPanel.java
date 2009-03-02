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
package org.ourproject.kune.workspace.client.signin;

import java.util.Date;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialogExtended;
import org.ourproject.kune.platf.client.ui.dialogs.MessageToolbar;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser.Level;

import com.calclab.suco.client.events.Listener0;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.gwtext.client.core.Ext;

public abstract class SignInAbstractPanel extends BasicDialogExtended {

    protected final I18nTranslationService i18n;
    private final MessageToolbar messageErrorBar;

    public SignInAbstractPanel(String dialogId, I18nTranslationService i18n, String title, boolean modal,
            boolean autoscroll, int width, int heigth, String icon, String firstButtonTitle, String cancelButtonTitle,
            Listener0 onFirstButtonClick, Listener0 onCancelButtonClick, Images images, String errorLabelId,
            int tabIndexStart) {
        this(dialogId, i18n, title, modal, autoscroll, width, heigth, icon, firstButtonTitle, Ext.generateId(),
                cancelButtonTitle, Ext.generateId(), onFirstButtonClick, onCancelButtonClick, images, errorLabelId,
                tabIndexStart);
    }

    public SignInAbstractPanel(String dialogId, I18nTranslationService i18n, final String title, final boolean modal,
            final boolean autoscroll, final int width, final int heigth, final String icon,
            final String firstButtonTitle, final String firstButtonId, final String cancelButtonTitle,
            final String cancelButtonId, final Listener0 onFirstButtonClick, final Listener0 onCancelButtonClick,
            Images images, String errorLabelId, int tabIndexStart) {
        super(dialogId, title, modal, autoscroll, width, heigth, icon, firstButtonTitle, firstButtonId,
                cancelButtonTitle, cancelButtonId, onFirstButtonClick, onCancelButtonClick, tabIndexStart);
        this.i18n = i18n;

        messageErrorBar = new MessageToolbar(images, errorLabelId);
        super.setBottomToolbar(messageErrorBar.getToolbar());
    }

    @Override
    public void hide() {
        if (super.isVisible()) {
            super.hide();
        }
    }

    public void hideMessages() {
        messageErrorBar.hideErrorMessage();
    }

    public void maskProcessing() {
        mask(i18n.t("Processing"));
    }

    public void setCookie(final String userHash) {
        // http://code.google.com/p/google-web-toolkit-incubator/wiki/LoginSecurityFAQ
        final long duration = Session.SESSION_DURATION;
        final Date expires = new Date(System.currentTimeMillis() + duration);
        Cookies.setCookie(Session.USERHASH, userHash, expires, null, "/", false);
        GWT.log("Received hash: " + userHash, null);
    }

    public void setErrorMessage(final String message, final Level level) {
        messageErrorBar.setErrorMessage(message, level);
    }
}
