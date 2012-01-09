package cc.kune.wave.server.kspecific;

import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.waveprotocol.box.common.DeltaSequence;
import org.waveprotocol.box.server.waveserver.WaveBus;
import org.waveprotocol.box.server.waveserver.WaveBus.Subscriber;
import org.waveprotocol.wave.model.id.IdUtil;
import org.waveprotocol.wave.model.id.WaveletId;
import org.waveprotocol.wave.model.id.WaveletName;
import org.waveprotocol.wave.model.operation.wave.AddParticipant;
import org.waveprotocol.wave.model.operation.wave.TransformedWaveletDelta;
import org.waveprotocol.wave.model.operation.wave.WaveletBlipOperation;
import org.waveprotocol.wave.model.operation.wave.WaveletOperation;
import org.waveprotocol.wave.model.version.HashedVersion;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.wave.data.ReadableWaveletData;
import org.waveprotocol.wave.model.waveref.WaveRef;

import cc.kune.core.server.LogThis;
import cc.kune.core.server.mail.FormatedString;
import cc.kune.core.server.notifier.NotifySender;
import cc.kune.core.server.notifier.NotifyType;
import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.domain.User;
import cc.kune.domain.finders.UserFinder;
import cc.kune.wave.server.KuneWaveUtils;
import cc.kune.wave.server.ParticipantUtils;

import com.google.inject.Inject;

@LogThis
public class WaveEmailNotifier {
  public static final Log LOG = LogFactory.getLog(WaveEmailNotifier.class);

  @Inject
  public WaveEmailNotifier(final WaveBus waveBus, final NotifySender notifyService,
      final KuneBasicProperties basicProperties, final ParticipantUtils partUtils,
      final UserFinder userFinder) {
    waveBus.subscribe(new Subscriber() {
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
              final String url = KuneWaveUtils.getUrl(WaveRef.of(wavelet.getWaveId(), waveletId));
              final FormatedString body = FormatedString.build(
                  "Hi there,<br><br>You have a new message in %s. <a href=\"%s#%s\">Read more</a>.<br>",
                  basicProperties.getSiteCommonName(), basicProperties.getSiteUrl(), url);
              final String address = participant.getAddress();
              if (partUtils.isLocal(address)) {
                final String userName = partUtils.getAddressName(address);
                try {
                  final User user = userFinder.findByShortName(userName);
                  notifyService.send(NotifyType.email, FormatedString.build("You have a new message"),
                      body, true, false, user);
                  // notifyService.send(NotifyType.chat,
                  // FormatedString.build("New message"), body, true,
                  // user);
                } catch (final NoResultException e) {
                  // Seems is not a local user
                }
              }
            } else if (op instanceof WaveletBlipOperation) {

            }
          }
        }
      }
    });
  }
}
