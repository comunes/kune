package cc.kune.core.client.sn.actions;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.SocialNetworkServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class SocialNetClientUtils {

  private final I18nTranslationService i18n;
  private final Session session;
  private final Provider<SocialNetworkServiceAsync> snServiceProvider;
  private final StateManager stateManager;

  @Inject
  public SocialNetClientUtils(final StateManager stateManager, final Session session,
      final I18nTranslationService i18n, final Provider<SocialNetworkServiceAsync> snServiceProvider) {
    this.stateManager = stateManager;
    this.session = session;
    this.i18n = i18n;
    this.snServiceProvider = snServiceProvider;
  }

  public void changeToAdmin(final String shortName) {
    NotifyUser.showProgress();
    snServiceProvider.get().setCollabAsAdmin(session.getUserHash(),
        session.getCurrentState().getStateToken(), shortName,
        new AsyncCallbackSimple<SocialNetworkDataDTO>() {
          @Override
          public void onSuccess(final SocialNetworkDataDTO result) {
            SocialNetClientUtils.this.onSuccess(result);
          }
        });
  }

  public void changeToCollab(final String shortName) {
    NotifyUser.showProgress();
    snServiceProvider.get().setAdminAsCollab(session.getUserHash(),
        session.getCurrentState().getStateToken(), shortName,
        new AsyncCallbackSimple<SocialNetworkDataDTO>() {
          @Override
          public void onSuccess(final SocialNetworkDataDTO result) {
            SocialNetClientUtils.this.onSuccess(result);
          }
        });
  }

  private void onSuccess(final SocialNetworkDataDTO result) {
    NotifyUser.hideProgress();
    NotifyUser.info(i18n.t("Member type changed"));
    stateManager.setSocialNetwork(result);
  }
}
