package cc.kune.chat.server;

import cc.kune.chat.shared.ChatConstants;
import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.ContentNotFoundException;
import cc.kune.core.client.errors.GroupNotFoundException;
import cc.kune.core.server.content.ContentUtils;
import cc.kune.core.server.content.CreationService;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.xmpp.ChatConnection;
import cc.kune.core.server.xmpp.XmppManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Container;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ChatManagerDefault implements ChatManager {

  private final CreationService creationService;
  private final GroupManager groupManager;
  private final XmppManager xmppManager;

  @Inject
  public ChatManagerDefault(final XmppManager xmppManager, final GroupManager groupManager,
      final CreationService creationService) {
    this.xmppManager = xmppManager;
    this.groupManager = groupManager;
    this.creationService = creationService;
  }

  @Override
  public Container addRoom(final String userHash, final User user, final StateToken parentToken,
      final String roomName, final String subject) {
    final String groupShortName = parentToken.getGroup();
    final String userShortName = user.getShortName();
    final ChatConnection connection = xmppManager.login(userShortName, user.getPassword(), userHash);
    xmppManager.createRoom(connection, roomName, userShortName + userHash, subject);
    xmppManager.disconnect(connection);
    try {
      return creationService.createFolder(groupManager.findByShortName(groupShortName),
          ContentUtils.parseId(parentToken.getFolder()), roomName, user.getLanguage(),
          ChatConstants.TYPE_ROOM);
    } catch (final ContentNotFoundException e) {
      xmppManager.destroyRoom(connection, roomName);
      throw new ContentNotFoundException();
    } catch (final AccessViolationException e) {
      xmppManager.destroyRoom(connection, roomName);
      throw new AccessViolationException();
    } catch (final GroupNotFoundException e) {
      xmppManager.destroyRoom(connection, roomName);
      throw new GroupNotFoundException();
    }
  }
}
