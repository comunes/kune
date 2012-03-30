/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

package cc.kune.core.server.persist;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.LRUMap;

/**
 * The Class CachedCollection implements some custom cache for often accessed
 * (and costly) objects
 * 
 * @param <K>
 *          the key type
 * @param <V>
 *          the value type
 */
public class CachedCollection<K, V> implements Map<K, V> {

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

  @Override
  public void clear() {
    cache.clear();
  }

  @Override
  public boolean containsKey(final Object key) {
    return cache.containsKey(key);
  }

  @Override
  public boolean containsValue(final Object value) {
    return cache.containsKey(value);
  }

  @Override
  public Set<java.util.Map.Entry<K, V>> entrySet() {
    return cache.entrySet();
  }

  @Override
  public V get(final Object key) {
    return (V) cache.get(key);
  }

  @Override
  public boolean isEmpty() {
    return cache.isEmpty();
  }

  @Override
  public Set<K> keySet() {
    return cache.keySet();
  }

  @Override
  public Object put(final Object key, final Object value) {
    return cache.put(key, value);
  }

  @Override
  public void putAll(final Map<? extends K, ? extends V> m) {
    cache.putAll(m);
  }

  @Override
  public V remove(final Object key) {
    return (V) cache.remove(key);
  }

  @Override
  public int size() {
    return cache.size();
  }

  @Override
  public Collection<V> values() {
    return cache.values();
  }
}
