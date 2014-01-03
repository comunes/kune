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

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.content.XMLActionReader;

// TODO: Auto-generated Javadoc
/**
 * The Class XMLKuneClientActionsTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class XMLKuneClientActionsTest {

  /** The actions. */
  private XMLKuneClientActions actions;

  /** The extensions. */
  private Map<String, XMLWaveExtension> extensions;

  /** The gui action descriptors. */
  private List<XMLGuiActionDescriptor> guiActionDescriptors;

  /**
   * Before.
   * 
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Before
  public void before() throws IOException {
    actions = new XMLActionReader(java.util.Arrays.asList("src/main/webapp/")).getActions();
    extensions = actions.getExtensions();
    guiActionDescriptors = actions.getGuiActionDescriptors();
  }

  /**
   * Test extensions.
   */
  @Test
  public void testExtensions() {
    assertTrue(extensions.size() > 0);
    for (final XMLWaveExtension extension : extensions.values()) {
      assertTrue(extension.getExtName().length() > 0);
      assertTrue(extension.getGadgetUrl().length() > 0);
      assertTrue(extension.getIconUrl().length() > 0 || extension.getIconCss().length() > 0);
    }
  }

  /**
   * Test gui actions.
   */
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
