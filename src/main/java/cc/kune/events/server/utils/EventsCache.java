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

package cc.kune.events.server.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.server.LogThis;
import cc.kune.core.server.persist.CachedCollection;
import cc.kune.domain.Container;

import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class EventsCache is used to cache events of a group. Right now is only
 * used in the export functionality because we don't have a way to detect gadget
 * changes (of dates, etc) to expire this elements.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@LogThis
@Singleton
public class EventsCache extends CachedCollection<Container, List<Map<String, String>>> {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(EventsCache.class);

  /**
   * Instantiates a new events cache.
   */
  public EventsCache() {
    super(100);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.persist.CachedCollection#get(java.lang.Object)
   */
  @Override
  public List<Map<String, String>> get(final Object key) {
    final List<Map<String, String>> list = super.get(key);
    if (list != null) {
      LOG.debug("Using events cache for " + ((Container) key).getStateToken());
    } else {
      LOG.debug("Events cache empty for " + ((Container) key).getStateToken());
    }
    return list;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Map#put(java.lang.Object, java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<Map<String, String>> put(final Container container, final List<Map<String, String>> value) {
    final Object put = super.put(container, value);
    LOG.debug("Event list added for container " + container.getStateToken() + ", size " + this.size());
    return (List<Map<String, String>>) put;
  }

}
