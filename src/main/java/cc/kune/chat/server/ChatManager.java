package cc.kune.chat.server;

import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Container;
import cc.kune.domain.User;

public interface ChatManager {

  Container addRoom(String userHash, User user, StateToken parentToken, String roomName, String subject);

}
