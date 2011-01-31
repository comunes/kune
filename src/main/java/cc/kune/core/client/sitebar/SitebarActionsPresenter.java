package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
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

    public static final MenuDescriptor OPTIONS_MENU = new MenuDescriptor();
    public static final ToolbarDescriptor TOOLBAR = new ToolbarDescriptor();

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

    public void init() {
        OPTIONS_MENU.putValue(Action.NAME, i18n.t("Options"));
        final AbstractExtendedAction action = new AbstractExtendedAction() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                getEventBus().fireEvent(new UserNotifyEvent("Testing only"));
            }
        };
        action.putValue(Action.NAME, "Test");
        final MenuItemDescriptor testMenuItem = new MenuItemDescriptor(OPTIONS_MENU, action);
        OPTIONS_MENU.setStyles("k-no-backimage, k-btn-sitebar");
        final ToolbarSeparatorDescriptor separator = new ToolbarSeparatorDescriptor(Type.separator,
                SitebarActionsPresenter.TOOLBAR);
        final ToolbarSeparatorDescriptor separator2 = new ToolbarSeparatorDescriptor(Type.separator,
                SitebarActionsPresenter.TOOLBAR);
        final ToolbarSeparatorDescriptor spacer = new ToolbarSeparatorDescriptor(Type.spacer,
                SitebarActionsPresenter.TOOLBAR);
        final IsActionExtensible right = getView().getRightBar();

        final MenuSeparatorDescriptor sep = new MenuSeparatorDescriptor(OPTIONS_MENU);
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
        right.addAction(TOOLBAR);
        // addAction(separator);
        right.addAction(OPTIONS_MENU);
        right.addAction(signInLink.get());
        right.addAction(signOutLink.get());
        // addAction(spacer);
        // addAction(separator2);
        right.addAction(newGroupLink.get());
        // addAction(spacer);
        right.addAction(testMenuItem);
        right.addAction(sep);
        right.addAction(bugs);
        right.addAction(new MenuItemDescriptor(OPTIONS_MENU, aboutAction));

    }

    @ProxyEvent
    public void onAppStart(final AppStartEvent event) {
        init();
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }
}
