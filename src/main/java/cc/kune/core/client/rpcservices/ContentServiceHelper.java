package cc.kune.core.client.rpcservices;

import cc.kune.common.client.notify.ConfirmAskEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.OnAcceptCallback;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;
import cc.kune.core.shared.dto.StateContainerDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class ContentServiceHelper {

  private final Provider<ContentServiceAsync> contentService;
  private final EventBus eventBus;
  private final I18nTranslationService i18n;
  private final Session session;
  private final StateManager stateManager;

  @Inject
  public ContentServiceHelper(final Session session, final I18nTranslationService i18n,
      final EventBus eventBus, final Provider<ContentServiceAsync> contentService,
      final StateManager stateManager) {
    this.session = session;
    this.i18n = i18n;
    this.eventBus = eventBus;
    this.contentService = contentService;
    this.stateManager = stateManager;
  }

  public void addParticipants(final StateToken token, final SocialNetworkSubGroup subGroup) {
    contentService.get().addParticipants(session.getUserHash(), token,
        session.getCurrentGroupShortName(), subGroup, new AsyncCallback<Boolean>() {
          @Override
          public void onFailure(final Throwable caught) {
            NotifyUser.important(i18n.t("Seems that the list of partipants were added partially. Please, retry"));
          }

          @Override
          public void onSuccess(final Boolean result) {
            NotifyUser.info(result ? subGroup.equals(SocialNetworkSubGroup.PUBLIC) ? i18n.t("Shared with general public. Now anyone can participate")
                : i18n.t("Shared with members")
                : i18n.t("All these members are already partipating"));
          }
        });
  }

  public void delContent(final StateToken token) {
    ConfirmAskEvent.fire(eventBus, i18n.t("Please confirm"), i18n.t("Are you sure?"), i18n.t("Yes"),
        i18n.t("No"), null, null, new OnAcceptCallback() {
          @Override
          public void onSuccess() {
            NotifyUser.showProgress();
            contentService.get().delContent(session.getUserHash(), token,
                new AsyncCallbackSimple<StateContainerDTO>() {
                  @Override
                  public void onSuccess(final StateContainerDTO state) {
                    final StateToken parentToken = state.getStateToken();
                    if (session.getCurrentStateToken().equals(parentToken)) {
                      stateManager.setRetrievedStateAndGo(state);
                    } else {
                      stateManager.gotoStateToken(parentToken, false);
                    }
                    NotifyUser.hideProgress();
                  }
                });
          }
        });
  }
}
