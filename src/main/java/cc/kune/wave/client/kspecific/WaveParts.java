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

import org.waveprotocol.wave.client.common.util.WindowUtil;

import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.AppStartEvent.AppStartHandler;
import cc.kune.core.client.state.Session;
import cc.kune.wave.client.kspecific.inboxcount.InboxCountPresenter;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class WaveParts.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class WaveParts {

  /**
   * Instantiates a new wave parts.
   * 
   * @param session
   *          the session
   * @param waveClientManager
   *          the wave client manager
   * @param waveOnlineStatus
   *          the wave online status
   * @param inboxCount
   *          the inbox count
   */
  @Inject
  public WaveParts(final Session session, final Provider<WaveClientManager> waveClientManager,
      final Provider<WaveStatusIndicator> waveOnlineStatus,
      final Provider<PostWaveOpenActions> postOpenActions, final Provider<InboxCountPresenter> inboxCount) {
    session.onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        inboxCount.get();
        waveClientManager.get();
        waveOnlineStatus.get();
        WindowUtil.instance = new WindowKuneWrapper();
        postOpenActions.get();
      }
    });

  }
}
