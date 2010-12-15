package cc.kune.core.client.notify;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class SpinerPresenter extends Presenter<SpinerPresenter.SpinerView, SpinerPresenter.SpinerProxy> {

    public interface SpinerProxy extends ProxyPlace<SpinerPresenter> {
    }

    public interface SpinerView extends View {

        void fade();

        void show(String message);

        void showLoading();
    }

    @Inject
    public SpinerPresenter(final EventBus eventBus, final SpinerView view, final SpinerProxy proxy) {
        super(eventBus, view, proxy);
    }

    public void fade() {
        getView().fade();
    }

    public void show(final String message) {
        getView().show(message);
    }

    public void showLoading() {
        getView().showLoading();
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }
}
