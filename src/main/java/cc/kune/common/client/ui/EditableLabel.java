/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
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
package cc.kune.common.client.ui;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.EditEvent.EditHandler;
import cc.kune.common.shared.utils.SimpleCallback;

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

// TODO: Auto-generated Javadoc
/**
 * The Class EditableLabel.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EditableLabel extends Composite implements HasEditHandler {

  /**
   * The Interface EditableLabelUiBinder.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface EditableLabelUiBinder extends UiBinder<Widget, EditableLabel> {
  }

  /** The Constant BLINK_TIME. */
  private static final int BLINK_TIME = 400;

  /** The ui binder. */
  private static EditableLabelUiBinder uiBinder = GWT.create(EditableLabelUiBinder.class);

  /** The editable. */
  private boolean editable;
  
  /** The label. */
  @UiField
  InlineLabel label;
  
  /** The textbox. */
  @UiField
  TextBox textbox;
  
  /** The tooltip. */
  private Tooltip tooltip;
  
  /** The tooltip text. */
  private String tooltipText;

  /**
   * Instantiates a new editable label.
   */
  public EditableLabel() {
    initWidget(uiBinder.createAndBindUi(this));
    label.setStylePrimaryName("k-editableLabel");
    editable = false;
    tooltipText = "";
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.HasEditHandler#addEditHandler(cc.kune.common.client.ui.EditEvent.EditHandler)
   */
  @Override
  public HandlerRegistration addEditHandler(final EditHandler handler) {
    return addHandler(handler, EditEvent.getType());
  }

  /**
   * Blink timer.
   *
   * @param add the add
   * @param callback the callback
   */
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

  /**
   * Edits the.
   */
  public void edit() {
    if (editable) {
      label.setVisible(false);
      textbox.setVisible(true);
      textbox.selectAll();
      textbox.setFocus(true);
    }
  }

  /**
   * Finish edit.
   */
  private void finishEdit() {
    label.setText(textbox.getText());
    label.setVisible(true);
    textbox.setVisible(false);
    fireEvent(new EditEvent(textbox.getValue()));
  }

  /**
   * Gets the tooltip value.
   *
   * @return the tooltip value
   */
  private String getTooltipValue() {
    return editable ? tooltipText : "";
  }

  /**
   * Handle keys.
   *
   * @param event the event
   */
  @UiHandler("textbox")
  void handleKeys(final KeyDownEvent event) {
    if (event.getNativeKeyCode() == 13) {
      finishEdit();
    }
  }

  /**
   * Highlight title.
   */
  public void highlightTitle() {
    if (editable) {
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
  }

  /**
   * On blur.
   *
   * @param event the event
   */
  @UiHandler("textbox")
  void onBlur(final BlurEvent event) {
    finishEdit();
  }

  /**
   * On click.
   *
   * @param e the e
   */
  @UiHandler("label")
  void onClick(final ClickEvent e) {
    edit();
  }

  /**
   * On mouse out.
   *
   * @param event the event
   */
  @UiHandler("label")
  void onMouseOut(final MouseOutEvent event) {
    if (editable) {
      label.removeStyleDependentName("high");
    }
  }

  /**
   * On mouse over.
   *
   * @param event the event
   */
  @UiHandler("label")
  void onMouseOver(final MouseOverEvent event) {
    if (editable) {
      label.addStyleDependentName("high");
    }
  }

  /**
   * Sets the editable.
   *
   * @param editable the new editable
   */
  public void setEditable(final boolean editable) {
    this.editable = editable;
    if (tooltip == null) {
      tooltip = Tooltip.to(label, getTooltipValue());

    } else {
      tooltip.setText(getTooltipValue());
    }
  }

  /**
   * Sets the text.
   *
   * @param text the new text
   */
  public void setText(final String text) {
    label.setText(text);
    textbox.setText(text);
  }

  /**
   * Sets the tooltip.
   *
   * @param tooltip the new tooltip
   */
  public void setTooltip(final String tooltip) {
    this.tooltipText = tooltip;
  }

}
