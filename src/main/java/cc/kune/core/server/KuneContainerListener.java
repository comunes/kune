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

@Singleton
public class KuneContainerListener implements ContainerListener {
  private final Logger logger; // NOPMD by vjrj on 16/07/09 22:38
  private final KunePersistenceService persistenceService;

  @Inject
  public KuneContainerListener(final KunePersistenceService persistenceService, final Logger logger) {
    this.persistenceService = persistenceService;
    this.logger = logger;
  }

  public Logger getLogger() {
    return logger;
  }

  @Override
  public void start() {
    logger.log(Level.INFO, "Kune persistence starting");
    persistenceService.start();
    logger.log(Level.INFO, "Kune persistence started");
    logger.log(Level.INFO, "Kune server started");
  }

  @Override
  public void stop() {
    logger.log(Level.INFO, "Stopping Kune...");
    logger.log(Level.INFO, "Kune server stopped");
  }
}