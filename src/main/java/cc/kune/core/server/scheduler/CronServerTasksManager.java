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

import cc.kune.core.server.notifier.ClearUpdatedWavesHourlyJob;
import cc.kune.core.server.notifier.PendingNotificationDailyJob;
import cc.kune.core.server.notifier.PendingNotificationHourlyJob;
import cc.kune.core.server.notifier.PendingNotificationImmediateJob;
import cc.kune.core.server.rack.ContainerListener;

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
      scheduleJob(PendingNotificationImmediateJob.class, "0 */2 * * * ?", "pendinnotifimmediate");
      scheduleJob(PendingNotificationHourlyJob.class, "0 0 * * * ?", "pendingnotifhourly");
      scheduleJob(ClearUpdatedWavesHourlyJob.class, "0 0 * * * ?", "clearupdatedwaveshourly");
      scheduleJob(PendingNotificationDailyJob.class, "0 5 0 * * ?", "pendingnotifdaily");
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
