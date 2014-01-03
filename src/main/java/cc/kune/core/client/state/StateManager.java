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
package cc.kune.core.client.state;

import cc.kune.common.client.actions.BeforeActionListener;
import cc.kune.core.client.events.GroupChangedEvent;
import cc.kune.core.client.events.SocialNetworkChangedEvent;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.ToolChangedEvent;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.google.gwt.event.shared.HandlerRegistration;

// TODO: Auto-generated Javadoc
/**
 * Some methods of the StateManager should we rewritten (because its origin is
 * previous to Apache Wave and now is very confusing), but this take control of
 * the #history changes, get the content from the server (or from cache), etc.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface StateManager {

  /**
   * Adds the before state change listener.
   * 
   * @param listener
   *          you can use this {@link BeforeActionListener} to intercept changes
   *          of state (for instance if you are editing and what to save, etc.
   */
  void addBeforeStateChangeListener(BeforeActionListener listener);

  /**
   * This permits to add custom #framents to urls and its associated actions.
   * 
   * @param token
   *          the url #fragment
   * @param historyTokenCallback
   *          the action that will be executed when the previous url fragment
   */
  void addSiteToken(String token, HistoryTokenCallback historyTokenCallback);

  /**
   * Gets the current token.
   * 
   * @return the current token
   */
  String getCurrentToken();

  /**
   * Goto default homepage.
   */
  void gotoDefaultHomepage();

  /**
   * Goto history token.
   * 
   * @param newToken
   *          the new token
   */
  void gotoHistoryToken(String newToken);

  /**
   * Goto history token but redirect to current.
   * 
   * @param signin
   *          the signin
   */
  void gotoHistoryTokenButRedirectToCurrent(String signin);

  /**
   * Goto home space.
   */
  void gotoHomeSpace();

  /**
   * Goto state token.
   * 
   * @param newToken
   *          the new token
   */
  void gotoStateToken(StateToken newToken);

  /**
   * Goto state token.
   * 
   * @param token
   *          the token
   * @param useCache
   *          the use cache
   */
  void gotoStateToken(StateToken token, boolean useCache);

  /**
   * On group changed.
   * 
   * @param fireNow
   *          the fire now
   * @param handler
   *          the handler
   * @return the handler registration
   */
  HandlerRegistration onGroupChanged(boolean fireNow, GroupChangedEvent.GroupChangedHandler handler);

  /**
   * On social network changed.
   * 
   * @param fireNow
   *          the fire now
   * @param handler
   *          the handler
   * @return the handler registration
   */
  HandlerRegistration onSocialNetworkChanged(boolean fireNow,
      SocialNetworkChangedEvent.SocialNetworkChangedHandler handler);

  /**
   * On state changed.
   * 
   * @param fireNow
   *          if true, fire handler with current state
   * @param handler
   *          the handler
   * @return the handler registration
   */
  HandlerRegistration onStateChanged(boolean fireNow, StateChangedEvent.StateChangedHandler handler);

  /**
   * On tool changed.
   * 
   * @param fireNow
   *          the fire now
   * @param handler
   *          the handler
   * @return the handler registration
   */
  HandlerRegistration onToolChanged(boolean fireNow, ToolChangedEvent.ToolChangedHandler handler);

  /**
   * Redirect or restore previous token.
   * 
   * @param fireChange
   *          the fire change
   */
  void redirectOrRestorePreviousToken(boolean fireChange);

  /**
   * Refresh current state.
   */
  void refreshCurrentState();

  /**
   * Refresh current state without cache.
   */
  void refreshCurrentStateWithoutCache();

  /**
   * Removes the before state change listener.
   * 
   * @param listener
   *          the listener
   */
  void removeBeforeStateChangeListener(BeforeActionListener listener);

  /**
   * Removes the cache.
   * 
   * @param parentToken
   *          the parent token
   */
  void removeCache(StateToken parentToken);

  /**
   * Removes the cache of group.
   * 
   * @param group
   *          the group
   */
  void removeCacheOfGroup(String group);

  /**
   * Removes the site token.
   * 
   * @param token
   *          the token
   */
  void removeSiteToken(String token);

  /**
   * Restore previous token.
   * 
   * @param fireChange
   *          if only change the browser #token or also fire the event
   */
  void restorePreviousToken(boolean fireChange);

  /**
   * Resume token change.
   */
  void resumeTokenChange();

  /**
   * Sets the history state token.
   * 
   * @param token
   *          the new history state token
   */
  void setHistoryStateToken(String token);

  /**
   * Sets the retrieved state.
   * 
   * @param state
   *          the new retrieved state
   */
  void setRetrievedState(StateAbstractDTO state);

  /**
   * Sets the retrieved state and go.
   * 
   * @param state
   *          the new retrieved state and go
   */
  void setRetrievedStateAndGo(StateAbstractDTO state);

  /**
   * Sets the social network.
   * 
   * @param socialNet
   *          the new social network
   */
  void setSocialNetwork(SocialNetworkDataDTO socialNet);

}
