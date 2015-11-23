package org.ourproject.massmob.client.ui;

import org.ourproject.massmob.client.StateManager;
import org.ourproject.massmob.client.ui.img.Images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.thezukunft.wave.connector.GadgetUpdateEvent;
import com.thezukunft.wave.connector.GadgetUpdateEventHandler;
import com.thezukunft.wave.connector.StateUpdateEvent;
import com.thezukunft.wave.connector.StateUpdateEventHandler;

import cc.kune.common.shared.res.ICalConstants;
import cc.kune.common.shared.utils.TextUtils;

public class DescriptionField extends Composite {

  interface DescriptionFieldUiBinder extends UiBinder<Widget, DescriptionField> {
  }

  private static DescriptionFieldUiBinder uiBinder = GWT.create(DescriptionFieldUiBinder.class);

  @UiField
  TextArea description;
  @UiField
  CustomDisclosure disclo;
  private final EventBus eventBus;
  private final Images img;
  private final StateManager stateManager;
  private String summary = "";
  private final Label summaryWidget;

  public DescriptionField(final EventBus eventBus, final StateManager stateManager, final Images img) {
    this.eventBus = eventBus;
    this.stateManager = stateManager;
    this.img = img;
    summaryWidget = new Label();
    summaryWidget.addStyleName("descrip-summary");
    initWidget(uiBinder.createAndBindUi(this));
    disclo.setOpen(stateManager.getDescOpen());
    disclo.setHeader(summaryWidget);
    eventBus.addHandler(GadgetUpdateEvent.TYPE, new GadgetUpdateEventHandler() {
      @Override
      public void onUpdate(final GadgetUpdateEvent event) {
        calcSummary(description.getValue());
        setTitleDescription();
      }
    });
    eventBus.addHandler(StateUpdateEvent.TYPE, new StateUpdateEventHandler() {
      @Override
      public void onUpdate(final StateUpdateEvent event) {
        update();
      }
    });
    update();
  }

  private void afterEdit() {
    final String newDescrip = description.getValue();
    if (!newDescrip.equals(stateManager.getValue(ICalConstants.DESCRIPTION))) {
      stateManager.setValue(ICalConstants.DESCRIPTION, newDescrip);
      calcSummary(newDescrip);
    }
  }

  private void calcSummary(final String text) {
    final int length = (int) ((disclo.getOffsetWidth() - 20) * 0.12);
    // Log.debug("offset: " + length);
    summary = TextUtils.ellipsis(text, length);
  }

  @UiFactory
  CustomDisclosure getDisclo() {
    return new CustomDisclosure(img);
  }

  @UiHandler("description")
  public void onBlur(final BlurEvent event) {
    afterEdit();
  }

  @UiHandler("description")
  public void onChange(final ChangeEvent event) {
    afterEdit();
  }

  @UiHandler("disclo")
  void onClose(final CloseEvent<DisclosurePanel> event) {
    updateGadgetSize();
    stateManager.setDescriptionOpen(false);
    setTitleDescription();
  }

  @UiHandler("disclo")
  void onOpen(final OpenEvent<DisclosurePanel> event) {
    updateGadgetSize();
    stateManager.setDescriptionOpen(true);
    setTitleDescription();
  }

  public void setEnabled(final boolean enabled) {
    description.setEnabled(enabled);
  }

  private void setTitleDescription() {
    if (disclo.isOpen()) {
      summaryWidget.setText("");
    } else {
      summaryWidget.setText(summary);
    }
  }

  public void update() {
    final String value = stateManager.getValue(ICalConstants.DESCRIPTION);
    description.setValue(value);
    calcSummary(value);
    setTitleDescription();
  }

  private void updateGadgetSize() {
    eventBus.fireEvent(new GadgetUpdateEvent());
  }

}
