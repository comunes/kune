// @formatter:off
/**
 * Copyright 2010 Google Inc.
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


package cc.kune.wave.client;

import org.waveprotocol.box.webclient.client.RemoteViewServiceMultiplexer;
import org.waveprotocol.box.webclient.client.StageTwoProvider;
import org.waveprotocol.box.webclient.search.WaveContext;
import org.waveprotocol.box.webclient.search.WaveStore;
import org.waveprotocol.wave.client.StageOne;
import org.waveprotocol.wave.client.StageThree;
import org.waveprotocol.wave.client.StageTwo;
import org.waveprotocol.wave.client.StageZero;
import org.waveprotocol.wave.client.Stages;
import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.common.util.AsyncHolder;
import org.waveprotocol.wave.client.common.util.AsyncHolder.Accessor;
import org.waveprotocol.wave.client.common.util.LogicalPanel;
import org.waveprotocol.wave.client.wavepanel.view.BlipView;
import org.waveprotocol.wave.client.wavepanel.view.dom.ModelAsViewProvider;
import org.waveprotocol.wave.client.wavepanel.view.dom.full.BlipQueueRenderer;
import org.waveprotocol.wave.client.wavepanel.view.dom.full.DomRenderer;
import org.waveprotocol.wave.client.wavepanel.view.dom.full.WavePanelResourceLoader;
import org.waveprotocol.wave.model.conversation.Conversation;
import org.waveprotocol.wave.model.conversation.ConversationBlip;
import org.waveprotocol.wave.model.conversation.ConversationView;
import org.waveprotocol.wave.model.id.IdGenerator;
import org.waveprotocol.wave.model.id.ModernIdSerialiser;
import org.waveprotocol.wave.model.waveref.WaveRef;

import com.google.gwt.dom.client.Element;

/**
 * Stages for loading the undercurrent Wave Panel
 *
 * @author zdwang@google.com (David Wang)
 */
public class KuneStagesProvider extends Stages {

  private final static AsyncHolder<Object> HALT = new AsyncHolder<Object>() {
    @Override
    public void call(final Accessor<Object> accessor) {
      // Never ready, so never notify the accessor.
    }
  };

  private final RemoteViewServiceMultiplexer channel;
  private boolean closed;
  private final IdGenerator idGenerator;
  private final boolean isNewWave;
  private final String localDomain;
  private StageOne one;
  private final ProfileManager profiles;
  private final LogicalPanel rootPanel;

  private final boolean showParticipantsPanel;
  private StageThree three;
  private StageTwo two;
  private WaveContext wave;
  private final Element wavePanelElement;
  private final WaveRef waveRef;
  private final WaveStore waveStore;

  /**
   * @param wavePanelElement The dom element to become the wave panel
   * @param rootPanel A panel that this an ancestor of wavePanelElement. This is
   *        used for adopting to the GWT widget tree.
   * @param waveRef the id of the wave to open. If null, it means, create a new
   *        wave.
   * @param channel communication channel.
   * @param isNewWave true if the wave is a new client-created wave
   * @param idGenerator
   */
  public KuneStagesProvider(final Element wavePanelElement, final LogicalPanel rootPanel, final WaveRef waveRef,
      final RemoteViewServiceMultiplexer channel, final IdGenerator idGenerator, final ProfileManager profiles,
      final WaveStore store, final boolean isNewWave, final String localDomain, final boolean showParticipantsPanel) {
    this.wavePanelElement = wavePanelElement;
    this.rootPanel = rootPanel;
    this.waveRef = waveRef;
    this.channel = channel;
    this.idGenerator = idGenerator;
    this.profiles = profiles;
    this.waveStore = store;
    this.isNewWave = isNewWave;
    this.localDomain = localDomain;
    this.showParticipantsPanel = showParticipantsPanel;
  }

  @Override
  protected AsyncHolder<StageOne> createStageOneLoader(final StageZero zero) {
    return haltIfClosed(new StageOne.DefaultProvider(zero) {
      @Override
      protected LogicalPanel createWaveContainer() {
        return rootPanel;
      }

      @Override
      protected Element createWaveHolder() {
        return wavePanelElement;
      }
    });
  }

  @Override
  protected AsyncHolder<StageThree> createStageThreeLoader(final StageTwo two) {
    return haltIfClosed(new StageThree.DefaultProvider(this.two = two) {
      @Override
      protected void create(final Accessor<StageThree> whenReady) {
        // Prepend an init wave flow onto the stage continuation.
        super.create(new Accessor<StageThree>() {
          @Override
          public void use(final StageThree x) {
            onStageThreeLoaded(x, whenReady);
          }
        });
      }

      @Override
      protected String getLocalDomain() {
        return localDomain;
      }
    });
  }

  @Override
  // Kune patch
  protected AsyncHolder<StageTwo> createStageTwoLoader(final StageOne one) {
    return haltIfClosed(new StageTwoProvider(
        this.one = one, waveRef.getWaveId(), channel, isNewWave, idGenerator, profiles) {
      @Override
      protected DomRenderer createRenderer() {
        return KuneFullDomWaveRendererImpl.create(getConversations(), getProfileManager(),
            getBlipDetailer(), getViewIdMapper(), getBlipQueue(), getThreadReadStateMonitor(),
            createViewFactories(), showParticipantsPanel);
      }

      @Override
      protected void installFeatures() {
        WavePanelResourceLoader.loadCss();
        // KuneWavePanelResourceLoader.loadCss();
      }
    });
  }

  @Override
  protected AsyncHolder<StageZero> createStageZeroLoader() {
    return haltIfClosed(super.createStageZeroLoader());
  }

  public void destroy() {
    if (wave != null) {
      waveStore.remove(wave);
      wave = null;
    }
    if (three != null) {
      three.getEditActions().stopEditing();
      three = null;
    }
    if (two != null) {
      two.getConnector().close();
      two = null;
    }
    if (one != null) {
      one.getWavePanel().destroy();
      one = null;
    }
    closed = true;
  }

  /**
   * @return a halting provider if this stage is closed. Otherwise, returns the
   *         given provider.
   */
  @SuppressWarnings("unchecked") // HALT is safe as a holder for any type
  private <T> AsyncHolder<T> haltIfClosed(final AsyncHolder<T> provider) {
    return closed ? (AsyncHolder<T>) HALT : provider;
  }

  private void handleExistingWave(final StageThree three) {
    // If there's blip reference then focus on that blip.
    final String documentId = waveRef.getDocumentId();
    if (documentId != null) {
      final ModelAsViewProvider views = two.getModelAsViewProvider();
      final BlipQueueRenderer blipQueue = two.getBlipQueue();
      final ConversationView wave = two.getConversations();
      blipQueue.flush();
      // Find conversation
      Conversation conversation;
      if (waveRef.hasWaveletId()) {
        final String id = ModernIdSerialiser.INSTANCE.serialiseWaveletId(waveRef.getWaveletId());
        conversation = wave.getConversation(id);
      } else {
        // Unspecified wavelet means root.
        conversation = wave.getRoot();
      }
      if (conversation != null) {
        // Find selected blip.
        final ConversationBlip blip = wave.getRoot().getBlip(documentId);
        if (blip != null) {
          final BlipView blipUi = views.getBlipView(blip);
          if (blipUi != null) {
            two.getStageOne().getFocusFrame().focus(blipUi);
          }
        }
      }
    }
  }

  private void initNewWave(final StageThree three) {
    // Do the new-wave flow.
    final ModelAsViewProvider views = two.getModelAsViewProvider();
    final BlipQueueRenderer blipQueue = two.getBlipQueue();
    final ConversationView wave = two.getConversations();

    // Force rendering to finish.
    blipQueue.flush();
    final BlipView blipUi = views.getBlipView(wave.getRoot().getRootThread().getFirstBlip());
    three.getEditActions().startEditing(blipUi);
  }

  private void onStageThreeLoaded(final StageThree x, final Accessor<StageThree> whenReady) {
    if (closed) {
      // Stop the loading process.
      return;
    }
    three = x;
    if (isNewWave) {
      initNewWave(x);
    } else {
      handleExistingWave(x);
    }
    wave = new WaveContext(
        two.getWave(), two.getConversations(), two.getSupplement(), two.getReadMonitor());
    waveStore.add(wave);
    whenReady.use(x);
  }
}
