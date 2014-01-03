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

/**
 *
 \*/
package cc.kune.core.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import cc.kune.core.server.persist.KunePersistenceService;
import cc.kune.core.server.rack.ContainerListener;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving kuneContainer events. The class that is
 * interested in processing a kuneContainer event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addKuneContainerListener<code> method. When
 * the kuneContainer event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see ContainerListener
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class KuneContainerListener implements ContainerListener {

  /** The logger. */
  private final Logger logger; // NOPMD by vjrj on 16/07/09 22:38

  /** The persistence service. */
  private final KunePersistenceService persistenceService;

  /**
   * Instantiates a new kune container listener.
   * 
   * @param persistenceService
   *          the persistence service
   * @param logger
   *          the logger
   */
  @Inject
  public KuneContainerListener(final KunePersistenceService persistenceService, final Logger logger) {
    this.persistenceService = persistenceService;
    this.logger = logger;
  }

  /**
   * Gets the logger.
   * 
   * @return the logger
   */
  public Logger getLogger() {
    return logger;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.rack.ContainerListener#start()
   */
  @Override
  public void start() {
    logger.log(Level.INFO, "Kune persistence starting");
    persistenceService.start();
    logger.log(Level.INFO, "Kune persistence started");
    logger.log(Level.INFO, "Kune server started");
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.rack.ContainerListener#stop()
   */
  @Override
  public void stop() {
    logger.log(Level.INFO, "Stopping Kune...");
    logger.log(Level.INFO, "Kune server stopped");
  }
}
