/**
 * Copyright 2012 Apache Wave
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package cc.kune.wave.server.search;

import com.typesafe.config.Config;
import org.waveprotocol.box.server.SearchModule;
import org.waveprotocol.box.server.waveserver.PerUserWaveViewBus;
import org.waveprotocol.box.server.waveserver.PerUserWaveViewHandler;
import org.waveprotocol.box.server.waveserver.PerUserWaveViewProvider;
import org.waveprotocol.box.server.waveserver.SearchProvider;
import org.waveprotocol.box.server.waveserver.SimpleSearchProviderImpl;
import org.waveprotocol.box.server.waveserver.WaveIndexer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

public class CustomSearchModule extends SearchModule {

  public static final Log LOG = LogFactory.getLog(CustomSearchModule.class);

  private final String searchType;

  @Inject
  public CustomSearchModule(Config config) {
    super(config);
    this.searchType = config.getString("core.search_type");
  }

  @Override
  public void configure() {
    try {
      // TODO: patch Wave to don't throw RuntimeException?
      super.configure();
      if ("db".equals(searchType)) {
        bind(SearchProvider.class).to(SimpleSearchProviderImpl.class).in(Singleton.class);
        bind(PerUserWaveViewProvider.class).to(CustomPerUserWaveViewHandler.class).in(Singleton.class);
        bind(PerUserWaveViewBus.Listener.class).to(CustomPerUserWaveViewHandler.class).in(
            Singleton.class);
        bind(PerUserWaveViewHandler.class).to(CustomPerUserWaveViewHandler.class).in(Singleton.class);
        bind(WaveIndexer.class).to(CustomWaveIndexerImpl.class);
      }
    } catch (final RuntimeException e) {
      LOG.error("Error in CustomSearchModule", e);
    }
  }
}
