package cc.kune.core.client.ws;

import cc.kune.core.client.CoreEventBus;
import cc.kune.core.client.ws.CorePresenter.ICoreView;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

/**
 * The Class CorePresenter.
 */
@Presenter(view = CoreView.class)
public class CorePresenter extends BasePresenter<ICoreView, CoreEventBus> {

    /**
     * The Interface ICoreView.
     */
    public interface ICoreView {
    }

    @Inject
    public CorePresenter(final I18nTranslationService i18n) {
    }

    /**
     * On start.
     */
    public void onStart() {
    }
}
