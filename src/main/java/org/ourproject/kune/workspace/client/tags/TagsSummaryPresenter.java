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

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.TagCloudResultDTO;
import org.ourproject.kune.platf.client.dto.TagCountDTO;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.search.SiteSearcher;
import org.ourproject.kune.workspace.client.search.SiteSearcherType;
import org.ourproject.kune.workspace.client.themes.WsTheme;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener2;
import com.calclab.suco.client.ioc.Provider;

public class TagsSummaryPresenter implements TagsSummary {

    private static final int MINSIZE = 11;
    private static final int MAXSIZE = 26;

    private TagsSummaryView view;
    private final Provider<SiteSearcher> searcherProvider;
    private final Session session;

    public TagsSummaryPresenter(final Session session, final Provider<SiteSearcher> searcherProvider,
            final StateManager stateManager, final WsThemePresenter wsThemePresenter) {
        this.session = session;
        this.searcherProvider = searcherProvider;
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                if (state instanceof StateContainerDTO) {
                    setState((StateContainerDTO) state);
                } else {
                    view.setVisible(false);
                }
            }
        });
        wsThemePresenter.onThemeChanged(new Listener2<WsTheme, WsTheme>() {
            public void onEvent(final WsTheme oldTheme, final WsTheme newTheme) {
                view.setTheme(oldTheme, newTheme);
            }
        });
    }

    public void doSearchTag(final String name) {
        searcherProvider.get().doSearchOfType(
                "group:" + session.getCurrentState().getGroup().getShortName() + " tag:" + name,
                SiteSearcherType.content);
    }

    public View getView() {
        return view;
    }

    public void init(final TagsSummaryView view) {
        this.view = view;
    }

    public void setGroupTags(final TagCloudResultDTO tagCloud) {
        setCloud(tagCloud);
        view.expand();
    }

    void setState(final StateContainerDTO state) {
        if (state.getTagCloudResult() != null && state.getTagCloudResult().getTagCountList().size() > 0) {
            Log.debug(state.getTagCloudResult().toString());
            setCloud(state.getTagCloudResult());
        } else {
            view.setVisible(false);
        }
    }

    private void setCloud(TagCloudResultDTO tagCloudResult) {
        // Inspired in snippet http://www.bytemycode.com/snippets/snippet/415/
        view.clear();
        int max = tagCloudResult.getMaxValue();
        int min = tagCloudResult.getMinValue();
        int diff = max - min;
        int step = (MAXSIZE - MINSIZE) / (diff == 0 ? 1 : diff);
        for (TagCountDTO tagCount : tagCloudResult.getTagCountList()) {
            int size = Math.round((MINSIZE + (tagCount.getCount() - min) * step));
            view.addTag(tagCount.getName(), tagCount.getCount(), "kune-ft" + size + "px");
        }
        view.setVisible(true);
    }
}
