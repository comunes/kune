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
package org.ourproject.kune.docs.server;

import java.util.Date;

import org.ourproject.kune.platf.client.errors.ContainerNotPermittedException;
import org.ourproject.kune.platf.client.errors.ContentNotPermittedException;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.server.content.ContainerManager;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.ContentStatus;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.ToolConfiguration;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.ToolConfigurationManager;
import org.ourproject.kune.platf.server.tool.ServerTool;
import org.ourproject.kune.platf.server.tool.ServerToolRegistry;
import org.ourproject.kune.platf.server.tool.ServerToolTarget;

import com.google.inject.Inject;

public class DocumentServerTool implements ServerTool {
    public static final String NAME = "docs";
    public static final String TYPE_ROOT = NAME + "." + "root";
    public static final String TYPE_FOLDER = NAME + "." + "folder";
    public static final String TYPE_DOCUMENT = NAME + "." + "doc";
    public static final String TYPE_UPLOADEDFILE = NAME + "." + ServerTool.UPLOADEDFILE_SUFFIX;

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

    public void checkTypesBeforeContainerCreation(String parentTypeId, String typeId) {
        checkContainerTypeId(parentTypeId, typeId);
    }

    public void checkTypesBeforeContentCreation(String parentTypeId, String typeId) {
        checkContentTypeId(parentTypeId, typeId);
    }

    public String getName() {
        return NAME;
    }

    public String getRootName() {
        return ROOT_NAME;
    }

    public ServerToolTarget getTarget() {
        return ServerToolTarget.forBoth;
    }

    public Group initGroup(final User user, final Group group) {
        final ToolConfiguration config = new ToolConfiguration();
        final Container rootFolder = containerManager.createRootFolder(group, NAME, ROOT_NAME, TYPE_ROOT);
        config.setRoot(rootFolder);
        group.setToolConfig(NAME, config);
        configurationManager.persist(config);
        final String longName = group.getLongName();
        final Content content = contentManager.createContent(i18n.t("About [%s]", longName), "", user, rootFolder,
                DocumentServerTool.TYPE_DOCUMENT);
        content.addAuthor(user);
        content.setLanguage(user.getLanguage());
        content.setTypeId(TYPE_DOCUMENT);
        content.setStatus(ContentStatus.publishedOnline);
        content.setPublishedOn(new Date());
        group.setDefaultContent(content);
        return group;
    }

    public void onCreateContainer(final Container container, final Container parent) {
    }

    public void onCreateContent(final Content content, final Container parent) {
    }

    @Inject
    public void register(final ServerToolRegistry registry) {
        registry.register(this);
    }

    void checkContainerTypeId(final String parentTypeId, final String typeId) {
        if (typeId.equals(TYPE_FOLDER)) {
            // ok valid container
            if ((typeId.equals(TYPE_FOLDER) && (parentTypeId.equals(TYPE_ROOT) || parentTypeId.equals(TYPE_FOLDER)))) {
                // ok
            } else {
                throw new ContainerNotPermittedException();
            }
        } else {
            throw new ContainerNotPermittedException();
        }
    }

    void checkContentTypeId(final String parentTypeId, final String typeId) {
        if (typeId.equals(TYPE_DOCUMENT) || typeId.equals(TYPE_UPLOADEDFILE)) {
            // ok valid content
            if ((typeId.equals(TYPE_DOCUMENT) && (parentTypeId.equals(TYPE_ROOT) || parentTypeId.equals(TYPE_FOLDER)))
                    || (typeId.equals(TYPE_UPLOADEDFILE) && (parentTypeId.equals(TYPE_ROOT) || parentTypeId.equals(TYPE_FOLDER)))) {
                // ok
            } else {
                throw new ContentNotPermittedException();
            }

        } else {
            throw new ContentNotPermittedException();
        }
    }
}
