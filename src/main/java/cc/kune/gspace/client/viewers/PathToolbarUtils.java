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

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.dnd.FolderContainerDropController;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.registry.IconsRegistry;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.ContainerDTO;
import cc.kune.core.shared.dto.ContainerSimpleDTO;
import cc.kune.gspace.client.actions.GotoTokenAction;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class PathToolbarUtils {

  private final Provider<FolderContainerDropController> dropController;
  private final EventBus eventBus;
  private final I18nTranslationService i18n;
  private final IconsRegistry iconsRegistry;
  private final StateManager stateManager;

  @Inject
  public PathToolbarUtils(final Provider<FolderContainerDropController> dropController,
      final StateManager stateManager, final ContentCapabilitiesRegistry capabilitiesRegistry,
      final EventBus eventBus, final I18nTranslationService i18n) {
    this.dropController = dropController;
    this.stateManager = stateManager;
    this.eventBus = eventBus;
    this.i18n = i18n;
    iconsRegistry = capabilitiesRegistry.getIconsRegistry();
  }

  String calculateStyle(final int pos, final int length) {
    if (length == 1) {
      return ToolbarStyles.CSSBTN;
    }
    if (pos == 0) {
      return ToolbarStyles.CSSBTNL;
    }
    if (pos == length - 1) {
      return ToolbarStyles.CSSBTNR;
    }
    return ToolbarStyles.CSSBTNC;
  }

  public GuiActionDescCollection createPath(final ContainerDTO container, final boolean withDrop) {
    final GuiActionDescCollection actions = new GuiActionDescCollection();
    final ContainerSimpleDTO[] path = container.getAbsolutePath();
    final int pathLength = path.length;
    if (pathLength > 0) {
      // This is we want to align to the right
      // for (int i = pathLength - 1; i >= 0; i--) {
      for (int i = 0; i < pathLength; i++) {
        final ButtonDescriptor btn = createPathButton(path[i], pathLength, i);
        if (withDrop) {
          if (i != pathLength - 1) {
            final FolderContainerDropController dropTarget = dropController.get();
            dropTarget.setTarget(path[i].getStateToken());
            btn.setDropTarget(dropTarget);
          }
        }
        actions.add(btn);
      }
    }
    return actions;
  }

  private ButtonDescriptor createPathButton(final ContainerSimpleDTO container, final int length,
      final int pos) {
    final String style = calculateStyle(pos, length);
    final String name = container.getName();
    final String title = pos == 0 ? i18n.t(name) : name;
    final GotoTokenAction action = new GotoTokenAction(
        iconsRegistry.getContentTypeIcon(container.getTypeId()), TextUtils.ellipsis(title, 15),
        i18n.t("Click to go here or drop contents here to move them"), container.getStateToken(), style,
        stateManager, eventBus);
    final ButtonDescriptor btn = new ButtonDescriptor(action) {
      @Override
      public void onDetach() {
        super.onDetach();
        action.onDettach();
      }
    };

    if (title.length() > 15) {
      btn.withToolTip(title);
    }
    return btn;
  }
}
