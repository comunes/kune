/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.docs.client.actions;

import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;

public abstract class AbstractFoldableContentActions {

    private static final String PUBLICATION_MENU = "Publication";

    protected final ActionRegistryByType actionsRegistry;
    protected final I18nUITranslationService i18n;
    protected final Session session;
    protected final StateManager stateManager;

    public AbstractFoldableContentActions(final Session session, final StateManager stateManager,
            final I18nUITranslationService i18n, final ActionRegistryByType actionsRegistry) {
        this.session = session;
        this.stateManager = stateManager;
        this.i18n = i18n;
        this.actionsRegistry = actionsRegistry;
        createActions();
        session.onAppStart(true, new AppStartHandler() {
            @Override
            public void onAppStart(final AppStartEvent event) {
                createPostSessionInitActions();
            }
        });
    }

    protected abstract void createActions();

    protected void createContentModeratedActions(final String parentMenuTitle, final String... contentsModerated) {
    }

    protected void createContentRenameAction(final String parentMenuTitle, final String textDescription,
            final String... registerInTypes) {
    }

    protected void createDelContainerAction(final String text, final String parentMenuTitle,
            final String... registerInTypes) {
    }

    protected void createDelContentAction(final String parentMenuTitle, final String textDescription,
            final String... registerInTypes) {
    }

    protected void createDownloadActions(final String typeUploadedfile) {

    }

    protected abstract void createPostSessionInitActions();

}
