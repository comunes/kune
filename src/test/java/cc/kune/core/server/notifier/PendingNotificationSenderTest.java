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
package cc.kune.core.server.notifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.core.server.mail.FormatedString;

public class PendingNotificationSenderTest extends AbstractPendingNotificationTest {

  private static final FormatedString BODY = FormatedString.build("Some body");
  private static final FormatedString OTHER_BODY = FormatedString.build("Some other body");
  private static final FormatedString OTHER_SUBJECT = FormatedString.build("Some other subject");
  private static final FormatedString SUBJECT = FormatedString.build("Some subject");
  private PendingNotificationSender manager;
  private PendingNotificationProvider otherNotif;
  private NotificationSender sender;
  private PendingNotificationProvider someForcedNotif;
  private PendingNotificationProvider someNotif;
  private PendingNotificationProvider someSimilarNotif;

  private void assertQueues(final int i, final int j, final int k) {
    assertEquals(i, manager.getImmediateCount());
    assertEquals(j, manager.getHourlyCount());
    assertEquals(k, manager.getDailyCount());
  }

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

  @Test
  public void compareNotif() {
    assertEquals(someNotif.get(), someNotif.get());
    assertEquals(someSimilarNotif.get(), someNotif.get());
    assertFalse(otherNotif.get().equals(someNotif.get()));

  }

  @Test
  public void testForcePendingNotification() {
    manager.add(someForcedNotif);
    assertQueues(1, 0, 0);
    manager.sendImmediateNotifications();
    assertQueues(0, 0, 0);
  }

  @Test
  public void testMixtureOfNotifications() {
    manager.add(someNotif);
    manager.add(someForcedNotif);
    assertQueues(2, 0, 0);
    manager.sendImmediateNotifications();
    assertQueues(0, 1, 0);
  }

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
