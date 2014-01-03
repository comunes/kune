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

import org.waveprotocol.wave.client.widget.common.ImplPanel;

import cc.kune.common.client.log.Log;
import cc.kune.wave.client.CustomStagesProvider;

import com.google.gwt.user.client.ui.HasWidgets;

/**
 * The Class WaveClientUtils.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class WaveClientUtils {

  /**
   * Clear.
   * 
   * @param wave
   *          the wave
   * @param waveHolder
   *          the wave holder
   * @param parent
   *          the parent
   */
  public static void clear(CustomStagesProvider wave, final ImplPanel waveHolder, final HasWidgets parent) {
    if (wave != null) {
      try {
        wave.destroy();
      } catch (final RuntimeException e) {
        // When editing: java.lang.RuntimeException: Component not found: MENU
        Log.error("Error clearing wave panel", e);
      }
      wave = null;
    }
    if (waveHolder != null && waveHolder.isAttached()) {
      waveHolder.removeFromParent();
      parent.remove(waveHolder);
    }
  }
}
