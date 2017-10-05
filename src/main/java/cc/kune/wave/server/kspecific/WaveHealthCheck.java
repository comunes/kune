/*
 *
 * Copyright (C) 2007-2017 Licensed to the Comunes Association (CA) un der
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

import org.waveprotocol.wave.model.id.WaveId;
import org.waveprotocol.wave.model.id.WaveletId;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.core.server.xmpp.XmppManager;

@Singleton
public class WaveHealthCheck extends HealthCheck {

  @Inject
  KuneWaveService waveService;
  @Inject
  XmppManager xmppManager;
  @Inject
  KuneBasicProperties properties;
  @Inject
  ParticipantUtils participantUtils;

  @Override
  protected Result check() throws Exception {
    final String defWave = properties.getWelcomewave();
    String admin = properties.getAdminShortName();
    try {
      if (!TextUtils.empty(defWave)) {
        final String domain = participantUtils.getDomain();
        final WaveId waveId = WaveId.ofChecked(domain, defWave);
        WaveletId waveletId = WaveletId.of(domain, "conv+root");
        waveService.fetchWave(waveId, waveletId, admin);
      }
    } catch (Exception e) {
      return Result.unhealthy("Cannot login via xmpp as admin user", e);
    }
    return Result.healthy();
  }

}
