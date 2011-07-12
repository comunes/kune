/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
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

public class TooltipTimers {

  private final TimerWrapper hideTimer;
  private final TimerWrapper securityTimer;
  private final TimerWrapper showTimer;

  public TooltipTimers(final TimerWrapper showTimer, final TimerWrapper hideTimer,
      final TimerWrapper securityTimer) {
    this.showTimer = showTimer;
    this.hideTimer = hideTimer;
    this.securityTimer = securityTimer;
  }

  public void cancel() {
    showTimer.cancel();
    hideTimer.cancel();
    securityTimer.cancel();
  }

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
