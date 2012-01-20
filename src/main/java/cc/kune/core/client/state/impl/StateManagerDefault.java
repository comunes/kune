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
package cc.kune.core.client.state.impl;

import org.waveprotocol.box.webclient.client.ClientEvents;
import org.waveprotocol.box.webclient.client.events.WaveSelectionEvent;
import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;
import org.waveprotocol.wave.util.escapers.GwtWaverefEncoder;

import cc.kune.common.client.actions.BeforeActionCollection;
import cc.kune.common.client.actions.BeforeActionListener;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.ProgressHideEvent;
import cc.kune.common.client.utils.Pair;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.events.GoHomeEvent;
import cc.kune.core.client.events.GroupChangedEvent;
import cc.kune.core.client.events.GroupChangedEvent.GroupChangedHandler;
import cc.kune.core.client.events.SocialNetworkChangedEvent;
import cc.kune.core.client.events.SocialNetworkChangedEvent.SocialNetworkChangedHandler;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.events.ToolChangedEvent;
import cc.kune.core.client.events.ToolChangedEvent.ToolChangedHandler;
import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignOutEvent;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.sitebar.spaces.Space;
import cc.kune.core.client.sitebar.spaces.SpaceConfEvent;
import cc.kune.core.client.sitebar.spaces.SpaceSelectEvent;
import cc.kune.core.client.state.ContentCache;
import cc.kune.core.client.state.HistoryTokenCallback;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokenListeners;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.TokenMatcher;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

public class StateManagerDefault implements StateManager, ValueChangeHandler<String> {
  public interface OnFinishGetContent {
    void finish();
  }
  private final BeforeActionCollection beforeStateChangeCollection;
  private final ContentCache contentCache;
  private final EventBus eventBus;
  private final HistoryWrapper history;
  private StateToken previousGroupToken;
  /**
   * When a historyChanged is interrupted (for instance because you are editing
   * something), the new history token is stored here
   */
  private String resumedHistoryToken;
  private final Session session;
  private final SiteTokenListeners siteTokens;
  private final TokenMatcher tokenMatcher;

  @Inject
  public StateManagerDefault(final ContentCache contentProvider, final Session session,
      final HistoryWrapper history, final TokenMatcher tokenMatcher, final EventBus eventBus,
      final SiteTokenListeners siteTokens) {
    this.tokenMatcher = tokenMatcher;
    this.eventBus = eventBus;
    this.contentCache = contentProvider;
    this.session = session;
    this.history = history;
    this.previousGroupToken = null;
    this.resumedHistoryToken = null;
    tokenMatcher.init(GwtWaverefEncoder.INSTANCE);
    this.siteTokens = siteTokens;
    beforeStateChangeCollection = new BeforeActionCollection();
    session.onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        session.onUserSignIn(false, new UserSignInEvent.UserSignInHandler() {
          @Override
          public void onUserSignIn(final UserSignInEvent event) {
            // refreshCurrentGroupState();
          }
        });
        session.onUserSignOut(false, new UserSignOutEvent.UserSignOutHandler() {
          @Override
          public void onUserSignOut(final UserSignOutEvent event) {
            refreshCurrentStateWithoutCache();
          }
        });
        processCurrentHistoryToken();
      }
    });
    onSocialNetworkChanged(false, new SocialNetworkChangedHandler() {
      @Override
      public void onSocialNetworkChanged(final SocialNetworkChangedEvent event) {
        contentCache.removeCacheOfGroup(event.getState().getStateToken().getGroup());
      }
    });
    eventBus.addHandler(GoHomeEvent.getType(), new GoHomeEvent.GoHomeHandler() {
      @Override
      public void onGoHome(final GoHomeEvent event) {
        gotoDefaultHomepage();
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
      ToolChangedEvent.fire(eventBus, previousGroupToken, newState.getStateToken());
    }
  }

  private void doActionOrSignInIfNeeded(final HistoryTokenCallback tokenListener,
      final String currentToken, final String secondPart) {
    // First of all we see if we are starting up, and we get the def content
    // first
    if (startingUp()) {
      // Starting with some token like "signin": load defContent first
      Log.debug("Starting up with some token like #signin or #token(param): load defContent first");
      getContent(new StateToken(SiteTokens.GROUP_HOME), false, new OnFinishGetContent() {
        @Override
        public void finish() {
          doActionOrSignInIfNeededStarted(tokenListener, currentToken, secondPart);
        }
      });
    } else {
      doActionOrSignInIfNeededStarted(tokenListener, currentToken, secondPart);
    }
  }

  private void doActionOrSignInIfNeededStarted(final HistoryTokenCallback tokenListener,
      final String currentToken, final String secondPart) {
    if (tokenListener.authMandatory() && session.isNotLogged()) {
      Log.debug("login mandatory for " + currentToken);
      // Ok, we have to redirect because this token (for instance
      // #translate) needs the user authenticated
      redirectButSignInBefore(currentToken);
    } else {
      // The auth is not mandatory, go ahead with the token action
      Log.debug("Executing action related with historytoken " + secondPart);
      tokenListener.onHistoryToken(secondPart);
    }
  }

  private void getContent(final StateToken newState) {
    Log.debug("Get Content: " + newState);
    getContent(newState, false);
  }

  private void getContent(final StateToken newState, final boolean setBrowserHistory) {
    final OnFinishGetContent doNothing = new OnFinishGetContent() {
      @Override
      public void finish() {
        // Do nothing
      }
    };
    getContent(newState, setBrowserHistory, doNothing);
  }

  private void getContent(final StateToken newState, final boolean setBrowserHistory,
      final OnFinishGetContent andThen) {
    // NotifyUser.info("loading: " + newState + " because current:" +
    // session.getCurrentStateToken());
    contentCache.getContent(session.getUserHash(), newState,
        new AsyncCallbackSimple<StateAbstractDTO>() {
          @Override
          public void onSuccess(final StateAbstractDTO newState) {
            setState(newState);
            final String currentToken = newState.getStateToken().toString();
            SpaceConfEvent.fire(eventBus, Space.groupSpace, currentToken);
            SpaceConfEvent.fire(eventBus, Space.publicSpace, TokenUtils.preview(currentToken));
            if (setBrowserHistory) {
              history.newItem(currentToken, false);
              SpaceSelectEvent.fire(eventBus, Space.groupSpace);
            }
            andThen.finish();
          }
        });
  }

  @Override
  public String getCurrentToken() {
    return history.getToken();
  }

  private String getPreviousGroup() {
    final String previousGroup = startingUp() ? "" : previousGroupToken.getGroup();
    return previousGroup;
  }

  private String getPreviousTool() {
    final String previousTool = startingUp() ? "" : previousGroupToken.getTool();
    return previousTool;
  }

  @Override
  public void gotoDefaultHomepage() {
    Log.debug("Goto def page called");
    getContent(new StateToken(SiteTokens.GROUP_HOME), true);
  }

  @Override
  public void gotoHistoryToken(final String token) {
    Log.debug("StateManager: history goto-string-token: " + token);
    history.newItem(token);
  }

  @Override
  public void gotoHistoryTokenButRedirectToCurrent(final String token) {
    gotoHistoryToken(TokenUtils.addRedirect(token, history.getToken()));

  }

  @Override
  public void gotoStateToken(final StateToken newToken) {
    Log.debug("StateManager: history goto-token: " + newToken + ", previous: " + previousGroupToken);
    history.newItem(newToken.getEncoded());
  }

  @Override
  public void gotoStateToken(final StateToken token, final boolean useCache) {
    if (!useCache) {
      contentCache.remove(token);
    }
    gotoStateToken(token);
  }

  @Override
  public void onGroupChanged(final boolean fireNow, final GroupChangedHandler handler) {
    eventBus.addHandler(GroupChangedEvent.getType(), handler);
    final StateAbstractDTO currentState = session.getCurrentState();
    if (fireNow && currentState != null) {
      handler.onGroupChanged(new GroupChangedEvent(getPreviousGroup(),
          currentState.getStateToken().getGroup()));
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
  public void onStateChanged(final boolean fireNow, final StateChangedHandler handler) {
    eventBus.addHandler(StateChangedEvent.getType(), handler);
    final StateAbstractDTO currentState = session.getCurrentState();
    if (fireNow && currentState != null) {
      handler.onStateChanged(new StateChangedEvent(currentState));
    }
  }

  @Override
  public void onToolChanged(final boolean fireNow, final ToolChangedHandler handler) {
    eventBus.addHandler(ToolChangedEvent.getType(), handler);
    final StateAbstractDTO currentState = session.getCurrentState();
    if (fireNow && currentState != null) {
      handler.onToolChanged(new ToolChangedEvent(previousGroupToken, currentState.getStateToken()));
    }
  }

  @Override
  public void onValueChange(final ValueChangeEvent<String> event) {
    Log.info("History event value changed: " + event.getValue());
    processHistoryToken(event.getValue());
  }

  private void processCurrentHistoryToken() {
    processHistoryToken(history.getToken());
  }

  void processHistoryToken(final String newToken) {
    // http://code.google.com/p/google-web-toolkit-doc-1-5/wiki/DevGuideHistory
    if (beforeStateChangeCollection.checkBeforeAction()) {
      // There isn't a beforeStateChange listener that stops this history
      // change
      final HistoryTokenCallback tokenListener = newToken != null ? siteTokens.get(newToken.toLowerCase())
          : null;
      boolean isSpecialHash = false;
      Log.debug("StateManager: on history changed (" + newToken + ")");
      if (tokenListener != null) {
        isSpecialHash = true;
        Log.debug("token is one of #newgroup #signin #translate without #hash(redirection) ...");
        doActionOrSignInIfNeeded(tokenListener, newToken, newToken);
      } else {
        Log.debug("Is not a special hash like #newgroup, etc, or maybe has a #hash(redirection)");
        // token is not one of #newgroup #signin #translate ...
        final String newTokenLower = newToken != null ? newToken.toLowerCase() : null;
        if (newTokenLower != null && tokenMatcher.hasRedirect(newTokenLower)) {
          final Pair<String, String> redirect = tokenMatcher.getRedirect(newTokenLower);
          final String firstToken = redirect.getLeft();
          final String sndToken = redirect.getRight();
          if (firstToken.equals(SiteTokens.PREVIEW)) {
            SpaceSelectEvent.fire(eventBus, Space.publicSpace);
            SpaceConfEvent.fire(eventBus, Space.groupSpace, sndToken);
            SpaceConfEvent.fire(eventBus, Space.publicSpace, TokenUtils.preview(sndToken));
            getContent(new StateToken(sndToken));
          } else {
            final HistoryTokenCallback tokenWithRedirect = siteTokens.get(firstToken);
            if (tokenWithRedirect != null) {
              isSpecialHash = true;
              Log.info("Is some #subtitle(foo) or #verifyemail(hash) etc");
              doActionOrSignInIfNeeded(tokenWithRedirect, newToken,
                  tokenMatcher.getRedirect(newToken).getRight());
            } else if (firstToken.equals(SiteTokens.NEW_GROUP)) {
              siteTokens.get(SiteTokens.NEW_GROUP).onHistoryToken(newToken);
            } else if (firstToken.equals(SiteTokens.SIGN_IN)) {
              if (session.isLogged()) {
                // We are logged, then redirect:
                history.newItem(sndToken, false);
                processHistoryToken(sndToken);
              } else {
                // We have to login
                siteTokens.get(SiteTokens.SIGN_IN).onHistoryToken(newToken);
              }
            }
          }
        }
        if (tokenMatcher.isWaveToken(newToken)) {
          if (session.isLogged()) {
            SpaceConfEvent.fire(eventBus, Space.userSpace, newToken);
            SpaceSelectEvent.fire(eventBus, Space.userSpace);
            try {
              ClientEvents.get().fireEvent(
                  new WaveSelectionEvent(GwtWaverefEncoder.decodeWaveRefFromPath(newToken)));
            } catch (final InvalidWaveRefException e) {
              Log.error(newToken, e);
            }
          } else {
            // Wave, but don't logged
            redirectButSignInBefore(newToken);
            if (startingUp()) {
              // Starting application (with Wave)
              getContent(new StateToken(SiteTokens.GROUP_HOME), false);
            }
          }
        } else if (!isSpecialHash) {
          if (tokenMatcher.isGroupToken(newToken)) {
            SpaceConfEvent.fire(eventBus, Space.groupSpace, newToken);
            SpaceConfEvent.fire(eventBus, Space.publicSpace, TokenUtils.preview(newToken));
            SpaceSelectEvent.fire(eventBus, Space.groupSpace);
            getContent(new StateToken(newToken));
          } else {
            Log.debug("Last option, get default with token: " + newToken);
            getContent(new StateToken(SiteTokens.GROUP_HOME), false);
          }
        }
      }
    } else {
      resumedHistoryToken = newToken;
    }
  }

  private void redirectButSignInBefore(final String newHistoryToken) {
    history.newItem(TokenUtils.addRedirect(SiteTokens.SIGN_IN, newHistoryToken));
  }

  @Override
  public void redirectOrRestorePreviousToken() {
    final String token = history.getToken();
    if (tokenMatcher.hasRedirect(token)) {
      // URL of the form signin(group.tool)
      final String previousToken = tokenMatcher.getRedirect(token).getRight();
      if (previousToken.equals(SiteTokens.WAVE_INBOX) && session.isNotLogged()) {
        // signin(inbox) && cancel
        restorePreviousToken();
      } else {
        history.newItem(previousToken); // FIXMEKK
      }
    } else {
      // No redirect then restore previous token
      restorePreviousToken();
    }
  }

  /**
   * <p>
   * Reload current state (using client cache if available)
   * </p>
   */
  @Override
  public void refreshCurrentState() {
    processHistoryToken(history.getToken());
  }

  /**
   * <p>
   * Reload current state (not using client cache)
   * </p>
   */
  @Override
  public void refreshCurrentStateWithoutCache() {
    final StateToken currentStateToken = session.getCurrentStateToken();
    if (currentStateToken == null) {
      processCurrentHistoryToken();
    } else {
      contentCache.remove(currentStateToken);
      getContent(currentStateToken);
    }
  }

  @Override
  public void removeBeforeStateChangeListener(final BeforeActionListener listener) {
    beforeStateChangeCollection.remove(listener);
  }

  @Override
  public void removeCache(final StateToken parentToken) {
    contentCache.remove(parentToken);
  }

  @Override
  public void removeCacheOfGroup(final String group) {
    contentCache.removeCacheOfGroup(group);
  }

  @Override
  public void removeSiteToken(final String token) {
    siteTokens.remove(token);
  }

  @Override
  public void restorePreviousToken() {
    if (previousGroupToken != null) {
      gotoStateToken(previousGroupToken);
    }
  }

  @Override
  public void resumeTokenChange() {
    if (resumedHistoryToken != null) {
      // Is this reload redundant?
      refreshCurrentState();
      gotoHistoryToken(resumedHistoryToken);
      resumedHistoryToken = null;
    }
  }

  @Override
  public void setRetrievedState(final StateAbstractDTO newState) {
    contentCache.cache(newState.getStateToken(), newState);
  }

  @Override
  public void setRetrievedStateAndGo(final StateAbstractDTO newState) {
    setRetrievedState(newState);
    final String token = newState.getStateToken().toString();
    if (history.getToken().equals(token)) {
      setState(newState);
    }
    history.newItem(token);
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
    contentCache.cache(newToken, newState);
    // history.newItem(newToken.toString(), false);
    StateChangedEvent.fire(eventBus, newState);
    checkGroupAndToolChange(newState);
    previousGroupToken = newToken.copy();
    eventBus.fireEvent(new ProgressHideEvent());
  }

  private boolean startingUp() {
    return previousGroupToken == null;
  }
}
