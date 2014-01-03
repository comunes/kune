/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
import cc.kune.core.server.notifier.LocalUserDestinationProvider;
import cc.kune.core.server.notifier.NotificationType;
import cc.kune.core.server.notifier.PendingNotification;
import cc.kune.core.server.notifier.PendingNotificationProvider;
import cc.kune.core.server.notifier.PendingNotificationSender;
import cc.kune.core.server.notifier.WaveDestinationProvider;
import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.core.server.rack.ContainerListener;
import cc.kune.core.server.utils.FormattedString;
import cc.kune.domain.User;
import cc.kune.domain.finders.UserFinder;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class WaveEmailNotifier.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class WaveEmailNotifier implements ContainerListener {
  
  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(WaveEmailNotifier.class);

  /** The basic properties. */
  private final KuneBasicProperties basicProperties;
  
  /** The notificator. */
  private final PendingNotificationSender notificator;
  
  /** The part utils. */
  private final ParticipantUtils partUtils;
  
  /** The updated waves in last hour. */
  private final HashSet<WaveId> updatedWavesInLastHour;
  
  /** The user finder. */
  private final UserFinder userFinder;
  
  /** The wave bus. */
  private final WaveBus waveBus;
  
  /** The wave service. */
  private final KuneWaveService waveService;

  /**
   * Instantiates a new wave email notifier.
   *
   * @param waveBus the wave bus
   * @param notificator the notificator
   * @param basicProperties the basic properties
   * @param partUtils the part utils
   * @param waveService the wave service
   * @param userFinder the user finder
   */
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

  /**
   * Clear updated waves.
   */
  public void clearUpdatedWaves() {
    updatedWavesInLastHour.clear();
  }

  /* (non-Javadoc)
   * @see cc.kune.core.server.rack.ContainerListener#start()
   */
  @Override
  public void start() {
    final String siteCommonName = basicProperties.getSiteCommonName();
    final String siteUrl = basicProperties.getSiteUrl();
    waveBus.subscribe(new Subscriber() {

      private PendingNotification createPendingNotif(final ParticipantId participant,
          final FormattedString subject, final FormattedString body) {
        final String address = participant.getAddress();
        if (partUtils.isLocal(address)) {
          final String userName = partUtils.getAddressName(address);
          try {
            final User user = userFinder.findByShortName(userName);
            return new PendingNotification(NotificationType.email, subject, body, true, false,
                new LocalUserDestinationProvider(user));
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

      private FormattedString newWaveTemplate(final String by, final String url) {
        return FormattedString.build(
            "Hi there,<br><br>You have a new message by '%s' in your inbox at %s. <a href=\"%s#%s\">Read more</a>.<br>",
            by, siteCommonName, siteUrl, url);
      }

      private FormattedString removeWaveTemplate(final String by, final String title) {
        return FormattedString.build(
            "Hi there,<br><br>You have been removed by '%s' from message '%s' at %s. <a href=\"%s#%s\">Read more</a>.<br>",
            by, title, siteCommonName, siteUrl, SiteTokens.WAVE_INBOX);
      }

      private FormattedString updatedWaveTemplate(final String by, final String title, final String url) {
        return FormattedString.build(
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
              final AddParticipant addParticipantOp = (AddParticipant) op;
              final ParticipantId by = addParticipantOp.getContext().getCreator();
              final ParticipantId to = addParticipantOp.getParticipantId();
              if (!by.equals(to)) {
                notificator.add(new PendingNotificationProvider() {
                  @Override
                  public PendingNotification get() {
                    LOG.debug(String.format("New wave from '%s' to '%s'", by, to));
                    final String byS = by.toString();
                    return createPendingNotif(to, FormattedString.build("New message by %s", byS),
                        newWaveTemplate(by.toString(), getUrl(wavelet, waveletId)));
                  }
                });
              }
            } else if (op instanceof RemoveParticipant) {
              final RemoveParticipant removeParticipantOp = (RemoveParticipant) op;
              final ParticipantId by = removeParticipantOp.getContext().getCreator();
              final ParticipantId to = removeParticipantOp.getParticipantId();
              if (!by.equals(to)) {
                notificator.add(new PendingNotificationProvider() {
                  @Override
                  public PendingNotification get() {
                    final String title = getTitle(wavelet, by);
                    LOG.debug(String.format("'%s' removed from wave '%s' to '%s'", by, title, to));
                    return createPendingNotif(to, FormattedString.build(
                        "You have been removed as participant of message: %s", title),
                        removeWaveTemplate(by.toString(), title));
                  }
                });
              }
            } else if (op instanceof WaveletBlipOperation) {
              // Replies, etc
              final WaveId waveId = wavelet.getWaveId();
              if (!updatedWavesInLastHour.contains(waveId)) {
                updatedWavesInLastHour.add(waveId);
                notificator.add(new PendingNotificationProvider() {
                  @Override
                  public PendingNotification get() {
                    final WaveRef waveref = WaveRef.of(waveId, waveletId);
                    final WaveletBlipOperation blipOp = (WaveletBlipOperation) op;
                    final ParticipantId by = blipOp.getContext().getCreator();
                    final String title = getTitle(wavelet, by);
                    final String url = KuneWaveServerUtils.getUrl(waveref);
                    LOG.debug(String.format("'%s' update wave '%s'", by, title));
                    final String byS = by.toString();
                    return new PendingNotification(NotificationType.email, FormattedString.build(
                        "%s (message updated by %s)", title, byS), updatedWaveTemplate(byS, title, url),
                        true, false, new WaveDestinationProvider(waveref, byS));
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

  /* (non-Javadoc)
   * @see cc.kune.core.server.rack.ContainerListener#stop()
   */
  @Override
  public void stop() {
  }

}
