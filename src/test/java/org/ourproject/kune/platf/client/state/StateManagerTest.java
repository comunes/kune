package org.ourproject.kune.platf.client.state;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourproject.kune.platf.client.app.HistoryWrapper;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.sitebar.SiteToken;

import com.calclab.suco.client.signal.Slot;
import com.calclab.suco.client.signal.Slot2;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class StateManagerTest {

    private static final String HASH = "someUserHash";
    private StateManagerDefault stateManager;
    private HistoryWrapper history;
    private ContentProvider contentProvider;
    private Session session;

    @SuppressWarnings("unchecked")
    @Test
    public void changeStateWithDifferentGroupsMustFireSlot() {
	final Slot2<String, String> groupSlot = Mockito.mock(Slot2.class);
	stateManager.onGroupChanged(groupSlot);
	stateManager.onHistoryChanged("group1.tool1");
	stateManager.onHistoryChanged("group2.tool1");
	// TODO, think how to test this
	// Mockito.verify(groupSlot).onEvent("group1", "group2");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void changeStateWithDifferentToolsMustFireSlot() {
	final Slot2<String, String> toolSlot = Mockito.mock(Slot2.class);
	stateManager.onToolChanged(toolSlot);
	stateManager.gotoToken("group1.tool1");
	stateManager.gotoToken("group1.tool2");
	// TODO, think how to test this
	// Mockito.verify(toolSlot).onEvent("tool1", "tool2");
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
    public void siteTokenFirstLoadDefContentAndFireSlot() {
	final Slot slot = Mockito.mock(Slot.class);
	final String token = SiteToken.signin.toString();
	stateManager.addSiteToken(token, slot);
	stateManager.onHistoryChanged(token);
	Mockito.verify(slot, Mockito.times(1)).onEvent(Mockito.anyObject());
	Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
		(StateToken) Mockito.anyObject(), (AsyncCallback<StateDTO>) Mockito.anyObject());
    }
}
