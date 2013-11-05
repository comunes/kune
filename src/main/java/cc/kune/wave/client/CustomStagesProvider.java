// @formatter:off
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package cc.kune.wave.client;

import java.util.Set;

import org.waveprotocol.box.webclient.client.RemoteViewServiceMultiplexer;
import org.waveprotocol.box.webclient.client.StageTwoProvider;
import org.waveprotocol.box.webclient.search.WaveContext;
import org.waveprotocol.box.webclient.search.WaveStore;
import org.waveprotocol.box.webclient.widget.frame.FramedPanel;
import org.waveprotocol.wave.client.StageOne;
import org.waveprotocol.wave.client.StageThree;
import org.waveprotocol.wave.client.StageTwo;
import org.waveprotocol.wave.client.StageZero;
import org.waveprotocol.wave.client.Stages;
import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.common.util.AsyncHolder;
import org.waveprotocol.wave.client.common.util.AsyncHolder.Accessor;
import org.waveprotocol.wave.client.common.util.LogicalPanel;
import org.waveprotocol.wave.client.editor.EditorStaticDeps;
import org.waveprotocol.wave.client.wavepanel.impl.WavePanelImpl;
import org.waveprotocol.wave.client.wavepanel.impl.edit.Actions;
import org.waveprotocol.wave.client.wavepanel.impl.edit.EditController;
import org.waveprotocol.wave.client.wavepanel.impl.edit.EditSession;
import org.waveprotocol.wave.client.wavepanel.impl.edit.KeepFocusInView;
import org.waveprotocol.wave.client.wavepanel.impl.edit.i18n.ParticipantMessages;
import org.waveprotocol.wave.client.wavepanel.impl.focus.FocusBlipSelector;
import org.waveprotocol.wave.client.wavepanel.impl.focus.FocusFramePresenter;
import org.waveprotocol.wave.client.wavepanel.impl.focus.ViewTraverser;
import org.waveprotocol.wave.client.wavepanel.impl.indicator.ReplyIndicatorController;
import org.waveprotocol.wave.client.wavepanel.impl.menu.MenuController;
import org.waveprotocol.wave.client.wavepanel.impl.menu.i18n.MenuMessages;
import org.waveprotocol.wave.client.wavepanel.impl.reader.Reader;
import org.waveprotocol.wave.client.wavepanel.impl.title.WaveTitleHandler;
import org.waveprotocol.wave.client.wavepanel.impl.toolbar.ToolbarSwitcher;
import org.waveprotocol.wave.client.wavepanel.view.BlipView;
import org.waveprotocol.wave.client.wavepanel.view.dom.ModelAsViewProvider;
import org.waveprotocol.wave.client.wavepanel.view.dom.full.BlipQueueRenderer;
import org.waveprotocol.wave.client.widget.popup.PopupChromeFactory;
import org.waveprotocol.wave.client.widget.popup.PopupFactory;
import org.waveprotocol.wave.model.conversation.ConversationView;
import org.waveprotocol.wave.model.id.IdGenerator;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.WaveRef;

import cc.kune.common.client.log.Log;
import cc.kune.wave.client.kspecific.AurorisColorPicker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * Stages for loading the undercurrent Wave Panel.
 *
 * @author zdwang@google.com (David Wang)
 */
public class CustomStagesProvider extends Stages {

  /** The Constant HALT. */
  private final static AsyncHolder<Object> HALT = new AsyncHolder<Object>() {
    @Override
    public void call(final Accessor<Object> accessor) {
      // Never ready, so never notify the accessor.
    }
  };

  /**
   * Finds the blip that should receive the focus and selects it.
   *
   * @param reader the reader
   * @param views the views
   * @param wave the wave
   * @param focusFrame the focus frame
   * @param waveRef the wave ref
   */
  private static void selectAndFocusOnBlip(final Reader reader, final ModelAsViewProvider views,
      final ConversationView wave, final FocusFramePresenter focusFrame, final WaveRef waveRef) {
    final FocusBlipSelector blipSelector =
        FocusBlipSelector.create(wave, views, reader, new ViewTraverser());
    final BlipView blipUi = blipSelector.selectBlipByWaveRef(waveRef);
    // Focus on the selected blip.
    if (blipUi != null) {
      focusFrame.focus(blipUi);
    }
  }
  
  /** The channel. */
  private final RemoteViewServiceMultiplexer channel;
  
  /** The closed. */
  private boolean closed;
  
  /** The color picker. */
  private final Provider<AurorisColorPicker> colorPicker;
  
  /** The event bus. */
  private final EventBus eventBus;
  
  /** The id generator. */
  private final IdGenerator idGenerator;
  
  /** The is new wave. */
  private final boolean isNewWave;
  
  /** The local domain. */
  private final String localDomain;
  
  /** The one. */
  private StageOne one;
  
  /** The participants. */
  private final Set<ParticipantId> participants;
  
  /** The profiles. */
  private final ProfileManager profiles;

  /** The root panel. */
  private final LogicalPanel rootPanel;
  
  /** The three. */
  private StageThree three;
  
  /** The two. */
  private StageTwo two;
  
  /** The wave. */
  private WaveContext wave;
  
  /** The wave frame. */
  @SuppressWarnings("unused")
  private final FramedPanel waveFrame;

  /** The wave panel element. */
  private final Element wavePanelElement;

  /** The wave ref. */
  private final WaveRef waveRef;

  /** The wave store. */
  private final WaveStore waveStore;
  
  /** The wave unsaved indicator. */
  private final CustomSavedStateIndicator waveUnsavedIndicator;

  /**
   * Instantiates a new custom stages provider.
   *
   * @param wavePanelElement the DOM element to become the wave panel.
   * @param waveUnsavedIndicator the wave unsaved indicator
   * @param rootPanel a panel that this an ancestor of wavePanelElement. This is
   * used for adopting to the GWT widget tree.
   * @param waveFrame the wave frame.
   * @param waveRef the id of the wave to open. If null, it means, create a new
   * wave.
   * @param channel the communication channel.
   * @param idGenerator the id generator
   * @param profiles the profiles
   * @param store the store
   * @param isNewWave true if the wave is a new client-created wave
   * @param localDomain the local domain
   * @param participants the participants to add to the newly created wave. null
   * if only the creator should be added
   * @param eventBus the event bus
   * @param colorPicker the color picker
   */
  public CustomStagesProvider(final Element wavePanelElement, final CustomSavedStateIndicator waveUnsavedIndicator,
      final LogicalPanel rootPanel, final FramedPanel waveFrame, final WaveRef waveRef, final RemoteViewServiceMultiplexer channel,
      final IdGenerator idGenerator, final ProfileManager profiles, final WaveStore store, final boolean isNewWave,
      final String localDomain, final Set<ParticipantId> participants, final EventBus eventBus, final Provider<AurorisColorPicker> colorPicker) {
    this.waveUnsavedIndicator = waveUnsavedIndicator;
    this.wavePanelElement = wavePanelElement;
    this.waveFrame = waveFrame;
    this.rootPanel = rootPanel;
    this.waveRef = waveRef;
    this.channel = channel;
    this.idGenerator = idGenerator;
    this.profiles = profiles;
    this.waveStore = store;
    this.isNewWave = isNewWave;
    this.localDomain = localDomain;
    this.participants = participants;
    this.eventBus = eventBus;
    this.colorPicker = colorPicker;
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.Stages#createStageOneLoader(org.waveprotocol.wave.client.StageZero)
   */
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

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.Stages#createStageThreeLoader(org.waveprotocol.wave.client.StageTwo)
   */
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

      @Override
      protected void install() {
        // FIXME kune revise this (in StageThree)
        EditorStaticDeps.setPopupProvider(PopupFactory.getProvider());
        EditorStaticDeps.setPopupChromeProvider(PopupChromeFactory.getProvider());

        // Eagerly install some features.
        final WavePanelImpl panel = stageTwo.getStageOne().getWavePanel();
        final FocusFramePresenter focus = stageTwo.getStageOne().getFocusFrame();
        final ParticipantId user = stageTwo.getSignedInUser();
        final ModelAsViewProvider models = stageTwo.getModelAsViewProvider();
        final ProfileManager profiles = stageTwo.getProfileManager();

        final MenuMessages menuMessages = GWT.create(MenuMessages.class);
        final ParticipantMessages participantMessages = GWT.create(ParticipantMessages.class);

        final Actions actions = getEditActions();
        final EditSession edit = getEditSession();
        MenuController.install(actions, panel, menuMessages);
        ToolbarSwitcher.install(stageTwo.getStageOne().getWavePanel(), getEditSession(),
            getViewToolbar(), getEditToolbar());
        WaveTitleHandler.install(edit, models);
        ReplyIndicatorController.install(actions, edit, panel);
        EditController.install(focus, actions, panel);
        CustomParticipantController.install(panel, models, profiles, getLocalDomain(), user,  participantMessages, eventBus);
        KeepFocusInView.install(edit, panel);
        stageTwo.getDiffController().upgrade(edit);
        colorPicker.get();
      }
    });
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.Stages#createStageTwoLoader(org.waveprotocol.wave.client.StageOne)
   */
  @Override
  protected AsyncHolder<StageTwo> createStageTwoLoader(final StageOne one) {
    return haltIfClosed(new StageTwoProvider(this.one = one, waveRef, channel, isNewWave,
      idGenerator, profiles, waveUnsavedIndicator, participants));
  };

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.Stages#createStageZeroLoader()
   */
  @Override
  protected AsyncHolder<StageZero> createStageZeroLoader() {
    return haltIfClosed(super.createStageZeroLoader());
  }

  /**
   * Destroy.
   */
  public void destroy() {
    Log.info("Destroy wave");
    if (wave != null) {
      waveStore.remove(wave);
      wave = null;
    }
    if (three != null) {
      try {
        three.getEditActions().stopEditing();
      } catch (final Exception e) {
        Log.warn("A exception stopping editing", e);
      }
      three = null;
    }
    if (two != null) {
      try {
        two.getConnector().close();
      } catch (final Exception e) {
        Log.warn("A exception closing connector", e);
      }
      two = null;
    }
    if (one != null) {
      try {
        one.getWavePanel().destroy();
      } catch (final Exception e) {
        Log.warn("A exception destroying panel", e);
      }
      one = null;
    }
    closed = true;
  }

  /**
   * Halt if closed.
   *
   * @param <T> the generic type
   * @param provider the provider
   * @return a halting provider if this stage is closed. Otherwise, returns the
   * given provider.
   */
  @SuppressWarnings("unchecked") // HALT is safe as a holder for any type
  private <T> AsyncHolder<T> haltIfClosed(final AsyncHolder<T> provider) {
    Log.info("Halt if closed?: " + closed + " provider: " + provider);
    return closed ? (AsyncHolder<T>) HALT : provider;
  }

  /**
   * Handle existing wave.
   *
   * @param three the three
   */
  private void handleExistingWave(final StageThree three) {
  Log.info("Handle existing wave");
  if (waveRef.hasDocumentId()) {
    final BlipQueueRenderer blipQueue = two.getBlipQueue();
    blipQueue.flush();
    selectAndFocusOnBlip(two.getReader(), two.getModelAsViewProvider(), two.getConversations(),
        one.getFocusFrame(), waveRef);
  }
  }
  
  /**
   * Inits the new wave.
   *
   * @param three the three
   */
  private void initNewWave(final StageThree three) {
    Log.info("Init new wave");
    // Do the new-wave flow.
    final ModelAsViewProvider views = two.getModelAsViewProvider();
    final BlipQueueRenderer blipQueue = two.getBlipQueue();
    final ConversationView wave = two.getConversations();

    // Force rendering to finish.
    blipQueue.flush();
    final BlipView blipUi = views.getBlipView(wave.getRoot().getRootThread().getFirstBlip());
    three.getEditActions().startEditing(blipUi);
  }

  /**
 * A hook to install features that are not dependent an a certain stage.
 */
  protected void install() {
  // WindowTitleHandler.install(waveStore, waveFrame);
  }


  /**
   * On stage three loaded.
   *
   * @param x the x
   * @param whenReady the when ready
   */
  private void onStageThreeLoaded(final StageThree x, final Accessor<StageThree> whenReady) {
    Log.info("On stage three loaded");
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
    install();
    whenReady.use(x);
  }

}
