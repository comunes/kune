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
package cc.kune.gspace.client.maxmin;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ActionStyles;
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

// TODO: Auto-generated Javadoc
/**
 * The Class MaxMinWorkspacePresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class MaxMinWorkspacePresenter
    extends
    Presenter<MaxMinWorkspacePresenter.MaxMinWorkspaceView, MaxMinWorkspacePresenter.MaxMinWorkspaceProxy>

implements MaxMinWorkspace {

  /**
   * The Class MaximizeAction.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public class MaximizeAction extends AbstractExtendedAction {

    /**
     * Instantiates a new maximize action.
     * 
     * @param name
     *          the name
     * @param img
     *          the img
     * @param tooltip
     *          the tooltip
     */
    public MaximizeAction(final String name, final ImageResource img, final String tooltip) {
      super();
      putValue(Action.NAME, name);
      putValue(Action.SMALL_ICON, img);
      putValue(Action.TOOLTIP, tooltip);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.
     * common.client.actions.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event) {
      showMaximized(true);
    }
  }

  /**
   * The Interface MaxMinWorkspaceProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface MaxMinWorkspaceProxy extends Proxy<MaxMinWorkspacePresenter> {
  }

  /**
   * The Interface MaxMinWorkspaceView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface MaxMinWorkspaceView extends View, IsMaximizable {

  }

  /**
   * The Class MinimizeAction.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public class MinimizeAction extends AbstractExtendedAction {

    /**
     * Instantiates a new minimize action.
     * 
     * @param name
     *          the name
     * @param img
     *          the img
     * @param tooltip
     *          the tooltip
     */
    public MinimizeAction(final String name, final ImageResource img, final String tooltip) {
      super();
      putValue(Action.NAME, name);
      putValue(Action.SMALL_ICON, img);
      putValue(Action.TOOLTIP, tooltip);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.
     * common.client.actions.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event) {
      showMaximized(false);
    }
  }

  /** The Constant MAX_ICON. */
  public static final String MAX_ICON = "mmwp-max_bt";

  /** The Constant MIN_ICON. */
  public static final String MIN_ICON = "mmwp-min_bt";

  /** The Constant SMALL_SCREEN_HEIGHT_LIMIT. */
  private static final int SMALL_SCREEN_HEIGHT_LIMIT = 550;

  /** The images. */
  private final IconicResources images;

  /** The maximize button bar. */
  private IconLabelDescriptor maximizeButtonBar;

  /** The maximize button menu. */
  private MenuItemDescriptor maximizeButtonMenu;

  /** The maximized. */
  private boolean maximized;

  /** The minimize button bar. */
  private IconLabelDescriptor minimizeButtonBar;

  /** The minimize button menu. */
  private MenuItemDescriptor minimizeButtonMenu;

  /** The shortcut reg. */
  private final GlobalShortcutRegister shortcutReg;

  /** The Small screen. */
  private boolean SmallScreen;

  /**
   * Instantiates a new max min workspace presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param shortcutReg
   *          the shortcut reg
   * @param images
   *          the images
   */
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
   * under a fixed threshold.
   */
  private void checkSmallScreen() {
    SmallScreen = Window.getClientHeight() < SMALL_SCREEN_HEIGHT_LIMIT;
  }

  /**
   * Creates the actions.
   */
  private void createActions() {
    final KeyStroke shortcut = Shortcut.getShortcut(true, true, false, false, Character.valueOf('F'));

    if (SmallScreen) {
      // in small screens, add MaxMin button to the sitebar very visible
      final MaximizeAction maximizeAction = new MaximizeAction("", images.maximize(),
          I18n.t("Maximize the workspace in Inbox and Group Space"));
      maximizeAction.setShortcut(shortcut);
      maximizeButtonBar = new IconLabelDescriptor(maximizeAction);
      maximizeButtonBar.withId(MAX_ICON).withParent(SitebarActions.RIGHT_TOOLBAR).withPosition(0).withStyles(
          ActionStyles.SITEBAR_STYLE_FL);

      final MinimizeAction minimizeAction = new MinimizeAction("", images.minimize(),
          I18n.t("Restore the normal workspace size in Inbox and Group Space"));
      minimizeAction.setShortcut(shortcut);
      minimizeButtonBar = new IconLabelDescriptor(minimizeAction);
      minimizeButtonBar.withId(MIN_ICON).withParent(SitebarActions.RIGHT_TOOLBAR).withPosition(1).withStyles(
          ActionStyles.SITEBAR_STYLE_FL).withVisible(false);

    } else {
      // in large screens, add the MaxMin button to the More menu
      final MaximizeAction maximizeAction = new MaximizeAction(I18n.t("Maximize the workspace"),
          images.maximize(), I18n.t("Maximize the workspace in Inbox and Group Space"));
      maximizeAction.setShortcut(shortcut);
      maximizeButtonMenu = new MenuItemDescriptor(SitebarActions.MORE_MENU, maximizeAction);
      maximizeButtonMenu.withPosition(0).withId(MAX_ICON);

      final MinimizeAction minimizeAction = new MinimizeAction(I18n.t("Minimize the workspace"),
          images.minimize(), I18n.t("Restore the normal workspace size in Inbox and Group Space"));
      minimizeAction.setShortcut(shortcut);
      minimizeButtonMenu = new MenuItemDescriptor(SitebarActions.MORE_MENU, minimizeAction);
      minimizeButtonMenu.withPosition(1).withId(MIN_ICON).withVisible(false);
    }

    shortcutReg.put(shortcut, new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        showMaximized(!maximized);
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.maxmin.MaxMinWorkspace#maximize()
   */
  @Override
  public void maximize() {
    showMaximized(true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.maxmin.MaxMinWorkspace#minimize()
   */
  @Override
  public void minimize() {
    showMaximized(false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
   */
  @Override
  protected void onBind() {
    super.onBind();
    createActions();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
   */
  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  /**
   * Show maximized.
   * 
   * @param maximized
   *          the maximized
   */
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
