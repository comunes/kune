package cc.kune.gspace.client;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cc.kune.core.shared.FileConstants;
import cc.kune.gspace.client.viewers.EmbedHelper;

public class EmbedHelperTest {

  @Test
  public void testContentSeveralUrls() {
    final String expected = "something http://server.example.com" + FileConstants.LOGODOWNLOADSERVLET
        + " something ";
    final String content = "something " + FileConstants.LOGODOWNLOADSERVLET + " something ";
    assertEquals(expected + expected + expected,
        EmbedHelper.fixContentUrls("http://server.example.com/", content + content + content));
  }

  @Test
  public void testContentUrl() {
    assertEquals(
        "something http://server.example.com" + FileConstants.LOGODOWNLOADSERVLET + " something",
        EmbedHelper.fixContentUrls("http://server.example.com/", "something "
            + FileConstants.LOGODOWNLOADSERVLET + " something"));
  }

}
