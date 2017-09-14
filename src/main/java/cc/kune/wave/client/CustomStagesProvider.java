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
 *   http://www.apache.org/licenses/LICENSE-2.0
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
import org.waveprotocol.wave.client.wavepanel.view.dom.full.ViewFactory;
import org.waveprotocol.wave.client.widget.popup.PopupChromeFactory;
import org.waveprotocol.wave.client.widget.popup.PopupFactory;
import org.waveprotocol.wave.model.conversation.ConversationView;
import org.waveprotocol.wave.model.document.WaveContext;
import org.waveprotocol.wave.model.id.IdGenerator;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.WaveRef;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.ui.EditableLabel;
import cc.kune.wave.client.kspecific.AurorisColorPicker;

/**
 * Stages for loading the undercurrent Wave Panel
 *
 * @author zdwang@google.com (David Wang)
 */
public class CustomStagesProvider extends Stages {

  private final static AsyncHolder<Object> HALT = new AsyncHolder<Object>() {
    @Override
    public void call(Accessor<Object> accessor) {
      // Never ready, so never notify the accessor.
    }
  };

  private final Element wavePanelElement;
  private final CustomSavedStateIndicator waveUnsavedIndicator;
  private final LogicalPanel rootPanel;
  private final WaveRef waveRef;
  private final RemoteViewServiceMultiplexer channel;
  private final IdGenerator idGenerator;
  private final ProfileManager profiles;
  private final WaveStore waveStore;
  private final boolean isNewWave;
  private final String localDomain;

  private boolean closed;
  private StageOne one;
  private StageTwo two;
  private StageThree three;
  private WaveContext wave;

  private Set<ParticipantId> participants;

  private final Provider<AurorisColorPicker> colorPicker;
  private final CustomEditToolbar customEditToolbar;
  private final EditableLabel editableTitle;
  private final EventBus eventBus;
  private final ViewFactory viewFactory;



  /**
   * @param wavePanelElement the DOM element to become the wave panel.
   * @param unsavedIndicatorElement the element that displays the wave saved state.
   * @param rootPanel a panel that this an ancestor of wavePanelElement. This is
   *        used for adopting to the GWT widget tree.
   * @param waveFrame the wave frame.
   * @param waveRef the id of the wave to open. If null, it means, create a new
   *        wave.
   * @param channel the communication channel.
   * @param isNewWave true if the wave is a new client-created wave
   * @param idGenerator
   * @param participants the participants to add to the newly created wave. null
   *                     if only the creator should be added
   */
  public CustomStagesProvider(Element wavePanelElement, CustomSavedStateIndicator waveUnsavedIndicator,
      LogicalPanel rootPanel, EditableLabel editableTitle, WaveRef waveRef, RemoteViewServiceMultiplexer channel,
      IdGenerator idGenerator, ProfileManager profiles, WaveStore store, boolean isNewWave,
      String localDomain, Set<ParticipantId> participants, EventBus eventBus, Provider<AurorisColorPicker> colorPicker, CustomEditToolbar customEditToolbar, ViewFactory viewFactory) {
    this.wavePanelElement = wavePanelElement;
    this.waveUnsavedIndicator = waveUnsavedIndicator;
    this.rootPanel = rootPanel;
    this.waveRef = waveRef;
    this.channel = channel;
    this.idGenerator = idGenerator;
    this.profiles = profiles;
    this.waveStore = store;
    this.isNewWave = isNewWave;
    this.localDomain = localDomain;
    this.participants = participants;
    this.editableTitle = editableTitle;
    this.eventBus = eventBus;
    this.colorPicker = colorPicker;
    this.customEditToolbar = customEditToolbar;
    this.viewFactory = viewFactory;
  }

  @Override
  protected AsyncHolder<StageZero> createStageZeroLoader() {
    return haltIfClosed(super.createStageZeroLoader());
  }

  @Override
  protected AsyncHolder<StageOne> createStageOneLoader(StageZero zero) {
    return haltIfClosed(new StageOne.DefaultProvider(zero) {
      @Override
      protected Element createWaveHolder() {
        return wavePanelElement;
      }

      @Override
      protected LogicalPanel createWaveContainer() {
        return rootPanel;
      }
    });
  }

  @Override
  protected AsyncHolder<StageTwo> createStageTwoLoader(StageOne one) {
    return haltIfClosed(new StageTwoProvider(this.one = one, waveRef, channel, isNewWave,
        idGenerator, profiles, waveUnsavedIndicator, participants) {

      @Override
      protected ViewFactory createViewFactories() {
          return viewFactory;
        }});
  };

  @Override
  protected AsyncHolder<StageThree> createStageThreeLoader(final StageTwo two) {
    return haltIfClosed(new StageThree.DefaultProvider(this.two = two) {
      @Override
      protected void create(final Accessor<StageThree> whenReady) {
        // Prepend an init wave flow onto the stage continuation.
        super.create(new Accessor<StageThree>() {
          @Override
          public void use(StageThree x) {
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

        final FocusBlipSelector blipSelector =
            FocusBlipSelector.create(stageTwo.getConversations(), stageTwo.getModelAsViewProvider(), stageTwo.getReader(), new ViewTraverser());
        CustomEditController.install(focus, actions, edit,panel, blipSelector, customEditToolbar);
      }
    });
  }

  private void onStageThreeLoaded(StageThree x, Accessor<StageThree> whenReady) {
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

  private void initNewWave(StageThree three) {
    Log.info("Init new wave");
    // Do the new-wave flow.
    ModelAsViewProvider views = two.getModelAsViewProvider();
    BlipQueueRenderer blipQueue = two.getBlipQueue();
    ConversationView wave = two.getConversations();

    // Force rendering to finish.
    blipQueue.flush();
    BlipView blipUi = views.getBlipView(wave.getRoot().getRootThread().getFirstBlip());
    three.getEditActions().startEditing(blipUi);
  }

  private void handleExistingWave(StageThree three) {
    Log.info("Handle existing wave");
    if (waveRef.hasDocumentId()) {
      BlipQueueRenderer blipQueue = two.getBlipQueue();
      blipQueue.flush();
      selectAndFocusOnBlip(two.getReader(), two.getModelAsViewProvider(), two.getConversations(),
          one.getFocusFrame(), waveRef);
    }
  }

  /**
   * A hook to install features that are not dependent an a certain stage.
   */
  protected void install() {
    CustomWindowTitleHandler.install(waveStore, editableTitle);
  }

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
   * Finds the blip that should receive the focus and selects it.
   */
  private static void selectAndFocusOnBlip(Reader reader, ModelAsViewProvider views,
      ConversationView wave, FocusFramePresenter focusFrame, WaveRef waveRef) {
    FocusBlipSelector blipSelector =
        FocusBlipSelector.create(wave, views, reader, new ViewTraverser());
    BlipView blipUi = blipSelector.selectBlipByWaveRef(waveRef);
    // Focus on the selected blip.
    if (blipUi != null) {
      focusFrame.focus(blipUi);
    }
  }

  /**
   * @return a halting provider if this stage is closed. Otherwise, returns the
   *         given provider.
   */
  @SuppressWarnings("unchecked") // HALT is safe as a holder for any type
  private <T> AsyncHolder<T> haltIfClosed(AsyncHolder<T> provider) {
    Log.info("Halt?: " + closed + ", if not provider: " + provider.getClass());
    return closed ? (AsyncHolder<T>) HALT : provider;
  }
}
