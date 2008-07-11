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

package org.ourproject.kune.workspace.client.tags;

import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.TagResultDTO;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.search.SiteSearcher;
import org.ourproject.kune.workspace.client.search.SiteSearcherType;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;
import org.ourproject.kune.workspace.client.workspace.Tags;

import com.calclab.suco.client.container.Provider;

public class TagsPresenter implements Tags {

    private TagsView view;
    private final Provider<SiteSearcher> searcherProvider;
    private final Provider<Session> sessionProvider;

    public TagsPresenter(final Provider<Session> sessionProvider, final Provider<SiteSearcher> searcherProvider) {
	this.sessionProvider = sessionProvider;
	this.searcherProvider = searcherProvider;
    }

    public void doSearchTag(final String name) {
	searcherProvider.get().doSearchOfType(
		"group:" + sessionProvider.get().getCurrentState().getGroup().getShortName() + " tag:" + name,
		SiteSearcherType.content);
    }

    public View getView() {
	return view;
    }

    public void init(final TagsView view) {
	this.view = view;
    }

    public void setGroupTags(final List<TagResultDTO> groupTags) {
	view.setTags(groupTags);
    }

    public void setState(final StateDTO state) {
	view.setTags(state.getGroupTags());
    }

    public void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
	view.setTheme(oldTheme, newTheme);
    }
}
