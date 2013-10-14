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
 */
package cc.kune.gxtbinds.client.actions.gxtui;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.PushButtonDescriptor;
import cc.kune.common.client.errors.NotImplementedException;
import cc.kune.common.shared.res.KuneIcon;

import com.extjs.gxt.ui.client.Style.ButtonScale;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToggleButton;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public abstract class AbstractGxtButtonGui extends AbstractChildGuiItem {

  private Button button;

  public AbstractGxtButtonGui() {
    super();
  }

  public AbstractGxtButtonGui(final ButtonDescriptor buttonDescriptor) {
    super(buttonDescriptor);
  }

  @Override
  protected void addStyle(final String style) {
    button.addStyleName(style);
  }

  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;
    descriptor.putValue(ParentWidget.PARENT_UI, this);
    if (descriptor instanceof PushButtonDescriptor) {
      button = new ToggleButton();
    } else {
      button = new Button();
    }
    button.setAutoWidth(false);
    button.setAutoHeight(false);
    button.setBorders(false);
    final String id = descriptor.getId();
    if (id != null) {
      button.ensureDebugId(id);
    }
    button.addSelectionListener(new SelectionListener<ButtonEvent>() {
      @Override
      public void componentSelected(final ButtonEvent event) {
        descriptor.fire(new ActionEvent(button, getTargetObjectOfAction(descriptor),
            Event.as(event.getEvent())));
      }
    });
    if (!descriptor.isChild()) {
      // If button is inside a toolbar don't init...
      initWidget(button);
    } else {
      if (descriptor.isChild()) {
        child = button;
      }
    }
    super.create(descriptor);
    configureItemFromProperties();
    return this;
  }

  @Override
  protected void setEnabled(final boolean enabled) {
    button.setEnabled(enabled);
  }

  @Override
  public void setIcon(final KuneIcon icon) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setIconBackground(final String backgroundColor) {
    throw new NotImplementedException();
  }

  @Override
  public void setIconResource(final ImageResource icon) {
    button.setIcon(AbstractImagePrototype.create(icon));
    button.setScale(ButtonScale.SMALL);
  }

  @Override
  protected void setIconStyle(final String style) {
    button.setIconStyle(style);
    button.setScale(ButtonScale.SMALL);
  }

  @Override
  public void setIconUrl(final String url) {
    throw new NotImplementedException();
  }

  public void setPressed(final boolean pressed) {
    final ToggleButton toggleButton = (ToggleButton) button;

    if (toggleButton.isPressed() != pressed) {
      toggleButton.toggle(pressed);
    }
  }

  @Override
  protected void setText(final String text) {
    button.setText(text);
  }

  @Override
  public void setVisible(final boolean visible) {
    if (button.isRendered()) {
      // ??
    }
    button.setVisible(visible);
  }

  @Override
  public boolean shouldBeAdded() {
    return !descriptor.isChild();
  }
}
