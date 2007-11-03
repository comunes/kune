/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.state;

import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.errors.GroupNotFoundException;
import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.client.tool.ClientTool;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.workspace.client.dto.StateDTO;
import org.ourproject.kune.workspace.client.workspace.Workspace;

import to.tipit.gwtlib.FireLog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class StateManagerDefault implements StateManager {
    private final Application app;
    private final Session session;
    private final ContentProvider provider;
    private final Workspace workspace;
    private StateDTO oldState;
    private String lastTheme;

    public StateManagerDefault(final ContentProvider provider, final Application app, final Session session) {
        this.provider = provider;
        this.app = app;
        this.session = session;
        this.workspace = app.getWorkspace();
    }

    public void reload() {
        onHistoryChanged(History.getToken());
    }

    public Session getSession() {
        return session;
    }

    public void onHistoryChanged(final String historyToken) {
        GWT.log("State: " + historyToken, null);
        if (historyToken.equals(Site.NEWGROUP_TOKEN)) {
            Site.doNewGroup(oldState.getState().getEncoded());
        } else if (historyToken.equals(Site.LOGIN_TOKEN)) {
            Site.doLogin(oldState.getState().getEncoded());
        } else if (historyToken.equals(Site.FIXME_TOKEN)) {
            loadContent(oldState);
        } else {
            onHistoryChanged(new StateToken(historyToken));
        }
    }

    private void onHistoryChanged(final StateToken newState) {
        Site.showProgressProcessing();
        provider.getContent(session.userHash, newState, new AsyncCallback() {
            public void onFailure(final Throwable caught) {
                processErrorException(caught);
            }

            public void onSuccess(final Object result) {
                GWT.log("State response: " + result, null);
                StateDTO newStateDTO = (StateDTO) result;
                loadContent(newStateDTO);
                oldState = newStateDTO;
            }
        });
    }

    public void setState(final StateDTO content) {
        final StateToken state = content.getState();
        provider.cache(state, content);
        setState(state);
    }

    public void setState(final StateToken state) {
        History.newItem(state.getEncoded());
    }

    private void loadContent(final StateDTO state) {
        GWT.log("title: " + state.getTitle(), null);
        session.setCurrent(state);
        final GroupDTO group = state.getGroup();
        app.setGroupState(group.getShortName());
        boolean isAdmin = state.getGroupRights().isAdministrable();
        if (isAdmin) {
            workspace.getThemeMenuComponent().setVisible(true);
        } else {
            workspace.getThemeMenuComponent().setVisible(false);
        }
        setWsTheme(group);
        workspace.showGroup(group, isAdmin);
        final String toolName = state.getToolName();
        workspace.setTool(toolName);

        final ClientTool clientTool = app.getTool(toolName);
        clientTool.setContent(state);
        workspace.getContentTitleComponent().setContentTitle(state.getTitle(), "11/06/07");
        workspace.getContentSubTitleComponent().setContentSubTitle("by Luther Blissett, Luther Blissett Jr", "English");
        workspace.getContentBottomToolBarComponent().setRate(state.isRateable(), session.isLogged(), state.getRate(),
                state.getRateByUsers(), state.getCurrentUserRate());
        workspace.setContent(clientTool.getContent());
        workspace.setContext(clientTool.getContext());
        workspace.getLicenseComponent().setLicense(state.getGroup().getLongName(), state.getLicense());
        workspace.getTagsComponent().setTags("FIXME");

        if (oldState != null && oldState.getGroup().getShortName().equals(state.getGroup().getShortName())
                && oldState.getGroupRights().equals(state.getGroupRights())) {
            // Same group, same rights, do nothing
            FireLog.debug("Same group, same rights, not reloading SN");
        } else {
            loadSocialNetwork();
        }

        // only for UI tests:
        workspace.getBuddiesPresenceComponent().setBuddiesPresence();
        Site.hideProgress();
    }

    private void setWsTheme(final GroupDTO group) {
        String nextTheme = group.getWorkspaceTheme();
        if (lastTheme == null || lastTheme != nextTheme) {
            workspace.setTheme(nextTheme);
        }
        lastTheme = nextTheme;
    }

    public String getUser() {
        return session.userHash;
    }

    public void reloadSocialNetwork() {
        Site.sitebar.reloadUserInfo(session.userHash);
        loadSocialNetwork();
    }

    // TODO: Extract this to a utility class
    public void processErrorException(final Throwable caught) {
        Site.hideProgress();
        try {
            throw caught;
        } catch (final AccessViolationException e) {
            // i18n
            Site.error("You don't have rights to do that");
        } catch (final UserMustBeLoggedException e) {
            // i18n
            Site.error("Please sign in or register");
        } catch (final GroupNotFoundException e) {
            Site.error("Group not found");
        } catch (final ContentNotFoundException e) {
            Site.error("Content not found");
        } catch (final Throwable e) {
            Site.error("Error performing operation");
            GWT.log("Other kind of exception in StateManagerDefault/processErrorException", null);
            throw new RuntimeException();
        }
    }

    private void loadSocialNetwork() {
        StateDTO state;
        if (session != null && (state = session.getCurrentState()) != null) {
            workspace.getGroupMembersComponent().getGroupMembers(session.userHash, state.getGroup(),
                    state.getGroupRights());
            workspace.getParticipationComponent().getParticipation(session.userHash, state.getGroup(),
                    state.getGroupRights());
        }
    }

}
