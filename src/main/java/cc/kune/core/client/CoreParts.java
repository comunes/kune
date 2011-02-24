package cc.kune.core.client;

import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
import cc.kune.core.client.sitebar.SiteUserOptionsPresenter;
import cc.kune.core.client.sn.GroupMembersPresenter;
import cc.kune.core.client.sn.actions.GroupMembersConfActions;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.ui.footer.license.EntityLicensePresenter;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class CoreParts {

    @Inject
    public CoreParts(final Session session, final Provider<GroupMembersPresenter> grouMembersPresenter,
            final Provider<GroupMembersConfActions> groupMembersConfActions,
            final Provider<SiteUserOptionsPresenter> userOptions, final Provider<EntityLicensePresenter> licenseFooter) {
        session.onInitDataReceived(true, new AppStartHandler() {
            @Override
            public void onAppStart(final AppStartEvent event) {
                groupMembersConfActions.get();
                grouMembersPresenter.get();
                userOptions.get();
                licenseFooter.get();
            }
        });
    }
}
