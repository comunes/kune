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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.content.XMLActionReader;

public class XMLKuneClientActionsTest {

  private XMLKuneClientActions actions;
  private Map<String, XMLWaveExtension> extensions;
  private List<XMLGuiActionDescriptor> guiActionDescriptors;

  @Before
  public void before() throws IOException {
    actions = new XMLActionReader().getActions();
    extensions = actions.getExtensions();
    guiActionDescriptors = actions.getGuiActionDescriptors();
  }

  @Test
  public void testExtensions() {
    assertTrue(extensions.size() > 0);
    for (final XMLWaveExtension extension : extensions.values()) {
      assertTrue(extension.getExtName().length() > 0);
      assertTrue(extension.getGadgetUrl().length() > 0);
      assertTrue(extension.getIconUrl().length() > 0 || extension.getIconCss().length() > 0);
    }
  }

  @Test
  public void testGuiActions() {
    assertTrue(guiActionDescriptors.size() > 0);
    for (final XMLGuiActionDescriptor descrip : guiActionDescriptors) {
      assertTrue(descrip.getDescName().length() > 0);
      assertTrue(descrip.getType().length() > 0);
      final String extensionName = descrip.getExtensionName();
      assertTrue(extensionName.length() > 0);
      assertNotNull(extensions.get(extensionName));
      assertTrue(descrip.getPath().length() >= 0);
      assertTrue(descrip.isEnabled() || !descrip.isEnabled());
      final List<XMLTypeId> typeIds = descrip.getTypeIds();
      assertTrue(typeIds.size() > 0);
      for (final XMLTypeId typeId : typeIds) {
        assertTrue(typeId.getOrigTypeId().length() > 0);
        assertTrue(typeId.getDestTypeId().length() > 0);
      }
      final XMLRol rol = descrip.getRol();
      assertNotNull(rol);
      assertTrue(rol.isAuthNeed() || !rol.isAuthNeed());
      assertTrue(rol.getRolRequired() != null);
    }
  }
}
