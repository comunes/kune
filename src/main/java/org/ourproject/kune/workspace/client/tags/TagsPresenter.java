/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.workspace.client.tags;

import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.TagResultDTO;
import org.ourproject.kune.platf.client.search.SearchSiteView;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.workspace.TagsComponent;

public class TagsPresenter implements TagsComponent {

    private TagsView view;
    private final Session session;

    public TagsPresenter(final Session session) {
        this.session = session;
    }

    public void setState(final StateDTO state) {
        view.setTags(state.getGroupTags());
    }

    public void setGroupTags(final List<TagResultDTO> groupTags) {
        view.setTags(groupTags);
    }

    public void init(final TagsView view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void doSearchTag(final String name) {
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.SHOW_SEARCHER,
                "group:" + session.getCurrentState().getGroup().getShortName() + " tag:" + name,
                new Integer(SearchSiteView.CONTENT_SEARCH));
    }
}
