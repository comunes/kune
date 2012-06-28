/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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

import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.server.mail.FormatedString;
import cc.kune.core.server.notifier.NotificationType;
import cc.kune.core.server.notifier.PendingNotification;
import cc.kune.core.server.notifier.PendingNotificationProvider;
import cc.kune.core.server.notifier.PendingNotificationSender;
import cc.kune.core.server.notifier.SimpleDestinationProvider;
import cc.kune.core.server.notifier.WaveDestinationProvider;
import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.core.server.rack.ContainerListener;
import cc.kune.domain.User;
import cc.kune.domain.finders.UserFinder;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class WaveEmailNotifier implements ContainerListener {
  public static final Log LOG = LogFactory.getLog(WaveEmailNotifier.class);

  private final KuneBasicProperties basicProperties;
  private final PendingNotificationSender notificator;
  private final ParticipantUtils partUtils;
  private final HashSet<WaveId> updatedWavesInLastHour;
  private final UserFinder userFinder;
  private final WaveBus waveBus;
  private final KuneWaveService waveService;

  @Inject
  public WaveEmailNotifier(final WaveBus waveBus, final PendingNotificationSender notificator,
      final KuneBasicProperties basicProperties, final ParticipantUtils partUtils,
      final KuneWaveService waveService, final UserFinder userFinder) {
    this.waveBus = waveBus;
    this.notificator = notificator;
    this.basicProperties = basicProperties;
    this.partUtils = partUtils;
    this.waveService = waveService;
    this.userFinder = userFinder;
    updatedWavesInLastHour = new HashSet<WaveId>();
    LOG.info("WaveEmailNotifier created");
  }

  public void clearUpdatedWaves() {
    updatedWavesInLastHour.clear();
  }

  @Override
  public void start() {
    final String siteCommonName = basicProperties.getSiteCommonName();
    final String siteUrl = basicProperties.getSiteUrl();
    waveBus.subscribe(new Subscriber() {

      private PendingNotification createPendingNotif(final ParticipantId participant,
          final FormatedString subject, final FormatedString body) {
        final String address = participant.getAddress();
        if (partUtils.isLocal(address)) {
          final String userName = partUtils.getAddressName(address);
          try {
            final User user = userFinder.findByShortName(userName);
            return new PendingNotification(NotificationType.email, subject, body, true, false,
                new SimpleDestinationProvider(user));
          } catch (final NoResultException e) {
            // Seems is not a local user
          }
        }
        return PendingNotification.NONE;
      }

      private String getTitle(final ReadableWaveletData wavelet, final ParticipantId by) {
        final String title = waveService.getTitle(
            WaveRef.of(wavelet.getWaveId(), wavelet.getWaveletId()), by.toString());
        return TextUtils.notEmpty(title) ? title : "Not subject";
      }

      private String getUrl(final ReadableWaveletData wavelet, final WaveletId waveletId) {
        return KuneWaveServerUtils.getUrl(WaveRef.of(wavelet.getWaveId(), waveletId));
      }

      private FormatedString newWaveTemplate(final String by, final String url) {
        return FormatedString.build(
            "Hi there,<br><br>You have a new message by '%s' in your inbox at %s. <a href=\"%s#%s\">Read more</a>.<br>",
            by, siteCommonName, siteUrl, url);
      }

      private FormatedString removeWaveTemplate(final String by, final String title) {
        return FormatedString.build(
            "Hi there,<br><br>You have been removed by '%s' from message '%s' at %s. <a href=\"%s#%s\">Read more</a>.<br>",
            by, title, siteCommonName, siteUrl, SiteTokens.WAVE_INBOX);
      }

      private FormatedString updatedWaveTemplate(final String by, final String title, final String url) {
        return FormatedString.build(
            "Hi there,<br><br>The message '%s' was updated by '%s' in your inbox at %s. <a href=\"%s#%s\">Read more</a>.<br>",
            title, by, siteCommonName, siteUrl, url);
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
              notificator.add(new PendingNotificationProvider() {
                @Override
                public PendingNotification get() {
                  final AddParticipant addParticipantOp = (AddParticipant) op;
                  final ParticipantId by = addParticipantOp.getContext().getCreator();
                  final ParticipantId to = addParticipantOp.getParticipantId();
                  LOG.info(String.format("New wave from '%s' to '%s'", by, to));
                  return createPendingNotif(to, FormatedString.build("You have a new message"),
                      newWaveTemplate(by.toString(), getUrl(wavelet, waveletId)));
                }
              });
            } else if (op instanceof RemoveParticipant) {
              notificator.add(new PendingNotificationProvider() {
                @Override
                public PendingNotification get() {
                  final RemoveParticipant removeParticipantOp = (RemoveParticipant) op;
                  final ParticipantId by = removeParticipantOp.getContext().getCreator();
                  final ParticipantId to = removeParticipantOp.getParticipantId();
                  final String title = getTitle(wavelet, by);
                  LOG.info(String.format("'%s' removed from wave '%s' to '%s'", by, title, to));
                  return createPendingNotif(to,
                      FormatedString.build("You have been removed from a message"),
                      removeWaveTemplate(by.toString(), title));
                }
              });
            } else if (op instanceof WaveletBlipOperation) {
              // Replies, etc
              final WaveId waveId = wavelet.getWaveId();
              if (!updatedWavesInLastHour.contains(waveId)) {
                updatedWavesInLastHour.add(waveId);
                notificator.add(new PendingNotificationProvider() {
                  @Override
                  public PendingNotification get() {
                    final WaveRef waveref = WaveRef.of(waveId, waveletId);
                    final ParticipantId by = ((WaveletBlipOperation) op).getContext().getCreator();
                    final String title = getTitle(wavelet, by);
                    final String url = KuneWaveServerUtils.getUrl(waveref);
                    LOG.info(String.format("'%s' update wave '%s'", by, title));
                    return new PendingNotification(NotificationType.email,
                        FormatedString.build("You have an update message"), updatedWaveTemplate(
                            by.toString(), title, url), true, false, new WaveDestinationProvider(
                            waveref, by.toString()));
                  }
                });
              }
            }
          }
        }
      }
    });
    LOG.info("WaveEmailNotifier started");
  }

  @Override
  public void stop() {
  }

}
