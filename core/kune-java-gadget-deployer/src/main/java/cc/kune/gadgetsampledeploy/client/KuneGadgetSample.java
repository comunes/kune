/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under 
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
package cc.kune.gadgetsampledeploy.client;

import cc.kune.gadgetsample.client.KuneGadgetSampleMainPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.gadgets.client.DynamicHeightFeature;
import com.google.gwt.gadgets.client.Gadget;
import com.google.gwt.gadgets.client.NeedsDynamicHeight;
import com.google.gwt.gadgets.client.NeedsSetPrefs;
import com.google.gwt.gadgets.client.SetPrefsFeature;
import com.google.gwt.gadgets.client.UserPreferences;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.thezukunft.wave.connector.GadgetUpdateEvent;
import com.thezukunft.wave.connector.GadgetUpdateEventHandler;
import com.thezukunft.wave.connectorimpl.NeedsWave;
import com.thezukunft.wave.connectorimpl.WaveGINWrapper;
import com.thezukunft.wave.connectorimpl.WaveGadget;

// TODO: Auto-generated Javadoc
/**
 * The Class KuneGadgetSample.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Gadget.ModulePrefs( //
title = "Kune gadget sample", //
author = "The kune development team", //
author_link = "http://kune.ourproject.org", //
height = 640// , //
// width=550 //
// Commented only to use 100% width (see WAVE-309)//
)
@Gadget.InjectContent(files = { "ModuleContent.txt" })
public class KuneGadgetSample extends WaveGadget<UserPreferences> implements NeedsWave, EntryPoint,
    NeedsDynamicHeight, NeedsSetPrefs {

  // private MassmobPreferences userPrefsFeature;

  /** The dyn height feature. */
  private DynamicHeightFeature dynHeightFeature;

  /** The gin. */
  protected KuneGadgetSampleDeployGinjector gin;

  /** The set prefs feature. */
  private SetPrefsFeature setPrefsFeature;

  /**
   * Check ready.
   */
  private void checkReady() {
    if (dynHeightFeature != null && setPrefsFeature != null) {
      initGadget();
    }
  }

  /**
   * Creates the ui objects.
   */
  private void createUIObjects() {
    gin = GWT.create(KuneGadgetSampleDeployGinjector.class);
    final WaveGINWrapper w = (WaveGINWrapper) gin.getWave();
    w.setWave(getWave());

    final KuneGadgetSampleMainPanel mainPanel = gin.getMainPanel();

    gin.getEventBus().addHandler(GadgetUpdateEvent.TYPE, new GadgetUpdateEventHandler() {
      @Override
      public void onUpdate(final GadgetUpdateEvent event) {
        setHeight(mainPanel);
      }
    });
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        dynHeightFeature.getContentDiv().add(mainPanel);
        setHeight(mainPanel);
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.gadgets.client.Gadget#init(com.google.gwt.gadgets.client
   * .UserPreferences)
   */
  @Override
  protected void init(final UserPreferences preferences) {
  }

  /**
   * Inits the gadget.
   */
  private void initGadget() {
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      private Timer timer;

      @Override
      public void execute() {
        if (getWave().isInWaveContainer()) {
          // We only create and initialize the gadget panel when we are running
          // in a the wave container. If not we wait.
          timer = new Timer() {
            @Override
            public void run() {
              if (getWave().getState() == null) {
                timer.schedule(100);
              } else {
                // Ok state ready, create widget
                timer.cancel();
                createUIObjects();
              }
            }
          };
          timer.run();
        } else {
          GWT.log("The gadget is not running in a wave container");
        }
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.gadgets.client.NeedsDynamicHeight#initializeFeature(com.
   * google.gwt.gadgets.client.DynamicHeightFeature)
   */
  @Override
  public void initializeFeature(final DynamicHeightFeature dynHeightFeature) {
    this.dynHeightFeature = dynHeightFeature;
    checkReady();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.gadgets.client.NeedsSetPrefs#initializeFeature(com.google
   * .gwt.gadgets.client.SetPrefsFeature)
   */
  @Override
  public void initializeFeature(final SetPrefsFeature feature) {
    this.setPrefsFeature = feature;
    checkReady();
  }

  /**
   * Sets the height.
   * 
   * @param gadget
   *          the new height
   */
  private void setHeight(final KuneGadgetSampleMainPanel gadget) {
    dynHeightFeature.adjustHeight();
  }
}
