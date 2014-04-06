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

import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.SearchModule;
import org.waveprotocol.box.server.waveserver.PerUserWaveViewBus;
import org.waveprotocol.box.server.waveserver.PerUserWaveViewHandler;
import org.waveprotocol.box.server.waveserver.PerUserWaveViewProvider;
import org.waveprotocol.box.server.waveserver.WaveIndexer;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

public class CustomSearchModule extends SearchModule {

  private final String searchType;

  @Inject
  public CustomSearchModule(@Named(CoreSettings.SEARCH_TYPE) final String searchType,
      @Named(CoreSettings.INDEX_DIRECTORY) final String indexDirectory) {
    super(searchType, indexDirectory);
    this.searchType = searchType;
  }

  @Override
  public void configure() {
    try {
      // TODO: patch Wave to don't throw RuntimeException
      super.configure();
    } catch (final RuntimeException e) {
      if ("db".equals(searchType)) {
        bind(PerUserWaveViewProvider.class).to(CustomPerUserWaveViewHandler.class).in(Singleton.class);
        bind(PerUserWaveViewBus.Listener.class).to(CustomPerUserWaveViewHandler.class).in(
            Singleton.class);
        bind(PerUserWaveViewHandler.class).to(CustomPerUserWaveViewHandler.class).in(Singleton.class);
        bind(WaveIndexer.class).to(CustomWaveIndexerImpl.class);
      }
    }
  }
}
