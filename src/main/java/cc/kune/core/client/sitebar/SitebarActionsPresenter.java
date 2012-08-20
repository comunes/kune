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
package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuSeparatorDescriptor;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.common.client.ui.KuneWindowUtils;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.resources.CoreResources;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class SitebarActionsPresenter extends
    Presenter<SitebarActionsPresenter.SitebarActionsView, SitebarActionsPresenter.SitebarActionsProxy>
    implements SitebarActions {

  @ProxyCodeSplit
  public interface SitebarActionsProxy extends Proxy<SitebarActionsPresenter> {
  }

  public interface SitebarActionsView extends View {

    IsActionExtensible getLeftBar();

    IsActionExtensible getRightBar();

    void showAboutDialog();

    void showErrorDialog();
  }

  public static final String SITE_OPTIONS_MENU = "kune-sop-om";
  private final CommonResources commonRes;
  private final I18nTranslationService i18n;
  private final Provider<MyGroupsMenu> myGroupsMenu;
  private final Provider<SitebarNewGroupLink> newGroupLink;
  private final CoreResources res;
  private final Provider<SitebarSignInLink> signInLink;
  private final Provider<SitebarSignOutLink> signOutLink;

  @Inject
  public SitebarActionsPresenter(final EventBus eventBus, final SitebarActionsView view,
      final SitebarActionsProxy proxy, final I18nTranslationService i18n,
      final Provider<SitebarNewGroupLink> newGroupLink, final Provider<SitebarSignOutLink> signOutLink,
      final Provider<SitebarSignInLink> signInLink, final CoreResources res,
      final CommonResources commonRes, final Provider<MyGroupsMenu> myGroupsMenu) {
    super(eventBus, view, proxy);
    this.i18n = i18n;
    this.newGroupLink = newGroupLink;
    this.signOutLink = signOutLink;
    this.signInLink = signInLink;
    this.myGroupsMenu = myGroupsMenu;
    this.res = res;
    this.commonRes = commonRes;
    init();
  }

  private MenuItemDescriptor createGotoKune() {
    final MenuItemDescriptor gotoKuneDevSite = new MenuItemDescriptor(MORE_MENU, new AbstractAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        KuneWindowUtils.open("http://kune.ourproject.org/");
      }
    });
    gotoKuneDevSite.putValue(Action.NAME, i18n.t("kune development site"));
    gotoKuneDevSite.putValue(Action.SMALL_ICON, res.kuneIcon16());
    return gotoKuneDevSite;
  }

  public MenuDescriptor getOptionsMenu() {
    return MORE_MENU;
  }

  private void init() {
    MORE_MENU.withId(SITE_OPTIONS_MENU);
  }

  @ProxyEvent
  public void onAppStart(final AppStartEvent event) {
    MORE_MENU.withText(i18n.t("More"));
    MORE_MENU.withIcon(res.arrowdownsitebar());
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
    bugsAction.putValue(Action.SMALL_ICON, res.bug());

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
    aboutAction.putValue(Action.NAME, i18n.t("About kune"));
    aboutAction.putValue(Action.SMALL_ICON, commonRes.info());
    errorAction.putValue(Action.NAME, i18n.t("Errors info"));
    errorAction.putValue(Action.SMALL_ICON, commonRes.important());
    faqAction.putValue(Action.NAME, i18n.t("Kune FAQ"));
    faqAction.putValue(Action.SMALL_ICON, res.kuneIcon16());
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

  @Override
  public void refreshActions() {
    refreshActionsImpl();
  }

  private void refreshActionsImpl() {
    getView().getLeftBar().clear();
    getView().getRightBar().clear();
    getView().getLeftBar().add(LEFT_TOOLBAR);
    getView().getRightBar().add(RIGHT_TOOLBAR);
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

}
