package org.ourproject.massmob.client.ui;

import org.ourproject.massmob.client.CustomConstants;
import org.ourproject.massmob.client.StateManager;
import org.ourproject.massmob.client.ui.EditEvent.EditHandler;
import org.ourproject.massmob.client.ui.img.Images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.thezukunft.wave.connector.GadgetUpdateEvent;
import com.thezukunft.wave.connector.Participant;
import com.thezukunft.wave.connector.ParticipantUpdateEvent;
import com.thezukunft.wave.connector.ParticipantUpdateEventHandler;
import com.thezukunft.wave.connector.StateUpdateEvent;
import com.thezukunft.wave.connector.StateUpdateEventHandler;
import com.thezukunft.wave.connector.Wave;

public class Assistance extends Composite {

  interface AssistanceUiBinder extends UiBinder<Widget, Assistance> {
  }

  private static AssistanceUiBinder uiBinder = GWT.create(AssistanceUiBinder.class);

  private final AssistanceTitleSumData data;
  @UiField
  CustomDisclosure disclo;
  private final EventBus eventBus;
  @UiField
  AssistanceHeader header;
  private final AssistanceTitleSum headerSum;
  private final Images img;

  @UiField
  FlowPanel maybep;
  @UiField
  FlowPanel nop;
  @UiField
  SetMyStatus setMyStatus;
  private final StateManager stateManager;
  private final Wave wave;
  @UiField
  FlowPanel yesp;

  public Assistance(final EventBus eventBus, final Wave wave, final StateManager stateManager,
      final Images img) {
    this.eventBus = eventBus;
    this.wave = wave;
    this.stateManager = stateManager;
    this.img = img;
    initWidget(uiBinder.createAndBindUi(this));
    data = new AssistanceTitleSumData();
    headerSum = new AssistanceTitleSum(data);
    final boolean assistantOpen = stateManager.getAssistantOpen();
    headerSum.setVisible(!assistantOpen);
    disclo.setHeader(headerSum);
    disclo.setOpen(assistantOpen);
    eventBus.addHandler(StateUpdateEvent.TYPE, new StateUpdateEventHandler() {

      @Override
      public void onUpdate(final StateUpdateEvent event) {
        updateView(wave.getParticipants());
      }
    });
    eventBus.addHandler(ParticipantUpdateEvent.TYPE, new ParticipantUpdateEventHandler() {
      @Override
      public void onUpdate(final ParticipantUpdateEvent event) {
        updateView(wave.getParticipants());
      }
    });
    updateView(wave.getParticipants());
    setMyStatus.setValue(stateManager.getStatus(wave.getViewer()));
    setMyStatus.addEditHandler(new EditHandler() {
      @Override
      public void fire(final EditEvent event) {
        stateManager.setStatus(event.getText());
      }
    });
  }

  public void addMaybe(final ParticipantAnswer part) {
    data.maybe++;
    maybep.insert(part, 0);
  }

  public void addNo(final ParticipantAnswer part) {
    data.no++;
    nop.insert(part, 0);
  }

  public void addYes(final ParticipantAnswer part) {
    data.yes++;
    yesp.insert(part, 0);
  }

  @UiFactory
  CustomDisclosure getDisclo() {
    return new CustomDisclosure(img);
  }

  @UiFactory
  AssistanceHeader makeAssitHeader() {
    return new AssistanceHeader(stateManager);
  }

  @UiHandler("disclo")
  void onClose(final CloseEvent<DisclosurePanel> event) {
    headerSum.setVisible(true);
    updateGadgetSize();
    stateManager.setAssistanceOpen(false);
  }

  @UiHandler("disclo")
  void onOpen(final OpenEvent<DisclosurePanel> event) {
    headerSum.setVisible(false);
    updateGadgetSize();
    stateManager.setAssistanceOpen(true);
  }

  private void reset() {
    data.reset();
    yesp.clear();
    nop.clear();
    maybep.clear();
  }

  public void setEnabled(final boolean enabled) {
    setMyStatus.setEnabled(enabled);
  }

  private void updateGadgetSize() {
    eventBus.fireEvent(new GadgetUpdateEvent());
  }

  private void updateView(final JsArray<Participant> participants) {
    reset();
    for (int i = 0; i < participants.length(); i++) {
      data.total++;
      final Participant part = participants.get(i);
      final String answer = stateManager.getAnswer(part);
      if (part.getId().equals(wave.getViewer().getId())) {
        setMyStatus.setVisible(answer != null);
        updateGadgetSize();
      }
      if (answer == null) {
        data.noyet++;
      } else {
        final String status = stateManager.getStatus(part);
        final String shortName = part.getDisplayName().split("@")[0]; // ParticipantId.DOMAIN_PREFIX
        final ParticipantAnswer partAnswer = new ParticipantAnswer(part.getThumbnailUrl(), shortName,
            part.getDisplayName(), status);
        if (answer.equals(CustomConstants.YES)) {
          addYes(partAnswer);
        } else if (answer.equals(CustomConstants.NO)) {
          addNo(partAnswer);
        } else if (answer.equals(CustomConstants.MAYBE)) {
          addMaybe(partAnswer);
        }
      }
    }
    headerSum.set(data);
  }
}
