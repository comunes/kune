package cc.kune.chat.client.actions;

import java.util.Date;

import cc.kune.chat.client.ChatClient;
import cc.kune.chat.client.ChatInstances;
import cc.kune.chat.client.resources.ChatResources;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.SimpleResponseCallback;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class StartAssemblyWithMembersAction extends OpenGroupPublicChatRoomAction {

  private final Provider<ContentServiceAsync> contentService;

  @Inject
  public StartAssemblyWithMembersAction(final Session session,
      final AccessRightsClientManager accessRightsClientManager, final ChatClient chatClient,
      final StateManager stateManager, final I18nTranslationService i18n, final ChatResources res,
      final ChatInstances chatInstances, final Provider<ContentServiceAsync> contentService) {
    super(session, accessRightsClientManager, chatClient, stateManager, i18n, res, chatInstances);
    this.contentService = contentService;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    NotifyUser.askConfirmation(i18n.t("Please confirm"),
        i18n.t("Start a collaborative document for the meeting minutes?"), new SimpleResponseCallback() {
          @Override
          public void onCancel() {
          }

          @Override
          public void onSuccess() {
            contentService.get().writeTo(
                session.getUserHash(),
                session.getCurrentStateToken(),
                false,
                i18n.t("Meeting minutes of [%s] on [%s]", session.getCurrentGroupShortName(),
                    DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG).format(new Date())),
                i18n.t("You can collaboratively edit this document with the meeting minutes."),
                new AsyncCallbackSimple<String>() {
                  @Override
                  public void onSuccess(final String url) {
                    stateManager.gotoHistoryToken(url);
                    NotifyUser.info("Now you can edit this message");
                  }
                });
          }
        });
    super.actionPerformed(event);
  }

}
