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

@Gadget.ModulePrefs( //
title = "Kune gadget sample", //
author = "The kune development team", //
author_link = "http://kune.ourproject.org", //
height = 640// , //
// Default width 320
// Commented only to use 100% width=550 (WAVE-309)//
)
@Gadget.InjectContent(files = { "ModuleContent.txt" })
public class KuneGadgetSample extends WaveGadget<UserPreferences> implements NeedsWave, EntryPoint,
    NeedsDynamicHeight, NeedsSetPrefs {

  // private MassmobPreferences userPrefsFeature;

  private DynamicHeightFeature dynHeightFeature;
  protected KuneGadgetSampleDeployGinjector gin;
  private SetPrefsFeature setPrefsFeature;

  private void checkReady() {
    if (dynHeightFeature != null && setPrefsFeature != null) {
      initGadget();
    }
  }

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
        RootPanel.get().add(mainPanel);
        setHeight(mainPanel);
      }
    });
  }

  @Override
  protected void init(final UserPreferences preferences) {
  }

  private void initGadget() {
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      private Timer timer;

      @Override
      public void execute() {
        if (getWave().isInWaveContainer()) {
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

  @Override
  public void initializeFeature(final DynamicHeightFeature dynHeightFeature) {
    this.dynHeightFeature = dynHeightFeature;
    checkReady();
  }

  @Override
  public void initializeFeature(final SetPrefsFeature feature) {
    this.setPrefsFeature = feature;
    checkReady();
  }

  private void setHeight(final KuneGadgetSampleMainPanel gadget) {
    dynHeightFeature.adjustHeight();
  }
}
