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
package cc.kune.core.server.content;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.waveprotocol.box.server.CoreSettings;

import cc.kune.core.client.actions.xml.XMLKuneClientActions;
import cc.kune.core.server.manager.file.FileDownloadManagerUtils;
import cc.kune.core.shared.actions.xml.XMLActionsConstants;

import com.calclab.emite.xtesting.ServicesTester;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

// TODO: Auto-generated Javadoc
/**
 * The Class XMLActionReader.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class XMLActionReader {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(XMLActionReader.class);

  /** The actions. */
  private XMLKuneClientActions actions;

  /**
   * Instantiates a new xML action reader.
   * 
   * @param resourceBases
   *          the resource bases
   */
  @Inject
  public XMLActionReader(@Named(CoreSettings.RESOURCE_BASES) final List<String> resourceBases) {
    try {
      final InputStream iStream = FileDownloadManagerUtils.getInputStreamInResourceBases(resourceBases,
          XMLActionsConstants.ACTIONS_XML_LOCATION_PATH_ABS);
      final String xml = FileDownloadManagerUtils.getInpuStreamAsString(iStream);
      actions = new XMLKuneClientActions(new ServicesTester(), xml);
    } catch (final IOException e) {
      LOG.error("Error reading extension actions", e);
    }
  }

  /**
   * Gets the actions.
   * 
   * @return the actions
   */
  public XMLKuneClientActions getActions() {
    return actions;
  }

}
