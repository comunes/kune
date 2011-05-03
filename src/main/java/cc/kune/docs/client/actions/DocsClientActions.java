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
 \*/
package cc.kune.docs.client.actions;

import static cc.kune.docs.shared.DocsConstants.TYPE_DOCUMENT;
import static cc.kune.docs.shared.DocsConstants.TYPE_FOLDER;
import static cc.kune.docs.shared.DocsConstants.TYPE_ROOT;
import static cc.kune.docs.shared.DocsConstants.TYPE_UPLOADEDFILE;
import cc.kune.blogs.client.actions.ContentViewerOptionsMenu;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.gspace.client.actions.AbstractFoldableToolActions;
import cc.kune.gspace.client.actions.ActionGroups;
import cc.kune.gspace.client.actions.ParticipateInContentBtn;
import cc.kune.gspace.client.actions.RefreshContentMenuItem;
import cc.kune.gspace.client.actions.SetAsHomePageMenuItem;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DocsClientActions extends AbstractFoldableToolActions {

    final String[] all = { TYPE_ROOT, TYPE_FOLDER, TYPE_DOCUMENT, TYPE_UPLOADEDFILE };
    final String[] containers = { TYPE_ROOT, TYPE_FOLDER };
    final String[] containersNoRoot = { TYPE_FOLDER };
    final String[] contents = { TYPE_DOCUMENT, TYPE_UPLOADEDFILE };
    final String[] contentsModerated = { TYPE_DOCUMENT, TYPE_UPLOADEDFILE };

    @Inject
    public DocsClientActions(final I18nUITranslationService i18n, final Session session,
            final StateManager stateManager, final ActionRegistryByType registry, final CoreResources res,
            final Provider<GoParentFolderBtn> folderGoUp, final Provider<NewDocBtn> newDocBtn,
            final Provider<NewFolderBtn> newFolderBtn, final Provider<OpenDocMenuItem> openContentMenuItem,
            final Provider<DelDocMenuItem> delContentMenuItem,
            final Provider<ContentViewerOptionsMenu> optionsMenuContent,
            final Provider<ParticipateInContentBtn> participateBtn,
            final Provider<DelFolderMenuItem> delFolderMenuItem, final Provider<RefreshContentMenuItem> refresh,
            final Provider<SetAsHomePageMenuItem> setAsHomePage) {
        super(session, stateManager, i18n, registry);
        actionsRegistry.addAction(ActionGroups.VIEW, optionsMenuContent, all);
        actionsRegistry.addAction(ActionGroups.VIEW, refresh, all);
        actionsRegistry.addAction(ActionGroups.VIEW, folderGoUp, contents);
        actionsRegistry.addAction(ActionGroups.VIEW, folderGoUp, containersNoRoot);
        actionsRegistry.addAction(ActionGroups.VIEW, newDocBtn, containers);
        actionsRegistry.addAction(ActionGroups.VIEW, participateBtn,
         contents);
        actionsRegistry.addAction(ActionGroups.VIEW, newFolderBtn, containers);
        actionsRegistry.addAction(ActionGroups.MENUITEM, openContentMenuItem, contents);
        actionsRegistry.addAction(ActionGroups.MENUITEM, openContentMenuItem, containersNoRoot);
        actionsRegistry.addAction(ActionGroups.MENUITEM, delContentMenuItem, contents);
        actionsRegistry.addAction(ActionGroups.MENUITEM, delFolderMenuItem, containersNoRoot);
        actionsRegistry.addAction(ActionGroups.MENUITEM, setAsHomePage, contents);
    }

    @Override
    protected void createPostSessionInitActions() {
    }
}
