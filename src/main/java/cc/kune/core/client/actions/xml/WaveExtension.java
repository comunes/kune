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
package cc.kune.core.client.actions.xml;

// TODO: Auto-generated Javadoc
/**
 * The Class WaveExtension.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class WaveExtension {

  /**
   * The Class Builder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class Builder {

    /** The gadget url. */
    private String gadgetUrl;

    /** The icon css. */
    private String iconCss;

    /** The icon url. */
    private String iconUrl;

    /** The installer url. */
    private String installerUrl;

    /** The name. */
    private String name;

    /**
     * Instantiates a new builder.
     */
    public Builder() {
    }

    /**
     * Builds the.
     * 
     * @return the wave extension
     */
    public WaveExtension build() {
      return new WaveExtension(this);
    }

    /**
     * Gadget url.
     * 
     * @param gadgetUrl
     *          the gadget url
     * @return the builder
     */
    public Builder gadgetUrl(final String gadgetUrl) {
      this.gadgetUrl = gadgetUrl;
      return this;
    }

    /**
     * Icon css.
     * 
     * @param iconCss
     *          the icon css
     * @return the builder
     */
    public Builder iconCss(final String iconCss) {
      this.iconCss = iconCss;
      return this;
    }

    /**
     * Icon url.
     * 
     * @param iconUrl
     *          the icon url
     * @return the builder
     */
    public Builder iconUrl(final String iconUrl) {
      this.iconUrl = iconUrl;
      return this;
    }

    /**
     * Installer url.
     * 
     * @param installerUrl
     *          the installer url
     * @return the builder
     */
    public Builder installerUrl(final String installerUrl) {
      this.installerUrl = installerUrl;
      return this;
    }

    /**
     * Name.
     * 
     * @param name
     *          the name
     * @return the builder
     */
    public Builder name(final String name) {
      this.name = name;
      return this;
    }
  }

  /** The gadget url. */
  private final String gadgetUrl;

  /** The icon css. */
  private final String iconCss;

  /** The icon url. */
  private final String iconUrl;

  /** The installer url. */
  private final String installerUrl;

  /** The name. */
  private final String name;

  /**
   * Instantiates a new wave extension.
   * 
   * @param builder
   *          the builder
   */
  public WaveExtension(final Builder builder) {
    gadgetUrl = builder.gadgetUrl;
    iconCss = builder.iconCss;
    iconUrl = builder.iconUrl;
    installerUrl = builder.installerUrl;
    name = builder.name;
  }

  /**
   * Gets the gadget url.
   * 
   * @return the gadget url
   */
  public String getGadgetUrl() {
    return gadgetUrl;
  }

  /**
   * Gets the icon css.
   * 
   * @return the icon css
   */
  public String getIconCss() {
    return iconCss;
  }

  /**
   * Gets the icon url.
   * 
   * @return the icon url
   */
  public String getIconUrl() {
    return iconUrl;
  }

  /**
   * Gets the installer url.
   * 
   * @return the installer url
   */
  public String getInstallerUrl() {
    return installerUrl;
  }

  /**
   * Gets the name.
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }

}
