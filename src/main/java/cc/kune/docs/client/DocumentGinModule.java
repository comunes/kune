package cc.kune.docs.client;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class DocumentGinModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bind(DocumentClientTool.class).in(Singleton.class);
        bindPresenter(DocsViewerPresenter.class, DocsViewerPresenter.DocsViewerView.class, DocsViewerPanel.class,
                DocsViewerPresenter.DocsViewerProxy.class);
    }

}
