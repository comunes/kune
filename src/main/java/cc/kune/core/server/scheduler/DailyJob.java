package cc.kune.core.server.scheduler;

import java.text.ParseException;

import org.quartz.SchedulerException;

public abstract class DailyJob extends AbstractJob {

  public DailyJob(final CronServerTasksManager manager) throws ParseException, SchedulerException {
    super(manager, "0 0 * * * ?");
  }

}
