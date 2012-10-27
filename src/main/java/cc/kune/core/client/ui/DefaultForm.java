/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

public class DefaultForm {

  public static final int BIG_FIELD_WIDTH = 280;
  public static final int DEF_FIELD_LABEL_WITH = 85;
  public static final int DEF_FIELD_WIDTH = 200;
  public static final int DEF_MEDIUM_FIELD_WIDTH = 150;
  public static final int DEF_SMALL_FIELD_WIDTH = 100;
  public static final int DEF_XSMALL_FIELD_WIDTH = 50;

  private final FormPanel form;

  public DefaultForm() {
    this(HorizontalAlignment.LEFT);
  }

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

  public DefaultForm(final String title) {
    this(title, HorizontalAlignment.LEFT);
  }

  public DefaultForm(final String title, final HorizontalAlignment buttonAlign) {
    this(buttonAlign);
    form.setTitle(title);
  }

  public void add(final Field<?> field) {
    form.add(field);
  }

  public void add(final FieldSet fieldset) {
    form.add(fieldset);
  }

  public void add(final Label label) {
    form.add(label);
  }

  public void add(final Widget widget) {
    form.add(widget);
  }

  public void addButton(final Button button) {
    form.addButton(button);
  }

  public void addListener(final EventType eventType, final Listener<? extends BaseEvent> listener) {
    form.addListener(eventType, listener);
  }

  public void addStyleName(final String cls) {
    form.addStyleName(cls);
  }

  public FormPanel getFormPanel() {
    return form;
  }

  public void insert(final int index, final Component component) {
    form.insert(component, index);
  }

  public boolean isValid() {
    return form.isValid();
  }

  public void removeStyleName(final String cls) {
    form.removeStyleName(cls);
  }

  public void reset() {
    form.reset();
  }

  public void setAutoHeight(final boolean autoHeight) {
    form.setAutoHeight(autoHeight);
  }

  public void setAutoWidth(final boolean autoWidth) {
    form.setAutoWidth(autoWidth);
  }

  public void setFrame(final boolean frame) {
    form.setFrame(frame);
  }

  public void setHeight(final int height) {
    form.setHeight(height);
  }

  public void setHideLabels(final boolean hide) {
    form.setHideLabels(hide);
  }

  public void setIconCls(final String iconCls) {
    form.setIconStyle(iconCls);
  }

  public void setPadding(final int padding) {
    form.setPadding(padding);
  }

  public void setWidth(final int width) {
    form.setWidth(width);
  }

  public void validate() {
    final List<Field<?>> fields = form.getFields();
    for (final Field<?> field : fields) {
      field.validate();
    }
  }
}
