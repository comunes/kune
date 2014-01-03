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

package cc.kune.core.server.persist;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.LRUMap;

// TODO: Auto-generated Javadoc
/**
 * The Class CachedCollection implements some custom cache for often accessed
 * (and costly) objects.
 * 
 * @param <K>
 *          the key type
 * @param <V>
 *          the value type
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CachedCollection<K, V> {

  /** The cache. */
  // private final Map<K, V> cache;
  private final Map cache;

  /**
   * Instantiates a new cached collection.
   * 
   * @param size
   *          the size of the cache
   */
  public CachedCollection(final int size) {
    cache = Collections.synchronizedMap(new LRUMap(size));
  }

  /**
   * Clear.
   */
  public void clear() {
    cache.clear();
  }

  /**
   * Contains key.
   * 
   * @param key
   *          the key
   * @return true, if successful
   */
  public boolean containsKey(final Object key) {
    return cache.containsKey(key);
  }

  /**
   * Contains value.
   * 
   * @param value
   *          the value
   * @return true, if successful
   */
  public boolean containsValue(final Object value) {
    return cache.containsKey(value);
  }

  /**
   * Entry set.
   * 
   * @return the sets the
   */
  public Set<java.util.Map.Entry<K, V>> entrySet() {
    return cache.entrySet();
  }

  /**
   * Gets the.
   * 
   * @param key
   *          the key
   * @return the v
   */
  public V get(final Object key) {
    return (V) cache.get(key);
  }

  /**
   * Checks if is empty.
   * 
   * @return true, if is empty
   */
  public boolean isEmpty() {
    return cache.isEmpty();
  }

  /**
   * Key set.
   * 
   * @return the sets the
   */
  public Set<K> keySet() {
    return cache.keySet();
  }

  /**
   * Put.
   * 
   * @param key
   *          the key
   * @param value
   *          the value
   * @return the v
   */
  public V put(final K key, final V value) {
    return (V) cache.put(key, value);
  }

  /**
   * Put all.
   * 
   * @param m
   *          the m
   */
  public void putAll(final Map<? extends K, ? extends V> m) {
    cache.putAll(m);
  }

  /**
   * Removes the.
   * 
   * @param key
   *          the key
   * @return the v
   */
  public V remove(final Object key) {
    return (V) cache.remove(key);
  }

  /**
   * Size.
   * 
   * @return the int
   */
  public int size() {
    return cache.size();
  }

  /**
   * Values.
   * 
   * @return the collection
   */
  public Collection<V> values() {
    return cache.values();
  }
}
