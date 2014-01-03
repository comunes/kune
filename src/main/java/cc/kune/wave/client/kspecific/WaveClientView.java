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
package cc.kune.wave.client.kspecific;

import org.waveprotocol.box.webclient.client.RemoteViewServiceMultiplexer;
import org.waveprotocol.box.webclient.client.WaveWebSocketClient;
import org.waveprotocol.box.webclient.search.SimpleSearch;
import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.common.safehtml.SafeHtml;
import org.waveprotocol.wave.client.common.util.AsyncHolder.Accessor;
import org.waveprotocol.wave.client.widget.common.ImplPanel;

import cc.kune.gspace.client.maxmin.IsMaximizable;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.IsWidget;

// TODO: Auto-generated Javadoc
/**
 * The Interface WaveClientView.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface WaveClientView extends IsWidget, IsMaximizable {

  void addToBottonBar(IsWidget widget);

  /**
   * Clear.
   */
  void clear();

  /**
   * Gets the channel.
   * 
   * @return the channel
   */
  RemoteViewServiceMultiplexer getChannel();

  /**
   * Gets the loading.
   * 
   * @return the loading
   */
  Element getLoading();

  /**
   * Gets the profiles.
   * 
   * @return the profiles
   */
  ProfileManager getProfiles();

  SimpleSearch getSearch();

  /**
   * Gets the stack trace async.
   * 
   * @param caught
   *          the caught
   * @param accessor
   *          the accessor
   * @return the stack trace async
   */
  void getStackTraceAsync(Throwable caught, Accessor<SafeHtml> accessor);

  /**
   * Gets the wave holder.
   * 
   * @return the wave holder
   */
  ImplPanel getWaveHolder();

  /**
   * Gets the web socket.
   * 
   * @return the web socket
   */
  WaveWebSocketClient getWebSocket();

  void hideBottomToolbar();

  /**
   * Login.
   */
  void login();

  /**
   * Logout.
   */
  void logout();

  void showBottomToolbar();

}
