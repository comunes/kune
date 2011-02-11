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
package cc.kune.core.client.ws.entheader;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourproject.kune.workspace.client.themes.WsThemeManager;

import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.ws.entheader.EntityHeaderPresenter.EntityHeaderView;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.GroupType;

public class EntityLogoPresenterTest {

    private EntityHeaderPresenter entityLogoPresenter;
    private Session session;
    private StateManager stateManager;
    private EntityHeaderView view;

    @Before
    public void before() {
        stateManager = Mockito.mock(StateManager.class);
        session = Mockito.mock(Session.class);
        final WsThemeManager theme = Mockito.mock(WsThemeManager.class);
        // entityLogoPresenter = new EntityHeaderPresenter(stateManager, theme,
        // session);
        view = Mockito.mock(EntityHeaderView.class);
        // entityLogoPresenter.init(view);
    }

    @Test
    public void testCommProjectNoLogo() {
        final GroupDTO group = new GroupDTO("shortname", "longname", GroupType.COMMUNITY);
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

    private void testGroupWIthLogo(final GroupType groupType) {
        final GroupDTO group = new GroupDTO("shortname", "longname", groupType);
        group.setHasLogo(true);
        entityLogoPresenter.setGroupLogo(group);
        Mockito.verify(view, Mockito.times(1)).setLogoImageVisible(true);
        Mockito.verify(view, Mockito.never()).setLogoImageVisible(false);
        Mockito.verify(view, Mockito.never()).showDefUserLogo();
        Mockito.verify(view, Mockito.times(1)).setLogoImage((StateToken) Mockito.anyObject());
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
        final GroupDTO group = new GroupDTO("shortname", "longname", GroupType.PERSONAL);
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

}
