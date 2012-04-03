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

package cc.kune.core.server.state;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.common.shared.utils.Pair;
import cc.kune.core.server.persist.CachedCollection;
import cc.kune.wave.server.kspecific.KuneWaveService;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.wave.api.Wavelet;

@Singleton
public class RenderedWavesCache extends CachedCollection<String, Pair<Long, String>> {
  public static final Log LOG = LogFactory.getLog(RenderedWavesCache.class);

  private static final String SEP = "---";

  private final KuneWaveService kuneWaveService;

  @Inject
  public RenderedWavesCache(final KuneWaveService kuneWaveService) {
    super(50);
    this.kuneWaveService = kuneWaveService;
  }

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

  private String ref(final Wavelet wavelet) {
    return wavelet.getWaveId() + SEP + wavelet.getWaveletId();
  }

}
