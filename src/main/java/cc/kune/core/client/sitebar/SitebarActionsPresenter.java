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
package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuSeparatorDescriptor;
import cc.kune.common.client.ui.KuneWindowUtils;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.resources.iconic.IconicResources;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class SitebarActionsPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SitebarActionsPresenter extends
    Presenter<SitebarActionsPresenter.SitebarActionsView, SitebarActionsPresenter.SitebarActionsProxy>
    implements SitebarActions {

  /**
   * The Interface SitebarActionsProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface SitebarActionsProxy extends Proxy<SitebarActionsPresenter> {
  }

  /**
   * The Interface SitebarActionsView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface SitebarActionsView extends View {

    /**
     * Gets the left bar.
     * 
     * @return the left bar
     */
    IsActionExtensible getLeftBar();

    /**
     * Gets the right bar.
     * 
     * @return the right bar
     */
    IsActionExtensible getRightBar();

    /**
     * Show about dialog.
     */
    void showAboutDialog();

    /**
     * Show error dialog.
     */
    void showErrorDialog();
  }

  /** The Constant SITE_OPTIONS_MENU. */
  public static final String SITE_OPTIONS_MENU = "kune-sop-om";

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The icons. */
  private final IconicResources icons;

  /** The my groups menu. */
  private final Provider<MyGroupsMenu> myGroupsMenu;

  /** The new group link. */
  private final Provider<SitebarNewGroupLink> newGroupLink;

  /** The res. */
  private final CoreResources res;

  /** The sign in link. */
  private final Provider<SitebarSignInLink> signInLink;

  /** The sign out link. */
  private final Provider<SitebarSignOutLink> signOutLink;

  /**
   * Instantiates a new sitebar actions presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param i18n
   *          the i18n
   * @param newGroupLink
   *          the new group link
   * @param signOutLink
   *          the sign out link
   * @param signInLink
   *          the sign in link
   * @param res
   *          the res
   * @param icons
   *          the icons
   * @param myGroupsMenu
   *          the my groups menu
   */
  @Inject
  public SitebarActionsPresenter(final EventBus eventBus, final SitebarActionsView view,
      final SitebarActionsProxy proxy, final I18nTranslationService i18n,
      final Provider<SitebarNewGroupLink> newGroupLink, final Provider<SitebarSignOutLink> signOutLink,
      final Provider<SitebarSignInLink> signInLink, final CoreResources res,
      final IconicResources icons, final Provider<MyGroupsMenu> myGroupsMenu) {
    super(eventBus, view, proxy);
    this.i18n = i18n;
    this.newGroupLink = newGroupLink;
    this.signOutLink = signOutLink;
    this.signInLink = signInLink;
    this.icons = icons;
    this.myGroupsMenu = myGroupsMenu;
    this.res = res;
    init();
  }

  /**
   * Creates the goto kune.
   * 
   * @return the menu item descriptor
   */
  private MenuItemDescriptor createGotoKune() {
    final MenuItemDescriptor gotoKuneDevSite = new MenuItemDescriptor(MORE_MENU, new AbstractAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        KuneWindowUtils.open("http://kune.ourproject.org/");
      }
    });
    gotoKuneDevSite.putValue(Action.NAME, i18n.t("Kune development site"));
    gotoKuneDevSite.putValue(Action.SMALL_ICON, icons.kune());
    return gotoKuneDevSite;
  }

  /**
   * Gets the options menu.
   * 
   * @return the options menu
   */
  public MenuDescriptor getOptionsMenu() {
    return MORE_MENU;
  }

  /**
   * Inits the.
   */
  private void init() {
    MORE_MENU.withId(SITE_OPTIONS_MENU);
  }

  /**
   * On app start.
   * 
   * @param event
   *          the event
   */
  @ProxyEvent
  public void onAppStart(final AppStartEvent event) {
    MORE_MENU.withText(i18n.t("More"));
    MORE_MENU.withIcon(res.arrowdownsitebarSmall());
    MORE_MENU.setStyles("k-no-backimage, k-btn-sitebar");
    // OPTIONS_MENU.putValue(AbstractGxtMenuGui.MENU_POSITION,
    // AbstractGxtMenuGui.MenuPosition.bl);

    final AbstractExtendedAction bugsAction = new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        KuneWindowUtils.open("http://kune.ourproject.org/issues/");
      }
    };
    bugsAction.putValue(Action.NAME, i18n.t("Report Kune issues/problems"));
    bugsAction.putValue(Action.SMALL_ICON, icons.toolsGrey());

    final AbstractExtendedAction errorAction = new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        getView().showErrorDialog();
      }
    };

    final AbstractExtendedAction aboutAction = new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        getView().showAboutDialog();
      }
    };

    final AbstractExtendedAction wavePowered = new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        KuneWindowUtils.open("http://incubator.apache.org/wave/");
      }
    };

    final AbstractExtendedAction faqAction = new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        KuneWindowUtils.open("http://kune.ourproject.org/faq/");
      }
    };

    wavePowered.putValue(Action.NAME, i18n.t("Apache Wave powered"));
    wavePowered.putValue(Action.SMALL_ICON, res.waveIcon());
    aboutAction.putValue(Action.NAME, i18n.t("About Kune"));
    aboutAction.putValue(Action.SMALL_ICON, icons.info());
    errorAction.putValue(Action.NAME, i18n.t("Errors info"));
    errorAction.putValue(Action.SMALL_ICON, icons.alert());
    faqAction.putValue(Action.NAME, i18n.t("Kune FAQ"));
    faqAction.putValue(Action.SMALL_ICON, icons.kune());
    // aboutAction.setShortcut(shortcut);
    // shortcutReg.put(shortcut, aboutAction);

    signInLink.get();
    myGroupsMenu.get();
    newGroupLink.get();
    createGotoKune();
    MenuItemDescriptor.build(MORE_MENU, faqAction);
    MenuItemDescriptor.build(MORE_MENU, bugsAction);
    MenuItemDescriptor.build(MORE_MENU, errorAction);
    MenuItemDescriptor.build(MORE_MENU, aboutAction);
    MenuSeparatorDescriptor.build(MORE_MENU);
    MenuItemDescriptor.build(MORE_MENU, wavePowered);
    MORE_MENU.setParent(RIGHT_TOOLBAR);
    signOutLink.get();
    refreshActionsImpl();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.sitebar.SitebarActions#refreshActions()
   */
  @Override
  public void refreshActions() {
    refreshActionsImpl();
  }

  /**
   * Refresh actions impl.
   */
  private void refreshActionsImpl() {
    getView().getLeftBar().clear();
    getView().getRightBar().clear();
    getView().getLeftBar().add(LEFT_TOOLBAR);
    getView().getRightBar().add(RIGHT_TOOLBAR);
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

}
