package cc.kune.core.server.scheduler;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;

public abstract class AbstractJob implements Job {
  private static final String DEF_GROUP = "groupdef";

  public AbstractJob(final CronServerTasksManager manager, final String cronExpression)
      throws ParseException, SchedulerException {
    final JobDetail job = newJob(HourlyJob.class).withIdentity("hourlyjob1", DEF_GROUP).build();

    final CronTrigger trigger = newTrigger().withIdentity("hourlytrigger1", DEF_GROUP).withSchedule(
        cronSchedule(cronExpression)).build();

    manager.scheduleJob(job, trigger);
  }
}
