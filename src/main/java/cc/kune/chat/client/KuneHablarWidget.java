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
package cc.kune.chat.client;

import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.HablarDisplay;
import com.calclab.hablar.core.client.HablarPresenter;
import com.calclab.hablar.core.client.mvp.DefaultEventBus;
import com.calclab.hablar.core.client.pages.tabs.TabsLayout.TabHeaderSize;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class KuneHablarWidget extends LayoutPanel implements HablarDisplay {
  public class HablarNoLoggerEventBus extends DefaultEventBus {

    @Override
    public void fireEvent(final GwtEvent<?> event) {
      // GWT.log("EVENT: " + event.toDebugString(), null);
      super.fireEvent(event);
    }
  }

  private final Hablar hablar;

  @UiConstructor
  public KuneHablarWidget(final Layout layout, final TabHeaderSize tabHeaderSize) {
    addStyleName("hablar-HablarWidget");
    if (layout == Layout.accordion) {
      hablar = HablarPresenter.createAccordionPresenter(new HablarNoLoggerEventBus(), this);
    } else if (layout == Layout.tabs) {
      hablar = HablarPresenter.createTabsPresenter(new HablarNoLoggerEventBus(), this, tabHeaderSize);
    } else {
      throw new IllegalStateException("Unimplemented layout: " + layout);
    }
  }

  @Override
  public Widget asWidget() {
    return this;
  }

  @Override
  public void forceLayout() {
    // GWT.log("FORCE LAYOUT");
    super.forceLayout();
  }

  public Hablar getHablar() {
    return hablar;
  }

}
