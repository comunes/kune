package cc.kune.gadgetsampletester.client;

import cc.kune.gadgetsample.client.KuneGadgetSampleMainPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.thezukunft.wave.connector.ModeChangeEvent;
import com.thezukunft.wave.connectormock.WaveMock;

public class KuneGadgetSampleTesterEntryPoint implements EntryPoint {

  @Override
  public void onModuleLoad() {
    final KuneGadgetSampleGinInjector gin = GWT.create(KuneGadgetSampleGinInjector.class);

    final WaveMock waveMock = (WaveMock) gin.getWave();

    // We initialize some participants
    waveMock.initRandomParticipants();

    // We have to create the gadget using gin so it can use injection of its dependencies (evenBus, etc)

    final KuneGadgetSampleMainPanel gadget = gin.getMainPanel();
    final KuneGadgetSampleMainPanel gadget2 = gin.getMainPanel();

    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        RootPanel.get().add(new Label ("Gadget of user 1"));
        RootPanel.get().add(gadget);
        RootPanel.get().add(new Label ("Gadget of user 2"));
        RootPanel.get().add(gadget2);
        gin.getEventBus().fireEvent(new ModeChangeEvent(0));
      }
    });
  }

}
