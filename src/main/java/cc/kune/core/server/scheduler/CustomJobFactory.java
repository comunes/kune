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
package cc.kune.core.server.scheduler;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

import com.google.inject.Injector;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating CustomJob objects. See:
 * http://javaeenotes.blogspot.com
 * /2011/09/inject-instances-in-quartz-jobs-with.html
 */
@Singleton
public class CustomJobFactory implements JobFactory {

  /** The injector. */
  private Injector injector;

  /*
   * (non-Javadoc)
   * 
   * @see org.quartz.spi.JobFactory#newJob(org.quartz.spi.TriggerFiredBundle,
   * org.quartz.Scheduler)
   */
  @Override
  public Job newJob(final TriggerFiredBundle bundle, final Scheduler scheduler)
      throws SchedulerException {
    return injector.getInstance(bundle.getJobDetail().getJobClass());
  }

  /**
   * Sets the injector.
   * 
   * @param injector
   *          the new injector
   */
  public void setInjector(final Injector injector) {
    this.injector = injector;
  }
}
