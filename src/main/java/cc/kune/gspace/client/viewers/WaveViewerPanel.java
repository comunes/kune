/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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
package cc.kune.gspace.client.viewers;

import cc.kune.wave.client.CustomEditToolbar;
import cc.kune.wave.client.CustomSavedStateIndicator;
import cc.kune.wave.client.kspecific.AurorisColorPicker;
import cc.kune.wave.client.kspecific.WaveClientProvider;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.View;

public class WaveViewerPanel extends AbstractWaveViewerPanel implements View {
  private Widget widget;

  public WaveViewerPanel(final WaveClientProvider waveClient, final EventBus eventBus,
      final CustomSavedStateIndicator waveUnsavedIndicator,
      final Provider<AurorisColorPicker> colorPicker, final Provider<CustomEditToolbar> customEditToolbar) {
    super(waveClient, eventBus, waveUnsavedIndicator, colorPicker, customEditToolbar);
  }

  @Override
  public void addToSlot(final Object slot, final IsWidget content) {
  }

  @Override
  public Widget asWidget() {
    return widget;
  }

  protected void initWidget(final Widget widget) {
    this.widget = widget;
  }

  @Override
  public void removeFromSlot(final Object slot, final IsWidget content) {
  }

  @Override
  public void setInSlot(final Object slot, final IsWidget content) {
  }
}
