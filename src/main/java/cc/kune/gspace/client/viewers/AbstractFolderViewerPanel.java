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
package cc.kune.gspace.client.viewers;

import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.ui.HasEditHandler;
import cc.kune.common.client.ui.UiUtils;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.dnd.FolderContainerDropController;
import cc.kune.core.client.dnd.FolderContentDropController;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.gspace.client.armor.GSpaceCenter;
import cc.kune.gspace.client.viewers.FolderViewerPresenter.FolderViewerView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.ViewImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractFolderViewerPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractFolderViewerPanel extends ViewImpl implements FolderViewerView {

  /** The capabilities registry. */
  protected final ContentCapabilitiesRegistry capabilitiesRegistry;

  /** The container drop controller prov. */
  protected final Provider<FolderContainerDropController> containerDropControllerProv;

  /** The content drop controller prov. */
  protected final Provider<FolderContentDropController> contentDropControllerProv;

  /** The content title. */
  private final ContentTitleWidget contentTitle;

  /** The drag controller. */
  protected final KuneDragController dragController;

  /** The empty label. */
  private final InlineLabel emptyLabel;

  /** The empty panel. */
  private final FlowPanel emptyPanel;

  /** The gs armor. */
  protected final GSpaceArmor gsArmor;

  /** The i18n. */
  protected final I18nTranslationService i18n;

  /** The widget. */
  protected Widget widget;

  /**
   * Instantiates a new abstract folder viewer panel.
   * 
   * @param gsArmor
   *          the gs armor
   * @param eventBus
   *          the event bus
   * @param i18n
   *          the i18n
   * @param capabilitiesRegistry
   *          the capabilities registry
   * @param dragController
   *          the drag controller
   * @param contentDropControllerProv
   *          the content drop controller prov
   * @param containerDropControllerProv
   *          the container drop controller prov
   */
  public AbstractFolderViewerPanel(final GSpaceArmor gsArmor, final EventBus eventBus,
      final I18nTranslationService i18n, final ContentCapabilitiesRegistry capabilitiesRegistry,
      final KuneDragController dragController,
      final Provider<FolderContentDropController> contentDropControllerProv,
      final Provider<FolderContainerDropController> containerDropControllerProv) {
    this.gsArmor = gsArmor;
    this.i18n = i18n;
    this.capabilitiesRegistry = capabilitiesRegistry;
    this.dragController = dragController;
    this.contentDropControllerProv = contentDropControllerProv;
    this.containerDropControllerProv = containerDropControllerProv;
    emptyPanel = new FlowPanel();
    emptyLabel = new InlineLabel(i18n.t("This is empty."));
    emptyLabel.setStyleName("k-empty-msg");
    emptyPanel.setStyleName("k-empty-folder-panel");
    emptyPanel.add(emptyLabel);
    contentTitle = new ContentTitleWidget(i18n, gsArmor, capabilitiesRegistry.getIconsRegistry());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return widget;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.viewers.AbstractFolderViewerView#attach()
   */
  @Override
  public void attach() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.viewers.AbstractFolderViewerView#clear()
   */
  @Override
  public void clear() {
    gsArmor.getSubheaderToolbar().clear();
    gsArmor.getDocFooterToolbar().clear();
    gsArmor.getDocContainer().clear();
    UiUtils.clear(gsArmor.getDocHeader());
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.viewers.AbstractFolderViewerView#detach()
   */
  @Override
  public void detach() {
    clear();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.viewers.AbstractFolderViewerView#editTitle()
   */
  @Override
  public void editTitle() {
    contentTitle.edit();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.viewers.AbstractFolderViewerView#getEditTitle()
   */
  @Override
  public HasEditHandler getEditTitle() {
    return contentTitle.getEditableTitle();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.AbstractFolderViewerView#highlightTitle()
   */
  @Override
  public void highlightTitle() {
    contentTitle.highlightTitle();
  }

  /**
   * Resize height.
   * 
   * @param w
   *          the w
   */
  protected void resizeHeight(final Widget w) {
    w.setHeight(String.valueOf(gsArmor.getDocContainerHeight()));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.AbstractFolderViewerView#setContainer(cc.
   * kune.core.shared.dto.StateContainerDTO)
   */
  @Override
  public void setContainer(final StateContainerDTO state) {
    final String title = state.getContainer().isRoot() ? i18n.t(state.getTitle()) : state.getTitle();
    contentTitle.setTitle(title, state.getTypeId(), state.getContainerRights().isEditable()
        && capabilitiesRegistry.isRenamable(state.getTypeId()));
    Window.setTitle(state.getGroup().getLongName() + ": " + state.getTitle());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.AbstractFolderViewerView#setEditableTitle
   * (java.lang.String)
   */
  @Override
  public void setEditableTitle(final String title) {
    contentTitle.setText(title);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.AbstractFolderViewerView#setFooterActions
   * (cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection)
   */
  @Override
  public void setFooterActions(final GuiActionDescCollection actions) {
    setToolbarActions(actions, gsArmor.getDocFooterToolbar());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.AbstractFolderViewerView#setSubheaderActions
   * (cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection)
   */
  @Override
  public void setSubheaderActions(final GuiActionDescCollection actions) {
    setToolbarActions(actions, gsArmor.getSubheaderToolbar());
  }

  /**
   * Sets the toolbar actions.
   * 
   * @param actions
   *          the actions
   * @param toolbar
   *          the toolbar
   */
  private void setToolbarActions(final GuiActionDescCollection actions, final IsActionExtensible toolbar) {
    toolbar.clear();
    toolbar.addAll(actions);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.AbstractFolderViewerView#showEmptyMsg(java
   * .lang.String)
   */
  @Override
  public void showEmptyMsg(final String emptyMessage) {
    gsArmor.enableCenterScroll(false);
    emptyLabel.setText(emptyMessage);
    gsArmor.getDocContainer().add(emptyPanel);
    gsArmor.getDocContainer().showWidget(emptyPanel);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.viewers.AbstractFolderViewerView#showFolder()
   */
  @Override
  public void showFolder() {
    gsArmor.enableCenterScroll(true);
    final GSpaceCenter docContainer = gsArmor.getDocContainer();
    docContainer.add(widget);
    docContainer.showWidget(widget);
  }

}
