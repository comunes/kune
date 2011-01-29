package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.notify.msgs.UserNotifyEvent;
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
        IsActionExtensible {

    @ProxyCodeSplit
    public interface SitebarActionsProxy extends Proxy<SitebarActionsPresenter> {
    }
    public interface SitebarActionsView extends View, IsActionExtensible {
    }

    public static final ToolbarDescriptor TOOLBAR = new ToolbarDescriptor();

    private final I18nTranslationService i18n;
    private final Provider<SitebarNewGroupLink> newGroupLink;
    private MenuDescriptor optionsMenu;
    private final Provider<SitebarSignInLink> signInLink;
    private final Provider<SitebarSignOutLink> signOutLink;

    @Inject
    public SitebarActionsPresenter(final EventBus eventBus, final SitebarActionsView view,
            final SitebarActionsProxy proxy, final I18nTranslationService i18n,
            final Provider<SitebarNewGroupLink> newGroupLink, final Provider<SitebarSignOutLink> signOutLink,
            final Provider<SitebarSignInLink> signInLink) {
        super(eventBus, view, proxy);
        this.i18n = i18n;
        this.newGroupLink = newGroupLink;
        this.signOutLink = signOutLink;
        this.signInLink = signInLink;
    }

    @Override
    public void addAction(final AbstractGuiActionDescrip action) {
        getView().addAction(action);
    }

    @Override
    public void addActions(final AbstractGuiActionDescrip... actions) {
        getView().addActions(actions);

    }

    @Override
    public void addActions(final GuiActionDescCollection actions) {
        getView().addActions(actions);
    }

    public MenuDescriptor getOptionsMenu() {
        return optionsMenu;
    }

    public void init() {
        optionsMenu = new MenuDescriptor(i18n.t("Options"));
        final AbstractExtendedAction action = new AbstractExtendedAction() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                getEventBus().fireEvent(new UserNotifyEvent("Testing only"));
            }
        };
        action.putValue(Action.NAME, "Test");
        final MenuItemDescriptor mItem = new MenuItemDescriptor(optionsMenu, action);
        optionsMenu.setStyles("k-no-backimage, k-btn-sitebar");
        final ToolbarSeparatorDescriptor separator = new ToolbarSeparatorDescriptor(Type.separator,
                SitebarActionsPresenter.TOOLBAR);
        final ToolbarSeparatorDescriptor spacer = new ToolbarSeparatorDescriptor(Type.spacer,
                SitebarActionsPresenter.TOOLBAR);
        addAction(TOOLBAR);
        addAction(signInLink.get());
        addAction(signOutLink.get());
        addAction(spacer);
        addAction(separator);
        addAction(newGroupLink.get());
        addAction(spacer);
        addAction(separator);
        addAction(optionsMenu);
        addAction(mItem);
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
