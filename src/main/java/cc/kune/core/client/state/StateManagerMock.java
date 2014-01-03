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
import cc.kune.common.client.ui.KuneWindowUtils;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.core.client.events.GroupChangedEvent.GroupChangedHandler;
import cc.kune.core.client.events.SocialNetworkChangedEvent.SocialNetworkChangedHandler;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.events.ToolChangedEvent.ToolChangedHandler;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.google.gwt.event.shared.HandlerRegistration;

public class StateManagerMock implements StateManager {

  @Override
  public void addBeforeStateChangeListener(final BeforeActionListener listener) {

  }

  @Override
  public void addSiteToken(final String token, final HistoryTokenCallback historyTokenCallback) {

  }

  @Override
  public String getCurrentToken() {

    return null;
  }

  @Override
  public void gotoDefaultHomepage() {

  }

  @Override
  public void gotoHistoryToken(final String newToken) {
    if ((SiteTokens.REGISTER.equals(newToken))) {
      final String protocol = WindowUtils.getProtocol();
      KuneWindowUtils.open(protocol + "//" + WindowUtils.getHost() + "#!" + SiteTokens.REGISTER);
    }
  }

  @Override
  public void gotoHistoryTokenButRedirectToCurrent(final String signin) {

  }

  @Override
  public void gotoHomeSpace() {

  }

  @Override
  public void gotoStateToken(final StateToken newToken) {

  }

  @Override
  public void gotoStateToken(final StateToken token, final boolean useCache) {

  }

  @Override
  public HandlerRegistration onGroupChanged(final boolean fireNow, final GroupChangedHandler handler) {

    return null;
  }

  @Override
  public HandlerRegistration onSocialNetworkChanged(final boolean fireNow,
      final SocialNetworkChangedHandler handler) {

    return null;
  }

  @Override
  public HandlerRegistration onStateChanged(final boolean fireNow, final StateChangedHandler handler) {

    return null;
  }

  @Override
  public HandlerRegistration onToolChanged(final boolean fireNow, final ToolChangedHandler handler) {

    return null;
  }

  @Override
  public void redirectOrRestorePreviousToken(final boolean fireChange) {

  }

  @Override
  public void refreshCurrentState() {

  }

  @Override
  public void refreshCurrentStateWithoutCache() {

  }

  @Override
  public void removeBeforeStateChangeListener(final BeforeActionListener listener) {

  }

  @Override
  public void removeCache(final StateToken parentToken) {

  }

  @Override
  public void removeCacheOfGroup(final String group) {

  }

  @Override
  public void removeSiteToken(final String token) {

  }

  @Override
  public void restorePreviousToken(final boolean fireChange) {

  }

  @Override
  public void resumeTokenChange() {

  }

  @Override
  public void setHistoryStateToken(final String token) {

  }

  @Override
  public void setRetrievedState(final StateAbstractDTO state) {

  }

  @Override
  public void setRetrievedStateAndGo(final StateAbstractDTO state) {

  }

  @Override
  public void setSocialNetwork(final SocialNetworkDataDTO socialNet) {

  }

}
