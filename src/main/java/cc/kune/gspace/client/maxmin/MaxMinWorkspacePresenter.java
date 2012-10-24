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
package cc.kune.gspace.client.maxmin;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.Shortcut;
import cc.kune.common.client.actions.ui.descrip.IconLabelDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.sitebar.SitebarActions;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class MaxMinWorkspacePresenter
    extends
    Presenter<MaxMinWorkspacePresenter.MaxMinWorkspaceView, MaxMinWorkspacePresenter.MaxMinWorkspaceProxy>

implements MaxMinWorkspace {

  public class MaximizeAction extends AbstractExtendedAction {
    public MaximizeAction(final String name, final ImageResource img, final String tooltip) {
      super();
      putValue(Action.NAME, name);
      putValue(Action.SMALL_ICON, img);
      putValue(Action.TOOLTIP, tooltip);
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      showMaximized(true);
    }
  }
  @ProxyCodeSplit
  public interface MaxMinWorkspaceProxy extends Proxy<MaxMinWorkspacePresenter> {
  }

  public interface MaxMinWorkspaceView extends View, IsMaximizable {

  }
  public class MinimizeAction extends AbstractExtendedAction {
    public MinimizeAction(final String name, final ImageResource img, final String tooltip) {
      super();
      putValue(Action.NAME, name);
      putValue(Action.SMALL_ICON, img);
      putValue(Action.TOOLTIP, tooltip);
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      showMaximized(false);
    }
  }
  private static final int LIMIT_SMALL_SCREEN = 2000;

  public static final String MAX_ICON = "mmwp-max_bt";

  public static final String MIN_ICON = "mmwp-min_bt";

  private final IconicResources images;

  private IconLabelDescriptor maximizeButtonBar;

  private MenuItemDescriptor maximizeButtonMenu;

  private boolean maximized;

  private IconLabelDescriptor minimizeButtonBar;

  private MenuItemDescriptor minimizeButtonMenu;

  private final GlobalShortcutRegister shortcutReg;

  private boolean SmallScreen;

  @Inject
  public MaxMinWorkspacePresenter(final EventBus eventBus, final MaxMinWorkspaceView view,
      final MaxMinWorkspaceProxy proxy, final GlobalShortcutRegister shortcutReg,
      final IconicResources images) {
    super(eventBus, view, proxy);
    this.shortcutReg = shortcutReg;
    this.images = images;
    maximized = false;

    checkSmallScreen();
  }

  /**
   * Checks if the client's screen size is small, that is, its browser height
   * under a fixed threshold
   * 
   */
  private void checkSmallScreen() {
    SmallScreen = Window.getClientHeight() < LIMIT_SMALL_SCREEN;
  }

  private void createActions() {
    final KeyStroke shortcut = Shortcut.getShortcut(true, true, false, false, Character.valueOf('F'));

    if (SmallScreen) {
      // in small screens, add MaxMin button to the sitebar very visible
      final MaximizeAction maximizeAction = new MaximizeAction(I18n.t("Maximize"), images.maximize(),
          I18n.t("Maximize the workspace in Inbox and Group Space"));
      maximizeAction.setShortcut(shortcut);
      maximizeButtonBar = new IconLabelDescriptor(maximizeAction);
      maximizeButtonBar.setId(MAX_ICON);
      maximizeButtonBar.setParent(SitebarActions.RIGHT_TOOLBAR);
      maximizeButtonBar.setPosition(0);
      maximizeButtonBar.setStyles(SitebarActions.COMMON_LINK_STYLE);

      final MinimizeAction minimizeAction = new MinimizeAction(I18n.t("Minimize"), images.minimize(),
          I18n.t("Restore the normal workspace size in Inbox and Group Space"));
      minimizeAction.setShortcut(shortcut);
      minimizeButtonBar = new IconLabelDescriptor(minimizeAction);
      minimizeButtonBar.setId(MIN_ICON);
      minimizeButtonBar.setParent(SitebarActions.RIGHT_TOOLBAR);
      minimizeButtonBar.setPosition(1);
      minimizeButtonBar.setVisible(false);
      minimizeButtonBar.setStyles(SitebarActions.COMMON_LINK_STYLE);

    } else {
      // in large screens, add the MaxMin button to the More menu
      final MaximizeAction maximizeAction = new MaximizeAction(I18n.t("Maximize the workspace"),
          images.maximize(), I18n.t("Maximize the workspace in Inbox and Group Space"));
      maximizeAction.setShortcut(shortcut);
      maximizeButtonMenu = new MenuItemDescriptor(SitebarActions.MORE_MENU, maximizeAction);
      maximizeButtonMenu.setPosition(0);
      maximizeButtonMenu.setId(MAX_ICON);

      final MinimizeAction minimizeAction = new MinimizeAction(I18n.t("Minimize the workspace"),
          images.minimize(), I18n.t("Restore the normal workspace size in Inbox and Group Space"));
      minimizeAction.setShortcut(shortcut);
      minimizeButtonMenu = new MenuItemDescriptor(SitebarActions.MORE_MENU, minimizeAction);
      minimizeButtonMenu.setPosition(1);
      minimizeButtonMenu.setVisible(false);
      minimizeButtonMenu.setId(MIN_ICON);
    }

    shortcutReg.put(shortcut, new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        showMaximized(!maximized);
      }
    });
  }

  @Override
  public void maximize() {
    showMaximized(true);
  }

  @Override
  public void minimize() {
    showMaximized(false);
  }

  @Override
  protected void onBind() {
    super.onBind();
    createActions();
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  private void showMaximized(final boolean maximized) {
    if (SmallScreen) {
      maximizeButtonBar.setVisible(!maximized);
      minimizeButtonBar.setVisible(maximized);
    } else {
      maximizeButtonMenu.setVisible(!maximized);
      minimizeButtonMenu.setVisible(maximized);
    }
    this.maximized = maximized;
    getView().setMaximized(maximized);
  }
}
