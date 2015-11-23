package org.ourproject.massmob.client.ui;

import org.ourproject.massmob.client.CustomConstants;
import org.ourproject.massmob.client.StateManager;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import cc.kune.common.client.notify.NotifyUser;

public class AssistanceHeader extends Composite {

  interface AssistanceHeaderUiBinder extends UiBinder<Widget, AssistanceHeader> {
  }

  interface RPStyle extends CssResource {
    String click();

    String out();

    String over();
  }

  private static final String DEF_TITLE = "will you assist? click to answer";

  private static AssistanceHeaderUiBinder uiBinder = GWT.create(AssistanceHeaderUiBinder.class);

  @UiField
  Label maybel;
  @UiField
  Label nol;
  private final StateManager stateManager;
  @UiField
  RPStyle style;
  @UiField
  Label yesl;

  public AssistanceHeader(final StateManager statemanager) {
    this.stateManager = statemanager;
    initWidget(uiBinder.createAndBindUi(this));
    yesl.setTitle(DEF_TITLE);
    nol.setTitle(DEF_TITLE);
    maybel.setTitle(DEF_TITLE);
  }

  private void enableStyle(final Label label, final String style, final boolean enabled) {
    if (enabled) {
      label.addStyleName(style);
    } else {
      label.removeStyleName(style);
    }
  }

  @UiHandler("maybel")
  void onClickM(final ClickEvent e) {
    setStyle(maybel, style.click());
    stateManager.setAnswer(CustomConstants.MAYBE);
    NotifyUser.info("Updated");
  }

  @UiHandler("nol")
  void onClickN(final ClickEvent e) {
    setStyle(nol, style.click());
    stateManager.setAnswer(CustomConstants.NO);
    NotifyUser.info("Updated");
  }

  @UiHandler("yesl")
  void onClickY(final ClickEvent e) {
    setStyle(yesl, style.click());
    stateManager.setAnswer(CustomConstants.YES);
    NotifyUser.info("Updated");
  }

  @UiHandler("maybel")
  void onMouseOutM(final MouseOutEvent event) {
    setStyle(maybel, style.out());
  }

  @UiHandler("nol")
  void onMouseOutN(final MouseOutEvent event) {
    setStyle(nol, style.out());
  }

  @UiHandler("yesl")
  void onMouseOutY(final MouseOutEvent event) {
    setStyle(yesl, style.out());
  }

  @UiHandler("maybel")
  void onMouseOverM(final MouseOverEvent event) {
    setStyle(maybel, style.over());
  }

  @UiHandler("nol")
  void onMouseOverN(final MouseOverEvent event) {
    setStyle(nol, style.over());
  }

  @UiHandler("yesl")
  void onMouseOverY(final MouseOverEvent event) {
    setStyle(yesl, style.over());
  }

  @UiHandler("maybel")
  void onMouseUpM(final MouseUpEvent event) {
    setStyle(maybel, style.over());
  }

  @UiHandler("nol")
  void onMouseUpN(final MouseUpEvent event) {
    setStyle(nol, style.over());
  }

  @UiHandler("yesl")
  void onMouseUpY(final MouseUpEvent event) {
    setStyle(yesl, style.over());
  }

  private void setStyle(final Label label, final String styleName) {
    enableStyle(label, style.click(), styleName.equals(style.click()));
    enableStyle(label, style.over(), styleName.equals(style.over()));
    enableStyle(label, style.out(), styleName.equals(style.out()));
  }
}
