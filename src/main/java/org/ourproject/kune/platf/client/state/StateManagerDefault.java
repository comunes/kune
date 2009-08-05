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
package org.ourproject.kune.platf.client.state;

import java.util.HashMap;

import org.ourproject.kune.platf.client.actions.BeforeActionCollection;
import org.ourproject.kune.platf.client.actions.BeforeActionListener;
import org.ourproject.kune.platf.client.app.HistoryWrapper;
import org.ourproject.kune.platf.client.dto.SocialNetworkDataDTO;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Event;
import com.calclab.suco.client.events.Event2;
import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.events.Listener2;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

public class StateManagerDefault implements StateManager, ValueChangeHandler<String> {
    private final ContentProvider contentProvider;
    private StateToken previousToken;
    /**
     * When a historyChanged is interrupted (for instance because you are
     * editing something), the new token is stored here
     */
    private StateToken resumedToken;
    private final Session session;
    private final HistoryWrapper history;
    private final HashMap<String, Listener0> siteTokens;
    private final Event<StateAbstractDTO> onStateChanged;
    private final Event<StateAbstractDTO> onSocialNetworkChanged;
    private final Event2<String, String> onToolChanged;
    private final Event2<String, String> onGroupChanged;
    private final BeforeActionCollection beforeStateChangeCollection;

    public StateManagerDefault(final ContentProvider contentProvider, final Session session,
            final HistoryWrapper history) {
        this.contentProvider = contentProvider;
        this.session = session;
        this.history = history;
        this.previousToken = null;
        this.resumedToken = null;
        this.onStateChanged = new Event<StateAbstractDTO>("onStateChanged");
        this.onGroupChanged = new Event2<String, String>("onGroupChanged");
        this.onToolChanged = new Event2<String, String>("onToolChanged");
        this.onSocialNetworkChanged = new Event<StateAbstractDTO>("onSocialNetworkChanged");
        session.onUserSignIn(new Listener<UserInfoDTO>() {
            public void onEvent(final UserInfoDTO parameter) {
                if (previousToken == null) {
                    // starting up
                    reload();
                } else {
                    // do nothing, SigInPresent calls goto;
                }
            }
        });
        session.onUserSignOut(new Listener0() {
            public void onEvent() {
                reload();
            }
        });
        siteTokens = new HashMap<String, Listener0>();
        beforeStateChangeCollection = new BeforeActionCollection();
    }

    public void addBeforeStateChangeListener(final BeforeActionListener listener) {
        beforeStateChangeCollection.add(listener);
    }

    public void addSiteToken(final String token, final Listener0 listener) {
        siteTokens.put(token, listener);
    }

    public void gotoToken(final StateToken newToken) {
        Log.debug("StateManager: history goto-token newItem (" + newToken + ")");
        history.newItem(newToken.getEncoded());
    }

    public void gotoToken(final String token) {
        gotoToken(new StateToken(token));
    }

    public void onGroupChanged(final Listener2<String, String> listener) {
        onGroupChanged.add(listener);
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

    public void onValueChange(final ValueChangeEvent<String> event) {
        onHistoryChanged(event.getValue());
    }

    /**
     * <p>
     * Reload current state (using client cache if available)
     * </p>
     */
    public void reload() {
        onHistoryChanged(history.getToken());
    }

    public void removeBeforeStateChangeListener(final BeforeActionListener listener) {
        beforeStateChangeCollection.remove(listener);
    }

    public void removeSiteToken(final String token) {
        siteTokens.remove(token);
    }

    public void restorePreviousToken() {
        gotoToken(previousToken);
    }

    public void resumeTokenChange() {
        if (resumedToken != null) {
            reload();
            gotoToken(resumedToken);
            clearResumedToken();
        }
    }

    public void setRetrievedState(final StateAbstractDTO newState) {
        contentProvider.cache(newState.getStateToken(), newState);
        // setState(newState);
        history.newItem(newState.getStateToken().toString());
    }

    public void setSocialNetwork(final SocialNetworkDataDTO socialNet) {
        StateAbstractDTO state;
        if (session != null && (state = session.getCurrentState()) != null) {
            // After a SN operation, usually returns a SocialNetworkResultDTO
            // with new SN data and we refresh the state
            // to avoid to reload() again the state
            state.setSocialNetworkData(socialNet);
            onSocialNetworkChanged.fire(state);
        }
    }

    void onHistoryChanged(final String historyToken) {
        // http://code.google.com/p/google-web-toolkit-doc-1-5/wiki/DevGuideHistory
        if (beforeStateChangeCollection.checkBeforeAction()) {
            final Listener0 tokenListener = siteTokens.get(historyToken);
            Log.debug("StateManager: history token changed (" + historyToken + ")");
            if (tokenListener == null) {
                // Ok, normal token change
                onHistoryChanged(new StateToken(historyToken));
            } else {
                // token is one of #newgroup #signin #translate ...
                if (previousToken == null) {
                    // Starting with some token like "signin": load defContent
                    // also
                    onHistoryChanged("");
                }
                tokenListener.onEvent();
            }
        } else {
            resumedToken = new StateToken(historyToken);
        }
    }

    void setState(final StateAbstractDTO newState) {
        session.setCurrentState(newState);
        onStateChanged.fire(newState);
        NotifyUser.hideProgress();
        checkGroupAndToolChange(newState);
        previousToken = newState.getStateToken();

    }

    private void checkGroupAndToolChange(final StateAbstractDTO newState) {
        final String previousGroup = previousToken == null ? "" : previousToken.getGroup();
        final String newGroup = newState.getStateToken().getGroup();
        final String previousTokenTool = previousToken == null ? "" : previousToken.getTool();
        final String newTokenTool = newState.getStateToken().getTool();
        final String previousToolName = previousTokenTool == null ? "" : previousTokenTool;
        final String newToolName = newTokenTool == null ? "" : newTokenTool;

        if (previousToken == null || previousToolName == null || !previousToolName.equals(newToolName)) {
            onToolChanged.fire(previousToolName, newToolName);
        }
        if (previousToken == null || !previousGroup.equals(newGroup)) {
            onGroupChanged.fire(previousGroup, newGroup);
        }
    }

    private void clearResumedToken() {
        resumedToken = null;
    }

    private void onHistoryChanged(final StateToken newState) {
        contentProvider.getContent(session.getUserHash(), newState, new AsyncCallbackSimple<StateAbstractDTO>() {
            public void onSuccess(final StateAbstractDTO newState) {
                setState(newState);
            }
        });
    }
}
