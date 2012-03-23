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
package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.ui.IconLabel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;

public class GwtIconLabelGui extends AbstractGuiItem {
  private IconLabel iconLabel;

  @Override
  protected void addStyle(final String style) {
    iconLabel.addStyleName(style);
  }

  @Override
  protected void clearStyles() {
    iconLabel.setStyleName("k-none");
    iconLabel.setStyleName("k-table");
  }

  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;
    iconLabel = new IconLabel("");
    descriptor.putValue(ParentWidget.PARENT_UI, this);
    final String id = descriptor.getId();
    if (id != null) {
      iconLabel.setId(id);
    }
    initWidget(iconLabel);
    iconLabel.getFocus().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        final AbstractAction action = descriptor.getAction();
        if (action != null) {
          action.actionPerformed(new ActionEvent(iconLabel, getTargetObjectOfAction(descriptor),
              Event.as(event.getNativeEvent())));
        }
      }
    });
    configureItemFromProperties();
    return this;
  }

  @Override
  public void setEnabled(final boolean enabled) {
    super.setVisible(enabled);
  }

  @Override
  public void setIconBackground(final String color) {
    iconLabel.setLeftIconBackground(color);
  }

  @Override
  protected void setIconStyle(final String style) {
    iconLabel.setRightIcon(style);
    iconLabel.addRightIconStyle("k-fl");
  }

  @Override
  public void setIconUrl(final String url) {
    iconLabel.setLeftIconUrl(url);
  }

  @Override
  public void setText(final String text) {
    iconLabel.setText(text, descriptor.getDirection());
  }

  @Override
  public boolean shouldBeAdded() {
    return true;
  }

}
