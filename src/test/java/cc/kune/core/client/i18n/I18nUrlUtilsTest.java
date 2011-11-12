package cc.kune.core.client.i18n;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class I18nUrlUtilsTest {

  @Test
  public void simpleLangChange() {
    assertEquals("?locale=eu", I18nUrlUtils.changeLang("?locale=es", "eu"));
    assertEquals("?locale=eu#", I18nUrlUtils.changeLang("?locale=es#", "eu"));
    assertEquals("?locale=eu#hash", I18nUrlUtils.changeLang("?locale=es#hash", "eu"));
    assertEquals("?locale=eu&some=value&someother=someothervalue#hash",
        I18nUrlUtils.changeLang("?locale=es&some=value&someother=someothervalue#hash", "eu"));
    assertEquals("?locale=eu&some=value&someother=someothervalue#",
        I18nUrlUtils.changeLang("?locale=es&some=value&someother=someothervalue#", "eu"));
    assertEquals("?locale=eu&log_level=INFO&gwt.codesvr=127.0.0.1:9997#inbox",
        I18nUrlUtils.changeLang("?locale=pt-BR&log_level=INFO&gwt.codesvr=127.0.0.1:9997#inbox", "eu"));
    assertEquals("?locale=eu&log_level=INFO&gwt.codesvr=127.0.0.1:9997#admin",
        I18nUrlUtils.changeLang("?locale=es&log_level=INFO&gwt.codesvr=127.0.0.1:9997#admin", "eu"));
    assertEquals("?locale=eu", I18nUrlUtils.changeLang("", "eu"));
    assertEquals("?locale=eu#", I18nUrlUtils.changeLang("#", "eu"));
    assertEquals("?locale=eu#hash", I18nUrlUtils.changeLang("#hash", "eu"));
    assertEquals("?locale=eu&some=value&someother=someothervalue#hash",
        I18nUrlUtils.changeLang("?some=value&someother=someothervalue#hash", "eu"));
    assertEquals("?locale=eu&some=value&someother=someothervalue#",
        I18nUrlUtils.changeLang("?some=value&someother=someothervalue#", "eu"));
    assertEquals("?locale=eu&log_level=INFO&gwt.codesvr=127.0.0.1:9997#inbox",
        I18nUrlUtils.changeLang("?locale=pt-BR&log_level=INFO&gwt.codesvr=127.0.0.1:9997#inbox", "eu"));
    assertEquals("?locale=eu&log_level=INFO&gwt.codesvr=127.0.0.1:9997#admin",
        I18nUrlUtils.changeLang("?log_level=INFO&gwt.codesvr=127.0.0.1:9997#admin", "eu"));
    assertEquals("?locale=es&log_level=INFO&gwt.codesvr=127.0.0.1:9997#signin(admin)",
        I18nUrlUtils.changeLang("?locale=ar&log_level=INFO&gwt.codesvr=127.0.0.1:9997"
            + "#signin(admin)", "es"));
  }

}
