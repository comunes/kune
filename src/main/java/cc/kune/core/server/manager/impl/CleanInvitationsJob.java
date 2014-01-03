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
package cc.kune.core.server.manager.impl;

import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import cc.kune.core.server.manager.InvitationManager;
import cc.kune.core.server.persist.KuneTransactional;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class CleanInvitationsJob.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class CleanInvitationsJob implements Job {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(CleanInvitationsJob.class);

  /** The invitation manager. */
  private final InvitationManager invitationManager;

  /**
   * Instantiates a new clean invitations job.
   * 
   * @param invitationManager
   *          the invitation manager
   * @throws ParseException
   *           the parse exception
   * @throws SchedulerException
   *           the scheduler exception
   */
  @Inject
  public CleanInvitationsJob(final InvitationManager invitationManager) throws ParseException,
      SchedulerException {
    this.invitationManager = invitationManager;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
   */
  @Override
  @KuneTransactional
  public void execute(final JobExecutionContext context) throws JobExecutionException {
    LOG.info(String.format("Weekly invitations clear cron job start"));
    invitationManager.deleteOlderInvitations();
    LOG.info(String.format("Weekly invitations clear cron job end"));
  }

}
