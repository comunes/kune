
package org.ourproject.kune.docs.server;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.content.ContainerManager;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.ToolConfiguration;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.ToolConfigurationManager;
import org.ourproject.kune.platf.server.tool.ServerTool;
import org.ourproject.kune.platf.server.tool.ToolRegistry;

import com.google.inject.Inject;

public class DocumentServerTool implements ServerTool {
    public static final String TYPE_ROOT = "docs.root";
    public static final String TYPE_FOLDER = "docs.folder";
    public static final String TYPE_DOCUMENT = "docs.doc";
    public static final String NAME = "docs";
    public static final String ROOT_NAME = "documents";

    private final ContentManager contentManager;
    private final ToolConfigurationManager configurationManager;
    private final ContainerManager containerManager;
    private final I18nTranslationService i18n;

    @Inject
    public DocumentServerTool(final ContentManager contentManager, final ContainerManager containerManager,
            final ToolConfigurationManager configurationManager, final I18nTranslationService translationService) {
        this.contentManager = contentManager;
        this.containerManager = containerManager;
        this.configurationManager = configurationManager;
        this.i18n = translationService;
    }

    @Inject
    public void register(final ToolRegistry registry) {
        registry.register(this);
    }

    public String getName() {
        return NAME;
    }

    public void onCreateContainer(final Container container, final Container parent) {
        container.setTypeId(TYPE_FOLDER);
    }

    public void onCreateContent(final Content content, final Container parent) {
        content.setTypeId(TYPE_DOCUMENT);
    }

    public Group initGroup(final User user, final Group group) {
        ToolConfiguration config = new ToolConfiguration();
        Container container = containerManager.createRootFolder(group, NAME, ROOT_NAME, TYPE_ROOT);
        config.setRoot(container);
        group.setToolConfig(NAME, config);
        configurationManager.persist(config);
        Content descriptor = contentManager.createContent(i18n.t("Welcome"), i18n.t("This is an example of document. "
                + "You can modify it if you are a group's collaborator and/or delete it if you are admin."), user,
                container);
        descriptor.addAuthor(user);
        descriptor.setLanguage(user.getLanguage());
        descriptor.setTypeId(TYPE_DOCUMENT);
        group.setDefaultContent(descriptor);
        return group;
    }

}
