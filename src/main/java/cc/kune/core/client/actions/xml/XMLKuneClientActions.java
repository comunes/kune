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
package cc.kune.core.client.actions.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.calclab.emite.core.client.packet.DelegatedPacket;
import com.calclab.emite.core.client.packet.IPacket;
import com.calclab.emite.core.client.services.Services;

public class XMLKuneClientActions extends DelegatedPacket {

  private Map<String, XMLWaveExtension> extensions;
  private List<XMLGuiActionDescriptor> guiActionDescriptors;

  public XMLKuneClientActions(final Services services, final String xmlS) {
    super(services.toXML(xmlS));
  }

  public Map<String, XMLWaveExtension> getExtensions() {
    if (extensions == null) {
      extensions = new HashMap<String, XMLWaveExtension>();
      for (final IPacket x : getFirstChild("waveExtensions").getChildren()) {
        final XMLWaveExtension extension = new XMLWaveExtension(x);
        extensions.put(extension.getExtName(), extension);
      }
    }
    return extensions;
  }

  public List<XMLGuiActionDescriptor> getGuiActionDescriptors() {
    if (guiActionDescriptors == null) {
      guiActionDescriptors = new ArrayList<XMLGuiActionDescriptor>();
      for (final IPacket x : getFirstChild("guiActionDescriptors").getChildren()) {
        guiActionDescriptors.add(new XMLGuiActionDescriptor(x));
      }
    }
    return guiActionDescriptors;
  }
}
