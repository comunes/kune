package cc.kune.chat.client.actions;

import cc.kune.chat.client.ChatClient;
import cc.kune.chat.client.ChatInstances;
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class AddAsBuddieAction extends AbstractExtendedAction {
  private final ChatClient chatEngine;

  @Inject
  public AddAsBuddieAction(final ChatClient chatEngine, final ChatInstances chatInstances,
      final StateManager stateManager, final I18nTranslationService i18n, final CoreResources img) {
    super();
    this.chatEngine = chatEngine;
    putValue(Action.NAME, i18n.t(CoreMessages.ADD_AS_A_BUDDIE));
    putValue(Action.SMALL_ICON, img.addGreen());
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    String username = null;
    if (event.getTarget() instanceof GroupDTO) {
      username = ((GroupDTO) event.getTarget()).getShortName();
    } else if (event.getTarget() instanceof UserSimpleDTO) {
      username = ((UserSimpleDTO) event.getTarget()).getShortName();
    }
    if (username != null) {
      chatEngine.addNewBuddie(username);
      // NotifyUser.info("Added as buddie. Waiting buddie response");
      setEnabled(false);
    }
  }
}
