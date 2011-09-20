package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;

public class EventTargetUtils {

  public static StateToken getTargetToken(final ActionEvent event) {
    StateToken token;
    final Object target = event.getTarget();
    if (target instanceof GroupDTO) {
      token = ((GroupDTO) target).getStateToken();
    } else {
      token = ((UserSimpleDTO) target).getStateToken();
    }
    return token;
  }

  public static boolean isPerson(final ActionEvent event) {
    final Object target = event.getTarget();
    final boolean isUserDTO = target instanceof UserSimpleDTO;
    final boolean isGroup = target instanceof GroupDTO;
    return isUserDTO || (isGroup && ((GroupDTO) target).isPersonal());
  }

}
