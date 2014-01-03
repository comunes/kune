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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import cc.kune.common.client.utils.TimerWrapper;

// TODO: Auto-generated Javadoc
/**
 * The Class TooltipTimersTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TooltipTimersTest {

  /** The hide timer. */
  private TimerWrapper hideTimer;

  /** The hide timer scheduled. */
  protected boolean hideTimerScheduled = false;

  /** The security timer. */
  private TimerWrapper securityTimer;

  /** The show timer. */
  private TimerWrapper showTimer;

  /** The show timer scheduled. */
  protected boolean showTimerScheduled = false;

  /** The timers. */
  private TooltipTimers timers;

  /**
   * Before.
   */
  @Before
  public void before() {
    showTimer = Mockito.mock(TimerWrapper.class);
    hideTimer = Mockito.mock(TimerWrapper.class);
    securityTimer = Mockito.mock(TimerWrapper.class);
    timers = new TooltipTimers(showTimer, hideTimer, securityTimer);
    Mockito.doAnswer(new Answer<Object>() {
      @Override
      public Object answer(final InvocationOnMock invocation) throws Throwable {
        showTimerScheduled = true;
        return null;
      }
    }).when(showTimer).schedule(Mockito.anyInt());
    Mockito.doAnswer(new Answer<Object>() {

      @Override
      public Object answer(final InvocationOnMock invocation) throws Throwable {
        return showTimerScheduled;
      }
    }).when(showTimer).isScheduled();
    Mockito.doAnswer(new Answer<Object>() {

      @Override
      public Object answer(final InvocationOnMock invocation) throws Throwable {
        showTimerScheduled = false;
        return null;
      }
    }).when(showTimer).cancel();
    Mockito.doAnswer(new Answer<Object>() {
      @Override
      public Object answer(final InvocationOnMock invocation) throws Throwable {
        hideTimerScheduled = true;
        return null;
      }
    }).when(hideTimer).schedule(Mockito.anyInt());
    Mockito.doAnswer(new Answer<Object>() {

      @Override
      public Object answer(final InvocationOnMock invocation) throws Throwable {
        return hideTimerScheduled;
      }
    }).when(hideTimer).isScheduled();
    Mockito.doAnswer(new Answer<Object>() {

      @Override
      public Object answer(final InvocationOnMock invocation) throws Throwable {
        hideTimerScheduled = false;
        return null;
      }
    }).when(hideTimer).cancel();
  }

  /**
   * Test several over and outs only one timer each.
   */
  @Test
  public void testSeveralOverAndOutsOnlyOneTimerEach() {
    timers.onOver();
    timers.onOver();
    timers.onOver();
    timers.onOut();
    timers.onOut();
    Mockito.verify(showTimer, Mockito.times(1)).schedule(Mockito.anyInt());
    Mockito.verify(showTimer, Mockito.times(1)).cancel();
    Mockito.verify(hideTimer, Mockito.times(1)).schedule(Mockito.anyInt());
  }

  /**
   * Test several over only one timer.
   */
  @Test
  public void testSeveralOverOnlyOneTimer() {
    timers.onOver();
    timers.onOver();
    Mockito.verify(showTimer, Mockito.times(1)).schedule(Mockito.anyInt());
    Mockito.verify(hideTimer, Mockito.times(0)).cancel();
  }

  /**
   * Test several over outs and over only one timer each.
   */
  @Test
  public void testSeveralOverOutsAndOverOnlyOneTimerEach() {
    timers.onOver();
    timers.onOver();
    timers.onOver();
    timers.onOut();
    timers.onOut();
    timers.onOver();
    timers.onOver();
    timers.onOver();
    Mockito.verify(showTimer, Mockito.times(2)).schedule(Mockito.anyInt());
    Mockito.verify(showTimer, Mockito.times(1)).cancel();
    Mockito.verify(hideTimer, Mockito.times(1)).schedule(Mockito.anyInt());
    Mockito.verify(hideTimer, Mockito.times(1)).cancel();
  }
}
