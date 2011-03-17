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
package cc.kune.core.client.tags;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.core.client.state.EventBusTester;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.tags.TagsSummaryPresenter.TagsSummaryProxy;
import cc.kune.core.client.tags.TagsSummaryPresenter.TagsSummaryView;
import cc.kune.core.shared.domain.TagCloudResult;
import cc.kune.core.shared.domain.TagCount;
import cc.kune.core.shared.dto.StateContainerDTO;

public class TagsSummaryPresenterTest {

    private TagsSummaryPresenter tagsSummaryPresenter;
    private TagsSummaryView view;

    @SuppressWarnings("unchecked")
    @Before
    public void before() {
        final Session session = Mockito.mock(Session.class);
        final StateManager stateManager = Mockito.mock(StateManager.class);
        // final SiteSearcher searcher = Mockito.mock(SiteSearcher.class);
        TagsSummaryProxy proxy = Mockito.mock(TagsSummaryProxy.class);
        // Mockito.when(searcherProvider.get()).thenReturn(searcher);
        view = Mockito.mock(TagsSummaryView.class);
        EventBusTester eventBus = new EventBusTester();
        tagsSummaryPresenter = new TagsSummaryPresenter(eventBus, view, proxy, session, stateManager);
    }

    @Test
    public void noTagsViewNotVisible() {
        final StateContainerDTO state = new StateContainerDTO();
        tagsSummaryPresenter.setState(state);
        Mockito.verify(view).setVisible(false);
    }

    @Test
    public void withTagsViewFalse() {
        final StateContainerDTO state = new StateContainerDTO();
        final ArrayList<TagCount> list = new ArrayList<TagCount>();
        state.setTagCloudResult(new TagCloudResult(list, 0, 0));
        tagsSummaryPresenter.setState(state);
        Mockito.verify(view).setVisible(false);
    }

    @Test
    public void withTagsViewVisible() {
        final StateContainerDTO state = new StateContainerDTO();
        final ArrayList<TagCount> list = new ArrayList<TagCount>();
        list.add(new TagCount("abc", 1L));
        state.setTagCloudResult(new TagCloudResult(list, 0, 0));
        tagsSummaryPresenter.setState(state);
        Mockito.verify(view).setVisible(true);
    }
}
