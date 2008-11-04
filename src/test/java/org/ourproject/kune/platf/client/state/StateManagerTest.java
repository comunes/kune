package org.ourproject.kune.platf.client.state;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourproject.kune.platf.client.app.HistoryWrapper;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.site.SiteToken;
import org.ourproject.kune.workspace.client.sitebar.siteprogress.SiteProgress;

import com.calclab.suco.testing.listener.MockListener;
import com.calclab.suco.testing.listener.MockListener0;
import com.calclab.suco.testing.listener.MockListener2;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class StateManagerTest {

    private static final String HASH = "someUserHash";
    private StateManagerDefault stateManager;
    private HistoryWrapper history;
    private ContentProvider contentProvider;
    private Session session;
    private MockListener2<String, String> toolChangeListener;
    private MockListener2<String, String> groupChangeListener;
    private MockListener<StateAbstractDTO> stateChangeListener;
    private StateAbstractDTO state;
    private BeforeStateChangeListener beforeChangeListener1;
    private BeforeStateChangeListener beforeChangeListener2;

    @Before
    public void before() {
        contentProvider = Mockito.mock(ContentProvider.class);
        session = Mockito.mock(Session.class);
        history = Mockito.mock(HistoryWrapper.class);
        stateManager = new StateManagerDefault(contentProvider, session, history);
        Mockito.stub(session.getUserHash()).toReturn(HASH);
        state = Mockito.mock(StateAbstractDTO.class);
        stateChangeListener = new MockListener<StateAbstractDTO>();
        groupChangeListener = new MockListener2<String, String>();
        toolChangeListener = new MockListener2<String, String>();
        beforeChangeListener1 = Mockito.mock(BeforeStateChangeListener.class);
        beforeChangeListener2 = Mockito.mock(BeforeStateChangeListener.class);
        stateManager.onStateChanged(stateChangeListener);
        stateManager.onGroupChanged(groupChangeListener);
        stateManager.onToolChanged(toolChangeListener);
        new Site(Mockito.mock(I18nUITranslationService.class), Mockito.mock(SiteProgress.class), null);
    }

    @Test
    public void changeGroupWithNoTool() {
        changeState("group1", "group2");
        assertTrue(groupChangeListener.isCalledWithEquals("", "group1", "group1", "group2"));
        assertTrue(toolChangeListener.isCalledWithEquals("", ""));
        assertTrue(groupChangeListener.isCalled(2));
        assertTrue(stateChangeListener.isCalled(2));
    }

    @Test
    public void changeStateWithDifferentAndGroupsToolsMustFireListener() {
        changeState("group2.tool1", "group1.tool2");
        assertTrue(stateChangeListener.isCalled(2));
        assertTrue(groupChangeListener.isCalledWithEquals("", "group2", "group2", "group1"));
        assertTrue(toolChangeListener.isCalledWithEquals("", "tool1", "tool1", "tool2"));
    }

    @Test
    public void changeStateWithDifferentGroupsMustFireListener() {
        changeState("group1.tool1", "group2.tool1");
        assertTrue(stateChangeListener.isCalled(2));
        assertTrue(groupChangeListener.isCalledWithEquals("", "group1", "group1", "group2"));
        assertTrue(toolChangeListener.isCalledWithEquals("", "tool1"));
    }

    @Test
    public void changeStateWithDifferentToolsMustFireListener() {
        changeState("group1.tool1", "group1.tool2");
        assertTrue(stateChangeListener.isCalled(2));
        assertTrue(toolChangeListener.isCalledWithEquals("", "tool1", "tool1", "tool2"));
        assertTrue(groupChangeListener.isCalledWithEquals("", "group1"));
    }

    @Test
    public void changeToNoTool() {
        changeState("group1.tool1", "group1");
        assertTrue(groupChangeListener.isCalledWithEquals("", "group1"));
        assertTrue(toolChangeListener.isCalledWithEquals("", "tool1", "tool1", ""));
        assertTrue(groupChangeListener.isCalledOnce());
        assertTrue(stateChangeListener.isCalled(2));
    }

    @Test
    public void changeToSameToken() {
        changeState("group1.tool1", "group1.tool1");
        assertTrue(groupChangeListener.isCalledWithEquals("", "group1"));
        assertTrue(toolChangeListener.isCalledWithEquals("", "tool1"));
        assertTrue(groupChangeListener.isCalledOnce());
        assertTrue(stateChangeListener.isCalled(2));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getDefGroup() {
        stateManager.onHistoryChanged("site.docs");
        Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
                (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void normalStartLoggedUser() {
        // When a user enter reload state, also if the application is starting
        // (and the user was logged)
        Mockito.stub(history.getToken()).toReturn("");
        stateManager.reload();
        Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
                (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void oneBeforeStateChangeListenerAddAndRemove() {
        String newToken = confBeforeStateChangeListeners(false, false);
        stateManager.onHistoryChanged(newToken);
        removeBeforeStateChangeListener();
        stateManager.onHistoryChanged(newToken);
        Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
                (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
    }

    @Test
    public void oneBeforeStateChangeListenerFalseAndResume() {
        String token = confBeforeStateChangeListeners(false, true);
        stateManager.onHistoryChanged(token);
        Mockito.verify(history, Mockito.never()).newItem(token);
        removeBeforeStateChangeListener();
        stateManager.resumeTokenChange();
        Mockito.verify(history, Mockito.times(1)).newItem(token);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void oneBeforeStateChangeListenerReturnFalse() {
        stateManager.onHistoryChanged(confBeforeStateChangeListeners(true, false));
        Mockito.verify(contentProvider, Mockito.never()).getContent(Mockito.anyString(),
                (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void oneBeforeStateChangeListenerReturnFalseWithTwo() {
        stateManager.onHistoryChanged(confBeforeStateChangeListeners(false, false));
        Mockito.verify(contentProvider, Mockito.never()).getContent(Mockito.anyString(),
                (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void oneBeforeStateChangeListenerReturnTrue() {
        stateManager.onHistoryChanged(confBeforeStateChangeListeners(true, true));
        Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
                (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void siteTokenFirstLoadDefContentAndFireListener() {
        final MockListener0 listener = new MockListener0();
        final String token = SiteToken.signin.toString();
        stateManager.addSiteToken(token, listener);
        stateManager.onHistoryChanged(token);
        assertTrue(listener.isCalledOnce());
        Mockito.verify(contentProvider, Mockito.times(1)).getContent(Mockito.anyString(),
                (StateToken) Mockito.anyObject(), (AsyncCallback<StateAbstractDTO>) Mockito.anyObject());
    }

    @Test
    public void siteTokenTest() {
        MockListener0 siteTokenListener = new MockListener0();
        stateManager.addSiteToken("signin", siteTokenListener);
        stateManager.onHistoryChanged("signIn");
        siteTokenListener.isCalledOnce();
    }

    private void changeState(String... tokens) {
        for (String token : tokens) {
            Mockito.stub(state.getStateToken()).toReturn(new StateToken(token));
            stateManager.setState(state);
        }
    }

    private String confBeforeStateChangeListeners(boolean value, boolean value2) {
        stateManager.addBeforeStateChangeListener(beforeChangeListener1);
        stateManager.addBeforeStateChangeListener(beforeChangeListener2);
        String newToken = "something";
        Mockito.stub(beforeChangeListener1.beforeChange(newToken)).toReturn(value);
        Mockito.stub(beforeChangeListener2.beforeChange(newToken)).toReturn(value2);
        return newToken;
    }

    private void removeBeforeStateChangeListener() {
        stateManager.removeBeforeStateChangeListener(beforeChangeListener1);
        stateManager.removeBeforeStateChangeListener(beforeChangeListener2);
    }
}
