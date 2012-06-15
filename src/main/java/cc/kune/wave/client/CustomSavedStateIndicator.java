// @formatter:off
/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cc.kune.wave.client;

import org.waveprotocol.wave.client.scheduler.Scheduler;
import org.waveprotocol.wave.client.scheduler.SchedulerInstance;
import org.waveprotocol.wave.client.scheduler.TimerService;
import org.waveprotocol.wave.concurrencycontrol.common.UnsavedDataListener;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.IconLabelDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.i18n.I18n;
import cc.kune.core.client.sn.actions.SessionAction;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class CustomSavedStateIndicator implements UnsavedDataListener, Provider<GuiActionDescrip> {

  private enum SavedState {
    SAVED, UNSAVED;
  }

  private static final int UPDATE_DELAY_MS = 300;
  private static final int UPDATE_UNSAVED_DELAY_MS = 10;

  private final Scheduler.Task updateTask = new Scheduler.Task() {
    @Override
    public void execute() {
      updateDisplay();
    }
  };


  public static class WaveSaveAction extends SessionAction {

    @Inject
    public WaveSaveAction(final Session session, final I18nTranslationService i18n) {
      super(session, true);
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      // Do nothing
    }

  }



  private final TimerService scheduler;

  private SavedState visibleSavedState = SavedState.SAVED;
  private SavedState currentSavedState = null;
  private IconLabelDescriptor status;

  /**
   * Simple saved state indicator.
   *
   * @author danilatos@google.com (Daniel Danilatos)
   * @author yurize@apache.org (Yuri Zelikov)
   */
  @Inject
  public CustomSavedStateIndicator(WaveSaveAction action){
    this.scheduler = SchedulerInstance.getLowPriorityTimer();
    status = new IconLabelDescriptor(action);
    status.withStyles("k-unsave-status, k-fr");
  }

  public void saved() {
    maybeUpdateDisplay();
  }

  public void unsaved() {
    maybeUpdateDisplay();
  }

  private void maybeUpdateDisplay() {
    if (needsUpdating()) {
      switch (currentSavedState) {
      case SAVED:
        scheduler.scheduleDelayed(updateTask, UPDATE_DELAY_MS);
        break;
      case UNSAVED:
        scheduler.scheduleDelayed(updateTask, UPDATE_UNSAVED_DELAY_MS);
        updateDisplay();
        break;
      default:
        throw new AssertionError("unknown " + currentSavedState);
      }
    } else {
      scheduler.cancel(updateTask);
    }
  }

  private boolean needsUpdating() {
    return visibleSavedState != currentSavedState;
  }

  private void updateDisplay() {
    visibleSavedState = currentSavedState;
    switch (visibleSavedState) {
    case SAVED:
      status.withText("");
      break;
    case UNSAVED:
      status.withText(I18n.t("Saving"));
      break;
    default:
      throw new AssertionError("unknown " + currentSavedState);
    }
  }

  @Override
  public void onUpdate(UnsavedDataInfo unsavedDataInfo) {
    if (unsavedDataInfo.estimateUnacknowledgedSize() != 0) {
      currentSavedState = SavedState.UNSAVED;
      unsaved();
    } else {
      currentSavedState = SavedState.SAVED;
      saved();
    }
  }

  @Override
  public void onClose(boolean everythingCommitted) {
    if (everythingCommitted) {
      saved();
    } else {
      unsaved();
    }
  }

  @Override
  public GuiActionDescrip get() {
    return status;
  }
}
