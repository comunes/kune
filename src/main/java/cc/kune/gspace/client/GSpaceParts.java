package cc.kune.gspace.client;

import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
import cc.kune.core.client.state.Session;
import cc.kune.gspace.client.tags.TagsSummaryPresenter;
import cc.kune.gspace.client.tool.selector.ToolSelector;
import cc.kune.gspace.client.ui.footer.license.EntityLicensePresenter;
import cc.kune.gspace.client.viewers.DocViewerPresenter;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class GSpaceParts {

    @Inject
    public GSpaceParts(final Session session, final Provider<EntityLicensePresenter> licenseFooter,
            final Provider<TagsSummaryPresenter> tagsPresenter, final Provider<ToolSelector> toolSelector,
            final Provider<DocViewerPresenter> docsViewer, final Provider<FolderViewerPresenter> folderViewer) {
        session.onAppStart(true, new AppStartHandler() {
            @Override
            public void onAppStart(final AppStartEvent event) {
                licenseFooter.get();
                tagsPresenter.get();
                toolSelector.get();
                docsViewer.get();
                folderViewer.get();
            }
        });
    }
}
