package cc.kune.core.client;

import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
import cc.kune.core.client.sitebar.SiteUserOptionsPresenter;
import cc.kune.core.client.sn.GroupSNPresenter;
import cc.kune.core.client.sn.UserSNPresenter;
import cc.kune.core.client.sn.actions.registry.GroupSNConfActions;
import cc.kune.core.client.sn.actions.registry.UserSNConfActions;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class CoreParts {

  @Inject
  public CoreParts(final Session session, final Provider<GroupSNPresenter> groupMembersPresenter,
      final Provider<UserSNPresenter> buddiesAndParticipationPresenter,
      final Provider<GroupSNConfActions> groupMembersConfActions,
      final Provider<UserSNConfActions> userSNConfActions,
      final Provider<SiteUserOptionsPresenter> userOptions) {
    session.onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        groupMembersConfActions.get();
        userSNConfActions.get();
        groupMembersPresenter.get();
        buddiesAndParticipationPresenter.get();
        userOptions.get();
      }
    });
  }
}
