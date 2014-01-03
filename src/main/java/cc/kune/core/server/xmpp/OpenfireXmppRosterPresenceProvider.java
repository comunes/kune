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
package cc.kune.core.server.xmpp;

import javax.persistence.EntityManager;

import cc.kune.core.server.manager.impl.DefaultManager;
import cc.kune.core.server.persist.DataSourceOpenfire;
import cc.kune.core.server.persist.OpenfireTransactional;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class OpenfireXmppRosterPresenceProvider.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class OpenfireXmppRosterPresenceProvider extends DefaultManager<RosterPresence, Long> implements
    XmppRosterPresenceProvider {
  // private static final Log LOG =
  // LogFactory.getLog(OpenfireXmppRosterProvider.class);
  /** The finder. */
  private final OpenfireXmppRosterPresenceFinder finder;

  /**
   * Instantiates a new openfire xmpp roster presence provider.
   * 
   * @param em
   *          the em
   * @param finder
   *          the finder
   */
  @Inject
  public OpenfireXmppRosterPresenceProvider(@DataSourceOpenfire final Provider<EntityManager> em,
      final OpenfireXmppRosterPresenceFinder finder) {
    super(em, RosterPresence.class);
    this.finder = finder;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.xmpp.XmppRosterPresenceProvider#getLastConnected(java
   * .lang.String)
   */
  @Override
  @OpenfireTransactional
  public Long getLastConnected(final String user) {
    return finder.getLastConnected(user).getOfflineDate();
  }

}
