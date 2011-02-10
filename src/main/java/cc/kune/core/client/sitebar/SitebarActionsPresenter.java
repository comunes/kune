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

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.gxtui.AbstractGxtMenuGui;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarDescriptor;
import cc.kune.common.client.ui.KuneWindowUtils;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.notify.msgs.UserNotifyEvent;
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
        Presenter<SitebarActionsPresenter.SitebarActionsView, SitebarActionsPresenter.SitebarActionsProxy> {

    @ProxyCodeSplit
    public interface SitebarActionsProxy extends Proxy<SitebarActionsPresenter> {
    }
    public interface SitebarActionsView extends View {

        IsActionExtensible getLeftBar();

        IsActionExtensible getRightBar();
    }

    public static final ToolbarDescriptor LEFT_TOOLBAR = new ToolbarDescriptor();
    public static final MenuDescriptor OPTIONS_MENU = new MenuDescriptor();
    public static final ToolbarDescriptor RIGHT_TOOLBAR = new ToolbarDescriptor();

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

    public IsActionExtensible getLeftToolbar() {
        return getView().getLeftBar();
    }

    public MenuDescriptor getOptionsMenu() {
        return OPTIONS_MENU;
    }

    public IsActionExtensible getRightToolbar() {
        return getView().getRightBar();
    }

    private void init() {
        getView().getLeftBar().addAction(LEFT_TOOLBAR);
        getView().getRightBar().addAction(RIGHT_TOOLBAR);
    }

    @ProxyEvent
    public void onAppStart(final AppStartEvent event) {
        final IsActionExtensible right = getView().getRightBar();
        OPTIONS_MENU.putValue(Action.NAME, i18n.t("Options"));
        // OPTIONS_MENU.setParent(RIGHT_TOOLBAR);
        final AbstractExtendedAction action = new AbstractExtendedAction() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                getEventBus().fireEvent(new UserNotifyEvent("Testing only"));
            }
        };
        action.putValue(Action.NAME, "Test");
        final MenuItemDescriptor testMenuItem = new MenuItemDescriptor(OPTIONS_MENU, action);
        OPTIONS_MENU.setStyles("k-no-backimage, k-btn-sitebar");
        OPTIONS_MENU.putValue(AbstractGxtMenuGui.MENU_POSITION, AbstractGxtMenuGui.MenuPosition.bl);
        // final ToolbarSeparatorDescriptor separator = new
        // ToolbarSeparatorDescriptor(Type.separator, RIGHT_TOOLBAR);
        // final ToolbarSeparatorDescriptor separator2 = new
        // ToolbarSeparatorDescriptor(Type.separator, RIGHT_TOOLBAR);

        final MenuSeparatorDescriptor menuSeparator = new MenuSeparatorDescriptor(OPTIONS_MENU);
        final AbstractExtendedAction bugsAction = new AbstractExtendedAction() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                KuneWindowUtils.open("http://kune.ourproject.org/issues/");
            }
        };
        bugsAction.putValue(Action.NAME, i18n.t("Report Kune issues/problems"));
        bugsAction.putValue(Action.SMALL_ICON, icons.bug());
        final MenuItemDescriptor bugs = new MenuItemDescriptor(OPTIONS_MENU, bugsAction);

        // final KeyStroke shortcut = Shortcut.getShortcut(true, true, false,
        // false, Character.valueOf('K'));
        final AbstractExtendedAction aboutAction = new AbstractExtendedAction() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                // view.showAboutDialog();
            }
        };

        aboutAction.putValue(Action.NAME, i18n.t("About kune"));
        aboutAction.putValue(Action.SMALL_ICON, res.kuneIcon16());
        // aboutAction.setShortcut(shortcut);
        // shortcutReg.put(shortcut, aboutAction);

        right.addAction(signInLink.get());
        right.addAction(signOutLink.get());
        // right.addAction(separator2);
        right.addAction(newGroupLink.get());
        // right.addAction(separator);
        right.addAction(OPTIONS_MENU);
        right.addAction(testMenuItem);
        right.addAction(menuSeparator);
        right.addAction(bugs);
        right.addAction(new MenuItemDescriptor(OPTIONS_MENU, aboutAction));

    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }
}
