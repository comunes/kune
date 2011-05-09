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
package cc.kune.common.client.actions.ui.descrip;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AbstractGuiActionDescripTest {

    private GuiAddCondition addFalseCond;
    private GuiAddCondition addTrueCond1;
    private GuiAddCondition addTrueCond2;
    private GuiAddCondition addTrueCond3;
    private MenuDescriptor descriptor;

    @Before
    public void before() {
        descriptor = new MenuDescriptor();
        addTrueCond1 = Mockito.mock(GuiAddCondition.class);
        addTrueCond2 = Mockito.mock(GuiAddCondition.class);
        addTrueCond3 = Mockito.mock(GuiAddCondition.class);
        addFalseCond = Mockito.mock(GuiAddCondition.class);
        Mockito.stub(addTrueCond1.mustBeAdded((GuiActionDescrip) Mockito.any())).toReturn(true);
        Mockito.stub(addTrueCond2.mustBeAdded((GuiActionDescrip) Mockito.any())).toReturn(true);
        Mockito.stub(addTrueCond3.mustBeAdded((GuiActionDescrip) Mockito.any())).toReturn(true);
        Mockito.stub(addFalseCond.mustBeAdded((GuiActionDescrip) Mockito.any())).toReturn(false);
    }

    @Test
    public void onffalseAddConditionsShouldDontBeAdded() {
        descriptor.add(addTrueCond1);
        descriptor.add(addTrueCond2);
        descriptor.add(addFalseCond);
        assertFalse(descriptor.mustBeAdded());
    }

    @Test
    public void twoTrueAddConditionsMustBeAdded() {
        descriptor.add(addTrueCond1);
        descriptor.add(addTrueCond2);
        descriptor.add(addTrueCond3);
        assertTrue(descriptor.mustBeAdded());
    }

    @Test
    public void withoutAddConditionsMustBeAdded() {
        assertTrue(descriptor.mustBeAdded());
    }
}
