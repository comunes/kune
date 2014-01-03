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
package cc.kune.gspace.client.options.general;

import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.client.ui.MaskWidget;
import cc.kune.core.client.ui.DefaultForm;
import cc.kune.core.client.ui.dialogs.tabbed.TabTitleGenerator;
import cc.kune.gspace.client.options.EntityOptionsView;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityOptGeneralPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EntityOptGeneralPanel extends DefaultForm implements EntityOptGeneralView {

  /** The Constant TAB_ID. */
  public static final String TAB_ID = "k-eodlp-gen-id";

  /** The mask widget. */
  private final MaskWidget maskWidget;

  /** The tab title. */
  private final IconLabel tabTitle;

  /**
   * Instantiates a new entity opt general panel.
   * 
   * @param maskWidget
   *          the mask widget
   * @param img
   *          the img
   * @param title
   *          the title
   * @param introMessage
   *          the intro message
   */
  public EntityOptGeneralPanel(final MaskWidget maskWidget, final ImageResource img, final String title,
      final String introMessage) {
    this.maskWidget = maskWidget;

    tabTitle = TabTitleGenerator.generate(img, title, MAX_TABTITLE_LENGTH, TAB_ID);
    super.setWidth(EntityOptionsView.WIDTH);
    super.setFrame(true);
    super.getFormPanel().setLabelWidth(100);
    super.addStyleName("k-overflow-y-auto");
    final Label label = new Label();
    label.setText(introMessage);
    label.addStyleName("kune-Margin-10-tb");
    super.add(label);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.IsWidget#asWidget()
   */
  @Override
  public Widget asWidget() {
    return super.getFormPanel();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.general.EntityOptGeneralView#clear()
   */
  @Override
  public void clear() {
    super.getFormPanel().removeAll();
    super.reset();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.EntityOptionsTabView#getTabTitle()
   */
  @Override
  public IsWidget getTabTitle() {
    return tabTitle;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.EntityOptGeneralView#isRendered()
   */
  @Override
  public boolean isRendered() {
    return super.getFormPanel().isRendered();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.general.EntityOptGeneralView#mask()
   */
  @Override
  public void mask() {
    maskWidget.mask(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.general.EntityOptGeneralView#setChangeHandler
   * (com.google.gwt.event.dom.client.ChangeHandler)
   */
  @Override
  public void setChangeHandler(final ChangeHandler changeHandler) {
    final Listener<BaseEvent> listener = new Listener<BaseEvent>() {
      @Override
      public void handleEvent(final BaseEvent be) {
        changeHandler.onChange(null);
      }
    };
    for (@SuppressWarnings("rawtypes")
    final Field field : super.getFormPanel().getFields()) {
      // field.addListener(Events.Change, listener);
      field.addListener(Events.Blur, listener);
      field.addListener(Events.OnKeyPress, new Listener<FieldEvent>() {
        @Override
        public void handleEvent(final FieldEvent fe) {
          if (fe.getEvent().getKeyCode() == 13) {
            changeHandler.onChange(null);
          }
        }
      });
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.general.EntityOptGeneralView#unmask()
   */
  @Override
  public void unmask() {
    maskWidget.unMask();
  }
}
