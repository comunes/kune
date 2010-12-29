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
package cc.kune.core.client.errors;

import org.ourproject.common.client.notify.NotifyLevel;

import cc.kune.core.client.notify.AlertEvent;
import cc.kune.core.client.notify.ProgressHideEvent;
import cc.kune.core.client.notify.UserNotifyEvent;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Event0;
import com.calclab.suco.client.events.Listener0;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class ErrorHandler {

    private final Session session;
    private final I18nTranslationService i18n;
    private final Event0 onSessionExpired;
    private final PlaceManager placeManager;
    private final EventBus eventBus;

    @Inject
    public ErrorHandler(final Session session, final I18nTranslationService i18n, final PlaceManager placeManager,
            EventBus eventBus) {
        this.session = session;
        this.i18n = i18n;
        this.placeManager = placeManager;
        this.eventBus = eventBus;
        this.onSessionExpired = new Event0("onSessionExpired");
    }

    public void doSessionExpired() {
        onSessionExpired.fire();
        eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.info, "Your session has expired. Please log in again."));
    }

    public void onSessionExpired(final Listener0 listener) {
        onSessionExpired.add(listener);
    }

    public void process(final Throwable caught) {
        eventBus.fireEvent(new ProgressHideEvent());
        if (caught instanceof AccessViolationException) {
            logException(caught);
            eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
                    i18n.t("You do not have rights to perform that action")));
        } else if (caught instanceof SessionExpiredException) {
            logException(caught);
            doSessionExpired();
        } else if (caught instanceof UserMustBeLoggedException) {
            logException(caught);
            if (session.isLogged()) {
                doSessionExpired();
            } else {
                eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.important,
                        i18n.t("Please sign in or register to collaborate")));
            }
        } else if (caught instanceof GroupNotFoundException) {
            logException(caught);
            eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.veryImportant, i18n.t("Group not found")));
            goHome();
        } else if (caught instanceof IncompatibleRemoteServiceException) {
            eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error,
                    i18n.t("Your browser is outdated with the server software. Please reload this page.")));
        } else if (caught instanceof ContentNotFoundException) {
            logException(caught);
            eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.veryImportant, i18n.t("Content not found")));
            goHome();
        } else if (caught instanceof ContentNotPermittedException) {
            logException(caught);
            eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, i18n.t("Action not permitted in this location")));
            goHome();
        } else if (caught instanceof ContainerNotPermittedException) {
            logException(caught);
            eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, i18n.t("Action not permitted in this location")));
            goHome();
        } else if (caught instanceof LastAdminInGroupException) {
            logException(caught);
            eventBus.fireEvent(new AlertEvent(i18n.t("Warning"), i18n.t("Sorry, you are the last admin of this group."
                    + " Look for someone to substitute you appropriately as admin before leaving this group.")));
        } else if (caught instanceof AlreadyGroupMemberException) {
            logException(caught);
            eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, i18n.t("This group is already a group member")));
        } else if (caught instanceof AlreadyUserMemberException) {
            logException(caught);
            eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, i18n.t("This user is already a group member")));
        } else {
            logException(caught, true);
            eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.error, i18n.t("Error performing operation")));
            GWT.log("Other kind of exception in StateManagerDefault/processErrorException", caught);
        }
    }

    private void goHome() {
        placeManager.revealDefaultPlace();
    }

    private void logException(final Throwable caught) {
        logException(caught, false);
    }

    private void logException(final Throwable caught, final boolean showException) {
        if (showException) {
            Log.debug("Exception in KuneErrorHandler", caught);
        } else {
            Log.debug("Exception in KuneErrorHandler: " + caught.getMessage());
        }
    }

}
