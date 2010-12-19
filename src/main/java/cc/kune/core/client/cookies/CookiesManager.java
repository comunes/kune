package cc.kune.core.client.cookies;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;

public class CookiesManager extends Presenter<CookiesManager.CookiesManagerView, CookiesManager.CookiesManagerProxy> {

    @ProxyCodeSplit
    public interface CookiesManagerProxy extends Proxy<CookiesManager> {
    }

    public interface CookiesManagerView extends View {
        String getCurrentCookie();
    }

    @Inject
    public CookiesManager(final EventBus eventBus, final CookiesManagerView view, final CookiesManagerProxy proxy) {
        super(eventBus, view, proxy);
    }

    public String getCurrentCookie() {
        return getView().getCurrentCookie();
    }

    @Override
    protected void revealInParent() {
    }

}
