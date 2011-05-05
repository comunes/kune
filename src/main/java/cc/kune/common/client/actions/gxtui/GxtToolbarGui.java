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
package cc.kune.common.client.actions.gxtui;

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;

import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.ui.UIObject;

public class GxtToolbarGui extends AbstractGuiItem implements ParentWidget {

  private ToolBar toolbar;

  public GxtToolbarGui() {
    super();
  }

  public GxtToolbarGui(final GuiActionDescrip descriptor) {
    super(descriptor);
  }

  @Override
  public void add(final UIObject uiObject) {
    toolbar.add((Component) uiObject);
  }

  // public FillToolItem addFill() {
  // final FillToolItem item = new FillToolItem();
  // toolbar.add(item);
  // return item;
  // }
  //
  // public SeparatorToolItem addSeparator() {
  // final SeparatorToolItem item = new SeparatorToolItem();
  // toolbar.add(item);
  // return item;
  // }
  //
  // public LabelToolItem addSpacer() {
  // final LabelToolItem item = new LabelToolItem();
  // toolbar.add(item);
  // return item;
  // }

  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;
    toolbar = new ToolBar();
    initWidget(toolbar);
    configureItemFromProperties();
    descriptor.putValue(ParentWidget.PARENT_UI, this);
    return this;
  }

  @Override
  public void insert(final int position, final UIObject uiObject) {
    toolbar.insert((Component) uiObject, position);
  }

  @Override
  protected void setEnabled(final boolean enabled) {
  }

  @Override
  protected void setIconStyle(final String style) {
  }

  @Override
  public void setIconUrl(final String url) {
  }

  @Override
  protected void setText(final String text) {
  }

  @Override
  protected void setToolTipText(final String text) {
    toolbar.setTitle(text);
  }

}
