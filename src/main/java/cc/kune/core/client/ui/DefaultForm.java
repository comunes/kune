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
 \*/
package cc.kune.core.client.ui;

import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultForm.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class DefaultForm {

  /** The Constant BIG_FIELD_WIDTH. */
  public static final int BIG_FIELD_WIDTH = 280;

  /** The Constant DEF_FIELD_LABEL_WITH. */
  public static final int DEF_FIELD_LABEL_WITH = 85;

  /** The Constant DEF_FIELD_WIDTH. */
  public static final int DEF_FIELD_WIDTH = 200;

  /** The Constant DEF_MEDIUM_FIELD_WIDTH. */
  public static final int DEF_MEDIUM_FIELD_WIDTH = 150;

  /** The Constant DEF_SMALL_FIELD_WIDTH. */
  public static final int DEF_SMALL_FIELD_WIDTH = 100;

  /** The Constant DEF_XSMALL_FIELD_WIDTH. */
  public static final int DEF_XSMALL_FIELD_WIDTH = 50;

  /** The form. */
  private final FormPanel form;

  /**
   * Instantiates a new default form.
   */
  public DefaultForm() {
    this(HorizontalAlignment.LEFT);
  }

  /**
   * Instantiates a new default form.
   * 
   * @param buttonAlign
   *          the button align
   */
  public DefaultForm(final HorizontalAlignment buttonAlign) {
    form = new FormPanel();
    form.setFrame(true);
    form.setPadding(10);
    form.setBorders(false);
    form.setLabelWidth(DEF_FIELD_LABEL_WITH);
    form.setLabelAlign(LabelAlign.LEFT);
    form.setButtonAlign(buttonAlign);
    form.setHeaderVisible(false);
  }

  /**
   * Instantiates a new default form.
   * 
   * @param title
   *          the title
   */
  public DefaultForm(final String title) {
    this(title, HorizontalAlignment.LEFT);
  }

  /**
   * Instantiates a new default form.
   * 
   * @param title
   *          the title
   * @param buttonAlign
   *          the button align
   */
  public DefaultForm(final String title, final HorizontalAlignment buttonAlign) {
    this(buttonAlign);
    form.setTitle(title);
  }

  /**
   * Adds the.
   * 
   * @param field
   *          the field
   */
  public void add(final Field<?> field) {
    form.add(field);
  }

  /**
   * Adds the.
   * 
   * @param fieldset
   *          the fieldset
   */
  public void add(final FieldSet fieldset) {
    form.add(fieldset);
  }

  /**
   * Adds the.
   * 
   * @param label
   *          the label
   */
  public void add(final Label label) {
    form.add(label);
  }

  /**
   * Adds the.
   * 
   * @param widget
   *          the widget
   */
  public void add(final Widget widget) {
    form.add(widget);
  }

  /**
   * Adds the button.
   * 
   * @param button
   *          the button
   */
  public void addButton(final Button button) {
    form.addButton(button);
  }

  /**
   * Adds the listener.
   * 
   * @param eventType
   *          the event type
   * @param listener
   *          the listener
   */
  public void addListener(final EventType eventType, final Listener<? extends BaseEvent> listener) {
    form.addListener(eventType, listener);
  }

  /**
   * Adds the style name.
   * 
   * @param cls
   *          the cls
   */
  public void addStyleName(final String cls) {
    form.addStyleName(cls);
  }

  /**
   * Gets the form panel.
   * 
   * @return the form panel
   */
  public FormPanel getFormPanel() {
    return form;
  }

  /**
   * Insert.
   * 
   * @param index
   *          the index
   * @param component
   *          the component
   */
  public void insert(final int index, final Component component) {
    form.insert(component, index);
  }

  /**
   * Checks if is valid.
   * 
   * @return true, if is valid
   */
  public boolean isValid() {
    return form.isValid();
  }

  /**
   * Removes the style name.
   * 
   * @param cls
   *          the cls
   */
  public void removeStyleName(final String cls) {
    form.removeStyleName(cls);
  }

  /**
   * Reset.
   */
  public void reset() {
    form.reset();
  }

  /**
   * Sets the auto height.
   * 
   * @param autoHeight
   *          the new auto height
   */
  public void setAutoHeight(final boolean autoHeight) {
    form.setAutoHeight(autoHeight);
  }

  /**
   * Sets the auto width.
   * 
   * @param autoWidth
   *          the new auto width
   */
  public void setAutoWidth(final boolean autoWidth) {
    form.setAutoWidth(autoWidth);
  }

  /**
   * Sets the frame.
   * 
   * @param frame
   *          the new frame
   */
  public void setFrame(final boolean frame) {
    form.setFrame(frame);
  }

  /**
   * Sets the height.
   * 
   * @param height
   *          the new height
   */
  public void setHeight(final int height) {
    form.setHeight(height);
  }

  /**
   * Sets the hide labels.
   * 
   * @param hide
   *          the new hide labels
   */
  public void setHideLabels(final boolean hide) {
    form.setHideLabels(hide);
  }

  /**
   * Sets the icon cls.
   * 
   * @param iconCls
   *          the new icon cls
   */
  public void setIconCls(final String iconCls) {
    form.setIconStyle(iconCls);
  }

  /**
   * Sets the padding.
   * 
   * @param padding
   *          the new padding
   */
  public void setPadding(final int padding) {
    form.setPadding(padding);
  }

  /**
   * Sets the width.
   * 
   * @param width
   *          the new width
   */
  public void setWidth(final int width) {
    form.setWidth(width);
  }

  /**
   * Validate.
   */
  public void validate() {
    final List<Field<?>> fields = form.getFields();
    for (final Field<?> field : fields) {
      field.validate();
    }
  }
}
