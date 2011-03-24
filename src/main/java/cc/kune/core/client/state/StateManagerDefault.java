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
package cc.kune.core.client.state;

import java.util.HashMap;

import org.waveprotocol.box.webclient.client.HistorySupport;

import cc.kune.common.client.actions.BeforeActionCollection;
import cc.kune.common.client.actions.BeforeActionListener;
import cc.kune.common.client.errors.NotImplementedException;
import cc.kune.common.client.log.Log;
import cc.kune.core.client.notify.spiner.ProgressHideEvent;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.sitebar.spaces.Space;
import cc.kune.core.client.sitebar.spaces.SpaceSelectEvent;
import cc.kune.core.client.state.GroupChangedEvent.GroupChangedHandler;
import cc.kune.core.client.state.SocialNetworkChangedEvent.SocialNetworkChangedHandler;
import cc.kune.core.client.state.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.state.ToolChangedEvent.ToolChangedHandler;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener2;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class StateManagerDefault implements StateManager, ValueChangeHandler<String> {
    private final BeforeActionCollection beforeStateChangeCollection;
    private final ContentCache contentProvider;
    private final EventBus eventBus;
    private final HistoryWrapper history;
    private StateToken previousToken;
    /**
     * When a historyChanged is interrupted (for instance because you are
     * editing something), the new token is stored here
     */
    private StateToken resumedToken;
    private final Session session;
    private final HashMap<String, HistoryTokenCallback> siteTokens;

    @Inject
    public StateManagerDefault(final ContentCache contentProvider, final Session session, final HistoryWrapper history,
            final EventBus eventBus) {
        this.eventBus = eventBus;
        this.contentProvider = contentProvider;
        this.session = session;
        this.history = history;
        this.previousToken = null;
        this.resumedToken = null;
        siteTokens = new HashMap<String, HistoryTokenCallback>();
        beforeStateChangeCollection = new BeforeActionCollection();
        session.onUserSignIn(true, new UserSignInEvent.UserSignInHandler() {
            @Override
            public void onUserSignIn(final UserSignInEvent event) {
                if (previousToken == null) {
                    // starting up
                    reload();
                } else {
                    // do nothing, SigInPresent calls goto;
                }
            }
        });
        session.onUserSignOut(true, new UserSignOutEvent.UserSignOutHandler() {
            @Override
            public void onUserSignOut(final UserSignOutEvent event) {
                reload();
            }
        });
    }

    @Override
    public void addBeforeStateChangeListener(final BeforeActionListener listener) {
        beforeStateChangeCollection.add(listener);
    }

    @Override
    public void addSiteToken(final String token, final HistoryTokenCallback callback) {
        siteTokens.put(token.toLowerCase(), callback);
    }

    private void checkGroupAndToolChange(final StateAbstractDTO newState) {
        final String newGroup = newState.getStateToken().getGroup();
        final String previousGroup = getPreviousGroup();
        if (previousToken == null || !previousGroup.equals(newGroup)) {
            GroupChangedEvent.fire(eventBus, previousGroup, newGroup);
        }
        final String previousToolName = getPreviousTool();
        final String newTokenTool = newState.getStateToken().getTool();
        final String newToolName = newTokenTool == null ? "" : newTokenTool;
        if (previousToken == null || previousToolName == null || !previousToolName.equals(newToolName)) {
            ToolChangedEvent.fire(eventBus, previousToolName, newToolName);
        }
    }

    private void clearResumedToken() {
        resumedToken = null;
    }

    private String getPreviousGroup() {
        final String previousGroup = previousToken == null ? "" : previousToken.getGroup();
        return previousGroup;
    }

    private String getPreviousTool() {
        final String previousTool = previousToken == null ? "" : previousToken.getTool();
        return previousTool;
    }

    @Override
    public void gotoToken(final StateToken newToken) {
        Log.debug("StateManager: history goto-token newItem (" + newToken + ")");
        history.newItem(newToken.getEncoded());
    }

    @Override
    public void gotoToken(final String token) {
        Log.debug("StateManager: history goto-string-token newItem (" + token + ")");
        gotoToken(new StateToken(token));
    }

    @Override
    public void onGroupChanged(final boolean fireNow, final GroupChangedHandler handler) {
        eventBus.addHandler(GroupChangedEvent.getType(), handler);
        final StateAbstractDTO currentState = session.getCurrentState();
        if (fireNow && currentState != null) {
            handler.onGroupChanged(new GroupChangedEvent(getPreviousGroup(), currentState.getStateToken().getGroup()));
        }

    }

    @Override
    public void onGroupChanged(final Listener2<String, String> listener) {
        throw new NotImplementedException();
    }

    private void onHistoryChanged(final StateToken newState) {
        contentProvider.getContent(session.getUserHash(), newState, new AsyncCallbackSimple<StateAbstractDTO>() {
            @Override
            public void onSuccess(final StateAbstractDTO newState) {
                setState(newState);
            }
        });
    }

    void onHistoryChanged(final String historyToken) {
        // http://code.google.com/p/google-web-toolkit-doc-1-5/wiki/DevGuideHistory
        if (beforeStateChangeCollection.checkBeforeAction()) {
            final HistoryTokenCallback tokenListener = siteTokens.get(historyToken != null ? historyToken.toLowerCase()
                    : historyToken);
            Log.debug("StateManager: on history changed (" + historyToken + ")");
            if (tokenListener == null) {
                // Ok, normal token change
                // Is a Wave token?
                if (historyToken == null || HistorySupport.waveRefFromHistoryToken(historyToken) == null) {
                    // Non wave token
                    SpaceSelectEvent.fire(eventBus, Space.groupSpace);
                    onHistoryChanged(new StateToken(historyToken));
                } else {
                    SpaceSelectEvent.fire(eventBus, Space.userSpace);
                    // Wave token
                    // spaceSelector.onUserSpaceSelect();
                    if (session.isNotLogged()) {
                        // use r=? argument?
                    }
                }
            } else {
                // token is one of #newgroup #signin #translate ...
                if (previousToken == null) {
                    // Starting with some token like "signin": load defContent
                    // also
                    onHistoryChanged("");
                    SpaceSelectEvent.fire(eventBus, Space.groupSpace);
                }
                tokenListener.onHistoryToken();
            }
        } else {
            resumedToken = new StateToken(historyToken);
        }
    }

    @Override
    public void onSocialNetworkChanged(final boolean fireNow, final SocialNetworkChangedHandler handler) {
        eventBus.addHandler(SocialNetworkChangedEvent.getType(), handler);
        final StateAbstractDTO currentState = session.getCurrentState();
        if (fireNow && currentState != null) {
            handler.onSocialNetworkChanged(new SocialNetworkChangedEvent(currentState));
        }
    }

    @Override
    public void onSocialNetworkChanged(final Listener<StateAbstractDTO> listener) {
        throw new NotImplementedException();
    }

    @Override
    public void onStateChanged(final boolean fireNow, final StateChangedHandler handler) {
        eventBus.addHandler(StateChangedEvent.getType(), handler);
        final StateAbstractDTO currentState = session.getCurrentState();
        if (fireNow && currentState != null) {
            handler.onStateChanged(new StateChangedEvent(currentState));
        }
    }

    @Override
    public void onStateChanged(final Listener<StateAbstractDTO> listener) {
        throw new NotImplementedException();
    }

    @Override
    public void onToolChanged(final boolean fireNow, final ToolChangedHandler handler) {
        eventBus.addHandler(ToolChangedEvent.getType(), handler);
        final StateAbstractDTO currentState = session.getCurrentState();
        if (fireNow && currentState != null) {
            handler.onToolChanged(new ToolChangedEvent(getPreviousTool(), currentState.getStateToken().getTool()));
        }
    }

    @Override
    public void onToolChanged(final Listener2<String, String> listener) {
        throw new NotImplementedException();
    }

    @Override
    public void onValueChange(final ValueChangeEvent<String> event) {
        Log.info("History event value changed: " + event.getValue());
        onHistoryChanged(event.getValue());
    }

    /**
     * <p>
     * Reload current state (using client cache if available)
     * </p>
     */
    @Override
    public void reload() {
        Log.info("Reloading state");
        onHistoryChanged(history.getToken());
    }

    @Override
    public void removeBeforeStateChangeListener(final BeforeActionListener listener) {
        beforeStateChangeCollection.remove(listener);
    }

    @Override
    public void removeSiteToken(final String token) {
        siteTokens.remove(token);
    }

    @Override
    public void restorePreviousToken() {
        gotoToken(previousToken);
    }

    @Override
    public void resumeTokenChange() {
        if (resumedToken != null) {
            reload();
            gotoToken(resumedToken);
            clearResumedToken();
        }
    }

    @Override
    public void setRetrievedState(final StateAbstractDTO newState) {
        contentProvider.cache(newState.getStateToken(), newState);
        // setState(newState);
        history.newItem(newState.getStateToken().toString());
    }

    @Override
    public void setSocialNetwork(final SocialNetworkDataDTO socialNet) {
        StateAbstractDTO state;
        if (session != null && (state = session.getCurrentState()) != null) {
            // After a SN operation, usually returns a SocialNetworkResultDTO
            // with new SN data and we refresh the state
            // to avoid to reload() again the state
            state.setSocialNetworkData(socialNet);
            SocialNetworkChangedEvent.fire(eventBus, state);
        }
    }

    void setState(final StateAbstractDTO newState) {
        session.setCurrentState(newState);
        StateChangedEvent.fire(eventBus, newState);
        eventBus.fireEvent(new ProgressHideEvent());
        checkGroupAndToolChange(newState);
        previousToken = newState.getStateToken();
    }
}
