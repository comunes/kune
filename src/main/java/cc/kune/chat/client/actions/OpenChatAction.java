package cc.kune.chat.client.actions;

import cc.kune.chat.client.ChatClient;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.core.shared.dto.AbstractContentSimpleDTO;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class OpenChatAction extends RolAction {

  private final Provider<ChatClient> chatClient;
  private final I18nTranslationService i18n;
  private final Session session;
  private final Provider<SignIn> signIn;
  private final StateManager stateManager;

  @Inject
  public OpenChatAction(final Provider<ChatClient> chatClient, final Session session,
      final StateManager stateManager, final Provider<SignIn> signIn, final I18nTranslationService i18n) {
    super(AccessRolDTO.Viewer, false);
    this.chatClient = chatClient;
    this.session = session;
    this.stateManager = stateManager;
    this.signIn = signIn;
    this.i18n = i18n;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    if (session.isLogged()) {
      final ChatClient chat = chatClient.get();
      final Object target = event.getTarget();
      final String roomName = (target instanceof AbstractContentSimpleDTO) ? ((AbstractContentSimpleDTO) target).getName()
          : ((StateContainerDTO) session.getCurrentState()).getTitle();
      chat.joinRoom(roomName, session.getCurrentUserInfo().getShortName());
      chat.show();
    } else {
      signIn.get().setErrorMessage(i18n.t("Sign in or create an account to access to this room"),
          NotifyLevel.info);
      stateManager.gotoHistoryToken(TokenUtils.addRedirect(SiteTokens.SIGNIN,
          session.getCurrentStateToken().toString()));
    }
  }
}