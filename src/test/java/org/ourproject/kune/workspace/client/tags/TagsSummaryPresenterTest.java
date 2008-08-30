package org.ourproject.kune.workspace.client.tags;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.search.SiteSearcher;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsThemePresenter;

import com.calclab.suco.client.provider.Provider;

public class TagsSummaryPresenterTest {

    private TagsSummaryPresenter tagsSummaryPresenter;
    private TagsSummaryView view;

    @SuppressWarnings("unchecked")
    @Before
    public void before() {
	final Session session = Mockito.mock(Session.class);
	final StateManager stateManager = Mockito.mock(StateManager.class);
	final WsThemePresenter theme = Mockito.mock(WsThemePresenter.class);
	final SiteSearcher searcher = Mockito.mock(SiteSearcher.class);
	final Provider searcherProvider = Mockito.mock(Provider.class);
	Mockito.stub(searcherProvider.get()).toReturn(searcher);
	view = Mockito.mock(TagsSummaryView.class);
	tagsSummaryPresenter = new TagsSummaryPresenter(session, searcherProvider, stateManager, theme);
	tagsSummaryPresenter.init(view);
    }

    @Test
    public void noTagsViewNotVisible() {
	final StateDTO state = new StateDTO();
	tagsSummaryPresenter.setState(state);
	Mockito.verify(view).setVisible(false);
    }

    @Test
    public void withTagsViewVisible() {
	final StateDTO state = new StateDTO();
	state.setTags("test test1");
	tagsSummaryPresenter.setState(state);
	Mockito.verify(view).setVisible(true);
    }
}
