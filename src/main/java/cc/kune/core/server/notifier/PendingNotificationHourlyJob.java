package cc.kune.core.server.notifier;

import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import cc.kune.wave.server.kspecific.WaveEmailNotifier;

import com.google.inject.Inject;

public class PendingNotificationHourlyJob implements Job {

  public static final Log LOG = LogFactory.getLog(PendingNotificationHourlyJob.class);
  private final PendingNotificationSender pendingManager;

  private final WaveEmailNotifier waveNotifier;

  @Inject
  public PendingNotificationHourlyJob(final PendingNotificationSender pendingManager,
      final WaveEmailNotifier waveNotifier) throws ParseException, SchedulerException {
    this.pendingManager = pendingManager;
    this.waveNotifier = waveNotifier;
  }

  @Override
  public void execute(final JobExecutionContext context) throws JobExecutionException {
    LOG.info(String.format("Hourly notifications cron job start, %s", pendingManager));
    pendingManager.sendHourlyNotifications();
    waveNotifier.clearUpdatedWaves();
    LOG.info(String.format("Hourly notifications cron job end, %s", pendingManager));
  }

}
