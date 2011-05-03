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
import cc.kune.common.client.actions.gxtui.AbstractGxtMenuGui;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.common.client.ui.KuneWindowUtils;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.resources.icons.IconResources;
import cc.kune.core.shared.i18n.I18nTranslationService;

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
        Presenter<SitebarActionsPresenter.SitebarActionsView, SitebarActionsPresenter.SitebarActionsProxy> implements
        SitebarActions {

    @ProxyCodeSplit
    public interface SitebarActionsProxy extends Proxy<SitebarActionsPresenter> {
    }

    public interface SitebarActionsView extends View {

        IsActionExtensible getLeftBar();

        IsActionExtensible getRightBar();

        void showAboutDialog();

        void showErrorDialog();
    }
    public static final ToolbarDescriptor LEFT_TOOLBAR = new ToolbarDescriptor();

    public static final MenuDescriptor OPTIONS_MENU = new MenuDescriptor();
    public static final ToolbarDescriptor RIGHT_TOOLBAR = new ToolbarDescriptor();
    public static final String SITE_OPTIONS_MENU = "kune-sop-om";

    private final I18nTranslationService i18n;
    private final IconResources icons;
    private final Provider<SitebarNewGroupLink> newGroupLink;

    private final CoreResources res;
    private final Provider<SitebarSignInLink> signInLink;
    private final Provider<SitebarSignOutLink> signOutLink;

    @Inject
    public SitebarActionsPresenter(final EventBus eventBus, final SitebarActionsView view,
            final SitebarActionsProxy proxy, final I18nTranslationService i18n,
            final Provider<SitebarNewGroupLink> newGroupLink, final Provider<SitebarSignOutLink> signOutLink,
            final Provider<SitebarSignInLink> signInLink, final CoreResources res, final IconResources icons) {
        super(eventBus, view, proxy);
        this.i18n = i18n;
        this.newGroupLink = newGroupLink;
        this.signOutLink = signOutLink;
        this.signInLink = signInLink;
        this.res = res;
        this.icons = icons;
        init();
    }

    @Override
    public IsActionExtensible getLeftToolbar() {
        return getView().getLeftBar();
    }

    public MenuDescriptor getOptionsMenu() {
        return OPTIONS_MENU;
    }

    @Override
    public IsActionExtensible getRightToolbar() {
        return getView().getRightBar();
    }

    private void init() {
        OPTIONS_MENU.withId(SITE_OPTIONS_MENU);
        getView().getLeftBar().add(LEFT_TOOLBAR);
        getView().getRightBar().add(RIGHT_TOOLBAR);
    }

    @ProxyEvent
    public void onAppStart(final AppStartEvent event) {
        final IsActionExtensible right = getView().getRightBar();
        OPTIONS_MENU.putValue(Action.NAME, i18n.t("Options"));
        OPTIONS_MENU.putValue(Action.SMALL_ICON, res.arrowdownsitebar());
        OPTIONS_MENU.setParent(RIGHT_TOOLBAR);
        OPTIONS_MENU.setStyles("k-no-backimage, k-btn-sitebar, k-fl");
        OPTIONS_MENU.putValue(AbstractGxtMenuGui.MENU_POSITION, AbstractGxtMenuGui.MenuPosition.bl);
        final ToolbarSeparatorDescriptor separator = new ToolbarSeparatorDescriptor(Type.separator, RIGHT_TOOLBAR);
        final ToolbarSeparatorDescriptor separator2 = new ToolbarSeparatorDescriptor(Type.separator, RIGHT_TOOLBAR);

        final MenuSeparatorDescriptor menuSeparator = new MenuSeparatorDescriptor(OPTIONS_MENU);
        final AbstractExtendedAction bugsAction = new AbstractExtendedAction() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                KuneWindowUtils.open("http://kune.ourproject.org/issues/");
            }
        };
        bugsAction.putValue(Action.NAME, i18n.t("Report Kune issues/problems"));
        bugsAction.putValue(Action.SMALL_ICON, icons.bug());
        final MenuItemDescriptor reportBugs = new MenuItemDescriptor(OPTIONS_MENU, bugsAction);

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
        final MenuItemDescriptor gotoKuneDevSite = new MenuItemDescriptor(OPTIONS_MENU, new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                KuneWindowUtils.open("http://kune.ourproject.org/");
            }
        });
        gotoKuneDevSite.putValue(Action.NAME, i18n.t("kune development site"));
        gotoKuneDevSite.putValue(Action.SMALL_ICON, icons.kuneIcon16());

        wavePowered.putValue(Action.NAME, i18n.t("Apache Wave powered"));
        wavePowered.putValue(Action.SMALL_ICON, res.waveIcon());
        aboutAction.putValue(Action.NAME, i18n.t("About kune"));
        aboutAction.putValue(Action.SMALL_ICON, res.info());
        errorAction.putValue(Action.NAME, i18n.t("Errors info"));
        errorAction.putValue(Action.SMALL_ICON, res.important());
        // aboutAction.setShortcut(shortcut);
        // shortcutReg.put(shortcut, aboutAction);

        right.add(signInLink.get());
        right.add(signOutLink.get());
        right.add(separator2);
        right.add(newGroupLink.get());
        right.add(separator);
        right.add(OPTIONS_MENU);
        right.add(gotoKuneDevSite);
        right.add(reportBugs);
        right.add(new MenuItemDescriptor(OPTIONS_MENU, errorAction));
        right.add(new MenuItemDescriptor(OPTIONS_MENU, aboutAction));
        right.add(menuSeparator);
        right.add(new MenuItemDescriptor(OPTIONS_MENU, wavePowered));
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }

}
