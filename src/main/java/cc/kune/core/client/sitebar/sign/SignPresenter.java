package cc.kune.core.client.sitebar.sign;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class SignPresenter extends Presenter<SignPresenter.SignView, SignPresenter.SignProxy> {

    @ProxyCodeSplit
    @NameToken("sign")
    public interface SignProxy extends ProxyPlace<SignPresenter> {
    }
    public interface SignView extends View {
    }

    @Inject
    public SignPresenter(final EventBus eventBus, final SignView view, final SignProxy proxy) {
        super(eventBus, view, proxy);
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }
}