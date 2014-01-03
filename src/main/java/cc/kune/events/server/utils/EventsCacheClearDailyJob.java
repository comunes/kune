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

package cc.kune.events.server.utils;

import java.text.ParseException;

import org.quartz.SchedulerException;

import cc.kune.core.server.persist.ClearCacheDailyJob;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class EventsCacheClearDailyJob.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class EventsCacheClearDailyJob extends ClearCacheDailyJob {

  /**
   * Instantiates a new events cache clear daily job.
   * 
   * @param cache
   *          the cache
   * @throws ParseException
   *           the parse exception
   * @throws SchedulerException
   *           the scheduler exception
   */
  @Inject
  public EventsCacheClearDailyJob(final EventsCache cache) throws ParseException, SchedulerException {
    super(cache);
  }

}
