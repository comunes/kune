package cc.kune.lists.server.rpc;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.server.UserSession;
import cc.kune.core.server.auth.ActionLevel;
import cc.kune.core.server.auth.Authenticated;
import cc.kune.core.server.auth.Authorizated;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentUtils;
import cc.kune.core.server.rpc.ContentRPC;
import cc.kune.core.server.rpc.RPC;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.GroupListMode;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Container;
import cc.kune.domain.Group;
import cc.kune.lists.client.rpc.ListsService;
import cc.kune.lists.shared.ListsConstants;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class ListsRPC implements ListsService, RPC {
  private final ContainerManager contentManager;
  private final ContentRPC contentRPC;
  private final Provider<UserSession> userSessionProvider;

  @Inject
  public ListsRPC(final ContentRPC contentRPC, final Provider<UserSession> userSessionProvider,
      final ContainerManager contentManager) {
    this.contentRPC = contentRPC;
    this.userSessionProvider = userSessionProvider;
    this.contentManager = contentManager;
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.container)
  @Transactional
  public StateContainerDTO createList(final String userHash, final StateToken parentToken,
      final String title, final String description, final boolean isPublic) {
    final StateContainerDTO result = contentRPC.addFolder(userHash, parentToken, title,
        ListsConstants.TYPE_LIST);
    // Not public list, don't permit subscriptions neither view posts
    return contentRPC.getState(setContainerAcl(result.getStateToken(), isPublic));
  }

  private Group getUserGroup() {
    return userSessionProvider.get().getUser().getUserGroup();
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Viewer, actionLevel = ActionLevel.container)
  @Transactional
  public StateContentDTO newPost(final String userHash, final StateToken parentToken, final String title) {
    final StateContentDTO content = contentRPC.addContent(userHash, parentToken, title,
        ListsConstants.TYPE_POST);
    return content;
  }

  private Container setContainerAcl(final StateToken token, final boolean isPublic) {
    final Container container = contentManager.find(ContentUtils.parseId(token.getFolder()));
    final AccessLists acl = new AccessLists();
    acl.getAdmins().setMode(GroupListMode.NORMAL);
    acl.getAdmins().add(getUserGroup());
    acl.getEditors().setMode(isPublic ? GroupListMode.EVERYONE : GroupListMode.NOBODY);
    setViewersAcl(isPublic, acl);
    contentManager.setAccessList(container, acl);
    return container;
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Administrator, actionLevel = ActionLevel.container)
  @Transactional
  public StateContainerDTO setPublic(final String hash, final StateToken token, final Boolean isPublic) {
    final Container container = setPublicAcl(token, isPublic);
    return contentRPC.getState(container);
  }

  private Container setPublicAcl(final StateToken token, final boolean isPublic) {
    final Container container = contentManager.find(ContentUtils.parseId(token.getFolder()));
    final AccessLists acl = container.getAccessLists();
    setViewersAcl(isPublic, acl);
    contentManager.setAccessList(container, acl);
    return container;
  }

  private void setViewersAcl(final boolean isPublic, final AccessLists acl) {
    acl.getViewers().clear();
    acl.getViewers().setMode(isPublic ? GroupListMode.EVERYONE : GroupListMode.NOBODY);
  }

  @Override
  @Authenticated
  @Authorizated(accessRolRequired = AccessRol.Viewer, actionLevel = ActionLevel.container)
  @Transactional
  public StateContainerDTO subscribeToList(final String userHash, final StateToken token,
      final Boolean subscribe) {
    final Container container = contentManager.find(ContentUtils.parseId(token.getFolder()));
    final AccessLists acl = container.getAccessLists();
    if (subscribe) {
      if (!acl.getAdmins().includes(getUserGroup())
          && acl.getViewers().getMode().equals(GroupListMode.NOBODY)) {
        // Not public list, don't permit subscriptions neither view posts
        throw new AccessViolationException();
      } else {
        acl.getEditors().add(getUserGroup());
      }
    } else {
      acl.getEditors().remove(getUserGroup());
    }
    contentManager.setAccessList(container, acl);
    return contentRPC.getState(container);
  }

}
