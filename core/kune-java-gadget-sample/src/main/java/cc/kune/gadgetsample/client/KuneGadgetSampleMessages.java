package cc.kune.gadgetsample.client;

import com.google.gwt.i18n.client.Messages;

public interface KuneGadgetSampleMessages extends Messages {

  @DefaultMessage("Gadget of participant {0}")
  String gadgetOfParticipant(String username);

  String lock();

  String unlock();
}