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
package org.ourproject.kune.platf.client.services;

import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.AlreadyGroupMemberException;
import org.ourproject.kune.platf.client.errors.AlreadyUserMemberException;
import org.ourproject.kune.platf.client.errors.ContainerNotPermittedException;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.errors.ContentNotPermittedException;
import org.ourproject.kune.platf.client.errors.GroupNotFoundException;
import org.ourproject.kune.platf.client.errors.LastAdminInGroupException;
import org.ourproject.kune.platf.client.errors.SessionExpiredException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.site.Site;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Event0;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;

public class KuneErrorHandler {
    private final Session session;
    private final I18nTranslationService i18n;
    private final Event0 onSessionExpired;
    private final Provider<StateManager> stateManagerProvider;

    public KuneErrorHandler(final Session session, final I18nTranslationService i18n,
            final Provider<StateManager> stateManagerProvider) {
        this.session = session;
        this.i18n = i18n;
        this.stateManagerProvider = stateManagerProvider;
        this.onSessionExpired = new Event0("onSessionExpired");
    }

    public void doSessionExpired() {
        onSessionExpired.fire();
        Site.info("Your session has expired. Please log in again.");
    }

    public void onSessionExpired(final Listener0 listener) {
        onSessionExpired.add(listener);
    }

    public void process(final Throwable caught) {
        Site.hideProgress();
        try {
            throw caught;
        } catch (final AccessViolationException e) {
            logException(e);
            Site.error(i18n.t("You do not have rights to perform that action"));
        } catch (final SessionExpiredException e) {
            logException(e);
            doSessionExpired();
        } catch (final UserMustBeLoggedException e) {
            logException(e);
            if (session.isLogged()) {
                doSessionExpired();
            } else {
                Site.important(i18n.t("Please sign in or register to collaborate"));
            }
        } catch (final GroupNotFoundException e) {
            logException(e);
            Site.veryImportant(i18n.t("Group not found"));
            stateManagerProvider.get().gotoToken("");
        } catch (final IncompatibleRemoteServiceException e) {
            Site.error(i18n.t("Your browser is outdated with the server software. Please reload this page."));
        } catch (final ContentNotFoundException e) {
            logException(e);
            Site.veryImportant(i18n.t("Content not found"));
            stateManagerProvider.get().gotoToken("");
        } catch (final ContentNotPermittedException e) {
            logException(e);
            Site.error(i18n.t("Action not permitted in this location"));
            stateManagerProvider.get().gotoToken("");
        } catch (final ContainerNotPermittedException e) {
            logException(e);
            Site.error(i18n.t("Action not permitted in this location"));
            stateManagerProvider.get().gotoToken("");
        } catch (final LastAdminInGroupException e) {
            logException(e);
            Site.showAlertMessage(i18n.t("Warning"), i18n.t("Sorry, you are the last admin of this group."
                    + " Look for someone to substitute you appropriately as admin before leaving this group."));
        } catch (final AlreadyGroupMemberException e) {
            logException(e);
            Site.error(i18n.t("This group is already a group member"));
        } catch (final AlreadyUserMemberException e) {
            logException(e);
            Site.error(i18n.t("This user is already a group member"));
        } catch (final Throwable e) {
            logException(e, true);
            Site.error(i18n.t("Error performing operation"));
            GWT.log("Other kind of exception in StateManagerDefault/processErrorException", null);
        }
    }

    private void logException(final Throwable e) {
        logException(e, false);
    }

    private void logException(final Throwable e, final boolean showException) {
        if (showException) {
            Log.debug("Exception in KuneErrorHandler", e);
        } else {
            Log.debug(e.getMessage());
        }
    }

}
