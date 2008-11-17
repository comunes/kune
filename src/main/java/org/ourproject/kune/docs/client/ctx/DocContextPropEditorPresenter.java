/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.docs.client.ctx;

import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.cxt.AbstractContextPropEditorPresenter;
import org.ourproject.kune.workspace.client.tags.TagsSummary;
import org.ourproject.kune.workspace.client.title.EntitySubTitle;
import org.ourproject.kune.workspace.client.title.EntityTitle;

import com.calclab.suco.client.ioc.Provider;

public class DocContextPropEditorPresenter extends AbstractContextPropEditorPresenter implements DocContextPropEditor {

    public DocContextPropEditorPresenter(final Session session, final StateManager stateManager,
            ContentCapabilitiesRegistry capabilitiesRegistry, final Provider<TagsSummary> tagsSummaryProvider,
            final Provider<ContentServiceAsync> contentServiceProvider, final EntityTitle entityTitle,
            final EntitySubTitle entitySubTitle) {
        super(session, stateManager, capabilitiesRegistry, tagsSummaryProvider, contentServiceProvider, entityTitle,
                entitySubTitle);
    }
}
