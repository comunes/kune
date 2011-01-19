package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.IconLabelDescriptor;
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
    private MenuDescriptor optionsMenu;

    @Inject
    public SitebarActionsPresenter(EventBus eventBus, SitebarActionsView view, SitebarActionsProxy proxy,
            I18nTranslationService i18n) {
        super(eventBus, view, proxy);
        this.i18n = i18n;
    }

    @Override
    public void addAction(AbstractGuiActionDescrip action) {
        getView().addAction(action);
    }

    @Override
    public void addActions(AbstractGuiActionDescrip... actions) {
        getView().addActions(actions);

    }

    @Override
    public void addActions(GuiActionDescCollection actions) {
        getView().addActions(actions);
    }

    public MenuDescriptor getOptionsMenu() {
        return optionsMenu;
    }

    public void init() {
        optionsMenu = new MenuDescriptor(i18n.t("Options"));

        IconLabelDescriptor icon = new IconLabelDescriptor("test");
        AbstractExtendedAction action = new AbstractExtendedAction() {

            @Override
            public void actionPerformed(ActionEvent event) {
                getEventBus().fireEvent(new UserNotifyEvent("Testing only"));
            }
        };
        MenuItemDescriptor mItem = new MenuItemDescriptor(optionsMenu, action);
        optionsMenu.setStyles("k-floatright, k-no-backimage");
        action.putValue(Action.NAME, "kk");
        action.putValue(Action.SHORT_DESCRIPTION, "tooltip");
        ButtonDescriptor signIn = new ButtonDescriptor(action);
        ToolbarSeparatorDescriptor fill = new ToolbarSeparatorDescriptor(Type.fill, TOOLBAR);
        addAction(TOOLBAR);
        addAction(fill);
        addAction(icon);
        addAction(signIn);
        icon.setStyles("k-floatright");
        signIn.setStyles("k-floatright, k-no-backimage");
        addAction(optionsMenu);
        addAction(mItem);
    }

    @ProxyEvent
    public void onAppStart(AppStartEvent event) {
        init();
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }
}
