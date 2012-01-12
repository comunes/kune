package cc.kune.wave.server.kspecific;

import java.util.HashSet;

import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.waveprotocol.box.common.DeltaSequence;
import org.waveprotocol.box.server.waveserver.WaveBus;
import org.waveprotocol.box.server.waveserver.WaveBus.Subscriber;
import org.waveprotocol.wave.model.id.IdUtil;
import org.waveprotocol.wave.model.id.WaveId;
import org.waveprotocol.wave.model.id.WaveletId;
import org.waveprotocol.wave.model.id.WaveletName;
import org.waveprotocol.wave.model.operation.wave.AddParticipant;
import org.waveprotocol.wave.model.operation.wave.RemoveParticipant;
import org.waveprotocol.wave.model.operation.wave.TransformedWaveletDelta;
import org.waveprotocol.wave.model.operation.wave.WaveletBlipOperation;
import org.waveprotocol.wave.model.operation.wave.WaveletOperation;
import org.waveprotocol.wave.model.version.HashedVersion;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.wave.data.ReadableWaveletData;
import org.waveprotocol.wave.model.waveref.WaveRef;

import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.server.LogThis;
import cc.kune.core.server.mail.FormatedString;
import cc.kune.core.server.notifier.NotificationType;
import cc.kune.core.server.notifier.PendingNotification;
import cc.kune.core.server.notifier.PendingNotificationSender;
import cc.kune.core.server.notifier.SimpleDestinationProvider;
import cc.kune.core.server.notifier.WaveDestinationProvider;
import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.core.server.rack.ContainerListener;
import cc.kune.domain.User;
import cc.kune.domain.finders.UserFinder;
import cc.kune.wave.server.KuneWaveServerUtils;
import cc.kune.wave.server.ParticipantUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@LogThis
@Singleton
public class WaveEmailNotifier implements ContainerListener {
  public static final Log LOG = LogFactory.getLog(WaveEmailNotifier.class);
  private HashSet<WaveId> updatedWavesInLastHour;

  @Inject
  public WaveEmailNotifier(final WaveBus waveBus, final PendingNotificationSender notificator,
      final KuneBasicProperties basicProperties, final ParticipantUtils partUtils,
      final KuneWaveService waveService, final UserFinder userFinder) {

    final String siteCommonName = basicProperties.getSiteCommonName();
    final String siteUrl = basicProperties.getSiteUrl();
    updatedWavesInLastHour = new HashSet<WaveId>();
    waveBus.subscribe(new Subscriber() {

      private void addPendingNotif(final ParticipantId participant, final FormatedString subject,
          final FormatedString body) {
        final String address = participant.getAddress();
        if (partUtils.isLocal(address)) {
          final String userName = partUtils.getAddressName(address);
          try {
            final User user = userFinder.findByShortName(userName);
            notificator.add(new PendingNotification(NotificationType.email, subject, body, true, false,
                new SimpleDestinationProvider(user)));
          } catch (final NoResultException e) {
            // Seems is not a local user
          }
        }
      }

      private String getUrl(final ReadableWaveletData wavelet, final WaveletId waveletId) {
        return KuneWaveServerUtils.getUrl(WaveRef.of(wavelet.getWaveId(), waveletId));
      }

      private FormatedString newWaveTemplate(final String url) {
        return FormatedString.build(
            "Hi there,<br><br>You have a new message in your inbox at %s. <a href=\"%s#%s\">Read more</a>.<br>",
            siteCommonName, siteUrl, url);
      }

      private FormatedString removeWaveTemplate(final String by, final String title) {
        return FormatedString.build(
            "Hi there,<br><br>You have been removed by '%s' from message '%s' at %s. <a href=\"%s#%s\">Read more</a>.<br>",
            by, title, siteCommonName, siteUrl, SiteTokens.WAVEINBOX);
      }

      private FormatedString updatedWaveTemplate(final String by, final String url) {
        return FormatedString.build(
            "Hi there,<br><br>You have an updated message by '%s' in your inbox at %s. <a href=\"%s#%s\">Read more</a>.<br>",
            by, siteCommonName, siteUrl, url);
      }

      @Override
      public void waveletCommitted(final WaveletName waveletName, final HashedVersion version) {
      }

      @Override
      public void waveletUpdate(final ReadableWaveletData wavelet, final DeltaSequence deltas) {
        final WaveletId waveletId = wavelet.getWaveletId();
        if (IdUtil.isUserDataWavelet(waveletId)) {
          return;
        }
        for (final TransformedWaveletDelta delta : deltas) {
          for (final WaveletOperation op : delta) {
            if (op instanceof AddParticipant) {
              final ParticipantId participant = ((AddParticipant) op).getParticipantId();
              addPendingNotif(participant, FormatedString.build("You have a new message"),
                  newWaveTemplate(getUrl(wavelet, waveletId)));
            } else if (op instanceof RemoveParticipant) {
              final ParticipantId participant = ((RemoveParticipant) op).getParticipantId();
              final ParticipantId by = ((RemoveParticipant) op).getContext().getCreator();
              final String title = waveService.getTitle(WaveRef.of(wavelet.getWaveId(), waveletId),
                  by.toString());
              addPendingNotif(participant, FormatedString.build("You have been removed from message"),
                  removeWaveTemplate(by.toString(), title));
            } else if (op instanceof WaveletBlipOperation) {
              final WaveId waveId = wavelet.getWaveId();
              if (!updatedWavesInLastHour.contains(waveId)) {
                updatedWavesInLastHour.add(waveId);
                // Replies, etc
                final ParticipantId by = ((WaveletBlipOperation) op).getContext().getCreator();
                // Duplicate code above
                final WaveRef waveref = WaveRef.of(waveId, waveletId);
                final String url = KuneWaveServerUtils.getUrl(waveref);
                notificator.add(new PendingNotification(NotificationType.email,
                    FormatedString.build("You have an updated message"), updatedWaveTemplate(
                        by.toString(), url), true, false, new WaveDestinationProvider(waveref,
                        by.toString())));
              }
            }
          }
        }
      }
    });
  }

  public void clearUpdatedWaves() {
    updatedWavesInLastHour.clear();
  }

  @Override
  public void start() {
  }

  @Override
  public void stop() {
  }

}
