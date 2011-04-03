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

import org.waveprotocol.wave.util.escapers.GwtWaverefEncoder;

import cc.kune.common.client.actions.BeforeActionCollection;
import cc.kune.common.client.actions.BeforeActionListener;
import cc.kune.common.client.errors.NotImplementedException;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.utils.Pair;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
import cc.kune.core.client.notify.spiner.ProgressHideEvent;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.sitebar.spaces.Space;
import cc.kune.core.client.sitebar.spaces.SpaceConfEvent;
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
     * editing something), the new history token is stored here
     */
    private String resumedHistoryToken;
    private final Session session;
    private final HashMap<String, HistoryTokenCallback> siteTokens;
    private final TokenMatcher tokenMatcher;

    @Inject
    public StateManagerDefault(final ContentCache contentProvider, final Session session, final HistoryWrapper history,
            final TokenMatcher tokenMatcher, final EventBus eventBus) {
        this.tokenMatcher = tokenMatcher;
        this.eventBus = eventBus;
        this.contentProvider = contentProvider;
        this.session = session;
        this.history = history;
        this.previousToken = null;
        this.resumedHistoryToken = null;
        tokenMatcher.init(GwtWaverefEncoder.INSTANCE);
        siteTokens = new HashMap<String, HistoryTokenCallback>();
        beforeStateChangeCollection = new BeforeActionCollection();
        session.onAppStart(true, new AppStartHandler() {

            @Override
            public void onAppStart(final AppStartEvent event) {
                session.onUserSignIn(true, new UserSignInEvent.UserSignInHandler() {
                    @Override
                    public void onUserSignIn(final UserSignInEvent event) {
                        if (startingUp()) {
                            processHistoryToken(history.getToken());
                        } else {
                            refreshCurrentGroupState();
                        }
                    }
                });
                session.onUserSignOut(true, new UserSignOutEvent.UserSignOutHandler() {
                    @Override
                    public void onUserSignOut(final UserSignOutEvent event) {
                        if (startingUp()) {
                            processHistoryToken(history.getToken());
                        } else {
                            // Other perms, then refresh state from server
                            refreshCurrentGroupState();
                        }
                    }
                });
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
        if (startingUp() || !previousGroup.equals(newGroup)) {
            GroupChangedEvent.fire(eventBus, previousGroup, newGroup);
        }
        final String previousToolName = getPreviousTool();
        final String newTokenTool = newState.getStateToken().getTool();
        final String newToolName = newTokenTool == null ? "" : newTokenTool;
        if (startingUp() || previousToolName == null || !previousToolName.equals(newToolName)) {
            ToolChangedEvent.fire(eventBus, previousToolName, newToolName);
        }
    }

    private String getPreviousGroup() {
        final String previousGroup = startingUp() ? "" : previousToken.getGroup();
        return previousGroup;
    }

    private String getPreviousTool() {
        final String previousTool = startingUp() ? "" : previousToken.getTool();
        return previousTool;
    }

    @Override
    public void gotoHistoryToken(final String token) {
        Log.debug("StateManager: history goto-string-token newItem (" + token + ")");
        history.newItem(token);
    }

    @Override
    public void gotoHistoryTokenButRedirectToCurrent(final String token) {
        gotoHistoryToken(TokenUtils.addRedirect(token, history.getToken()));

    }

    @Override
    public void gotoStateToken(final StateToken newToken) {
        Log.debug("StateManager: history goto-token newItem (" + newToken + ")");
        history.newItem(newToken.getEncoded());
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
        // NotifyUser.info("loading: " + newState + " because current:" +
        // session.getCurrentStateToken());
        contentProvider.getContent(session.getUserHash(), newState, new AsyncCallbackSimple<StateAbstractDTO>() {
            @Override
            public void onSuccess(final StateAbstractDTO newState) {
                setState(newState);
            }
        });
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
        processHistoryToken(event.getValue());
    }

    void processHistoryToken(final String newHistoryToken) {
        // http://code.google.com/p/google-web-toolkit-doc-1-5/wiki/DevGuideHistory
        if (beforeStateChangeCollection.checkBeforeAction()) {
            // There isn't a beforeStateChange listener that stops this history
            // change
            HistoryTokenCallback tokenListener = null;
            if (newHistoryToken != null) {
                final String nToken = newHistoryToken.toLowerCase();
                tokenListener = siteTokens.get(nToken);
            }
            Log.debug("StateManager: on history changed (" + newHistoryToken + ")");
            if (tokenListener == null) {
                Log.debug("Is not a special hash");
                // token is not one of #newgroup #signin #translate ...
                final String nToken = newHistoryToken != null ? newHistoryToken.toLowerCase() : null;
                if (tokenMatcher.hasRedirect(nToken)) {
                    final Pair<String, String> redirect = tokenMatcher.getRedirect(nToken);
                    final String firstToken = redirect.getLeft();
                    final String sndToken = redirect.getRight();
                    if (firstToken.equals(SiteTokens.PREVIEW)) {
                        SpaceSelectEvent.fire(eventBus, Space.publicSpace);
                        SpaceConfEvent.fire(eventBus, Space.groupSpace, sndToken);
                        SpaceConfEvent.fire(eventBus, Space.publicSpace, TokenUtils.preview(sndToken));
                        onHistoryChanged(new StateToken(sndToken));
                    } else if (firstToken.equals(SiteTokens.NEWGROUP)) {
                        siteTokens.get(SiteTokens.NEWGROUP).onHistoryToken();
                    } else if (firstToken.equals(SiteTokens.SIGNIN)) {
                        if (session.isLogged()) {
                            // We are logged, then redirect:
                            history.newItem(sndToken, false);
                            processHistoryToken(sndToken);
                        } else {
                            // We have to loggin
                            siteTokens.get(SiteTokens.SIGNIN).onHistoryToken();
                        }
                    }
                } else if (tokenMatcher.isWaveToken(newHistoryToken)) {
                    if (session.isLogged()) {
                        SpaceSelectEvent.fire(eventBus, Space.userSpace);
                    } else {
                        history.newItem(TokenUtils.addRedirect(SiteTokens.SIGNIN, newHistoryToken));
                    }
                    if (startingUp()) {
                        // Starting application (with Wave url)
                        onHistoryChanged(new StateToken(SiteTokens.GROUP_HOME));
                    }
                } else if (tokenMatcher.isGroupToken(newHistoryToken)) {
                    SpaceConfEvent.fire(eventBus, Space.groupSpace, newHistoryToken);
                    SpaceConfEvent.fire(eventBus, Space.publicSpace, TokenUtils.preview(newHistoryToken));
                    SpaceSelectEvent.fire(eventBus, Space.groupSpace);
                    onHistoryChanged(new StateToken(newHistoryToken));
                } else {
                    // While we don't redefine token "" as home:
                    SpaceConfEvent.fire(eventBus, Space.groupSpace, SiteTokens.GROUP_HOME);
                    SpaceConfEvent.fire(eventBus, Space.publicSpace, TokenUtils.preview(SiteTokens.GROUP_HOME));
                    SpaceSelectEvent.fire(eventBus, Space.groupSpace);
                    onHistoryChanged(new StateToken(SiteTokens.GROUP_HOME));
                }
            } else {
                // token is one of #newgroup #signin #translate ...
                if (startingUp()) {
                    // Starting with some token like "signin": load defContent
                    // also
                    processHistoryToken("");
                    SpaceSelectEvent.fire(eventBus, Space.groupSpace);
                }
                // Fire the listener of this #hash token
                tokenListener.onHistoryToken();
            }
        } else {
            resumedHistoryToken = newHistoryToken;
        }
    }

    @Override
    public void redirectOrRestorePreviousToken() {
        final String token = history.getToken();
        if (tokenMatcher.hasRedirect(token)) {
            // URL of the form signin(group.tool)
            final String previousToken = tokenMatcher.getRedirect(token).getRight();
            if (previousToken.equals(SiteTokens.WAVEINBOX) && session.isNotLogged()) {
                // signin(inbox) && cancel
                restorePreviousToken();
            } else {
                history.newItem(previousToken);
            }
        } else {
            // No redirect then restore previous token
            restorePreviousToken();
        }
    }

    /**
     * <p>
     * Reload current state (not using client cache)
     * </p>
     */
    @Override
    public void refreshCurrentGroupState() {
        final StateToken currentStateToken = session.getCurrentStateToken();
        contentProvider.removeContent(currentStateToken);
        onHistoryChanged(currentStateToken);
    }

    /**
     * <p>
     * Reload current state (using client cache if available)
     * </p>
     */
    @Override
    public void reload() {
        processHistoryToken(history.getToken());
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
        gotoStateToken(previousToken);
    }

    @Override
    public void resumeTokenChange() {
        if (resumedHistoryToken != null) {
            // Is this reload redundant?
            reload();
            gotoHistoryToken(resumedHistoryToken);
            resumedHistoryToken = null;
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
        final StateToken newToken = newState.getStateToken();
        contentProvider.cache(newToken, newState);
        // history.newItem(newToken.toString(), false);
        StateChangedEvent.fire(eventBus, newState);
        checkGroupAndToolChange(newState);
        previousToken = newToken;
        eventBus.fireEvent(new ProgressHideEvent());
    }

    private boolean startingUp() {
        return previousToken == null;
    }
}
