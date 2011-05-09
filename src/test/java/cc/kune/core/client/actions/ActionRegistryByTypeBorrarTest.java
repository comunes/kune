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
package cc.kune.core.client.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.core.shared.domain.utils.AccessRights;

import com.google.inject.Provider;

public class ActionRegistryByTypeBorrarTest {

    private static final String OTHERACTIONGROUP = "otheractiongroup";
    private static final String SOMEACTIONGROUP = "someactiongroup";
    private GuiActionDescrip action;
    private ActionRegistryByType actionRegistryByType;
    private AccessRights allRights;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Before
    public void before() {
        actionRegistryByType = new ActionRegistryByType();
        action = Mockito.mock(GuiActionDescrip.class);
        allRights = new AccessRights(true, true, true);
        actionRegistryByType.addAction(SOMEACTIONGROUP, new Provider() {
            @Override
            public Object get() {
                return action;
            }
        });
    }

    @Test
    public void testDontGetCurrentActionsOfOtherGroup() {
        assertEquals(0, actionRegistryByType.getCurrentActions(null, false, allRights, OTHERACTIONGROUP).size());
    }

    @Test
    public void testGetCurrentActionsOfGroup() {
        assertEquals(1, actionRegistryByType.getCurrentActions(null, false, allRights, SOMEACTIONGROUP).size());
    }
}
