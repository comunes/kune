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

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;

/**
 * The Class WaveEmbedContainer.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class WaveEmbedContainer implements HasWaveContainer {

  /** The panel. */
  private final FlowPanel panel;

  /**
   * Instantiates a new wave embed panel.
   */
  public WaveEmbedContainer() {
    panel = new FlowPanel();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.wave.client.kspecific.HasWaveWidget#getForIsWidget()
   */
  @Override
  public ForIsWidget getForIsWidget() {
    return panel;
  }

}
