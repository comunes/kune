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
package cc.kune.wave.server;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.NoResultException;

import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;
import org.waveprotocol.wave.model.waveref.WaveRef;
import org.waveprotocol.wave.util.escapers.jvm.JavaWaverefEncoder;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.domain.Content;
import cc.kune.domain.User;
import cc.kune.domain.finders.UserFinder;
import cc.kune.wave.server.kspecific.KuneWaveService;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.wave.api.Participants;

public class KuneWaveServerUtils {

  @Inject
  static Provider<ParticipantUtils> participantUtils;
  @Inject
  static Provider<UserFinder> userFinder;
  @Inject
  static Provider<KuneWaveService> waveService;

  public static Collection<User> getLocalParticipants(final WaveRef waveref, final String author) {
    final Participants participants = waveService.get().getParticipants(waveref, author);
    final Set<User> list = new HashSet<User>();
    for (final String participant : participants) {
      participantUtils.get().isLocal(participant);
      try {
        list.add(userFinder.get().findByShortName(participantUtils.get().getAddressName(participant)));
      } catch (final NoResultException e) {
        // Seems is not a local user
      }
    }
    return list;
  }

  public static String getUrl(final WaveRef waveref) {
    return JavaWaverefEncoder.encodeToUriPathSegment(waveref);
  }

  public static WaveRef getWaveRef(final Content content) {
    try {
      return JavaWaverefEncoder.decodeWaveRefFromPath(String.valueOf(content.getWaveId()));
    } catch (final InvalidWaveRefException e) {
      throw new DefaultException("Error getting the wave");
    }
  }
}
