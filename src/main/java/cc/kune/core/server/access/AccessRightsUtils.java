package cc.kune.core.server.access;

import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AccessRightsUtils {

  @Inject
  private static AccessRightsService rightsService;

  public static boolean correctMember(final User user, final Group group, final AccessRol memberType) {

    final AccessRights accessRights = rightsService.get(user, group.getSocialNetwork().getAccessLists());

    switch (memberType) {
    case Administrator:
      return accessRights.isAdministrable();
    case Editor:
      return accessRights.isEditable();
    default:
      return accessRights.isVisible();
    }
  }
}
