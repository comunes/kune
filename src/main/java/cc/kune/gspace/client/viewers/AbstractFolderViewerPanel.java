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

public abstract class AbstractFolderViewerPanel extends ViewImpl implements FolderViewerView {
  protected final ContentCapabilitiesRegistry capabilitiesRegistry;
  protected final Provider<FolderContainerDropController> containerDropControllerProv;
  protected final Provider<FolderContentDropController> contentDropControllerProv;
  private final ContentTitleWidget contentTitle;
  protected final KuneDragController dragController;
  private final InlineLabel emptyLabel;
  private final FlowPanel emptyPanel;
  protected final GSpaceArmor gsArmor;
  protected final I18nTranslationService i18n;
  protected Widget widget;

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
    contentTitle = new ContentTitleWidget(i18n, gsArmor, capabilitiesRegistry.getIconsRegistryLight());
  }

  @Override
  public Widget asWidget() {
    return widget;
  }

  @Override
  public void attach() {
  }

  @Override
  public void clear() {
    gsArmor.getSubheaderToolbar().clear();
    gsArmor.getDocFooterToolbar().clear();
    gsArmor.getDocContainer().clear();
    UiUtils.clear(gsArmor.getDocHeader());
  }

  @Override
  public void detach() {
    clear();
  }

  @Override
  public void editTitle() {
    contentTitle.edit();
  }

  @Override
  public HasEditHandler getEditTitle() {
    return contentTitle.getEditableTitle();
  }

  @Override
  public void highlightTitle() {
    contentTitle.highlightTitle();
  }

  protected void resizeHeight(final Widget w) {
    w.setHeight(String.valueOf(gsArmor.getDocContainerHeight()));
  }

  @Override
  public void setContainer(final StateContainerDTO state) {
    final String title = state.getContainer().isRoot() ? i18n.t(state.getTitle()) : state.getTitle();
    contentTitle.setTitle(title, state.getTypeId(), state.getContainerRights().isEditable()
        && capabilitiesRegistry.isRenamable(state.getTypeId()));
    Window.setTitle(state.getGroup().getLongName() + ": " + state.getTitle());
  }

  @Override
  public void setEditableTitle(final String title) {
    contentTitle.setText(title);
  }

  @Override
  public void setFooterActions(final GuiActionDescCollection actions) {
    setToolbarActions(actions, gsArmor.getDocFooterToolbar());
  }

  @Override
  public void setSubheaderActions(final GuiActionDescCollection actions) {
    setToolbarActions(actions, gsArmor.getSubheaderToolbar());
  }

  private void setToolbarActions(final GuiActionDescCollection actions, final IsActionExtensible toolbar) {
    toolbar.clear();
    toolbar.addAll(actions);
  }

  @Override
  public void showEmptyMsg(final String emptyMessage) {
    gsArmor.enableCenterScroll(false);
    emptyLabel.setText(emptyMessage);
    gsArmor.getDocContainer().add(emptyPanel);
    gsArmor.getDocContainer().showWidget(emptyPanel);
  }

  @Override
  public void showFolder() {
    gsArmor.enableCenterScroll(true);
    final GSpaceCenter docContainer = gsArmor.getDocContainer();
    docContainer.add(widget);
    docContainer.showWidget(widget);
  }

}
