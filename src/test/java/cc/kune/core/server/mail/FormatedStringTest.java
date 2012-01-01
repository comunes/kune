package cc.kune.core.server.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class FormatedStringTest {

  @Test
  public void testQuotes() {
    assertNotNull(FormatedString.build("'%s''''\"%s\"", "test", "test2kl").getString());
    assertEquals("test 100%", FormatedString.build("%s 100%%", "test").getString());
  }
}
