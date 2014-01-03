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

import java.util.Collection;

import javax.persistence.EntityManager;

import cc.kune.core.server.manager.impl.DefaultManager;
import cc.kune.core.server.persist.DataSourceOpenfire;
import cc.kune.core.server.persist.OpenfireTransactional;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class OpenfireXmppRosterProvider.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class OpenfireXmppRosterProvider extends DefaultManager<RosterItem, Long> implements
    XmppRosterProvider {
  // private static final Log LOG =
  // LogFactory.getLog(OpenfireXmppRosterProvider.class);
  /** The finder. */
  private final OpenfireXmppRosterFinder finder;

  /**
   * Instantiates a new openfire xmpp roster provider.
   * 
   * @param em
   *          the em
   * @param finder
   *          the finder
   */
  @Inject
  public OpenfireXmppRosterProvider(@DataSourceOpenfire final Provider<EntityManager> em,
      final OpenfireXmppRosterFinder finder) {
    super(em, RosterItem.class);
    this.finder = finder;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.xmpp.XmppRosterProvider#count()
   */
  @Override
  @OpenfireTransactional
  public Long count() {
    return finder.count();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.xmpp.XmppRosterProvider#getRoster(java.lang.String)
   */
  @Override
  @OpenfireTransactional
  public Collection<RosterItem> getRoster(final String user) {
    return finder.get(user);
  }

}
