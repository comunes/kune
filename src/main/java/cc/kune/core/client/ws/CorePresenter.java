package cc.kune.core.client.ws;

import cc.kune.core.client.CoreEventBus;
import cc.kune.core.client.ws.CorePresenter.ICoreView;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = CoreView.class)
public class CorePresenter extends BasePresenter<ICoreView, CoreEventBus> {
    public interface ICoreView {
    }

    public void onStart() {
    }
}
