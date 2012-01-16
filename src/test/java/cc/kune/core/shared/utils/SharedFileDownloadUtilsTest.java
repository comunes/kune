package cc.kune.core.shared.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class SharedFileDownloadUtilsTest {

  private static final String GROUP = "groupname";
  private SharedFileDownloadUtils noPrefixUtils;
  private SharedFileDownloadUtils prefixUtils;

  @Before
  public void before() {
    prefixUtils = new SharedFileDownloadUtils("http://example.org");
    noPrefixUtils = new SharedFileDownloadUtils("");
  }

  @Test
  public void testGetLogoHtml() {
    assertTrue(prefixUtils.getLogoAvatarHtml(GROUP, false, false, 50, 5).contains(
        "'http://example.org/others/defgroup.gif"));
    assertTrue(noPrefixUtils.getLogoAvatarHtml(GROUP, false, true, 50, 5),
        prefixUtils.getLogoAvatarHtml(GROUP, false, true, 50, 5).contains("/others/unknown.jpg"));
    assertTrue(
        prefixUtils.getLogoAvatarHtml(GROUP, true, false, 50, 5),
        prefixUtils.getLogoAvatarHtml(GROUP, true, false, 50, 5).contains(
            "'http://example.org/ws/servlets/EntityLogoDownloadManager?token=groupname"));
    assertTrue(
        noPrefixUtils.getLogoAvatarHtml(GROUP, true, true, 50, 5),
        noPrefixUtils.getLogoAvatarHtml(GROUP, true, true, 50, 5).contains(
            "/ws/servlets/EntityLogoDownloadManager?token=groupname"));
    assertTrue(!noPrefixUtils.getLogoAvatarHtml(GROUP, true, true, 50, 5).contains("http"));
  }

  @Test
  public void testUserAvatar() {
    assertEquals("http://example.org/ws/servlets/UserLogoDownloadManager?username=groupname",
        prefixUtils.getUserAvatar(GROUP));
    assertEquals("/ws/servlets/UserLogoDownloadManager?username=groupname",
        noPrefixUtils.getUserAvatar(GROUP));
  }

}
