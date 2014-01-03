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
package cc.kune.core.server.notifier;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.core.server.utils.FormattedString;

// TODO: Auto-generated Javadoc
/**
 * The Class PendingNotificationSenderTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class PendingNotificationSenderTest extends AbstractPendingNotificationTest {

  /** The Constant BODY. */
  private static final FormattedString BODY = FormattedString.build("Some body");

  /** The Constant OTHER_BODY. */
  private static final FormattedString OTHER_BODY = FormattedString.build("Some other body");

  /** The Constant OTHER_SUBJECT. */
  private static final FormattedString OTHER_SUBJECT = FormattedString.build("Some other subject");

  /** The Constant SUBJECT. */
  private static final FormattedString SUBJECT = FormattedString.build("Some subject");

  /** The manager. */
  private PendingNotificationSender manager;

  /** The other notif. */
  private PendingNotificationProvider otherNotif;

  /** The sender. */
  private NotificationSender sender;

  /** The some forced notif. */
  private PendingNotificationProvider someForcedNotif;

  /** The some notif. */
  private PendingNotificationProvider someNotif;

  /** The some similar notif. */
  private PendingNotificationProvider someSimilarNotif;

  /**
   * Assert queues.
   * 
   * @param i
   *          the i
   * @param j
   *          the j
   * @param k
   *          the k
   */
  private void assertQueues(final int i, final int j, final int k) {
    assertEquals(i, manager.getImmediateCount());
    assertEquals(j, manager.getHourlyCount());
    assertEquals(k, manager.getDailyCount());
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.notifier.AbstractPendingNotificationTest#before()
   */
  @Override
  @Before
  public void before() {
    sender = Mockito.mock(NotificationSender.class);
    manager = new PendingNotificationSender(sender);
    otherNotif = new PendingNotificationProvider() {
      @Override
      public PendingNotification get() {
        return new PendingNotification(NotificationType.email, OTHER_SUBJECT, OTHER_BODY, false, false,
            someUserProvider);
      }
    };
    someForcedNotif = new PendingNotificationProvider() {
      @Override
      public PendingNotification get() {
        return new PendingNotification(NotificationType.email, SUBJECT, BODY, false, true,
            someUserProvider);
      }
    };

    someNotif = new PendingNotificationProvider() {
      @Override
      public PendingNotification get() {
        return new PendingNotification(NotificationType.email, SUBJECT, BODY, false, false,
            someUserProvider);
      }
    };

    someSimilarNotif = new PendingNotificationProvider() {
      @Override
      public PendingNotification get() {
        return new PendingNotification(NotificationType.email, SUBJECT, BODY, false, false,
            someUserProvider);
      }
    };
  }

  /**
   * Compare notif.
   */
  @Test
  public void compareNotif() {
    assertEquals(someNotif.get(), someNotif.get());
    assertEquals(someSimilarNotif.get(), someNotif.get());
    assertFalse(otherNotif.get().equals(someNotif.get()));

  }

  /**
   * Test force pending notification.
   */
  @Test
  public void testForcePendingNotification() {
    manager.add(someForcedNotif);
    assertQueues(1, 0, 0);
    manager.sendImmediateNotifications();
    assertQueues(0, 0, 0);
  }

  /**
   * Test mixture of notifications.
   */
  @Test
  public void testMixtureOfNotifications() {
    manager.add(someNotif);
    manager.add(someForcedNotif);
    assertQueues(2, 0, 0);
    manager.sendImmediateNotifications();
    assertQueues(0, 1, 0);
  }

  /**
   * Test simple pending notification.
   */
  @Test
  public void testSimplePendingNotification() {
    manager.add(someNotif);
    // we add two times (imagine two editions)
    manager.add(someNotif);
    manager.add(otherNotif);
    assertQueues(2, 0, 0);
    manager.sendImmediateNotifications();
    assertQueues(0, 2, 0);
    // we repeat some notification (for instance an edit) and should be ignored
    manager.add(someNotif);
    assertQueues(0, 2, 0);
    manager.sendHourlyNotifications();
    assertQueues(0, 0, 2);
    // we repeat some notification (for instance an edit) and should be ignored
    manager.add(someNotif);
    assertQueues(1, 0, 2);
    manager.sendImmediateNotifications();
    assertQueues(0, 1, 2);
    manager.sendHourlyNotifications();
    assertQueues(0, 0, 2);
    manager.sendDailyNotifications();
    assertQueues(0, 0, 0);
  }

}
