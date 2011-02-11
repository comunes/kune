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
