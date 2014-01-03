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

import org.waveprotocol.wave.client.common.util.DateUtils;

import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.res.KuneIcon;
import cc.kune.core.client.dnd.FolderContainerDropController;
import cc.kune.core.client.dnd.FolderContentDropController;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.gspace.client.viewers.FolderViewerPresenter.FolderViewerView;
import cc.kune.gspace.client.viewers.items.FolderItemDescriptor;
import cc.kune.gspace.client.viewers.items.FolderItemWidget;

import com.calclab.emite.core.client.packet.TextUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class FolderViewerAsTablePanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class FolderViewerAsTablePanel extends AbstractFolderViewerPanel {

  /**
   * The Interface FolderViewerAsTablePanelUiBinder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface FolderViewerAsTablePanelUiBinder extends UiBinder<Widget, FolderViewerAsTablePanel> {
  }

  /** The Constant ITEM_ID. */
  public static final String ITEM_ID = "k-fvat-item-";

  /** The ui binder. */
  private static FolderViewerAsTablePanelUiBinder uiBinder = GWT.create(FolderViewerAsTablePanelUiBinder.class);

  /** The flex. */
  @UiField
  FlexTable flex;

  /** The gui provider. */
  private final GuiProvider guiProvider;

  /** The res. */
  private final CoreResources res;

  /**
   * Instantiates a new folder viewer as table panel.
   * 
   * @param gsArmor
   *          the gs armor
   * @param i18n
   *          the i18n
   * @param eventBus
   *          the event bus
   * @param guiProvider
   *          the gui provider
   * @param res
   *          the res
   * @param capabilitiesRegistry
   *          the capabilities registry
   * @param dragController
   *          the drag controller
   * @param contentDropControllerProv
   *          the content drop controller prov
   * @param containerDropControllerProv
   *          the container drop controller prov
   */
  @Inject
  public FolderViewerAsTablePanel(final GSpaceArmor gsArmor, final I18nTranslationService i18n,
      final EventBus eventBus, final GuiProvider guiProvider, final CoreResources res,
      final ContentCapabilitiesRegistry capabilitiesRegistry, final KuneDragController dragController,
      final Provider<FolderContentDropController> contentDropControllerProv,
      final Provider<FolderContainerDropController> containerDropControllerProv) {
    super(gsArmor, eventBus, i18n, capabilitiesRegistry, dragController, contentDropControllerProv,
        containerDropControllerProv);
    this.guiProvider = guiProvider;
    this.res = res;
    widget = uiBinder.createAndBindUi(this);
    widget.addStyleName("k-folder-viewer");
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.AbstractFolderViewerView#addItem(cc.kune.
   * gspace.client.viewers.items.FolderItemDescriptor,
   * com.google.gwt.event.dom.client.ClickHandler,
   * com.google.gwt.event.dom.client.DoubleClickHandler)
   */
  @Override
  public void addItem(final FolderItemDescriptor item, final ClickHandler clickHandler,
      final DoubleClickHandler doubleClickHandler) {
    final int rowCount = flex.getRowCount();
    final FolderItemWidget itemWidget = new FolderItemWidget((KuneIcon) item.getIcon(),
        TextUtils.ellipsis(item.getText(), 70), item.getStateToken(), ITEM_ID + (flex.getRowCount() + 1));
    final ActionSimplePanel toolbar = new ActionSimplePanel(guiProvider, i18n);
    final long modifiedOn = item.getModififiedOn();
    if (modifiedOn != FolderViewerView.NO_DATE) {
      // String dateFormat = session.getCurrentLanguage().getDateFormatShort();
      itemWidget.setModifiedText(DateUtils.getInstance().formatPastDate(modifiedOn));
    }
    itemWidget.setMenu(toolbar);
    Tooltip.to(itemWidget, item.getTooltip());
    // FIXME make this under demand
    itemWidget.getRowClick().addClickHandler(clickHandler);
    itemWidget.getRowDoubleClick().addDoubleClickHandler(doubleClickHandler);
    if (ContentStatus.inTheDustbin.equals(item.getContentStatus())) {
      itemWidget.getTitleWidget().addStyleName("k-line-through");
    }
    itemWidget.getRowDoubleClick().addDoubleClickHandler(doubleClickHandler);
    itemWidget.getRowMouse().addMouseOutHandler(new MouseOutHandler() {
      @Override
      public void onMouseOut(final MouseOutEvent event) {
        itemWidget.setMenuVisible(false);
      }
    });
    final MenuDescriptor menu = new MenuDescriptor(i18n.t("Actions"));
    menu.withIcon(res.arrowdown()).withStyles("k-def-docbtn, k-btn, k-button");
    menu.setStandalone(false);
    toolbar.add(menu);
    final GuiActionDescCollection actions = item.getActionCollection();
    toolbar.setVisible(actions.size() > 0);
    itemWidget.setMenuVisible(false);

    itemWidget.getRowMouse().addMouseOverHandler(new MouseOverHandler() {
      boolean initialized = false;

      private void init(final FolderItemDescriptor item, final FolderItemWidget itemWidget,
          final ActionSimplePanel toolbar) {
        for (final GuiActionDescrip menuItem : actions) {
          menuItem.setParent(menu);
          toolbar.add(menuItem);
        }
      }

      @Override
      public void onMouseOver(final MouseOverEvent event) {
        if (!initialized) {
          init(item, itemWidget, toolbar);
          initialized = true;
        }
        itemWidget.setMenuVisible(true);
      }
    });
    flex.setWidget(rowCount + 1, 0, itemWidget);
    if (item.isDraggable()) {
      dragController.makeDraggable(itemWidget, itemWidget.getTitleWidget());
    }

    if (!item.isContainer() && item.isDroppable()) {
      final FolderContentDropController dropController = contentDropControllerProv.get();
      dropController.init(itemWidget);
      dropController.setTarget(item.getStateToken());
    }
    if (item.isContainer() && item.isDroppable()) {
      final FolderContainerDropController dropController = containerDropControllerProv.get();
      dropController.init(itemWidget);
      dropController.setTarget(item.getStateToken());
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.viewers.AbstractFolderViewerPanel#clear()
   */
  @Override
  public void clear() {
    flex.removeAllRows();
    super.clear();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.viewers.AbstractFolderViewerPanel#setContainer(cc
   * .kune.core.shared.dto.StateContainerDTO)
   */
  @Override
  public void setContainer(final StateContainerDTO state) {
    super.setContainer(state);
  }

}
