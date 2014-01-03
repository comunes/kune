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

import cc.kune.core.shared.dto.AccessRolDTO;

import com.calclab.emite.core.client.packet.DelegatedPacket;
import com.calclab.emite.core.client.packet.IPacket;

// TODO: Auto-generated Javadoc
/**
 * The Class XMLRol.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class XMLRol extends DelegatedPacket {

  /**
   * Instantiates a new xML rol.
   * 
   * @param delegate
   *          the delegate
   */
  public XMLRol(final IPacket delegate) {
    super(delegate);
  }

  /**
   * Gets the rol required.
   * 
   * @return the rol required
   */
  public AccessRolDTO getRolRequired() {
    return AccessRolDTO.valueOf(getFirstChild("rolRequired").getText());
  }

  /**
   * Checks if is auth need.
   * 
   * @return true, if is auth need
   */
  public boolean isAuthNeed() {
    return Boolean.valueOf(getFirstChild("authNeed").getText());
  }
}
