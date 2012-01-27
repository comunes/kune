package cc.kune.core.server.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

public class FormatedStringTest {

  @Test
  public void testCopy() {
    final FormatedString orig = new FormatedString(true, "Template", "arg1", "arg2", "arg3");
    final FormatedString copy = orig.copy();
    copy.setTemplate("Other");
    assertNotSame(orig, copy);
    assertNotSame(orig.getTemplate(), copy.getTemplate());
  }

  @Test
  public void testQuotes() {
    assertNotNull(FormatedString.build("'%s''''\"%s\"", "test", "test2kl").getString());
    assertEquals("test 100%", FormatedString.build("%s 100%%", "test").getString());
  }
}
