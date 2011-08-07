package cc.kune.core.server;

import java.net.URL;
import java.util.Date;
import java.util.List;

import cc.kune.core.client.errors.ContainerNotPermittedException;
import cc.kune.core.client.errors.ContentNotPermittedException;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.manager.ToolConfigurationManager;
import cc.kune.core.server.tool.ServerTool;
import cc.kune.core.server.tool.ServerToolRegistry;
import cc.kune.core.server.tool.ServerToolTarget;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.ToolConfiguration;
import cc.kune.domain.User;

import com.google.inject.Inject;

public abstract class AbstractServerTool implements ServerTool {

  protected final ToolConfigurationManager configurationManager;
  protected final ContainerManager containerManager;
  protected final ContentManager contentManager;
  protected final I18nTranslationService i18n;
  private final String name;
  private final String rootName;
  private final ServerToolTarget target;
  private final String typeRoot;
  private final List<String> validContainerParents;
  private final List<String> validContainers;
  private final List<String> validContentParents;
  private final List<String> validContents;

  public AbstractServerTool(final String name, final String rootName, final String typeRoot,
      final List<String> validContents, final List<String> validContentParents,
      final List<String> validContainers, final List<String> validContainerParents,
      final ContentManager contentManager, final ContainerManager containerManager,
      final ToolConfigurationManager configurationManager, final I18nTranslationService i18n,
      final ServerToolTarget target) {
    this.name = name;
    this.rootName = rootName;
    this.typeRoot = typeRoot;
    this.validContents = validContents;
    this.validContainers = validContainers;
    this.validContentParents = validContentParents;
    this.validContainerParents = validContainerParents;
    this.contentManager = contentManager;
    this.containerManager = containerManager;
    this.configurationManager = configurationManager;
    this.i18n = i18n;
    this.target = target;
  }

  @Override
  public void checkTypesBeforeContainerCreation(final String parentTypeId, final String typeId) {
    if (validContainers.contains(typeId) && validContainerParents.contains(parentTypeId)) {
      // ok
    } else {
      throw new ContainerNotPermittedException();
    }
  }

  @Override
  public void checkTypesBeforeContentCreation(final String parentTypeId, final String typeId) {
    if (validContents.contains(typeId) && validContentParents.contains(parentTypeId)) {
      // ok
    } else {
      throw new ContentNotPermittedException();
    }
  }

  protected Content createInitialContent(final User user, final Group group, final Container rootFolder,
      final String title, final String body, final String contentType) {
    final Content content = contentManager.createContent(title, body, user, rootFolder, contentType);
    setContentValues(content, contentType, user);
    return content;
  }

  protected Content createInitialContent(final User user, final Group group, final Container rootFolder,
      final String title, final String body, final String contentType, final URL gadgetUrl) {
    final Content content = contentManager.createContent(title, body, user, rootFolder, contentType,
        gadgetUrl);
    setContentValues(content, contentType, user);
    return content;
  }

  protected Container createRoot(final Group group) {
    final ToolConfiguration config = new ToolConfiguration();
    final Container rootFolder = containerManager.createRootFolder(group, name, rootName, typeRoot);
    setContainerAcl(rootFolder);
    config.setRoot(rootFolder);
    group.setToolConfig(name, config);
    configurationManager.persist(config);
    return rootFolder;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getRootName() {
    return rootName;
  }

  @Override
  public ServerToolTarget getTarget() {
    return target;
  }

  @Override
  public void onCreateContainer(final Container container, final Container parent) {
  }

  @Override
  public void onCreateContent(final Content content, final Container parent) {
  }

  @Override
  @Inject
  public void register(final ServerToolRegistry registry) {
    registry.register(this);
  }

  protected void setContainerAcl(final Container container) {
  }

  private void setContentValues(final Content content, final String contentType, final User author) {
    content.addAuthor(author);
    content.setLanguage(author.getLanguage());
    content.setTypeId(contentType);
    content.setStatus(ContentStatus.publishedOnline);
    content.setPublishedOn(new Date());
  }
}
