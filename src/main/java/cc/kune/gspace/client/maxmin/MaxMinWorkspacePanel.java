/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
package cc.kune.gspace.client.maxmin;

import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.gspace.client.maxmin.MaxMinWorkspacePresenter.MaxMinWorkspaceView;
import cc.kune.wave.client.kspecific.WaveClientProvider;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class MaxMinWorkspacePanel implements MaxMinWorkspaceView {

  private final GSpaceArmor gsArmor;
  private final WaveClientProvider waveClient;

  @Inject
  public MaxMinWorkspacePanel(final GSpaceArmor gsArmor, final WaveClientProvider waveClient) {
    this.gsArmor = gsArmor;
    this.waveClient = waveClient;
  }

  @Override
  public void addToSlot(final Object slot, final Widget content) {
  }

  @Override
  public Widget asWidget() {
    return null;
  }

  @Override
  public void removeFromSlot(final Object slot, final Widget content) {
  }

  @Override
  public void setInSlot(final Object slot, final Widget content) {
  }

  @Override
  public void setMaximized(final boolean maximized) {
    gsArmor.setMaximized(maximized);
    waveClient.get().setMaximized(maximized);
  }
}
