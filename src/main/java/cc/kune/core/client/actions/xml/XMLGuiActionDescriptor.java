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
package cc.kune.core.client.actions.xml;

import java.util.ArrayList;
import java.util.List;

import com.calclab.emite.core.client.packet.DelegatedPacket;
import com.calclab.emite.core.client.packet.IPacket;

public class XMLGuiActionDescriptor extends DelegatedPacket {

  private XMLRol rol;

  private List<XMLTypeId> typeIds;

  public XMLGuiActionDescriptor(final IPacket xml) {
    super(xml);
  }

  public String getDescName() {
    return getFirstChild("name").getText();
  }

  public String getDescription() {
    return getFirstChild("description").getText();
  }

  public String getExtensionName() {
    return getFirstChild("extensionName").getText();
  }

  public String getNewContentTextIntro() {
    return getFirstChild("new-content-textintro").getText();
  }

  public String getNewContentTitle() {
    return getFirstChild("new-content-title").getText();
  }

  public String getParticipants() {
    return getFirstChild("participants").getText();
  }

  public String getPath() {
    return getFirstChild("path").getText();
  }

  public XMLRol getRol() {
    if (rol == null) {
      rol = new XMLRol(getFirstChild("rol"));
    }
    return rol;
  }

  public String getType() {
    return getFirstChild("type").getText();
  }

  public List<XMLTypeId> getTypeIds() {
    if (typeIds == null) {
      typeIds = new ArrayList<XMLTypeId>();
      for (final IPacket p : getFirstChild("typeIds").getChildren()) {
        typeIds.add(new XMLTypeId(p));
      }
    }
    return typeIds;
  }

  public boolean isEnabled() {
    return Boolean.valueOf(getFirstChild("enabled").getText());
  }
}
