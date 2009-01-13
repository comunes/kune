package org.ourproject.kune.workspace.client.entityheader;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.GroupType;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

public class EntityLogoPresenterTest {

    private StateManager stateManager;
    private Session session;
    private EntityHeaderPresenter entityLogoPresenter;
    private EntityHeaderView view;

    @Before
    public void before() {
        stateManager = Mockito.mock(StateManager.class);
        session = Mockito.mock(Session.class);
        WsThemePresenter theme = Mockito.mock(WsThemePresenter.class);
        entityLogoPresenter = new EntityHeaderPresenter(stateManager, theme, session);
        view = Mockito.mock(EntityHeaderView.class);
        entityLogoPresenter.init(view);
    }

    @Test
    public void testCommProjectNoLogo() {
        GroupDTO group = new GroupDTO("shortname", "longname", GroupType.COMMUNITY);
        group.setHasLogo(false);
        entityLogoPresenter.setGroupLogo(group);
        Mockito.verify(view, Mockito.never()).setLogoImageVisible(true);
        Mockito.verify(view, Mockito.times(1)).setLogoImageVisible(false);
        Mockito.verify(view, Mockito.never()).showDefUserLogo();
        Mockito.verify(view, Mockito.never()).setLogoImage((StateToken) Mockito.anyObject());
    }

    @Test
    public void testCommunityWithLogo() {
        testGroupWIthLogo(GroupType.COMMUNITY);
    }

    @Test
    public void testOrgWithLogo() {
        testGroupWIthLogo(GroupType.ORGANIZATION);
    }

    @Test
    public void testOrphWithLogo() {
        testGroupWIthLogo(GroupType.ORPHANED_PROJECT);
    }

    @Test
    public void testPersonalGroupWithLogo() {
        testGroupWIthLogo(GroupType.PERSONAL);
    }

    @Test
    public void testPersonalProjectNoLogo() {
        GroupDTO group = new GroupDTO("shortname", "longname", GroupType.PERSONAL);
        group.setHasLogo(false);
        entityLogoPresenter.setGroupLogo(group);
        Mockito.verify(view, Mockito.times(1)).setLogoImageVisible(true);
        Mockito.verify(view, Mockito.never()).setLogoImageVisible(false);
        Mockito.verify(view, Mockito.times(1)).showDefUserLogo();
        Mockito.verify(view, Mockito.never()).setLogoImage((StateToken) Mockito.anyObject());
    }

    @Test
    public void testProjectWithLogo() {
        testGroupWIthLogo(GroupType.PROJECT);
    }

    private void testGroupWIthLogo(GroupType groupType) {
        GroupDTO group = new GroupDTO("shortname", "longname", groupType);
        group.setHasLogo(true);
        entityLogoPresenter.setGroupLogo(group);
        Mockito.verify(view, Mockito.times(1)).setLogoImageVisible(true);
        Mockito.verify(view, Mockito.never()).setLogoImageVisible(false);
        Mockito.verify(view, Mockito.never()).showDefUserLogo();
        Mockito.verify(view, Mockito.times(1)).setLogoImage((StateToken) Mockito.anyObject());
    }

}
