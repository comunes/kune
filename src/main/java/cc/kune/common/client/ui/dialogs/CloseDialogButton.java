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
package cc.kune.common.client.ui.dialogs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

public class CloseDialogButton extends Composite implements HasClickHandlers {

  interface CloseDialogButtonUiBinder extends UiBinder<Widget, CloseDialogButton> {
  }

  private static CloseDialogButtonUiBinder uiBinder = GWT.create(CloseDialogButtonUiBinder.class);

  @UiField
  PushButton closeBtn;

  public CloseDialogButton() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public HandlerRegistration addClickHandler(final ClickHandler handler) {
    return closeBtn.addClickHandler(handler);
  }

  @Override
  public void setVisible(final boolean visible) {
    closeBtn.setVisible(visible);
  }

}
