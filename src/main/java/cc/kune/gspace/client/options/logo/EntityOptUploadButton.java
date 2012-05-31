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
package cc.kune.gspace.client.options.logo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HasText;

public class EntityOptUploadButton extends Composite implements HasClickHandlers {
  private final Button btn;

  public EntityOptUploadButton(final String text) {
    final DecoratorPanel decorator = new DecoratorPanel();
    btn = new Button(text);
    btn.addStyleName("k-button");
    initWidget(decorator);
    decorator.setWidget(btn);
    decorator.setHeight("50px");
  }

  @Override
  public HandlerRegistration addClickHandler(final ClickHandler handler) {
    return addDomHandler(handler, ClickEvent.getType());
  }

  public HasText hasText() {
    return btn;
  }
}