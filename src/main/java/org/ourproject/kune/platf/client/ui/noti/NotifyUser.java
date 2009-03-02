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

import com.calclab.suco.client.events.Event;
import com.calclab.suco.client.events.Event0;
import com.calclab.suco.client.events.Event2;
import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.events.Listener2;

public class NotifyUser {

    public enum Level {
        info, important, veryImportant, error,
    }

    private static Event2<Level, String> onNotify;

    private static Event2<String, String> onAlert;

    private static Event<ConfirmationAsk> onConfirmationAsk;

    private static Event<String> onProgress;

    private static Event0 onHideProgress;

    private static I18nTranslationService i18n;

    public static void askConfirmation(String confirmationTitle, String confirmationText, Listener0 onConfirm,
            Listener0 onCancel) {
        onConfirmationAsk.fire(new ConfirmationAsk(confirmationTitle, confirmationText, onConfirm, onCancel));
    }

    public static void error(final String message) {
        onNotify.fire(Level.error, message);
    }

    public static void hideProgress() {
        onHideProgress.fire();
    }

    public static void important(final String message) {
        onNotify.fire(Level.important, message);
    }

    public static void info(final String message) {
        onNotify.fire(Level.info, message);
    }

    public static void showAlertMessage(String title, String message) {
        onAlert.fire(title, message);
    }

    public static void showProgress(final String text) {
        onProgress.fire(text);
    }

    public static void showProgressLoading() {
        onProgress.fire(i18n.t("Loading"));
    }

    public static void showProgressProcessing() {
        onProgress.fire(i18n.t("Processing"));
    }

    public static void showProgressSaving() {
        onProgress.fire(i18n.t("Saving"));
    }

    public static void showProgressStarting() {
        onProgress.fire(i18n.t("Starting"));
    }

    public static void veryImportant(final String message) {
        onNotify.fire(Level.veryImportant, message);
    }

    public NotifyUser(I18nTranslationService i18n) {
        NotifyUser.i18n = i18n;
        onNotify = new Event2<Level, String>("onNotify");
        onAlert = new Event2<String, String>("onAlert");
        onProgress = new Event<String>("onProgress");
        onHideProgress = new Event0("onHideProgress");
        onConfirmationAsk = new Event<ConfirmationAsk>("onConfirmationAsk");
    }

    public void addAlerter(final Listener2<String, String> listener) {
        onAlert.add(listener);
    }

    public void addConfirmationAsker(final Listener<ConfirmationAsk> listener) {
        onConfirmationAsk.add(listener);
    }

    public void addHideProgressNotifier(final Listener0 listener) {
        onHideProgress.add(listener);
    }

    public void addNotifier(final Listener2<Level, String> listener) {
        onNotify.add(listener);
    }

    public void addProgressNotifier(final Listener<String> listener) {
        onProgress.add(listener);
    }

}
