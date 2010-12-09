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

import cc.kune.core.client.CoreEventBus;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Event0;
import com.calclab.suco.client.events.Listener0;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.inject.Inject;
import com.mvp4g.client.event.BaseEventHandler;

public class ErrorHandler extends BaseEventHandler<CoreEventBus> {
    private final Session session;
    private final I18nTranslationService i18n;
    private final Event0 onSessionExpired;

    @Inject
    public ErrorHandler(final Session session, final I18nTranslationService i18n) {
        this.session = session;
        this.i18n = i18n;
        this.onSessionExpired = new Event0("onSessionExpired");
    }

    public void doSessionExpired() {
        onSessionExpired.fire();
        eventBus.notify(NotifyLevel.info, "Your session has expired. Please log in again.");
    }

    public void onSessionExpired(final Listener0 listener) {
        onSessionExpired.add(listener);
    }

    public void process(final Throwable caught) {
        eventBus.hideSpin();
        if (caught instanceof AccessViolationException) {
            logException(caught);
            eventBus.notify(NotifyLevel.error, i18n.t("You do not have rights to perform that action"));
        } else if (caught instanceof SessionExpiredException) {
            logException(caught);
            doSessionExpired();
        } else if (caught instanceof UserMustBeLoggedException) {
            logException(caught);
            if (session.isLogged()) {
                doSessionExpired();
            } else {
                eventBus.notify(NotifyLevel.important, i18n.t("Please sign in or register to collaborate"));
            }
        } else if (caught instanceof GroupNotFoundException) {
            logException(caught);

            eventBus.notify(NotifyLevel.veryImportant, i18n.t("Group not found"));
            eventBus.gotoToken("");
        } else if (caught instanceof IncompatibleRemoteServiceException) {
            eventBus.notify(NotifyLevel.error,
                    i18n.t("Your browser is outdated with the server software. Please reload this page."));
        } else if (caught instanceof ContentNotFoundException) {
            logException(caught);
            eventBus.notify(NotifyLevel.veryImportant, i18n.t("Content not found"));
            eventBus.gotoToken("");
        } else if (caught instanceof ContentNotPermittedException) {
            logException(caught);
            eventBus.notify(NotifyLevel.error, i18n.t("Action not permitted in this location"));
            eventBus.gotoToken("");
        } else if (caught instanceof ContainerNotPermittedException) {
            logException(caught);
            eventBus.notify(NotifyLevel.error, i18n.t("Action not permitted in this location"));
            eventBus.gotoToken("");
        } else if (caught instanceof LastAdminInGroupException) {
            logException(caught);
            eventBus.alert(i18n.t("Warning"), i18n.t("Sorry, you are the last admin of this group."
                    + " Look for someone to substitute you appropriately as admin before leaving this group."));
        } else if (caught instanceof AlreadyGroupMemberException) {
            logException(caught);
            eventBus.notify(NotifyLevel.error, i18n.t("This group is already a group member"));
        } else if (caught instanceof AlreadyUserMemberException) {
            logException(caught);
            eventBus.notify(NotifyLevel.error, i18n.t("This user is already a group member"));
        } else {
            logException(caught, true);
            eventBus.notify(NotifyLevel.error, i18n.t("Error performing operation"));
            GWT.log("Other kind of exception in StateManagerDefault/processErrorException", caught);
        }
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
