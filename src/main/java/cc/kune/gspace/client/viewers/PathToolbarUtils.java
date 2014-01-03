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

import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.ToolbarStyles;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.LabelDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.common.shared.utils.Url;
import cc.kune.core.client.dnd.FolderContainerDropController;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.registry.IconsRegistry;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.ContainerDTO;
import cc.kune.core.shared.dto.ContainerSimpleDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.gspace.client.actions.GotoTokenAction;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class PathToolbarUtils.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class PathToolbarUtils {

  /** The download provider. */
  private final Provider<ClientFileDownloadUtils> downloadProvider;

  /** The drop controller. */
  private final Provider<FolderContainerDropController> dropController;

  /** The event bus. */
  private final EventBus eventBus;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The icons registry. */
  private final IconsRegistry iconsRegistry;

  /** The state manager. */
  private final StateManager stateManager;

  /**
   * Instantiates a new path toolbar utils.
   * 
   * @param dropController
   *          the drop controller
   * @param stateManager
   *          the state manager
   * @param capabilitiesRegistry
   *          the capabilities registry
   * @param eventBus
   *          the event bus
   * @param i18n
   *          the i18n
   * @param downloadProvider
   *          the download provider
   */
  @Inject
  public PathToolbarUtils(final Provider<FolderContainerDropController> dropController,
      final StateManager stateManager, final ContentCapabilitiesRegistry capabilitiesRegistry,
      final EventBus eventBus, final I18nTranslationService i18n,
      final Provider<ClientFileDownloadUtils> downloadProvider) {
    this.dropController = dropController;
    this.stateManager = stateManager;
    this.eventBus = eventBus;
    this.i18n = i18n;
    this.downloadProvider = downloadProvider;
    iconsRegistry = capabilitiesRegistry.getIconsRegistry();
  }

  /**
   * Creates the group button.
   * 
   * @param group
   *          the group
   * @param showGroupName
   *          the show group name
   * @param minimal
   *          the minimal
   * @return the button descriptor
   */
  private ButtonDescriptor createGroupButton(final GroupDTO group, final boolean showGroupName,
      final boolean minimal) {
    final String style = ToolbarStyles.CSS_BTN_LEFT + (minimal ? ", " + ActionStyles.BTN_SMALL : "");
    final String tooltip = group.getLongName();
    final GotoTokenAction action = new GotoTokenAction(null,
        showGroupName ? group.getShortName() : null, tooltip, group.getStateToken(), style,
        stateManager, eventBus, false);
    final ButtonDescriptor btn = new ButtonDescriptor(action);
    // btn.withIcon(iconsRegistry.getContentTypeIcon(WikiToolConstants.TYPE_FOLDER));

    // FIXME: with Group Icon + Name we get some css issue
    // if (minimal)
    btn.withIcon(new Url(downloadProvider.get().getGroupLogo(group)));
    return btn;
  }

  /**
   * Creates the path.
   * 
   * @param group
   *          the group
   * @param container
   *          the container
   * @param withDrop
   *          the with drop
   * @param showGroupName
   *          the show group name
   * @return the gui action desc collection
   */
  public GuiActionDescCollection createPath(final GroupDTO group, final ContainerDTO container,
      final boolean withDrop, final boolean showGroupName) {
    return createPath(group, container, withDrop, showGroupName, null);
  }

  /**
   * Creates the path.
   * 
   * @param group
   *          the group
   * @param container
   *          the container
   * @param withDrop
   *          the with drop
   * @param showGroupName
   *          the show group name
   * @param extra
   *          the extra
   * @return the gui action desc collection
   */
  public GuiActionDescCollection createPath(final GroupDTO group, final ContainerDTO container,
      final boolean withDrop, final boolean showGroupName, final ContainerSimpleDTO extra) {
    final GuiActionDescCollection actions = new GuiActionDescCollection();
    final ContainerSimpleDTO[] path = container.getAbsolutePath();

    // the extra container is used to show sometimes the title of the current
    // content/container
    // is used in the Inbox to show the title of the current document

    final boolean hasExtra = extra != null;
    final int pathLength = path.length + (hasExtra ? 1 : 0);

    if (pathLength > 0) {
      actions.add(createGroupButton(group, showGroupName, !hasExtra));
      actions.add(new LabelDescriptor().withStyles("k-button-arrow"));

      // TODO RTL:
      // This is we want to align to the right
      // for (int i = pathLength - 1; i >= 0; i--) {

      for (int i = 0; i < pathLength; i++) {
        final boolean isNotTheLast = i != pathLength - 1;
        final boolean isTheLast = !isNotTheLast;
        final boolean isTheLastAndTheExtra = isTheLast && hasExtra;
        final ContainerSimpleDTO item = isTheLastAndTheExtra ? extra : path[i];
        final ButtonDescriptor btn = createPathButton(item, pathLength, i, isTheLastAndTheExtra,
            withDrop, hasExtra);
        if (withDrop) {
          if (isNotTheLast) {
            final FolderContainerDropController dropTarget = dropController.get();
            dropTarget.setTarget(item.getStateToken());
            btn.setDropTarget(dropTarget);
          }
        }
        actions.add(btn);
        // We add a small arrow between buttons
        if (isNotTheLast) {
          actions.add(new LabelDescriptor().withStyles("k-button-arrow"));
        }
      }
    }
    return actions;
  }

  /**
   * Creates the path button.
   * 
   * @param container
   *          the container
   * @param length
   *          the length
   * @param pos
   *          the pos
   * @param isTheLastExtra
   *          the is the last extra
   * @param withDrop
   *          the with drop
   * @param hasExtra
   *          the has extra
   * @return the button descriptor
   */
  private ButtonDescriptor createPathButton(final ContainerSimpleDTO container, final int length,
      final int pos, final boolean isTheLastExtra, final boolean withDrop, final boolean hasExtra) {
    // +1 because of the first group button
    final String style = ToolbarStyles.calculateStyle(pos + 1, length + 1);

    final String name = container.getName();
    // We should translate tool names: "Documents", "Wiki", etc.
    final String title = pos == 0 ? i18n.t(name) : name;

    // Normal buttons are smaller
    final int tooltipSize = isTheLastExtra ? 30 : 15;
    final String truncatedName = TextUtils.ellipsis(title, tooltipSize);

    final String tooltip = withDrop ? i18n.t("Click to go here or drop contents here to move them") : "";

    final GotoTokenAction action = new GotoTokenAction(
        iconsRegistry.getContentTypeIcon(container.getTypeId()), truncatedName, tooltip,
        container.getStateToken(), style, stateManager, eventBus, !hasExtra);
    final ButtonDescriptor btn = new ButtonDescriptor(action);

    // If truncated, show tooltip
    if (title.length() > tooltipSize) {
      btn.withToolTip(title);
    }
    return btn;
  }
}
