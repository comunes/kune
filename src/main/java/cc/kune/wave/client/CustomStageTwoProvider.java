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

import java.util.Set;

import org.waveprotocol.box.webclient.client.RemoteViewServiceMultiplexer;
import org.waveprotocol.box.webclient.client.RemoteWaveViewService;
import org.waveprotocol.box.webclient.client.Session;
import org.waveprotocol.wave.client.StageOne;
import org.waveprotocol.wave.client.StageTwo;
import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.common.util.AsyncHolder;
import org.waveprotocol.wave.concurrencycontrol.channel.WaveViewService;
import org.waveprotocol.wave.concurrencycontrol.common.UnsavedDataListener;
import org.waveprotocol.wave.model.id.IdGenerator;
import org.waveprotocol.wave.model.schema.SchemaProvider;
import org.waveprotocol.wave.model.schema.conversation.ConversationSchemas;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.wave.data.WaveViewData;
import org.waveprotocol.wave.model.wave.data.impl.WaveViewDataImpl;
import org.waveprotocol.wave.model.waveref.WaveRef;

import cc.kune.common.client.log.Log;

import com.google.common.base.Preconditions;
import com.google.gwt.user.client.Command;

// TODO: Auto-generated Javadoc
/**
 * Provides stage 2 of the staged loading of the wave panel.
 *
 * @author zdwang@google.com (David Wang)
 */
public class CustomStageTwoProvider extends StageTwo.DefaultProvider {

  /** The wave ref. */
  private final WaveRef waveRef;
  
  /** The channel. */
  private final RemoteViewServiceMultiplexer channel;
  
  /** The is new wave. */
  private final boolean isNewWave;
  // TODO: Remove this after WebClientBackend is deleted.
  /** The id generator. */
  private final IdGenerator idGenerator;

  // shared across other client components
  /** The profiles. */
  private final ProfileManager profiles;

  /**
   * Continuation to progress to the next stage. This will disappear with the
   * new protocol.
   */
  private AsyncHolder.Accessor<StageTwo> whenReady;
  
  /** The other participants. */
  private final Set<ParticipantId> otherParticipants;

  /**
   * Instantiates a new custom stage two provider.
   *
   * @param stageOne the stage one
   * @param waveRef the wave ref
   * @param channel communication channel
   * @param isNewWave the is new wave
   * @param idGenerator the id generator
   * @param profiles the profiles
   * @param unsavedDataListener the unsaved data listener
   * @param otherParticipants the other participants
   */
  public CustomStageTwoProvider(StageOne stageOne, WaveRef waveRef, RemoteViewServiceMultiplexer channel,
      boolean isNewWave, IdGenerator idGenerator, ProfileManager profiles,
      UnsavedDataListener unsavedDataListener, Set<ParticipantId> otherParticipants) {
    super(stageOne, unsavedDataListener);
    Preconditions.checkArgument(stageOne != null);
    Preconditions.checkArgument(waveRef != null);
    Preconditions.checkArgument(waveRef.getWaveId() != null);
    this.waveRef = waveRef;
    this.channel = channel;
    this.isNewWave = isNewWave;
    this.idGenerator = idGenerator;
    this.profiles = profiles;
    this.otherParticipants = otherParticipants;
    Log.info("StageTwo: created");
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.StageTwo.DefaultProvider#createSchemas()
   */
  @Override
  protected SchemaProvider createSchemas() {
    Log.info("StageTwo: create schemas");
    return new ConversationSchemas();
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.StageTwo.DefaultProvider#createSessionId()
   */
  @Override
  public String createSessionId() {
    Log.info("StageTwo: create session id");
    return Session.get().getIdSeed();
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.StageTwo.DefaultProvider#createIdGenerator()
   */
  @Override
  protected IdGenerator createIdGenerator() {
    Log.info("StageTwo: create id generator");
    return idGenerator;
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.StageTwo.DefaultProvider#createSignedInUser()
   */
  @Override
  protected ParticipantId createSignedInUser() {
    Log.info("StageTwo: create signed in user " + Session.get().getAddress());
    return ParticipantId.ofUnsafe(Session.get().getAddress());
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.StageTwo.DefaultProvider#createWaveViewService()
   */
  @Override
  protected WaveViewService createWaveViewService() {
    Log.info("StageTwo: create wave view service");
    return new RemoteWaveViewService(waveRef.getWaveId(), channel, getDocumentRegistry());
  }

  /**
   * Swaps order of open and render.
   */
  @Override
  protected void install() {
    if (isNewWave) {
      Log.info("StageTwo: Is new wave");
      // For a new wave, initial state comes from local initialization.
      getConversations().createRoot().getRootThread().appendBlip();

      // Adding any initial participant to the new wave
      getConversations().getRoot().addParticipantIds(otherParticipants);
      super.install();
      whenReady.use(CustomStageTwoProvider.this);
    } else {
      // For an existing wave, while we're still using the old protocol,
      // rendering must be delayed until the channel is opened, because the
      // initial state snapshots come from the channel.
      Log.info("StageTwo: ino not new wave");
      getConnector().connect(new Command() {
        @Override
        public void execute() {
          // This code must be kept in sync with the default install()
          // method, but excluding the connect() call.

          // Install diff control before rendering, because logical diff state
          // may
          // need to be adjusted due to arbitrary UI policies.
          getDiffController().install();
          Log.info("StageTwo: diff install");
          // Ensure the wave is rendered.
          stageOne.getDomAsViewProvider().setRenderer(getRenderer());
          ensureRendered();

          Log.info("StageTwo: install features");
          // Install eager UI.
          installFeatures();

          // Rendering, and therefore the whole stage is now ready.
          whenReady.use(CustomStageTwoProvider.this);
        }
      });
    }
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.StageTwo.DefaultProvider#createProfileManager()
   */
  @Override
  protected ProfileManager createProfileManager() {
    return profiles;
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.StageTwo.DefaultProvider#create(org.waveprotocol.wave.client.common.util.AsyncHolder.Accessor)
   */
  @Override
  protected void create(final AsyncHolder.Accessor<StageTwo> whenReady) {
    this.whenReady = whenReady;
    Log.info("StageTwo: create");
    super.create(new AsyncHolder.Accessor<StageTwo>() {
      @Override
      public void use(StageTwo x) {
        // Delay progression until rendering is ready.
      }
    });
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.StageTwo.DefaultProvider#fetchWave(org.waveprotocol.wave.client.common.util.AsyncHolder.Accessor)
   */
  @Override
  protected void fetchWave(final AsyncHolder.Accessor<WaveViewData> whenReady) {
    Log.info("StageTwo: fetch wave");
    whenReady.use(WaveViewDataImpl.create(waveRef.getWaveId()));
  }
}
