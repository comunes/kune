package org.ourproject.kune.platf.client.state;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourproject.kune.platf.client.app.HistoryWrapper;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.site.SiteToken;

import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener2;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class StateManagerTest {

    private static final String HASH = "someUserHash";
    private StateManagerDefault stateManager;
    private HistoryWrapper history;
    private ContentProvider contentProvider;
    private Session session;

    @SuppressWarnings("unchecked")
    @Test
    public void changeStateWithDifferentGroupsMustFireListener() {
	final Listener2<GroupDTO, GroupDTO> groupListener = Mockito.mock(Listener2.class);
	stateManager.onGroupChanged(groupListener);
	stateManager.onHistoryChanged("group1.tool1");
	stateManager.onHistoryChanged("group2.tool1");
	// TODO, think how to test this
	// Mockito.verify(groupListener).onEvent("group1", "group2");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void changeStateWithDifferentToolsMustFireListener() {
	final Listener2<String, String> toolListener = Mockito.mock(Listener2.class);
	stateManager.onToolChanged(toolListener);
	stateManager.gotoToken("group1.tool1");
	stateManager.gotoToken("group1.tool2");
	// TODO, think how to test this
	// Mockito.verify(toolListener).onEvent("tool1", "tool2");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getDefGroup() {
	stateManager.onHistoryChanged("site.docs");
	Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
		(StateToken) Mockito.anyObject(), (AsyncCallback<StateDTO>) Mockito.anyObject());
    }

    @SuppressWarnings("unchecked")
    @Before
    public void init() {
	contentProvider = Mockito.mock(ContentProvider.class);
	session = Mockito.mock(Session.class);
	history = Mockito.mock(HistoryWrapper.class);
	stateManager = new StateManagerDefault(contentProvider, session, history);
	Mockito.stub(session.getUserHash()).toReturn(HASH);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void normalStartLoggedUser() {
	// When a user enter reload state, also if the application is starting
	// (and the user was logged)
	Mockito.stub(history.getToken()).toReturn("");
	stateManager.reload();
	Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
		(StateToken) Mockito.anyObject(), (AsyncCallback<StateDTO>) Mockito.anyObject());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void siteTokenFirstLoadDefContentAndFireListener() {
	final Listener listener = Mockito.mock(Listener.class);
	final String token = SiteToken.signin.toString();
	stateManager.addSiteToken(token, listener);
	stateManager.onHistoryChanged(token);
	Mockito.verify(listener, Mockito.times(1)).onEvent(Mockito.anyObject());
	Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
		(StateToken) Mockito.anyObject(), (AsyncCallback<StateDTO>) Mockito.anyObject());
    }
}
