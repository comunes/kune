/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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

package cc.kune.core.server.manager.impl;

import org.hibernate.search.batchindexing.MassIndexerProgressMonitor;
import org.apache.commons.logging.Log;

public class DefaultMassIndexerProgressMonitor implements MassIndexerProgressMonitor {

  Log log;

  public DefaultMassIndexerProgressMonitor(Log log) {
    this.log = log;
  }

  @Override
  public void documentsAdded(long increment) {
    log.debug("MassIndexer: Entities loaded " + increment);
  }

  @Override
  public void indexingCompleted() {
    log.debug("MassIndexer: Indexing completed");
  }

  @Override
  public void entitiesLoaded(int size) {              //
    log.debug("MassIndexer: Entities loaded " + size);
  }

  @Override
  public void documentsBuilt(int number) {

    log.debug("MassIndexer: Documents build " + number);
  }

  @Override
  public void addToTotalCount(long count) {
    log.debug("MassIndexer: Add to total count " + count);
  }
}
