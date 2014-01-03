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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.NoResultException;

import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;
import org.waveprotocol.wave.model.waveref.WaveRef;
import org.waveprotocol.wave.util.escapers.jvm.JavaWaverefEncoder;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.notifier.Addressee;
import cc.kune.domain.Content;
import cc.kune.domain.User;
import cc.kune.domain.finders.UserFinder;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.wave.api.Participants;

// TODO: Auto-generated Javadoc
/**
 * The Class KuneWaveServerUtils.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneWaveServerUtils {

  /** The participant utils. */
  @Inject
  static Provider<ParticipantUtils> participantUtils;
  
  /** The user finder. */
  @Inject
  static Provider<UserFinder> userFinder;
  
  /** The wave service. */
  @Inject
  static Provider<KuneWaveService> waveService;

  /**
   * Gets the local participants except author.
   *
   * @param waveref the waveref
   * @param author the author
   * @return the local participants except author
   */
  public static Collection<Addressee> getLocalParticipantsExceptAuthor(final WaveRef waveref,
      final String author) {
    final Participants participants = waveService.get().getParticipants(waveref, author);
    participants.remove(author);
    final Set<Addressee> list = new HashSet<Addressee>();
    for (final String participant : participants) {
      participantUtils.get().isLocal(participant);
      try {
        final User user = userFinder.get().findByShortName(
            participantUtils.get().getAddressName(participant));
        list.add(Addressee.build(user));
      } catch (final NoResultException e) {
        // Seems is not a local user
      }
    }
    return list;
  }

  /**
   * Gets the url.
   *
   * @param waveref the waveref
   * @return the url
   */
  public static String getUrl(final WaveRef waveref) {
    return JavaWaverefEncoder.encodeToUriPathSegment(waveref);
  }

  /**
   * Gets the wave ref.
   *
   * @param content the content
   * @return the wave ref
   */
  public static WaveRef getWaveRef(final Content content) {
    try {
      return JavaWaverefEncoder.decodeWaveRefFromPath(String.valueOf(content.getWaveId()));
    } catch (final InvalidWaveRefException e) {
      throw new DefaultException("Error getting the wave");
    }
  }
}
