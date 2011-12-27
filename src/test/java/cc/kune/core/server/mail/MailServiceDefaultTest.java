package cc.kune.core.server.mail;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.mail.MailServiceDefault.FormatedString;
import cc.kune.core.server.properties.KunePropertiesDefault;

public class MailServiceDefaultTest {

  private MailServiceDefault service;

  @Test(expected = NullPointerException.class)
  public void basicException() {
    assertEquals(null, FormatedString.build(null).getString());
  }

  @Test(expected = NullPointerException.class)
  public void basicExceptionWithArgs() {
    assertEquals(null, FormatedString.build(null).getString());
    assertEquals(null, FormatedString.build(null, "arg").getString());
  }

  @Test
  public void basicFormat() {
    assertEquals("basic", FormatedString.build("basic").getString());
    assertEquals("basic arg", FormatedString.build("basic %s", "arg").getString());
    assertEquals("basic %s", FormatedString.build("basic %s", null).getString());
  }

  @Before
  public void before() {
    service = new MailServiceDefault(new KunePropertiesDefault("kune.properties"));
  }

  @Test
  public void simpleTest() {
    service.sendPlain(FormatedString.build("Some test subject"), FormatedString.build("Some body"),
        "vjrj@localhost");
  }
}
