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

package cc.kune.polymer.client;

import static cc.kune.polymer.client.Layout.*;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class PolymerUtils {

  public static void addFlexHorLayout(final Widget... widgets) {
    addFlexLayout(HORIZONTAL, widgets);
  }

  private static void addFlexLayout(final Layout horOVert, final Widget... widgets) {
    for (final Widget widget : widgets) {
      addLayout(widget.getElement(), horOVert, LAYOUT, FLEX);
    }
  }

  public static void addFlexVerLayout(final Widget... widgets) {
    addFlexLayout(VERTICAL, widgets);
  }

  public static void addLayout(final Element element, final Layout... layouts) {
    for (final Layout layout : layouts) {
      element.setAttribute(layout.getAttribute(), "");
    }
  }

}
