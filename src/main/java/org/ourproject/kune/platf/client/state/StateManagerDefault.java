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
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.UserBuddiesDataDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.workspace.client.site.Site;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.event.Events;
import com.calclab.suco.client.listener.Event;
import com.calclab.suco.client.listener.Event2;
import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener0;
import com.calclab.suco.client.listener.Listener2;

public class StateManagerDefault implements StateManager {
    private final ContentProvider contentProvider;
    private StateDTO oldState;
    private final Session session;
    private final HistoryWrapper history;
    private final HashMap<String, Listener<StateToken>> siteTokens;
    private final Event<StateDTO> onStateChanged;
    private final Event<StateDTO> onSocialNetworkChanged;
    private final Event2<String, String> onToolChanged;
    private final Event2<GroupDTO, GroupDTO> onGroupChanged;

    public StateManagerDefault(final ContentProvider contentProvider, final Session session,
            final HistoryWrapper history) {
        this.contentProvider = contentProvider;
        this.session = session;
        this.history = history;
        this.oldState = null;
        this.onStateChanged = Events.create(StateDTO.class, "onStateChanged");
        this.onGroupChanged = Events.create(GroupDTO.class, GroupDTO.class, "onGroupChanged");
        this.onToolChanged = Events.create(String.class, String.class, "onToolChanged");
        this.onSocialNetworkChanged = Events.create(StateDTO.class, "onSocialNetworkChanged");
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

    public void onSocialNetworkChanged(final Listener<StateDTO> listener) {
        onSocialNetworkChanged.add(listener);
    }

    public void onStateChanged(final Listener<StateDTO> listener) {
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
            final StateDTO newState = oldState;
            oldState = session.getCurrentState();
            setState(newState);
        }
    }

    public void setRetrievedState(final StateDTO newState) {
        contentProvider.cache(newState.getStateToken(), newState);
        setState(newState);
    }

    public void setSocialNetwork(final SocialNetworkResultDTO socialNet) {
        StateDTO state;
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

    private void checkGroupAndToolChange(final StateDTO oldState, final StateDTO newState) {
        final GroupDTO oldGroup = oldState != null ? oldState.getGroup() : null;
        final GroupDTO newGroup = newState.getGroup();
        final String oldToolName = oldState != null ? oldState.getToolName() : null;
        final String newToolName = newState.getToolName();
        if (oldState == null || !oldGroup.equals(newGroup)) {
            onGroupChanged.fire(oldGroup, newGroup);
        }
        if (oldState == null || oldToolName == null || !oldToolName.equals(newToolName)) {
            onToolChanged.fire(oldToolName, newToolName);
        }
    }

    private void onHistoryChanged(final StateToken newState) {
        contentProvider.getContent(session.getUserHash(), newState, new AsyncCallbackSimple<StateDTO>() {
            public void onSuccess(final StateDTO newState) {
                setState(newState);
            }
        });
    }

    private void setState(final StateDTO newState) {
        session.setCurrentState(newState);
        onStateChanged.fire(newState);
        Site.hideProgress();
        checkGroupAndToolChange(oldState, newState);
        oldState = newState;
    }
}
