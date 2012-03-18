package cc.kune.trash.client.actions;

import static cc.kune.trash.shared.TrashToolConstants.TYPE_ROOT;
import cc.kune.chat.client.actions.GoParentChatBtn;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.gspace.client.actions.AbstractFoldableToolActions;
import cc.kune.gspace.client.actions.ActionGroups;
import cc.kune.gspace.client.actions.ContentViewerOptionsMenu;
import cc.kune.gspace.client.actions.RefreshContentMenuItem;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TrashClientActions extends AbstractFoldableToolActions {

  final String[] all = { TYPE_ROOT };
  final String[] containers = { TYPE_ROOT };
  final String[] containersNoRoot = {};
  final String[] contents = {};

  @Inject
  public TrashClientActions(final I18nUITranslationService i18n, final Session session,
      final StateManager stateManager, final ActionRegistryByType registry, final CoreResources res,
      final Provider<RefreshContentMenuItem> refresh, final Provider<GoParentChatBtn> folderGoUp,
      final Provider<ContentViewerOptionsMenu> optionsMenuContent) {
    super(session, stateManager, i18n, registry);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, optionsMenuContent, all);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, refresh, all);
    // actionsRegistry.addAction(ActionGroups.TOOLBAR, folderGoUp,
    // DocsToolConstants.TYPE_DOCUMENT);
    // actionsRegistry.addAction(ActionGroups.ITEM_MENU, action,
    // containersNoRoot);
  }

  @Override
  protected void createPostSessionInitActions() {
  }
}