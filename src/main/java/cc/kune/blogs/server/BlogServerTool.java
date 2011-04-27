/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.blogs.server;

import static cc.kune.blogs.shared.BlogsConstants.NAME;
import static cc.kune.blogs.shared.BlogsConstants.TYPE_BLOG;
import static cc.kune.blogs.shared.BlogsConstants.TYPE_POST;
import static cc.kune.blogs.shared.BlogsConstants.TYPE_ROOT;
import static cc.kune.blogs.shared.BlogsConstants.TYPE_UPLOADEDFILE;
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
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.ToolConfiguration;
import cc.kune.domain.User;

import com.google.inject.Inject;

public class BlogServerTool implements ServerTool {

    public static final String ROOT_NAME = "blogs";

    private final ToolConfigurationManager configurationManager;
    private final ContainerManager containerManager;
    private final ContentManager contentManager;
    private final I18nTranslationService i18n;

    @Inject
    public BlogServerTool(final ContentManager contentManager, final ContainerManager containerManager,
            final ToolConfigurationManager configurationManager, final I18nTranslationService translationService) {
        this.contentManager = contentManager;
        this.containerManager = containerManager;
        this.configurationManager = configurationManager;
        this.i18n = translationService;
    }

    void checkContainerTypeId(final String parentTypeId, final String typeId) {
        if (typeId.equals(TYPE_BLOG)) {
            // ok valid container
            if ((typeId.equals(TYPE_BLOG) && parentTypeId.equals(TYPE_ROOT))) {
                // ok
            } else {
                throw new ContainerNotPermittedException();
            }
        } else {
            throw new ContainerNotPermittedException();
        }
    }

    void checkContentTypeId(final String parentTypeId, final String typeId) {
        if (typeId.equals(TYPE_UPLOADEDFILE) || typeId.equals(TYPE_POST)) {
            // ok valid content
            if ((typeId.equals(TYPE_UPLOADEDFILE) && parentTypeId.equals(TYPE_BLOG))
                    || (typeId.equals(TYPE_POST) && parentTypeId.equals(TYPE_BLOG))) {
                // ok
            } else {
                throw new ContentNotPermittedException();
            }

        } else {
            throw new ContentNotPermittedException();
        }
    }

    @Override
    public void checkTypesBeforeContainerCreation(final String parentTypeId, final String typeId) {
        checkContainerTypeId(parentTypeId, typeId);
    }

    @Override
    public void checkTypesBeforeContentCreation(final String parentTypeId, final String typeId) {
        checkContentTypeId(parentTypeId, typeId);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getRootName() {
        return ROOT_NAME;
    }

    @Override
    public ServerToolTarget getTarget() {
        return ServerToolTarget.forBoth;
    }

    @Override
    public Group initGroup(final User user, final Group group, final Object... otherVars) {
        final ToolConfiguration config = new ToolConfiguration();
        final Container rootFolder = containerManager.createRootFolder(group, NAME, ROOT_NAME, TYPE_ROOT);
        config.setRoot(rootFolder);
        group.setToolConfig(NAME, config);
        configurationManager.persist(config);

        final I18nLanguage language = user.getLanguage();
        final Container blog = containerManager.createFolder(group, rootFolder, i18n.t("Blog sample"), language,
                TYPE_BLOG);

        final Content content = contentManager.createContent(i18n.t("A post sample"),
                i18n.t("This is only a post sample. You can edit it, rename the post and this blog"), user, blog,
                TYPE_POST);
        content.addAuthor(user);
        content.setLanguage(language);
        content.setTypeId(TYPE_POST);
        content.setStatus(ContentStatus.publishedOnline);

        contentManager.save(user, content);
        return group;
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
}
