package org.ourproject.massmob.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import cc.kune.common.client.tooltip.Tooltip;

public class ParticipantAnswer extends Composite {

  interface ParticipantAnswerUiBinder extends UiBinder<Widget, ParticipantAnswer> {
  }

  private static ParticipantAnswerUiBinder uiBinder = GWT.create(ParticipantAnswerUiBinder.class);

  @UiField
  Image avatar;
  @UiField
  Label name;
  @UiField
  Label status;

  public ParticipantAnswer(final String url, final String name, final String tooltip, final String status) {
    initWidget(uiBinder.createAndBindUi(this));
    this.avatar.setUrl(url);
    this.name.setText(name);
    this.status.setText(status);
    Tooltip.to(this.name, tooltip);
    Tooltip.to(this.avatar, tooltip);
  }

  public void setStatus(final String status) {
    this.status.setText(status);
  }
}
