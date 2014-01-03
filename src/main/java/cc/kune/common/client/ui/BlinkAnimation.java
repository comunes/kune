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
package cc.kune.common.client.ui;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.UIObject;

// TODO: Auto-generated Javadoc
/**
 * Source:
 * http://stackoverflow.com/questions/2316590/blink-flash-effect-or-animation
 * -in-gwt
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
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

  /** The blink. */
  private boolean blink;

  /** The interval. */
  private final int interval;

  /** The iteration. */
  private int iteration;

  /** The stop iter. */
  private int stopIter;

  /** The timer. */
  private final Timer timer;

  /**
   * Instantiates a new blink animation.
   *
   * @param obj the obj
   */
  public BlinkAnimation(final UIObject obj) {
    this(obj, 200);
  }

  /**
   * Instantiates a new blink animation.
   *
   * @param obj the object to animate
   * @param interval between blinks
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
   * Animate till {@link BlinkAnimation#stop() }.
   */
  public void animate() {
    animate(-1);
  }

  /**
   * Animate.
   *
   * @param numTimes to blink (3, 4, ... etc)
   */
  public void animate(final int numTimes) {
    iteration = 0;
    stopIter = numTimes;
    blink = false;
    timer.scheduleRepeating(interval);
  }

  /**
   * Stop the animation now.
   */
  public void stop() {
    timer.cancel();
  }
}
