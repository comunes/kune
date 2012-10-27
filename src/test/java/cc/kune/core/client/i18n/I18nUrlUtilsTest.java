/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
