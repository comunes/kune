/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.common.client.ui;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.UIObject;

/**
 * Source:
 * http://stackoverflow.com/questions/2316590/blink-flash-effect-or-animation
 * -in-gwt
 */
/**
 * @author vjrj
 *
 */
/**
 * @author vjrj
 * 
 */
public class BlinkAnimation {

  private boolean blink;

  private final int interval;

  private int iteration;

  private int stopIter;

  private final Timer timer;

  public BlinkAnimation(final UIObject obj) {
    this(obj, 200);
  }

  /**
   * @param obj
   *          the object to animate
   * @param interval
   *          between blinks
   */
  public BlinkAnimation(final UIObject obj, final int interval) {
    this.interval = interval;

    timer = new Timer() {
      @Override
      public void run() {
        if (!blink) {
          obj.addStyleDependentName("blink");
          iteration++;
        } else {
          obj.removeStyleDependentName("blink");
          if (iteration == stopIter) {
            timer.cancel();
          }
        }
        blink = !blink;
      }
    };
  }

  /**
   * Animate till {@link BlinkAnimation#stop() }
   */
  public void animate() {
    animate(-1);
  }

  /**
   * @param numTimes
   *          to blink (3, 4, ... etc)
   */
  public void animate(final int numTimes) {
    iteration = 0;
    stopIter = numTimes;
    blink = false;
    timer.scheduleRepeating(interval);
  }

  /**
   * Stop the animation now
   */
  public void stop() {
    timer.cancel();
  }
}
