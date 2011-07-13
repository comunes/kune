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
package cc.kune.common.client.ui;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.EditEvent.EditHandler;
import cc.kune.common.client.utils.SimpleCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditableLabel extends Composite implements HasEditHandler {

  interface EditableLabelUiBinder extends UiBinder<Widget, EditableLabel> {
  }

  private static final int BLINK_TIME = 400;

  private static EditableLabelUiBinder uiBinder = GWT.create(EditableLabelUiBinder.class);

  private boolean editable;
  @UiField
  InlineLabel label;
  @UiField
  TextBox textbox;
  private Tooltip tooltip;
  private String tooltipText;

  public EditableLabel() {
    initWidget(uiBinder.createAndBindUi(this));
    label.setStylePrimaryName("k-editableLabel");
    editable = false;
    tooltipText = "";
  }

  @Override
  public HandlerRegistration addEditHandler(final EditHandler handler) {
    return addHandler(handler, EditEvent.getType());
  }

  private void blinkTimer(final boolean add, final SimpleCallback callback) {
    new Timer() {
      @Override
      public void run() {
        if (add) {
          label.addStyleDependentName("high");
        } else {
          label.removeStyleDependentName("high");
        }
        callback.onCallback();
      }
    }.schedule(BLINK_TIME);
  }

  public void edit() {
    if (editable) {
      label.setVisible(false);
      textbox.setVisible(true);
      textbox.selectAll();
      textbox.setFocus(true);
    }
  }

  private void finishEdit() {
    label.setText(textbox.getText());
    label.setVisible(true);
    textbox.setVisible(false);
    fireEvent(new EditEvent(textbox.getValue()));
  }

  private String getTooltipValue() {
    return editable ? tooltipText : "";
  }

  @UiHandler("textbox")
  void handleKeys(final KeyDownEvent event) {
    if (event.getNativeKeyCode() == 13) {
      finishEdit();
    }
  }

  public void highlightTitle() {
    if (editable) {
      blinkTimer(true, new SimpleCallback() {
        @Override
        public void onCallback() {
          blinkTimer(false, new SimpleCallback() {
            @Override
            public void onCallback() {
              blinkTimer(true, new SimpleCallback() {
                @Override
                public void onCallback() {
                  blinkTimer(false, new SimpleCallback() {
                    @Override
                    public void onCallback() {
                      tooltip.showTemporally();
                    }
                  });
                }
              });
            }
          });
        }
      });
    }
  }

  @UiHandler("textbox")
  void onBlur(final BlurEvent event) {
    finishEdit();
  }

  @UiHandler("label")
  void onClick(final ClickEvent e) {
    edit();
  }

  @UiHandler("label")
  void onMouseOut(final MouseOutEvent event) {
    if (editable) {
      label.removeStyleDependentName("high");
    }
  }

  @UiHandler("label")
  void onMouseOver(final MouseOverEvent event) {
    if (editable) {
      label.addStyleDependentName("high");
    }
  }

  public void setEditable(final boolean editable) {
    this.editable = editable;
    if (tooltip == null) {
      tooltip = Tooltip.to(label, getTooltipValue());

    } else {
      tooltip.setText(getTooltipValue());
    }
  }

  public void setText(final String text) {
    label.setText(text);
    textbox.setText(text);
  }

  public void setTooltip(final String tooltip) {
    this.tooltipText = tooltip;
  }

}
