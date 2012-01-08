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
package cc.kune.gspace.client.viewers;

import cc.kune.common.client.actions.gxtui.AbstractGxtMenuGui;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.dnd.FolderViewerDropController;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.ui.BasicDragableThumb;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.gspace.client.viewers.items.FolderItemDescriptor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class FolderViewerAsFlowPanel extends AbstractFolderViewerPanel {
  interface FolderViewerAsFlowPanelUiBinder extends UiBinder<Widget, FolderViewerAsFlowPanel> {
  }
  private static final int ICONLABELMAXSIZE = 20;
  private static final int ICONSIZE = 100;
  private static FolderViewerAsFlowPanelUiBinder uiBinder = GWT.create(FolderViewerAsFlowPanelUiBinder.class);
  @UiField
  FlowPanel flow;

  @Inject
  public FolderViewerAsFlowPanel(final GSpaceArmor gsArmor, final I18nTranslationService i18n,
      final ContentCapabilitiesRegistry capabilitiesRegistry, final KuneDragController dragController,
      final Provider<FolderViewerDropController> dropControllerProv) {
    super(gsArmor, i18n, capabilitiesRegistry, dragController, dropControllerProv);
    widget = uiBinder.createAndBindUi(this);
  }

  @Override
  public void addItem(final FolderItemDescriptor item, final ClickHandler clickHandler,
      final DoubleClickHandler doubleClickHandler) {
    // In this viewer we don't use the clickHandler from the presenter
    flow.add(createThumb(item.getText(), item.getIcon(), item.getTooltip(), "",
        item.getActionCollection(), doubleClickHandler));
  }

  @Override
  public void clear() {
    flow.clear();
    super.clear();
  }

  public BasicDragableThumb createThumb(final String text, final Object icon, final String tooltip,
      final String tooltipTitle, final GuiActionDescCollection menuitems,
      final DoubleClickHandler doubleClickHandler) {
    final BasicDragableThumb thumb = new BasicDragableThumb(icon, ICONSIZE, text, ICONLABELMAXSIZE,
        false);
    final MenuDescriptor menu = new MenuDescriptor();
    menu.setStandalone(true);
    menu.putValue(AbstractGxtMenuGui.MENU_POSITION, AbstractGxtMenuGui.MenuPosition.bl);
    for (final GuiActionDescrip item : menuitems) {
      item.setParent(menu);
    }
    final ClickHandler clickHand = new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        menu.show(thumb);
      }
    };
    thumb.addClickHandler(clickHand);
    thumb.addDoubleClickHandler(doubleClickHandler);
    gsArmor.getSubheaderToolbar().add(menu);
    gsArmor.getSubheaderToolbar().addAll(menuitems);
    thumb.setTooltip(tooltipTitle, tooltip);
    thumb.setLabelVisible(true);
    return thumb;
  }

  @Override
  public void setContainer(final StateContainerDTO state) {
    super.setContainer(state);
    gsArmor.getDocContainer().add(new HTML("<b>Note:</b> This GUI is provisional<br/>"));
  }

}
