package cc.kune.docs.client;

import cc.kune.docs.client.actions.DocsClientActions;
import cc.kune.docs.client.viewers.DocViewerPanel;
import cc.kune.docs.client.viewers.DocViewerPresenter;
import cc.kune.docs.client.viewers.FolderViewerPanel;
import cc.kune.docs.client.viewers.FolderViewerPresenter;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class DocsGinModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        bind(DocsClientTool.class).in(Singleton.class);
        bind(DocsClientActions.class).in(Singleton.class);
        bindPresenter(DocViewerPresenter.class, DocViewerPresenter.DocViewerView.class, DocViewerPanel.class,
                DocViewerPresenter.DocViewerProxy.class);
        bindPresenter(FolderViewerPresenter.class, FolderViewerPresenter.FolderViewerView.class,
                FolderViewerPanel.class, FolderViewerPresenter.FolderViewerProxy.class);
    }

}
