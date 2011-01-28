package cc.kune.core.client.sitebar.spaces;

import cc.kune.common.client.noti.NotifyUser;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.wspace.client.WsArmor;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class SpaceSelectorPresenter extends
        Presenter<SpaceSelectorPresenter.SpaceSelectorView, SpaceSelectorPresenter.SpaceSelectorProxy> implements
        SpaceSelectorUiHandlers {

    @ProxyCodeSplit
    public interface SpaceSelectorProxy extends Proxy<SpaceSelectorPresenter> {
    }

    public interface SpaceSelectorView extends View, HasUiHandlers<SpaceSelectorUiHandlers> {

        void setGroupBtnDown(boolean down);

        void setHomeBtnDown(boolean down);

        void setPublicBtnDown(boolean down);

        void setUserBtnDown(boolean down);
    }

    private final WsArmor armor;
    private final I18nTranslationService i18n;
    private final Session session;
    private final Provider<SignIn> signIn;

    @Inject
    public SpaceSelectorPresenter(final EventBus eventBus, final SpaceSelectorView view,
            final SpaceSelectorProxy proxy, final WsArmor armor, final Session session, final Provider<SignIn> sigIn,
            final I18nTranslationService i18n) {
        super(eventBus, view, proxy);
        this.armor = armor;
        this.session = session;
        this.signIn = sigIn;
        this.i18n = i18n;
        getView().setUiHandlers(this);
    }

    @ProxyEvent
    public void onAppStart(final AppStartEvent event) {
        getView().setHomeBtnDown(false);
        getView().setUserBtnDown(false);
        getView().setGroupBtnDown(false);
        getView().setPublicBtnDown(false);
        onGroupSpaceSelect();
    }

    @Override
    public void onGroupSpaceSelect() {
        armor.selectGroupSpace();
        getView().setHomeBtnDown(false);
        getView().setUserBtnDown(false);
        getView().setGroupBtnDown(true);
        getView().setPublicBtnDown(false);
    }

    @Override
    public void onHomeSpaceSelect() {
        armor.selectHomeSpace();
        getView().setHomeBtnDown(true);
        getView().setUserBtnDown(false);
        getView().setGroupBtnDown(false);
        getView().setPublicBtnDown(false);
    }

    @Override
    public void onPublicSpaceClick() {
        armor.selectPublicSpace();
        getView().setHomeBtnDown(false);
        getView().setUserBtnDown(false);
        getView().setGroupBtnDown(false);
        getView().setPublicBtnDown(true);
    }

    @Override
    public void onUserSpaceSelect() {
        if (session.isLogged()) {
            armor.selectUserSpace();
            getView().setHomeBtnDown(false);
            getView().setUserBtnDown(true);
            getView().setGroupBtnDown(false);
            getView().setPublicBtnDown(false);
        } else {
            signIn.get().doSignIn();
            getView().setUserBtnDown(false);
            NotifyUser.info(i18n.t("Sign in to access to your workspace"));
        }
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }
}