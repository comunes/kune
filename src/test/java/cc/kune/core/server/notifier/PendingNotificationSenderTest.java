package cc.kune.core.server.notifier;

import static org.junit.Assert.assertEquals;

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

  private void assertQueues(final int i, final int j, final int k) {
    // TODO Auto-generated method stub
    assertEquals(manager.getImmediateCount(), i);
    assertEquals(manager.getHourlyCount(), j);
    assertEquals(manager.getDailyCount(), k);
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
