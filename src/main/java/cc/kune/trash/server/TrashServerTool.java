package cc.kune.trash.server;

import static cc.kune.trash.shared.TrashToolConstants.NAME;
import static cc.kune.trash.shared.TrashToolConstants.ROOT_NAME;
import static cc.kune.trash.shared.TrashToolConstants.TYPE_ROOT;

import java.util.Arrays;
import java.util.Collections;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.server.AbstractServerTool;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.content.CreationService;
import cc.kune.core.server.manager.ToolConfigurationManager;
import cc.kune.core.server.tool.ServerToolTarget;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;

public class TrashServerTool extends AbstractServerTool {

  @Inject
  public TrashServerTool(final ContentManager contentManager, final ContainerManager containerManager,
      final ToolConfigurationManager configurationManager, final I18nTranslationService i18n,
      final CreationService creationService) {
    super(NAME, ROOT_NAME, TYPE_ROOT, Collections.<String> emptyList(), Arrays.asList(TYPE_ROOT),
        Collections.<String> emptyList(), Arrays.asList(TYPE_ROOT), contentManager, containerManager,
        creationService, configurationManager, i18n, ServerToolTarget.forBoth);
  }

  public Group initGroup(final Group group) {
    return initGroup(null, group);
  }

  @Override
  public Group initGroup(final User user, final Group group, final Object... vars) {
    createRoot(group);
    return group;
  }
}