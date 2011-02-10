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
package cc.kune.common.client.noti;

import cc.kune.common.client.utils.SimpleCallback;
import cc.kune.core.client.notify.msgs.UserNotifyEvent;
import cc.kune.core.client.notify.spiner.ProgressHideEvent;
import cc.kune.core.client.notify.spiner.ProgressShowEvent;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.event.shared.EventBus;

public class NotifyUser {
    private static EventBus eventBus;
    private static I18nTranslationService i18n;
    private static SimpleCallback onOk;

    public static void askConfirmation(final String tittle, final String message, final SimpleCallback callback) {
        eventBus.fireEvent(new ConfirmAskEvent(tittle, message, i18n.t("Yes"), i18n.t("No"), callback));
    }

    public static void error(final String message) {
        eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, message));
    }

    public static void error(final String message, final boolean closeable) {
        eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, message, closeable));
    }

    public static void error(final String message, final String title, final boolean closeable) {
        eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, message, title, closeable));
    }

    public static void error(final String message, final String title, final String id, final boolean closeable) {
        final UserNotifyEvent event = new UserNotifyEvent(NotifyLevel.error, message, id, closeable);
        event.setId(id);
        eventBus.fireEvent(event);
    }

    public static void hideProgress() {
        eventBus.fireEvent(new ProgressHideEvent());
    }

    public static void important(final String message) {
        eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.important, message));
    }

    public static void info(final String message) {
        eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.info, message));
    }

    public static void init(final EventBus eventBus, final I18nTranslationService i18n) {
        NotifyUser.eventBus = eventBus;
        NotifyUser.i18n = i18n;
        onOk = new SimpleCallback() {

            @Override
            public void onCancel() {
                // Do nothing
            }

            @Override
            public void onSuccess() {
                // Do nothing
            }
        };

    }

    public static void showAlertMessage(final String title, final String message) {
        showAlertMessage(title, message, onOk);
    }

    public static void showAlertMessage(final String title, final String message, final SimpleCallback callback) {
        eventBus.fireEvent(new ConfirmAskEvent(title, message, i18n.t("Ok"), i18n.t("Close"), callback));
    }

    public static void showProgress(final String text) {
        eventBus.fireEvent(new ProgressShowEvent(text));
    }

    public static void showProgressLoading() {
        eventBus.fireEvent(new ProgressShowEvent());
    }

    public static void showProgressProcessing() {
        eventBus.fireEvent(new ProgressShowEvent());
    }

    public static void veryImportant(final String message) {
        eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.veryImportant, message));
    }
}
