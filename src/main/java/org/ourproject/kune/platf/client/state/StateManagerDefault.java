/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.state;

import java.util.HashMap;

import org.ourproject.kune.platf.client.app.HistoryWrapper;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.ParticipationDataDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.UserBuddiesDataDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.workspace.client.site.Site;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.listener.Event;
import com.calclab.suco.client.listener.Event2;
import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener0;
import com.calclab.suco.client.listener.Listener2;

public class StateManagerDefault implements StateManager {
    private final ContentProvider contentProvider;
    private StateAbstractDTO oldState;
    private final Session session;
    private final HistoryWrapper history;
    private final HashMap<String, Listener<StateToken>> siteTokens;
    private final Event<StateAbstractDTO> onStateChanged;
    private final Event<StateAbstractDTO> onSocialNetworkChanged;
    private final Event2<String, String> onToolChanged;
    private final Event2<GroupDTO, GroupDTO> onGroupChanged;

    public StateManagerDefault(final ContentProvider contentProvider, final Session session,
            final HistoryWrapper history) {
        this.contentProvider = contentProvider;
        this.session = session;
        this.history = history;
        this.oldState = null;
        this.onStateChanged = new Event<StateAbstractDTO>("onStateChanged");
        this.onGroupChanged = new Event2<GroupDTO, GroupDTO>("onGroupChanged");
        this.onToolChanged = new Event2<String, String>("onToolChanged");
        this.onSocialNetworkChanged = new Event<StateAbstractDTO>("onSocialNetworkChanged");
        session.onUserSignIn(new Listener<UserInfoDTO>() {
            public void onEvent(final UserInfoDTO parameter) {
                if (oldState != null) {
                    restorePreviousState();
                } else {
                    reload();
                }
            }
        });
        session.onUserSignOut(new Listener0() {
            public void onEvent() {
                reload();
            }
        });
        siteTokens = new HashMap<String, Listener<StateToken>>();
    }

    public void addSiteToken(final String token, final Listener<StateToken> listener) {
        siteTokens.put(token, listener);
    }

    public void gotoToken(final StateToken state) {
        Log.debug("StateManager: history goto-token newItem (" + state + ")");
        history.newItem(state.getEncoded());
    }

    public void gotoToken(final String token) {
        gotoToken(new StateToken(token));
    }

    public void onGroupChanged(final Listener2<GroupDTO, GroupDTO> listener) {
        onGroupChanged.add(listener);
    }

    public void onHistoryChanged(final String historyToken) {
        final Listener<StateToken> tokenListener = siteTokens.get(historyToken);
        Log.debug("StateManager: history token changed (" + historyToken + ")");
        if (tokenListener == null) {
            onHistoryChanged(new StateToken(historyToken));
        } else {
            StateToken stateToken;
            if (oldState == null) {
                // Starting with some token like "signin": load defContent also
                stateToken = new StateToken();
                onHistoryChanged(stateToken);
            } else {
                stateToken = oldState.getStateToken();
            }
            tokenListener.onEvent(stateToken);
        }
    }

    public void onSocialNetworkChanged(final Listener<StateAbstractDTO> listener) {
        onSocialNetworkChanged.add(listener);
    }

    public void onStateChanged(final Listener<StateAbstractDTO> listener) {
        onStateChanged.add(listener);
    }

    public void onToolChanged(final Listener2<String, String> listener) {
        onToolChanged.add(listener);
    }

    /**
     * <p>
     * Reload current state (using client cache if available)
     * </p>
     */
    public void reload() {
        onHistoryChanged(history.getToken());
    }

    public void removeSiteToken(final String token) {
        siteTokens.remove(token);
    }

    public void restorePreviousState() {
        if (oldState == null) {
            onHistoryChanged(new StateToken());
        } else {
            final StateAbstractDTO newState = oldState;
            oldState = session.getCurrentState();
            setState(newState);
        }
    }

    public void setRetrievedState(final StateAbstractDTO newState) {
        contentProvider.cache(newState.getStateToken(), newState);
        setState(newState);
    }

    public void setSocialNetwork(final SocialNetworkResultDTO socialNet) {
        StateAbstractDTO state;
        if (session != null && (state = session.getCurrentState()) != null) {
            // After a SN operation, usually returns a SocialNetworkResultDTO
            // with new SN data and we refresh the state
            // to avoid to reload() again the state
            final SocialNetworkDTO groupMembers = socialNet.getGroupMembers();
            final ParticipationDataDTO userParticipation = socialNet.getUserParticipation();
            final UserBuddiesDataDTO userBuddies = socialNet.getUserBuddies();
            state.setGroupMembers(groupMembers);
            state.setParticipation(userParticipation);
            state.setUserBuddies(userBuddies);
            onSocialNetworkChanged.fire(state);
        }
    }

    private void checkGroupAndToolChange(final StateAbstractDTO oldState, final StateAbstractDTO newState) {
        final GroupDTO oldGroup = oldState != null ? oldState.getGroup() : null;
        final GroupDTO newGroup = newState.getGroup();
        if (oldState == null || !oldGroup.equals(newGroup)) {
            onGroupChanged.fire(oldGroup, newGroup);
        }
        // TODO field with oldToolName
        String oldToolName = null;
        String newToolName = null;
        if (oldState instanceof StateContainerDTO) {
            oldToolName = oldState != null ? ((StateContainerDTO) oldState).getToolName() : null;
        }
        if (newState instanceof StateContainerDTO) {
            newToolName = ((StateContainerDTO) newState).getToolName();
        }
        if (oldState == null || oldToolName == null || !oldToolName.equals(newToolName)) {
            onToolChanged.fire(oldToolName, newToolName);
        }
    }

    private void onHistoryChanged(final StateToken newState) {
        contentProvider.getContent(session.getUserHash(), newState, new AsyncCallbackSimple<StateAbstractDTO>() {
            public void onSuccess(final StateAbstractDTO newState) {
                setState(newState);
            }
        });
    }

    private void setState(final StateAbstractDTO newState) {
        session.setCurrentState(newState);
        onStateChanged.fire(newState);
        Site.hideProgress();
        checkGroupAndToolChange(oldState, newState);
        oldState = newState;
    }
}
