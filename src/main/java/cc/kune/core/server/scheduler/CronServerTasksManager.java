package cc.kune.core.server.scheduler;

import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CronServerTasksManager {
  private final Scheduler sched;

  @Inject
  public CronServerTasksManager(final StdSchedulerFactory sf) throws SchedulerException {
    sched = sf.getScheduler();
  }

  public Date scheduleJob(final JobDetail job, final CronTrigger trigger) throws SchedulerException {
    return sched.scheduleJob(job, trigger);
  }

  public void start() throws SchedulerException {
    sched.start();
  }

  public void stop() throws SchedulerException {
    sched.shutdown(true);
  }
}
