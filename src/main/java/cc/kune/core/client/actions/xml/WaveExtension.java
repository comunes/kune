/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

public class WaveExtension {
  public static class Builder {
    private String gadgetUrl;
    private String iconCss;
    private String iconUrl;
    private String installerUrl;
    private String name;

    public Builder() {
    }

    public WaveExtension build() {
      return new WaveExtension(this);
    }

    public Builder gadgetUrl(final String gadgetUrl) {
      this.gadgetUrl = gadgetUrl;
      return this;
    }

    public Builder iconCss(final String iconCss) {
      this.iconCss = iconCss;
      return this;
    }

    public Builder iconUrl(final String iconUrl) {
      this.iconUrl = iconUrl;
      return this;
    }

    public Builder installerUrl(final String installerUrl) {
      this.installerUrl = installerUrl;
      return this;
    }

    public Builder name(final String name) {
      this.name = name;
      return this;
    }
  }

  private final String gadgetUrl;
  private final String iconCss;
  private final String iconUrl;
  private final String installerUrl;
  private final String name;

  public WaveExtension(final Builder builder) {
    gadgetUrl = builder.gadgetUrl;
    iconCss = builder.iconCss;
    iconUrl = builder.iconUrl;
    installerUrl = builder.installerUrl;
    name = builder.name;
  }

  public String getGadgetUrl() {
    return gadgetUrl;
  }

  public String getIconCss() {
    return iconCss;
  }

  public String getIconUrl() {
    return iconUrl;
  }

  public String getInstallerUrl() {
    return installerUrl;
  }

  public String getName() {
    return name;
  }

}
