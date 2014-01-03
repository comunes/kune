/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

// TODO: Auto-generated Javadoc
/**
 * The Class SeleniumConf.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public final class SeleniumConf {

  /**
   * The Enum Driver.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public enum Driver {

    /** The chrome. */
    chrome,
    /** The firefox. */
    firefox
  }

  /**
   * The Enum Lang.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public enum Lang {

    /** The en. */
    en,
    /** The es. */
    es
  }

  /**
   * The Enum Site.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public enum Site {

    /** The demo. */
    demo("kune.beta.iepala.es"),
    /** The kunecc. */
    kunecc("kune.cc"),
    /** The localhost. */
    localhost("localhost:8888", "&log_level=INFO&gwt.codesvr=127.0.0.1:9997"),
    /** The socialglobal. */
    socialglobal("social.gloobal.net");

    /** The domain. */
    private String domain;

    /** The params. */
    private String params;

    /**
     * Instantiates a new site.
     * 
     * @param domain
     *          the domain
     */
    Site(final String domain) {
      this(domain, "");
    }

    /**
     * Instantiates a new site.
     * 
     * @param domain
     *          the domain
     * @param params
     *          the params
     */
    Site(final String domain, final String params) {
      this.domain = domain;
      this.params = "?locale=" + SeleniumConf.LANG + params;
    }

    /**
     * Gets the domain.
     * 
     * @return the domain
     */
    public String getDomain() {
      return domain;
    }

    /**
     * Gets the params.
     * 
     * @return the params
     */
    public String getParams() {
      return params;
    }
  }

  /** The Constant DRIVER. */
  public static final Driver DRIVER = Driver.firefox;
  /* Configure this for use other lang, site, or driver */
  /** The Constant LANG. */
  public static final Lang LANG = Lang.es;

  /** The Constant SITE. */
  public static final Site SITE = Site.socialglobal;

  /** The Constant TIMEOUT. */
  public static final int TIMEOUT = 25;

  /**
   * Instantiates a new selenium conf.
   */
  SeleniumConf() {
    // Final class
  }
}
