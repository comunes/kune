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
package cc.kune.selenium;

public final class SeleniumConf {

  public enum Driver {
    chrome, firefox
  }

  public enum Lang {
    en, es
  }

  public enum Site {
    demo("kune.beta.iepala.es"), eurosur("beta.eurosur.org"), kunecc("kune.cc"), localhost(
        "localhost:8888", "&log_level=INFO&gwt.codesvr=127.0.0.1:9997");

    private String domain;
    private String params;

    Site(final String domain) {
      this(domain, "");
    }

    Site(final String domain, final String params) {
      this.domain = domain;
      this.params = "?locale=" + SeleniumConf.LANG + params;
    }

    public String getDomain() {
      return domain;
    }

    public String getParams() {
      return params;
    }
  }

  public static final Driver DRIVER = Driver.firefox;
  /* Configure this for use other lang, site, or driver */
  public static final Lang LANG = Lang.es;
  public static final Site SITE = Site.demo;
  public static final int TIMEOUT = 25;

  SeleniumConf() {
    // Final class
  }
}
