package org.ourproject.kune.platf.client.state;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.app.HistoryWrapper;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.ui.newtmp.WorkspaceManager;

public class StateManagerTest {

    private StateManager stateManager;
    private HistoryWrapper history;

    @Test
    public void fixmeToken() {
	stateManager.onHistoryChanged(Site.FIXME_TOKEN);
    }

    @Test
    public void getDefGroup() {
	stateManager.onHistoryChanged("site.docs");
    }

    @Before
    public void init() {
	final ContentProvider contentProvider = EasyMock.createStrictMock(ContentProvider.class);
	final Application application = EasyMock.createStrictMock(Application.class);
	final Session session = EasyMock.createStrictMock(Session.class);
	history = EasyMock.createStrictMock(HistoryWrapper.class);
	final WorkspaceManager ws = EasyMock.createStrictMock(WorkspaceManager.class);
	stateManager = new StateManagerDefault(contentProvider, application, session, history, ws);
    }

    @Test
    public void normalStartLoggedUser() {
	// When a user enter reload state, also if the application is starting
	// (and the user was logged)
	EasyMock.expect(history.getToken()).andReturn("");
	EasyMock.replay(history);
	stateManager.reload();
	EasyMock.verify(history);
    }

}
