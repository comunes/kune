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
package cc.kune.gspace.client.tool.selector;

import cc.kune.core.client.dnd.FolderContainerDropController;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.gspace.client.tool.selector.ToolSelectorItemPresenter.ToolSelectorItemView;
import cc.kune.gspace.client.tool.selector.ToolSelectorPresenter.ToolSelectorView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.ViewImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class ToolSelectorPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ToolSelectorPanel extends ViewImpl implements ToolSelectorView {

  /**
   * The Interface ToolSelectorPanelUiBinder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface ToolSelectorPanelUiBinder extends UiBinder<Widget, ToolSelectorPanel> {
  }

  /** The ui binder. */
  private static ToolSelectorPanelUiBinder uiBinder = GWT.create(ToolSelectorPanelUiBinder.class);

  /** The drop controller prov. */
  private final Provider<FolderContainerDropController> dropControllerProv;

  /** The flow. */
  @UiField
  FlowPanel flow;

  /**
   * Instantiates a new tool selector panel.
   * 
   * @param wsArmor
   *          the ws armor
   * @param dropControllerProv
   *          the drop controller prov
   */
  @Inject
  public ToolSelectorPanel(final GSpaceArmor wsArmor,
      final Provider<FolderContainerDropController> dropControllerProv) {
    this.dropControllerProv = dropControllerProv;
    wsArmor.getEntityToolsCenter().add(uiBinder.createAndBindUi(this));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.tool.selector.ToolSelectorPresenter.ToolSelectorView
   * #addItem(cc.kune.gspace.client.tool.selector.ToolSelectorItemPresenter.
   * ToolSelectorItemView)
   */
  @Override
  public void addItem(final ToolSelectorItemView item) {
    final Widget widget = item.asWidget();
    flow.add(widget);
    final FolderContainerDropController dropController = dropControllerProv.get();
    dropController.init(((ToolSelectorItemPanel) widget).getFocusPanel());
    dropController.setTarget(item.getTarget());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return flow;
  }

}
