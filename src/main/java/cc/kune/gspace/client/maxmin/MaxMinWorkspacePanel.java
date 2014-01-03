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
package cc.kune.gspace.client.maxmin;

import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.gspace.client.maxmin.MaxMinWorkspacePresenter.MaxMinWorkspaceView;
import cc.kune.wave.client.kspecific.WaveClientProvider;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class MaxMinWorkspacePanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class MaxMinWorkspacePanel implements MaxMinWorkspaceView {

  /** The gs armor. */
  private final GSpaceArmor gsArmor;

  /** The wave client. */
  private final WaveClientProvider waveClient;

  /**
   * Instantiates a new max min workspace panel.
   * 
   * @param gsArmor
   *          the gs armor
   * @param waveClient
   *          the wave client
   */
  @Inject
  public MaxMinWorkspacePanel(final GSpaceArmor gsArmor, final WaveClientProvider waveClient) {
    this.gsArmor = gsArmor;
    this.waveClient = waveClient;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#addToSlot(java.lang.Object,
   * com.google.gwt.user.client.ui.Widget)
   */
  @Override
  public void addToSlot(final Object slot, final Widget content) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#removeFromSlot(java.lang.Object,
   * com.google.gwt.user.client.ui.Widget)
   */
  @Override
  public void removeFromSlot(final Object slot, final Widget content) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#setInSlot(java.lang.Object,
   * com.google.gwt.user.client.ui.Widget)
   */
  @Override
  public void setInSlot(final Object slot, final Widget content) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.maxmin.IsMaximizable#setMaximized(boolean)
   */
  @Override
  public void setMaximized(final boolean maximized) {
    gsArmor.setMaximized(maximized);
    waveClient.get().setMaximized(maximized);
  }
}
