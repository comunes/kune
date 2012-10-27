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
package cc.kune.core.server.scheduler;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import cc.kune.core.server.manager.impl.SocialNetworkCacheClearDailyJob;
import cc.kune.core.server.notifier.ClearUpdatedWavesHourlyJob;
import cc.kune.core.server.notifier.PendingNotificationDailyJob;
import cc.kune.core.server.notifier.PendingNotificationHourlyJob;
import cc.kune.core.server.notifier.PendingNotificationImmediateJob;
import cc.kune.core.server.rack.ContainerListener;
import cc.kune.events.server.utils.EventsCacheClearDailyJob;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CronServerTasksManager implements ContainerListener {
  private static final String DEF_GROUP = "groupdef";
  public static final Log LOG = LogFactory.getLog(CronServerTasksManager.class);
  private final Scheduler sched;

  @Inject
  public CronServerTasksManager(final StdSchedulerFactory sf, final CustomJobFactory jobFactory)
      throws SchedulerException {
    sched = sf.getScheduler();
    sched.setJobFactory(jobFactory);
  }

  private void logError(final Exception e) {
    LOG.error("Error starting cron scheduler", e);
  }

  public Date scheduleJob(final Class<? extends Job> jobClass, final String cronExpression,
      final String identify) throws SchedulerException, ParseException {
    final JobDetail job = newJob(jobClass).withIdentity(identify + "job", DEF_GROUP).build();
    final CronTrigger trigger = newTrigger().withIdentity(identify + "trigger", DEF_GROUP).withSchedule(
        cronSchedule(cronExpression)).startNow().build();
    return sched.scheduleJob(job, trigger);
  }

  @Override
  public void start() {
    LOG.info("Starting cron manager");
    try {
      sched.start();
      scheduleJob(PendingNotificationImmediateJob.class, "0 */1 * * * ?", "pendinnotifimmediate");
      scheduleJob(PendingNotificationHourlyJob.class, "0 0 * * * ?", "pendingnotifhourly");
      scheduleJob(ClearUpdatedWavesHourlyJob.class, "0 0 * * * ?", "clearupdatedwaveshourly");
      scheduleJob(PendingNotificationDailyJob.class, "0 5 0 * * ?", "pendingnotifdaily");
      scheduleJob(EventsCacheClearDailyJob.class, "0 6 0 * * ?", "cleareventscachedaily");
      scheduleJob(SocialNetworkCacheClearDailyJob.class, "0 6 0 * * ?", "clearcontentcachedaily");
    } catch (final SchedulerException e) {
      logError(e);
    } catch (final ParseException e) {
      logError(e);
    }
    LOG.info("Cron manager started");
  }

  @Override
  public void stop() {
    LOG.info("Stopping cron manager");
    try {
      sched.shutdown(true);
    } catch (final SchedulerException e) {
      logError(e);
    }
  }
}
