/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
package cc.kune.gspace.client.options.tools;

import java.util.HashMap;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.client.ui.MaskWidget;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.ui.DefaultForm;
import cc.kune.core.client.ui.dialogs.tabbed.TabTitleGenerator;
import cc.kune.core.shared.dto.ToolSimpleDTO;
import cc.kune.gspace.client.options.EntityOptionsView;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class EntityOptToolsPanel extends DefaultForm implements EntityOptToolsView {
  public static final String TAB_ID = "k-eodlp-tools-id";
  private final HashMap<String, CheckBox> fields;
  private final I18nTranslationService i18n;
  private final MaskWidget maskWidget;
  private final IconLabel tabTitle;

  public EntityOptToolsPanel(final I18nTranslationService i18n, final IconicResources res,
      final MaskWidget maskWidget) {
    this.maskWidget = maskWidget;
    tabTitle = TabTitleGenerator.generate(res.toolsWhite(), i18n.t("Tools"), MAX_TABTITLE_LENGTH, TAB_ID);
    this.i18n = i18n;
    // super.setHeight(EntityOptionsView.HEIGHT);
    super.setWidth(EntityOptionsView.WIDTH);
    super.setFrame(true);
    // super.add(new HiddenField());
    super.getFormPanel().setLabelWidth(20);
    fields = new HashMap<String, CheckBox>();
    super.addStyleName("k-overflow-y-auto");
    // super.addStyleName("k-tab-panel");
  }

  @Override
  public void add(final ToolSimpleDTO tool, final boolean enabled, final boolean checked,
      final ClickHandler handler) {
    final CheckBox checkbox = new CheckBox();
    checkbox.setFieldLabel(i18n.t(tool.getRootName()));
    // checkbox.setFireChangeEventOnSetValue(false);
    checkbox.setValue(checked);
    setEnabled(checkbox, enabled);
    checkbox.addListener(Events.Change, new Listener<BaseEvent>() {
      @Override
      public void handleEvent(final BaseEvent be) {
        handler.onClick(null);
      }
    });
    super.add(checkbox);
    fields.put(tool.getName(), checkbox);
    doLayoutIfNeeded();
  }

  @Override
  public Widget asWidget() {
    return super.getFormPanel();
  }

  @Override
  public void clear() {
    super.getFormPanel().removeAll();
    fields.clear();
    final Label label = new Label();
    label.setText(i18n.t("Here you can select the tools used:"));
    label.addStyleName("kune-Margin-10-tb");
    super.add(label);
  }

  private void doLayoutIfNeeded() {
    if (super.getFormPanel().isRendered()) {
      super.getFormPanel().layout();
    }
  }

  @Override
  public IsWidget getTabTitle() {
    return tabTitle;
  }

  private CheckBox getTool(final String tool) {
    final CheckBox field = fields.get(tool);
    if (field == null) {
      Log.error("Field " + tool + " not found in EOTCP");
    }
    return field;
  }

  @Override
  public boolean isChecked(final String tool) {
    final CheckBox field = getTool(tool);
    return field.getValue();
  }

  @Override
  public void mask() {
    maskWidget.mask(this);
  }

  @Override
  public void setChecked(final String tool, final boolean checked) {
    final CheckBox field = getTool(tool);
    field.setValue(checked);
  }

  private void setEnabled(final CheckBox checkbox, final boolean enabled) {
    if (enabled) {
      checkbox.enable();
    } else {
      checkbox.disable();
    }
  }

  @Override
  public void setEnabled(final String tool, final boolean enabled) {
    final CheckBox field = getTool(tool);
    setEnabled(field, enabled);
  }

  @Override
  public void setTooltip(final String tool, final String tip) {
    final CheckBox field = getTool(tool);
    Tooltip.to(field, tip);
  }

  @Override
  public void unmask() {
    maskWidget.unMask();
  }
}
