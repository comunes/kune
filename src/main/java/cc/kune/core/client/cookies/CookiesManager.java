package cc.kune.core.client.cookies;

import cc.kune.core.client.CoreEventBus;
import cc.kune.core.client.cookies.CookiesManager.ICookiesView;
import cc.kune.core.client.state.Session;

import com.google.gwt.user.client.Cookies;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = CookiesView.class)
public class CookiesManager extends BasePresenter<ICookiesView, CoreEventBus> {
    public interface ICookiesView {
        String getCurrentCookie();
    }

    @Inject
    public CookiesManager() {
    }

    public String getCurrentCookie() {
        return Cookies.getCookie(Session.USERHASH);
        // view.getCurrentCookie();
    }

    public void onDoNothing() {
    }

}
