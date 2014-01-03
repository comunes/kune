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

package cc.kune.core.server.state;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.common.shared.utils.Pair;
import cc.kune.core.server.persist.CachedCollection;
import cc.kune.wave.server.kspecific.KuneWaveService;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.wave.api.Wavelet;

// TODO: Auto-generated Javadoc
/**
 * The Class RenderedWavesCache.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class RenderedWavesCache extends CachedCollection<String, Pair<Long, String>> {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(RenderedWavesCache.class);

  /** The Constant SEP. */
  private static final String SEP = "---";

  /** The kune wave service. */
  private final KuneWaveService kuneWaveService;

  /**
   * Instantiates a new rendered waves cache.
   * 
   * @param kuneWaveService
   *          the kune wave service
   */
  @Inject
  public RenderedWavesCache(final KuneWaveService kuneWaveService) {
    super(50);
    this.kuneWaveService = kuneWaveService;
  }

  /**
   * Gets the or render.
   * 
   * @param wavelet
   *          the wavelet
   * @return the or render
   */
  public String getOrRender(final Wavelet wavelet) {
    final Pair<Long, String> pair = get(ref(wavelet));
    final long lastModifiedTime = wavelet.getLastModifiedTime();
    if (pair != null) {
      LOG.debug("Existing rendered wave");
    }
    if (pair == null || pair.getLeft() < lastModifiedTime) {
      LOG.debug("not returning not existing or expired rendered wave");
      final String rendered = kuneWaveService.render(wavelet);
      put(ref(wavelet), Pair.create(lastModifiedTime, rendered));
      return rendered;
    }
    return pair.getRight();
  }

  /**
   * Ref.
   * 
   * @param wavelet
   *          the wavelet
   * @return the string
   */
  private String ref(final Wavelet wavelet) {
    return wavelet.getWaveId() + SEP + wavelet.getWaveletId();
  }

}
