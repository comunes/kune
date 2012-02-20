/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
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
package cc.kune.common.client.tooltip;

import cc.kune.common.client.utils.TimerWrapper;
import cc.kune.common.client.utils.TimerWrapper.Executer;
import cc.kune.common.shared.utils.TextUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class Tooltip extends PopupPanel {

  interface TooltipUiBinder extends UiBinder<Widget, Tooltip> {
  }

  static Tooltip current;

  private static TooltipUiBinder uiBinder = GWT.create(TooltipUiBinder.class);

  private static final int WIDTH_NOT_DEFINED = -1;

  public static void hideCurrent() {
    if (current != null) {
      current.hide();
    }
  }

  private static void setHideOnDetachListener(final Widget widget, final Tooltip tip) {
    widget.addAttachHandler(new Handler() {
      @Override
      public void onAttachOrDetach(final AttachEvent event) {
        if (!event.isAttached()) {
          tip.hide();
        }
      }
    });
  }

  public static Tooltip to(final Widget widget, final String text) {
    final Tooltip tip = new Tooltip();
    tip.to(widget);
    tip.setText(text);
    setHideOnDetachListener(widget, tip);
    return tip;
  }

  public static Tooltip to(final Widget widget, final Widget withContent) {
    final Tooltip tip = new Tooltip();
    tip.to(widget);
    tip.setContent(withContent);
    setHideOnDetachListener(widget, tip);
    return tip;
  }

  @UiField
  HTMLPanel arrow;
  @UiField
  HTMLPanel arrowBorder;
  private boolean containsText;
  @UiField
  FlowPanel content;
  @UiField
  FlowPanel flow;
  private Widget ofWidget;
  private final Label textLabel;
  private final TooltipTimers timers;
  @UiField
  InlineLabel title;
  @UiField
  HTMLPanel tooltip;
  private int width = WIDTH_NOT_DEFINED;

  public Tooltip() {
    super.add(uiBinder.createAndBindUi(this));
    super.setStyleName("k-tooltip-no-chrome");
    super.setAutoHideEnabled(false);
    super.setAnimationEnabled(false);
    final TimerWrapper overTimer = new TimerWrapper();
    overTimer.configure(new Executer() {
      @Override
      public void execute() {
        show();
      }
    });
    final Executer hideExecuter = new Executer() {
      @Override
      public void execute() {
        hide();
      }
    };
    final TimerWrapper outTimer = new TimerWrapper();
    final TimerWrapper securityTimer = new TimerWrapper();
    outTimer.configure(hideExecuter);
    securityTimer.configure(hideExecuter);
    timers = new TooltipTimers(overTimer, outTimer, securityTimer);
    textLabel = new Label();
  }

  private TooltipPosition calculatePosition() {
    return TooltipPositionCalculator.calculate(Window.getClientWidth(), Window.getClientHeight(),
        ofWidget.getAbsoluteLeft(), ofWidget.getAbsoluteTop(), ofWidget.getOffsetWidth(),
        ofWidget.getOffsetHeight(), Tooltip.this.getWidth(), Tooltip.this.getHeight());
  }

  protected int getHeight() {
    return tooltip.getElement().getOffsetHeight();
  }

  protected int getWidth() {
    return tooltip.getElement().getOffsetWidth();
  }

  @Override
  public void hide() {
    super.hide();
    timers.cancel();
    Tooltip.current = null;
  }

  @Override
  public boolean isVisible() {
    return super.isShowing();
  }

  public boolean isVisibleOrWillBe() {
    return tooltip.isVisible() || timers.showIsScheduled();
  }

  private void setContent(final Widget widget) {
    content.clear();
    content.add(widget);
    containsText = false;
  }

  public void setText(final String text) {
    content.clear();
    content.add(textLabel);
    textLabel.setText(text);
    containsText = true;
  }

  public void setWidth(final int width) {
    this.width = width;
  }

  @Override
  public void show() {
    if (!Tooltip.this.isShowing() && ofWidget.isAttached() && ofWidget.isVisible()
        && (!containsText || TextUtils.notEmpty(textLabel.getText()))) {
      Tooltip.super.show();
      if (Tooltip.current != null) {
        Tooltip.current.hide();
      }
      Tooltip.current = this;
      if (width != WIDTH_NOT_DEFINED) {
        tooltip.getElement().getStyle().setWidth(width, Unit.PX);
      } else if (tooltip.getOffsetWidth() > 430) {
        tooltip.getElement().getStyle().setWidth(430, Unit.PX);
      } else {
        tooltip.getElement().getStyle().clearWidth();
      }
      showAt();
    }
  }

  private void showAt() {
    Tooltip.this.showAt(calculatePosition());
  }

  protected void showAt(final TooltipPosition position) {
    this.setPopupPosition(position.getLeft(), position.getTop());
    switch (position.getArrowPosition()) {
    case N:
    case NW:
    case NE:
      arrow.getElement().getStyle().setLeft(position.getArrowLeft(), Unit.PX);
      arrow.getElement().getStyle().setTop(position.getArrowTop() + 3, Unit.PX);
      arrowBorder.getElement().getStyle().setLeft(position.getArrowLeft(), Unit.PX);
      arrowBorder.getElement().getStyle().setTop(position.getArrowTop() + 1, Unit.PX);
      arrow.getElement().removeClassName("k-tooltip-arrow-s");
      arrow.getElement().addClassName("k-tooltip-arrow-n");
      arrowBorder.getElement().removeClassName("k-tooltip-arrow-border-s");
      arrowBorder.getElement().addClassName("k-tooltip-arrow-border-n");
      break;
    case S:
    case SE:
    case SW:
      arrow.getElement().getStyle().setLeft(position.getArrowLeft(), Unit.PX);
      arrow.getElement().getStyle().setBottom(position.getArrowTop() + 2, Unit.PX);
      arrowBorder.getElement().getStyle().setLeft(position.getArrowLeft(), Unit.PX);
      arrowBorder.getElement().getStyle().setBottom(position.getArrowTop(), Unit.PX);
      arrow.getElement().addClassName("k-tooltip-arrow-s");
      arrow.getElement().removeClassName("k-tooltip-arrow-n");
      arrowBorder.getElement().addClassName("k-tooltip-arrow-border-s");
      arrowBorder.getElement().removeClassName("k-tooltip-arrow-border-n");
      break;
    }
  }

  public void showTemporally() {
    show();
    timers.showTemporally();
  }

  private void to(final Widget ofWidget) {
    this.ofWidget = ofWidget;
    ofWidget.addDomHandler(new MouseOverHandler() {
      @Override
      public void onMouseOver(final MouseOverEvent event) {
        timers.onOver();
      }
    }, MouseOverEvent.getType());
    ofWidget.addDomHandler(new FocusHandler() {
      @Override
      public void onFocus(final FocusEvent event) {
        timers.onOut();
      }
    }, FocusEvent.getType());
    ofWidget.addDomHandler(new BlurHandler() {
      @Override
      public void onBlur(final BlurEvent event) {
        timers.onOut();
      }
    }, BlurEvent.getType());
    ofWidget.addDomHandler(new MouseOutHandler() {
      @Override
      public void onMouseOut(final MouseOutEvent event) {
        timers.onOut();
      }
    }, MouseOutEvent.getType());
  }
}
