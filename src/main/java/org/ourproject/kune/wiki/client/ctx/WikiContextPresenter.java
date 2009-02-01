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
package org.ourproject.kune.wiki.client.ctx;

import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.wiki.client.WikiClientTool;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.cxt.ContextPropEditor;
import org.ourproject.kune.workspace.client.cxt.FoldableContextPresenter;

import com.calclab.suco.client.ioc.Provider;

public class WikiContextPresenter extends FoldableContextPresenter implements WikiContext {
    public WikiContextPresenter(final StateManager stateManager,
            final Provider<ContextNavigator> contextNavigatorProvider,
            final Provider<ContextPropEditor> contextPropEditorProvider) {
        super(WikiClientTool.NAME, stateManager, contextNavigatorProvider, contextPropEditorProvider);
    }
}
