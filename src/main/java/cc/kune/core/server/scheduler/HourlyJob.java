package cc.kune.core.server.scheduler;

import java.text.ParseException;

import org.quartz.SchedulerException;

public abstract class HourlyJob extends AbstractJob {

  public HourlyJob(final CronServerTasksManager manager) throws ParseException, SchedulerException {
    super(manager, "0 * * * * ?");
  }

}
