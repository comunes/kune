package cc.kune.chat.client.actions;

import cc.kune.chat.client.ChatClient;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.AbstractContentSimpleDTO;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.StateContainerDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class OpenChatAction extends RolAction {

  private final Provider<ChatClient> chatClient;
  private final Session session;

  @Inject
  public OpenChatAction(final Provider<ChatClient> chatClient, final Session session) {
    super(AccessRolDTO.Viewer, true);
    this.chatClient = chatClient;
    this.session = session;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    final ChatClient chat = chatClient.get();
    final Object target = event.getTarget();
    final String roomName = (target instanceof AbstractContentSimpleDTO) ? ((AbstractContentSimpleDTO) target).getName()
        : ((StateContainerDTO) session.getCurrentState()).getTitle();
    chat.joinRoom(roomName, session.getCurrentUserInfo().getShortName());
    chat.show();
  }
}