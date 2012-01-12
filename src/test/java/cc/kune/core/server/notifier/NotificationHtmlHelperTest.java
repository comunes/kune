package cc.kune.core.server.notifier;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.core.server.properties.KunePropertiesDefault;
import cc.kune.core.server.utils.AbsoluteFileDownloadUtils;

public class NotificationHtmlHelperTest {
  private static final String GROUP_NAME = "groupShortName";
  private static final String MESSAGE = "some message";

  NotificationHtmlHelper helper;

  @Test
  public void basicTest() {
    assertNotNull(helper.groupNotification(GROUP_NAME, false, MESSAGE).getString());
    assertNotNull(helper.groupNotification(GROUP_NAME, true, MESSAGE).getString());
  }

  @Before
  public void before() {
    helper = new NotificationHtmlHelper(new AbsoluteFileDownloadUtils(new KuneBasicProperties(
        new KunePropertiesDefault("kune.properties"))));
  }

}
