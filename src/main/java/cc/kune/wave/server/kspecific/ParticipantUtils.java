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

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

// TODO: Auto-generated Javadoc
/**
 * The Class ParticipantUtils.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class ParticipantUtils {

  /** The at domain. */
  private String atDomain;
  
  /** The domain. */
  private final String domain;
  
  /** The super admin. */
  private final ParticipantId superAdmin;

  /**
   * Instantiates a new participant utils.
   *
   * @param domain the domain
   * @param databaseProperties the database properties
   * @throws InvalidParticipantAddress the invalid participant address
   */
  @Inject
  public ParticipantUtils(@Named(CoreSettings.WAVE_SERVER_DOMAIN) final String domain,
      final KuneBasicProperties databaseProperties) throws InvalidParticipantAddress {
    this.domain = domain;
    superAdmin = ofImpl(databaseProperties.getAdminShortName());
  }

  /**
   * Array from.
   *
   * @param parts the parts
   * @return the string[]
   */
  public String[] arrayFrom(final Participants parts) {
    return parts.toArray(new String[parts.size()]);
  }

  /**
   * Array of participants but with lastParticipant at the end.
   *
   * @param parts the parts
   * @param lastParticipant the last participant
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

  /**
   * Gets the address name.
   *
   * @param address the address
   * @return the address name
   */
  public String getAddressName(final String address) {
    return address.contains(ParticipantId.DOMAIN_PREFIX) ? address.split(ParticipantId.DOMAIN_PREFIX)[0]
        : address;
  }

  /**
   * Gets the at domain.
   *
   * @return the at domain
   */
  private String getAtDomain() {
    if (atDomain == null) {
      atDomain = ParticipantId.DOMAIN_PREFIX + domain;
    }
    return atDomain;
  }

  /**
   * Gets the domain.
   *
   * @return the domain
   */
  public String getDomain() {
    return domain;
  }

  /**
   * Gets the public participant id.
   *
   * @return the public participant id
   */
  public ParticipantId getPublicParticipantId() {
    return of(getAtDomain());
  }

  /**
   * Gets the super admin.
   *
   * @return the super admin
   */
  public ParticipantId getSuperAdmin() {
    return superAdmin;
  }

  /**
   * Checks if is local.
   *
   * @param address the address
   * @return true, if is local
   */
  public boolean isLocal(final String address) {
    return address.contains(getAtDomain());
  }

  /**
   * List from.
   *
   * @param list the list
   * @return the participant id[]
   */
  public ParticipantId[] listFrom(final List<String> list) {
    final ParticipantId[] array = new ParticipantId[list.size()];
    for (int i = 0; i < list.size(); i++) {
      array[i] = of(list.get(i));
    }
    return array;
  }

  /**
   * List from.
   *
   * @param list the list
   * @return the participant id[]
   */
  public ParticipantId[] listFrom(final Set<User> list) {
    final ParticipantId[] array = new ParticipantId[list.size()];
    final Iterator<User> iterator = list.iterator();
    for (int i = 0; i < list.size(); i++) {
      array[i] = of(iterator.next().getShortName());
    }
    return array;
  }

  /**
   * List from.
   *
   * @param list the list
   * @return the participant id[]
   */
  public ParticipantId[] listFrom(final String... list) {
    final ParticipantId[] array = new ParticipantId[list.length];
    for (int i = 0; i < list.length; i++) {
      array[i] = of(list[i]);
    }
    return array;
  }

  /**
   * Of.
   *
   * @param list the list
   * @return the participant id[]
   */
  public ParticipantId[] of(final String... list) {
    return listFrom(list);
  }

  /**
   * Of.
   *
   * @param username the username
   * @return the participant id
   */
  public ParticipantId of(final String username) {
    return ofImpl(username);
  }

  /**
   * Of.
   *
   * @param author the author
   * @param list the list
   * @return the participant id[]
   */
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

  /**
   * Of impl.
   *
   * @param username the username
   * @return the participant id
   */
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

  public Set<String> toSet(final String[] array) {
    final Set<String> set = new TreeSet<String>(Collections.reverseOrder());
    set.addAll(Arrays.asList(array));
    return set;
  }

}
