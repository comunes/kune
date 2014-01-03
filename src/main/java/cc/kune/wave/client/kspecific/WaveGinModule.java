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

import cc.kune.chat.client.LastConnectedManager;
import cc.kune.chat.client.LastConnectedManagerImpl;
import cc.kune.core.client.ExtendedGinModule;
import cc.kune.wave.client.KuneWaveProfileManager;
import cc.kune.wave.client.RosterProfileUpdater;
import cc.kune.wave.client.WebClient;
import cc.kune.wave.client.kspecific.inboxcount.InboxCountPanel;
import cc.kune.wave.client.kspecific.inboxcount.InboxCountPresenter;
import cc.kune.wave.client.kspecific.inboxcount.InboxCountPresenter.InboxCountView;

// TODO: Auto-generated Javadoc
/**
 * The Class WaveGinModule.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class WaveGinModule extends ExtendedGinModule {
  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.inject.client.AbstractGinModule#configure()
   */
  @Override
  protected void configure() {
    s(HasWaveContainer.class, WaveUserSpaceContainer.class);
    s(LastConnectedManager.class, LastConnectedManagerImpl.class);
    s(RosterProfileUpdater.class);
    s(PostWaveOpenActions.class);
    s(AurorisColorPicker.class);
    s(WebClientMock.class);
    s(WebClient.class);
    s(WaveClientProvider.class);
    s(WaveClientManager.class);
    s(WaveStatusIndicator.class);
    eagle(WaveParts.class);
    s(KuneWaveProfileManager.class);
    s(InboxCountView.class, InboxCountPanel.class);
    s(InboxCountPresenter.class);
  }
}
