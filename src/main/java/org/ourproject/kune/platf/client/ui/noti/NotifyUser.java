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
package org.ourproject.kune.platf.client.ui.noti;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;

import com.calclab.suco.client.events.Event;
import com.calclab.suco.client.events.Event0;
import com.calclab.suco.client.events.Event2;
import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.events.Listener2;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class NotifyUser {

    public enum Level {
        info, important, veryImportant, error,
    }

    private static final Event2<Level, String> ON_NOTIFY = new Event2<Level, String>("onNotify");
    private static final Event2<String, String> ON_ALERT = new Event2<String, String>("onAlert");
    private static final Event<ConfirmationAsk> ON_CONFIRMATION_ASK = new Event<ConfirmationAsk>("onConfirmationAsk");
    private static final Event<String> ON_PROGRESS = new Event<String>("onProgress");
    private static final Event0 ON_HIDE_PROGRESS = new Event0("onHideProgress");

    private static I18nTranslationService i18n;

    private static Images images;

    public static void askConfirmation(final String confirmationTitle, final String confirmationText,
            final Listener0 onConfirm) {
        ON_CONFIRMATION_ASK.fire(new ConfirmationAsk(confirmationTitle, confirmationText, onConfirm, new Listener0() {
            public void onEvent() {
                // Do nothing
            }
        }));
    }

    public static void askConfirmation(final String confirmationTitle, final String confirmationText,
            final Listener0 onConfirm, final Listener0 onCancel) {
        ON_CONFIRMATION_ASK.fire(new ConfirmationAsk(confirmationTitle, confirmationText, onConfirm, onCancel));
    }

    public static void error(final String message) {
        ON_NOTIFY.fire(Level.error, message);
    }

    public static String getCls(final Level level) {
        switch (level) {
        case info:
            return "k-stm-info-icon";
        case important:
            return "k-stm-imp-icon";
        case veryImportant:
            return "k-stm-verimp-icon";
        case error:
        default:
            return "k-stm-error-icon";
        }
    }

    public static AbstractImagePrototype getImage(final Level level) {
        switch (level) {
        case info:
            return images.info();
        case important:
            return images.important();
        case veryImportant:
            return images.alert();
        case error:
        default:
            return images.error();
        }
    }

    public static void hideProgress() {
        ON_HIDE_PROGRESS.fire();
    }

    public static void important(final String message) {
        ON_NOTIFY.fire(Level.important, message);
    }

    public static void info(final String message) {
        ON_NOTIFY.fire(Level.info, message);
    }

    public static void showAlertMessage(final String title, final String message) {
        ON_ALERT.fire(title, message);
    }

    public static void showProgress(final String text) {
        ON_PROGRESS.fire(text);
    }

    public static void showProgressLoading() {
        ON_PROGRESS.fire(i18n.t("Loading"));
    }

    public static void showProgressProcessing() {
        ON_PROGRESS.fire(i18n.t("Processing"));
    }

    public static void showProgressSaving() {
        ON_PROGRESS.fire(i18n.t("Saving"));
    }

    public static void showProgressStarting() {
        ON_PROGRESS.fire(i18n.t("Starting"));
    }

    public static void veryImportant(final String message) {
        ON_NOTIFY.fire(Level.veryImportant, message);
    }

    public NotifyUser(final I18nTranslationService i18n, final Images images) {
        this();
        NotifyUser.i18n = i18n;
        NotifyUser.images = images;
    }

    private NotifyUser() {
    }

    public void addAlerter(final Listener2<String, String> listener) {
        ON_ALERT.add(listener);
    }

    public void addConfirmationAsker(final Listener<ConfirmationAsk> listener) {
        ON_CONFIRMATION_ASK.add(listener);
    }

    public void addHideProgressNotifier(final Listener0 listener) {
        ON_HIDE_PROGRESS.add(listener);
    }

    public void addNotifier(final Listener2<Level, String> listener) {
        ON_NOTIFY.add(listener);
    }

    public void addProgressNotifier(final Listener<String> listener) {
        ON_PROGRESS.add(listener);
    }

}
