package cc.kune.core.server.scheduler;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * A factory for creating CustomJob objects. See:
 * http://javaeenotes.blogspot.com
 * /2011/09/inject-instances-in-quartz-jobs-with.html
 */
@Singleton
public class CustomJobFactory implements JobFactory {

  private Injector injector;

  @Override
  public Job newJob(final TriggerFiredBundle bundle, final Scheduler scheduler)
      throws SchedulerException {
    return injector.getInstance(bundle.getJobDetail().getJobClass());
  }

  public void setInjector(final Injector injector) {
    this.injector = injector;
  }
}