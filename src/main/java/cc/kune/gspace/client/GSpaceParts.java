package cc.kune.gspace.client;

import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
import cc.kune.core.client.state.Session;
import cc.kune.gspace.client.options.GroupOptions;
import cc.kune.gspace.client.options.GroupOptionsCollection;
import cc.kune.gspace.client.options.UserOptions;
import cc.kune.gspace.client.options.UserOptionsCollection;
import cc.kune.gspace.client.options.license.GroupOptionsDefLicense;
import cc.kune.gspace.client.options.license.UserOptionsDefLicense;
import cc.kune.gspace.client.options.logo.GroupOptionsLogo;
import cc.kune.gspace.client.options.logo.UserOptionsLogo;
import cc.kune.gspace.client.options.pscape.GroupOptionsPublicSpaceConf;
import cc.kune.gspace.client.options.pscape.UserOptionsPublicSpaceConf;
import cc.kune.gspace.client.options.tools.GroupOptionsToolsConf;
import cc.kune.gspace.client.options.tools.UserOptionsToolsConf;
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
            final Provider<DocViewerPresenter> docsViewer, final Provider<FolderViewerPresenter> folderViewer,
            final Provider<GroupOptions> go, final Provider<UserOptions> uo,
            final Provider<GroupOptionsCollection> goc, final Provider<UserOptionsCollection> uoc,
            final Provider<GroupOptionsDefLicense> gdl, final Provider<GroupOptionsPublicSpaceConf> gps,
            final Provider<GroupOptionsLogo> gl, final Provider<GroupOptionsToolsConf> gtc,
            final Provider<UserOptionsDefLicense> udl, final Provider<UserOptionsPublicSpaceConf> ups,
            final Provider<UserOptionsLogo> ul, final Provider<UserOptionsToolsConf> utc) {
        // , final Provider<GroupOptionsPanel> gop,
        // , final Provider<UserOptionsPanel> uop,
        // final Provider<GroupOptionsPublicSpaceConfPanel> gpsp,
        // final Provider<GroupOptionsDefLicensePanel> gdlp,
        // final Provider<GroupOptionsLogoPanel> glp,
        // final Provider<GroupOptionsToolsConfPanel> gtcp,
        // final Provider<UserOptionsLogoPanel> ulp,
        // final Provider<UserOptionsDefLicensePanel> udlp,
        // final Provider<UserOptionsPublicSpaceConfPanel> upsp,
        // final Provider<UserOptionsToolsConfPanel> utcp

        session.onAppStart(true, new AppStartHandler() {
            @Override
            public void onAppStart(final AppStartEvent event) {
                licenseFooter.get();
                tagsPresenter.get();
                toolSelector.get();
                docsViewer.get();
                folderViewer.get();

                // Add User & Groups Options
                goc.get().add(gtc);
                goc.get().add(gl);
                goc.get().add(gps);
                goc.get().add(gdl);
                uoc.get().add(utc);
                uoc.get().add(ul);
                uoc.get().add(ups);
                uoc.get().add(udl);

                // Init
                go.get();
                uo.get();
            }
        });
    }
}
