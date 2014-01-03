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
package cc.kune.common.client.tooltip;

import cc.kune.common.client.utils.TimerWrapper;

// TODO: Auto-generated Javadoc
/**
 * The Class TooltipTimers.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TooltipTimers {

  /** The hide timer. */
  private final TimerWrapper hideTimer;
  
  /** The security timer. */
  private final TimerWrapper securityTimer;
  
  /** The show timer. */
  private final TimerWrapper showTimer;

  /**
   * Instantiates a new tooltip timers.
   *
   * @param showTimer the show timer
   * @param hideTimer the hide timer
   * @param securityTimer the security timer
   */
  public TooltipTimers(final TimerWrapper showTimer, final TimerWrapper hideTimer,
      final TimerWrapper securityTimer) {
    this.showTimer = showTimer;
    this.hideTimer = hideTimer;
    this.securityTimer = securityTimer;
  }

  /**
   * Cancel.
   */
  public void cancel() {
    showTimer.cancel();
    hideTimer.cancel();
    securityTimer.cancel();
  }

  /**
   * On out.
   */
  public void onOut() {
    if (showTimer.isScheduled()) {
      showTimer.cancel();
    }
    if (!hideTimer.isScheduled()) {
      hideTimer.schedule(650);
    }
    if (!securityTimer.isScheduled()) {
      securityTimer.cancel();
    }
  }

  /**
   * On over.
   */
  public void onOver() {
    if (!showTimer.isScheduled()) {
      showTimer.schedule(700);
    }
    if (hideTimer.isScheduled()) {
      hideTimer.cancel();
    }
    if (!securityTimer.isScheduled()) {
      securityTimer.schedule(7000);
    }
  }

  /**
   * Show is scheduled.
   *
   * @return true, if successful
   */
  public boolean showIsScheduled() {
    return showTimer.isScheduled();
  }

  /**
   * Show temporally.
   */
  public void showTemporally() {
    if (showTimer.isScheduled()) {
      showTimer.cancel();
    }
    if (hideTimer.isScheduled()) {
      hideTimer.cancel();
    }
    securityTimer.schedule(5000);
  }
}
