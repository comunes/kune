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

public class ClearUpdatedWavesHourlyJob implements Job {

  public static final Log LOG = LogFactory.getLog(ClearUpdatedWavesHourlyJob.class);

  private final WaveEmailNotifier waveNotifier;

  @Inject
  public ClearUpdatedWavesHourlyJob(final WaveEmailNotifier waveNotifier) throws ParseException,
      SchedulerException {
    this.waveNotifier = waveNotifier;
  }

  @Override
  public void execute(final JobExecutionContext context) throws JobExecutionException {
    waveNotifier.clearUpdatedWaves();
  }

}
