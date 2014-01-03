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
package cc.kune.common.client.utils;

import com.google.gwt.user.client.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class TimerWrapper.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TimerWrapper {
  
  /**
   * The Interface Executer.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface Executer {
    /**
     * Invokes the execute.
     */
    void execute();
  }

  /** The is scheduled. */
  private boolean isScheduled;
  
  /** The timer. */
  private Timer timer;

  /**
   * Instantiates a new timer wrapper.
   */
  public TimerWrapper() {
    isScheduled = false;
  }

  /**
   * Cancel.
   */
  public void cancel() {
    isScheduled = false;
    timer.cancel();
  }

  /**
   * Configure.
   *
   * @param onTime the on time
   */
  public void configure(final Executer onTime) {
    timer = new Timer() {
      @Override
      public void run() {
        isScheduled = false;
        onTime.execute();
      }
    };
  }

  /**
   * Checks if is scheduled.
   *
   * @return true, if is scheduled
   */
  public boolean isScheduled() {
    return isScheduled;
  }

  /**
   * Run.
   */
  public void run() {
    timer.run();
  }

  /**
   * Schedule.
   *
   * @param delayMillis the delay millis
   */
  public void schedule(final int delayMillis) {
    isScheduled = true;
    timer.schedule(delayMillis);
  }

  /**
   * Schedule repeating.
   *
   * @param delayMillis the delay millis
   */
  public void scheduleRepeating(final int delayMillis) {
    isScheduled = true;
    timer.scheduleRepeating(delayMillis);
  }
}
