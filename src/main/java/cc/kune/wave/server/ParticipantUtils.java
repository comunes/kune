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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.wave.model.wave.InvalidParticipantAddress;
import org.waveprotocol.wave.model.wave.ParticipantId;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.wave.api.Participants;

@Singleton
public class ParticipantUtils {

  private String atDomain;
  private final String domain;
  private final ParticipantId superAdmin;

  @Inject
  public ParticipantUtils(@Named(CoreSettings.WAVE_SERVER_DOMAIN) final String domain,
      final KuneBasicProperties databaseProperties) throws InvalidParticipantAddress {
    this.domain = domain;
    superAdmin = ofImpl(databaseProperties.getAdminShortName());
  }

  public String[] arrayFrom(final Participants parts) {
    return parts.toArray(new String[parts.size()]);
  }

  /**
   * Array of participants but with lastParticipant at the end
   * 
   * @param parts
   *          the parts
   * @param lastParticipant
   *          the last participant
   * @return the string[]
   */
  public String[] arrayFromOrdered(final Participants parts, final String lastParticipant) {
    final LinkedList<String> list = new LinkedList<String>();
    list.addAll(parts);
    // we order the list, so we put lastParticipant at the end
    if (list.contains(lastParticipant)) {
      while (list.remove(lastParticipant)) {
      }
      list.add(lastParticipant);
    }
    return list.toArray(new String[list.size()]);
  }

  public String getAddressName(final String address) {
    return address.contains(ParticipantId.DOMAIN_PREFIX) ? address.split(ParticipantId.DOMAIN_PREFIX)[0]
        : address;
  }

  private String getAtDomain() {
    if (atDomain == null) {
      atDomain = ParticipantId.DOMAIN_PREFIX + domain;
    }
    return atDomain;
  }

  public String getDomain() {
    return domain;
  }

  public ParticipantId getPublicParticipantId() {
    return of(getAtDomain());
  }

  public ParticipantId getSuperAdmin() {
    return superAdmin;
  }

  public boolean isLocal(final String address) {
    return address.contains(getAtDomain());
  }

  public ParticipantId[] listFrom(final List<String> list) {
    final ParticipantId[] array = new ParticipantId[list.size()];
    for (int i = 0; i < list.size(); i++) {
      array[i] = of(list.get(i));
    }
    return array;
  }

  public ParticipantId[] listFrom(final Set<User> list) {
    final ParticipantId[] array = new ParticipantId[list.size()];
    final Iterator<User> iterator = list.iterator();
    for (int i = 0; i < list.size(); i++) {
      array[i] = of(iterator.next().getShortName());
    }
    return array;
  }

  public ParticipantId[] listFrom(final String... list) {
    final ParticipantId[] array = new ParticipantId[list.length];
    for (int i = 0; i < list.length; i++) {
      array[i] = of(list[i]);
    }
    return array;
  }

  public ParticipantId[] of(final String... list) {
    return listFrom(list);
  }

  public ParticipantId of(final String username) {
    return ofImpl(username);
  }

  public ParticipantId[] of(final String author, final String[] list) {
    if (list == null) {
      return listFrom(author);
    }
    final ParticipantId[] array = new ParticipantId[list.length + 1];
    array[0] = of(author);
    for (int i = 0; i < list.length; i++) {
      array[i + 1] = of(list[i]);
    }
    return array;
  }

  private ParticipantId ofImpl(final String username) {
    try {
      if (username.contains(ParticipantId.DOMAIN_PREFIX)) {
        return ParticipantId.of(username);
      } else {
        return ParticipantId.of(username + getAtDomain());
      }
    } catch (final InvalidParticipantAddress e) {
      throw new DefaultException("Error getting Wave participant Id");
    }
  }

}
