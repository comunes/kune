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

import com.calclab.emite.core.client.packet.DelegatedPacket;
import com.calclab.emite.core.client.packet.IPacket;

// TODO: Auto-generated Javadoc
/**
 * The Class XMLWaveExtension.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class XMLWaveExtension extends DelegatedPacket {

  /**
   * Instantiates a new xML wave extension.
   * 
   * @param xml
   *          the xml
   */
  public XMLWaveExtension(final IPacket xml) {
    super(xml);
  }

  /**
   * Gets the ext name.
   * 
   * @return the ext name
   */
  public String getExtName() {
    return getFirstChild("name").getText();
  }

  /**
   * Gets the gadget url.
   * 
   * @return the gadget url
   */
  public String getGadgetUrl() {
    return getFirstChild("gadgetUrl").getText().trim();
  }

  /**
   * Gets the icon css.
   * 
   * @return the icon css
   */
  public String getIconCss() {
    return getFirstChild("iconCss").getText().trim();
  }

  /**
   * Gets the icon url.
   * 
   * @return the icon url
   */
  public String getIconUrl() {
    return getFirstChild("iconUrl").getText().trim();
  }

  /**
   * Gets the installer url.
   * 
   * @return the installer url
   */
  public String getInstallerUrl() {
    return getFirstChild("installerUrl").getText().trim();
  }

}
