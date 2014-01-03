/*
 *

 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.ProgressHideEvent;
import cc.kune.common.shared.utils.Pair;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.auth.SignIn;
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
import cc.kune.gspace.client.actions.ShowHelpContainerEvent;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * The Class StateManagerDefault.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class StateManagerDefault implements StateManager, ValueChangeHandler<String> {

  /**
   * The Interface OnFinishGetContent.
   * 
   * @author danigb@gmail.com
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface OnFinishGetContent {

    /**
     * Finish.
     */
    void finish();
  }

  /** The before state change collection. */
  private final BeforeActionCollection beforeStateChangeCollection;

  /** The content cache. */
  private final ContentCache contentCache;

  /** The event bus. */
  private final EventBus eventBus;

  /** The history. */
  private final HistoryWrapper history;

  /** The previous group token. */
  private StateToken previousGroupToken;

  /** The previous hash. */
  private String previousHash;

  /**
   * When a historyChanged is interrupted (for instance because you are editing
   * something), the new history token is stored here.
   */
  private String resumedHistoryToken;

  /** The session. */
  private final Session session;

  /** The sign in. */
  private final Provider<SignIn> signIn;

  /** The site tokens. */
  private final SiteTokenListeners siteTokens;

  /**
   * Instantiates a new state manager default.
   * 
   * @param contentProvider
   *          the content provider
   * @param session
   *          the session
   * @param history
   *          the history
   * @param tokenMatcher
   *          the token matcher
   * @param eventBus
   *          the event bus
   * @param siteTokens
   *          the site tokens
   * @param signIn
   *          the sign in
   */
  @Inject
  public StateManagerDefault(final ContentCache contentProvider, final Session session,
      final HistoryWrapper history, final EventBus eventBus, final SiteTokenListeners siteTokens,
      final Provider<SignIn> signIn) {
    this.eventBus = eventBus;
    this.contentCache = contentProvider;
    this.session = session;
    this.history = history;
    this.signIn = signIn;
    this.previousGroupToken = null;
    this.previousHash = null;
    this.resumedHistoryToken = null;
    this.siteTokens = siteTokens;
    beforeStateChangeCollection = new BeforeActionCollection();
    TokenMatcher.init(GwtWaverefEncoder.INSTANCE);
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.StateManager#addBeforeStateChangeListener(cc.
   * kune.common.client.actions.BeforeActionListener)
   */
  @Override
  public void addBeforeStateChangeListener(final BeforeActionListener listener) {
    beforeStateChangeCollection.add(listener);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.StateManager#addSiteToken(java.lang.String,
   * cc.kune.core.client.state.HistoryTokenCallback)
   */
  @Override
  public void addSiteToken(final String token, final HistoryTokenCallback callback) {
    siteTokens.put(token.toLowerCase(), callback);
  }

  /**
   * Check group and tool change.
   * 
   * @param newState
   *          the new state
   */
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

  /**
   * Do action or sign in if needed.
   * 
   * @param tokenListener
   *          the token listener
   * @param currentToken
   *          the current token
   * @param secondPart
   *          the second part
   */
  private void doActionOrSignInIfNeeded(final HistoryTokenCallback tokenListener,
      final String currentToken, final String firstPart, final String secondPart) {
    // First of all we see if we are starting up, and we get the def content
    // first
    if (startingUp()) {
      // Starting with some token like "signin": load defContent first
      Log.info("Starting up with a token like #signin or #token(param): load defContent first");
      getContent(new StateToken(SiteTokens.GROUP_HOME), false, new OnFinishGetContent() {
        @Override
        public void finish() {
          doActionOrSignInIfNeededAfterStarted(tokenListener, currentToken, firstPart, secondPart);
        }
      });
    } else {
      doActionOrSignInIfNeededAfterStarted(tokenListener, currentToken, firstPart, secondPart);
    }
  }

  /**
   * Do action or sign in if needed started.
   * 
   * @param tokenListener
   *          the token listener
   * @param currentToken
   *          the current token
   * @param secondPart
   *          the second part
   */
  private void doActionOrSignInIfNeededAfterStarted(final HistoryTokenCallback tokenListener,
      final String currentToken, final String firstPart, final String secondPart) {
    if (tokenListener.authMandatory() && session.isNotLogged()) {
      Log.debug("login mandatory for " + currentToken);
      // Ok, we have to redirect because this token (for instance
      // #translate) needs the user authenticated
      final String infoMessage = tokenListener.getInfoMessage();
      if (TextUtils.notEmpty(infoMessage)) {
        signIn.get().setErrorMessage(infoMessage, NotifyLevel.info);
      }
      if (SiteTokens.SIGN_IN.equals(firstPart)) {
        signIn.get().showSignInDialog(secondPart);
      } else {
        redirectButSignInBefore(currentToken);
      }
    } else {
      // The auth is not mandatory, go ahead with the token action
      Log.debug("Executing action related with historytoken " + secondPart);
      tokenListener.onHistoryToken(secondPart);
    }
  }

  /**
   * Gets the content.
   * 
   * @param newState
   *          the new state
   * @return the content
   */
  private void getContent(final StateToken newState) {
    Log.debug("Get Content: " + newState);
    getContent(newState, false);
  }

  /**
   * Gets the content.
   * 
   * @param newState
   *          the new state
   * @param setBrowserHistory
   *          the set browser history
   * @return the content
   */
  private void getContent(final StateToken newState, final boolean setBrowserHistory) {
    final OnFinishGetContent doNothing = new OnFinishGetContent() {
      @Override
      public void finish() {
        // Do nothing
      }
    };
    getContent(newState, setBrowserHistory, doNothing);
  }

  /**
   * Gets the content.
   * 
   * @param newState
   *          the new state
   * @param setBrowserHistory
   *          the set browser history
   * @param andThen
   *          the and then
   * @return the content
   */
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
              previousHash = history.getToken();
              history.newItem(currentToken, false);
              SpaceSelectEvent.fire(eventBus, Space.groupSpace);
            }
            andThen.finish();
          }
        });
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.StateManager#getCurrentToken()
   */
  @Override
  public String getCurrentToken() {
    return history.getToken();
  }

  /**
   * Gets the previous group.
   * 
   * @return the previous group
   */
  private String getPreviousGroup() {
    final String previousGroup = startingUp() ? "" : previousGroupToken.getGroup();
    return previousGroup;
  }

  /**
   * Gets the previous tool.
   * 
   * @return the previous tool
   */
  private String getPreviousTool() {
    final String previousTool = startingUp() ? "" : previousGroupToken.getTool();
    return previousTool;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.StateManager#gotoDefaultHomepage()
   */
  @Override
  public void gotoDefaultHomepage() {
    Log.debug("Goto def page called");
    getContent(new StateToken(SiteTokens.GROUP_HOME), true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.StateManager#gotoHistoryToken(java.lang.String)
   */
  @Override
  public void gotoHistoryToken(final String token) {
    Log.debug("StateManager: history goto-string-token: " + token);
    previousHash = history.getToken();
    history.newItem(token);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.StateManager#gotoHistoryTokenButRedirectToCurrent
   * (java.lang.String)
   */
  @Override
  public void gotoHistoryTokenButRedirectToCurrent(final String token) {
    gotoHistoryToken(TokenUtils.addRedirect(token, history.getToken()));
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.StateManager#gotoHomeSpace()
   */
  @Override
  public void gotoHomeSpace() {
    gotoHistoryToken(SiteTokens.HOME);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.StateManager#gotoStateToken(cc.kune.core.shared
   * .domain.utils.StateToken)
   */
  @Override
  public void gotoStateToken(final StateToken newToken) {
    Log.debug("StateManager: history goto-token: " + newToken + ", previous: " + previousGroupToken);
    previousHash = history.getToken();
    history.newItem(newToken.getEncoded());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.StateManager#gotoStateToken(cc.kune.core.shared
   * .domain.utils.StateToken, boolean)
   */
  @Override
  public void gotoStateToken(final StateToken token, final boolean useCache) {
    if (!useCache) {
      contentCache.remove(token);
    }
    gotoStateToken(token);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.StateManager#onGroupChanged(boolean,
   * cc.kune.core.client.events.GroupChangedEvent.GroupChangedHandler)
   */
  @Override
  public HandlerRegistration onGroupChanged(final boolean fireNow, final GroupChangedHandler handler) {
    final HandlerRegistration handlerReg = eventBus.addHandler(GroupChangedEvent.getType(), handler);
    final StateAbstractDTO currentState = session.getCurrentState();
    if (fireNow && currentState != null) {
      handler.onGroupChanged(new GroupChangedEvent(getPreviousGroup(),
          currentState.getStateToken().getGroup()));
    }
    return handlerReg;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.StateManager#onSocialNetworkChanged(boolean,
   * cc
   * .kune.core.client.events.SocialNetworkChangedEvent.SocialNetworkChangedHandler
   * )
   */
  @Override
  public HandlerRegistration onSocialNetworkChanged(final boolean fireNow,
      final SocialNetworkChangedHandler handler) {
    final HandlerRegistration handlerReg = eventBus.addHandler(SocialNetworkChangedEvent.getType(),
        handler);
    final StateAbstractDTO currentState = session.getCurrentState();
    if (fireNow && currentState != null) {
      handler.onSocialNetworkChanged(new SocialNetworkChangedEvent(currentState));
    }
    return handlerReg;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.StateManager#onStateChanged(boolean,
   * cc.kune.core.client.events.StateChangedEvent.StateChangedHandler)
   */
  @Override
  public HandlerRegistration onStateChanged(final boolean fireNow, final StateChangedHandler handler) {
    final HandlerRegistration handlerReg = eventBus.addHandler(StateChangedEvent.getType(), handler);
    final StateAbstractDTO currentState = session.getCurrentState();
    if (fireNow && currentState != null) {
      handler.onStateChanged(new StateChangedEvent(currentState));
    }
    return handlerReg;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.StateManager#onToolChanged(boolean,
   * cc.kune.core.client.events.ToolChangedEvent.ToolChangedHandler)
   */
  @Override
  public HandlerRegistration onToolChanged(final boolean fireNow, final ToolChangedHandler handler) {
    final HandlerRegistration handlerReg = eventBus.addHandler(ToolChangedEvent.getType(), handler);
    final StateAbstractDTO currentState = session.getCurrentState();
    if (fireNow && currentState != null) {
      handler.onToolChanged(new ToolChangedEvent(previousGroupToken, currentState.getStateToken()));
    }
    return handlerReg;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(com
   * .google.gwt.event.logical.shared.ValueChangeEvent)
   */
  @Override
  public void onValueChange(final ValueChangeEvent<String> event) {
    Log.info("History event value changed: " + event.getValue());
    // First of all check that we are generating this #!fragment urls, later
    history.checkHashbang();
    processHistoryToken(HistoryUtils.undoHashbang(event.getValue()));
  }

  /**
   * Process current history token.
   */
  private void processCurrentHistoryToken() {
    processHistoryToken(history.getToken());
  }

  /**
   * Process #history token changes (this method should be rewritten).
   * 
   * @param newToken
   *          the new token
   */
  void processHistoryToken(final String newToken) {
    // http://code.google.com/p/google-web-toolkit-doc-1-5/wiki/DevGuideHistory
    if (beforeStateChangeCollection.checkBeforeAction()) {
      // There isn't a beforeStateChange listener that stops this history
      // change

      // WARNING: String.toLowerCase breaks Wave URls
      final HistoryTokenCallback tokenListener = newToken != null ? siteTokens.get(newToken.toLowerCase())
          : null;
      boolean isSpecialHash = false;
      Log.info("StateManager: on history changed '" + newToken + "'");
      if (tokenListener != null) {
        isSpecialHash = true;
        Log.debug("token is one of #newgroup #signin #translate without #hash(redirection) ...");
        doActionOrSignInIfNeeded(tokenListener, newToken, newToken, newToken);
      } else {
        Log.debug("Is not a special hash like #newgroup, etc, or maybe has a #hash(redirection)");
        // token is not one of #newgroup #signin #translate ...
        final String newTokenLower = newToken != null ? newToken.toLowerCase() : null;
        if (newTokenLower != null && TokenMatcher.hasRedirect(newTokenLower)) {
          final Pair<String, String> redirect = TokenMatcher.getRedirect(newToken);
          final String firstToken = redirect.getLeft().toLowerCase();
          final String sndToken = redirect.getRight();
          if (firstToken.equals(SiteTokens.PREVIEW)) {
            SpaceSelectEvent.fire(eventBus, Space.publicSpace);
            SpaceConfEvent.fire(eventBus, Space.groupSpace, sndToken);
            SpaceConfEvent.fire(eventBus, Space.publicSpace, TokenUtils.preview(sndToken));
            getContent(new StateToken(sndToken));
            // Don't continue
            return;
          } else if (firstToken.equals(SiteTokens.TUTORIAL)) {
            getContent(new StateToken(sndToken), true, new OnFinishGetContent() {
              @Override
              public void finish() {
                ShowHelpContainerEvent.fire(eventBus);
              }
            });
            // Don't continue
            return;
          } else {
            final HistoryTokenCallback tokenWithRedirect = siteTokens.get(firstToken);
            if (tokenWithRedirect != null) {
              isSpecialHash = true;
              Log.info("Is some #subtitle(foo) or #verifyemail(hash) etc, firstToken " + firstToken);
              doActionOrSignInIfNeeded(tokenWithRedirect, newToken, firstToken, sndToken);
            }
          }
        }
        if (TokenMatcher.isWaveToken(newToken)) {
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
          // FIXME This can be in the top of isWaveToken....
          if (TokenMatcher.isGroupToken(newToken)) {
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

  /**
   * Redirect but sign in before.
   * 
   * @param newHistoryToken
   *          the new history token
   */
  private void redirectButSignInBefore(final String newHistoryToken) {
    history.newItem(TokenUtils.addRedirect(SiteTokens.SIGN_IN, newHistoryToken));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.StateManager#redirectOrRestorePreviousToken(boolean
   * )
   */
  @Override
  public void redirectOrRestorePreviousToken(final boolean fireChange) {
    final String token = history.getToken();
    if (TokenMatcher.hasRedirect(token)) {
      // URL of the form signin(group.tool)
      final String previousToken = TokenMatcher.getRedirect(token).getRight();
      if (previousToken.equals(SiteTokens.WAVE_INBOX) && session.isNotLogged()) {
        // signin(inbox) && cancel
        restorePreviousToken(fireChange);
      } else {
        history.newItem(previousHash);
      }
    } else {
      // No redirect then restore previous token
      restorePreviousToken(fireChange);
    }
  }

  /**
   * <p>
   * Reload current state (using client cache if available)
   * </p>
   * .
   */
  @Override
  public void refreshCurrentState() {
    processHistoryToken(history.getToken());
  }

  /**
   * <p>
   * Reload current state (not using client cache)
   * </p>
   * .
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.StateManager#removeBeforeStateChangeListener(
   * cc.kune.common.client.actions.BeforeActionListener)
   */
  @Override
  public void removeBeforeStateChangeListener(final BeforeActionListener listener) {
    beforeStateChangeCollection.remove(listener);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.StateManager#removeCache(cc.kune.core.shared.
   * domain.utils.StateToken)
   */
  @Override
  public void removeCache(final StateToken parentToken) {
    contentCache.remove(parentToken);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.StateManager#removeCacheOfGroup(java.lang.String)
   */
  @Override
  public void removeCacheOfGroup(final String group) {
    contentCache.removeCacheOfGroup(group);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.StateManager#removeSiteToken(java.lang.String)
   */
  @Override
  public void removeSiteToken(final String token) {
    siteTokens.remove(token);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.StateManager#restorePreviousToken(boolean)
   */
  @Override
  public void restorePreviousToken(final boolean fireChange) {
    if (previousHash != null) {
      if (fireChange) {
        gotoHistoryToken(previousHash);
      } else {
        setHistoryStateToken(previousHash);
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.state.StateManager#resumeTokenChange()
   */
  @Override
  public void resumeTokenChange() {
    if (resumedHistoryToken != null) {
      // Is this reload redundant?
      refreshCurrentState();
      gotoHistoryToken(resumedHistoryToken);
      resumedHistoryToken = null;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.StateManager#setHistoryStateToken(java.lang.String
   * )
   */
  @Override
  public void setHistoryStateToken(final String newToken) {
    Log.debug("StateManager: history goto-token: " + newToken + ", previous: " + previousGroupToken);
    previousHash = history.getToken();
    history.newItem(newToken, false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.StateManager#setRetrievedState(cc.kune.core.shared
   * .dto.StateAbstractDTO)
   */
  @Override
  public void setRetrievedState(final StateAbstractDTO newState) {
    contentCache.cache(newState.getStateToken(), newState);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.StateManager#setRetrievedStateAndGo(cc.kune.core
   * .shared.dto.StateAbstractDTO)
   */
  @Override
  public void setRetrievedStateAndGo(final StateAbstractDTO newState) {
    setRetrievedState(newState);
    final String token = newState.getStateToken().toString();
    if (history.getToken().equals(token)) {
      setState(newState);
    }
    previousHash = history.getToken();
    history.newItem(token);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.state.StateManager#setSocialNetwork(cc.kune.core.shared
   * .dto.SocialNetworkDataDTO)
   */
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

  /**
   * Sets the state.
   * 
   * @param newState
   *          the new state
   */
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

  /**
   * Starting up.
   * 
   * @return true, if successful
   */
  private boolean startingUp() {
    return previousGroupToken == null;
  }
}
