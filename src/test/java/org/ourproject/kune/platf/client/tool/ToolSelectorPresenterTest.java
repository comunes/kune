package org.ourproject.kune.platf.client.tool;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

public class ToolSelectorPresenterTest {

    private static final String TOOL1_NAME = "tool1";
    private static final String TOOL2_NAME = "tool2";
    private static final String GROUP1_NAME = "group1";
    private ToolSelectorPresenter toolSelector;
    private ToolSelectorItem toolSelectorItem1;
    private ToolSelectorItem toolSelectorItem2;

    @Test
    public void addFirstToolMustNotSelect() {
        setToolNames();
        toolSelector.addTool(toolSelectorItem1);
        Mockito.verify(toolSelectorItem1).setSelected(false);
    }

    @Test(expected = RuntimeException.class)
    public void addToolWithoutNameMustGiveException() {
        toolSelector.addTool(toolSelectorItem1);
    }

    @Test
    public void addTwoToolsMustSelectNothing() {
        setToolNames();
        toolSelector.addTool(toolSelectorItem1);
        toolSelector.addTool(toolSelectorItem2);
        Mockito.verify(toolSelectorItem1).setSelected(false);
        Mockito.verify(toolSelectorItem2).setSelected(false);
    }

    @Test(expected = RuntimeException.class)
    public void addTwoToolsWithSameNameMustGiveException() {
        setToolNames();
        final ToolSelectorItem toolSelectorItemCopy = Mockito.mock(ToolSelectorItem.class);
        Mockito.when(toolSelectorItemCopy.getShortName()).thenReturn(TOOL1_NAME);
        toolSelector.addTool(toolSelectorItem1);
        toolSelector.addTool(toolSelectorItemCopy);
    }

    @Before
    public void begin() {
        final StateManager stateManager = Mockito.mock(StateManager.class);
        final WsThemePresenter themePresenter = Mockito.mock(WsThemePresenter.class);
        toolSelector = new ToolSelectorPresenter(stateManager, themePresenter);
        toolSelectorItem1 = Mockito.mock(ToolSelectorItem.class);
        toolSelectorItem2 = Mockito.mock(ToolSelectorItem.class);
    }

    @Test
    public void setStateFirstMustSelect() {
        setToolNames();
        toolSelector.addTool(toolSelectorItem1);
        toolSelector.onToolChanged("", TOOL1_NAME);
        Mockito.verify(toolSelectorItem1, Mockito.times(1)).setSelected(false);
        Mockito.verify(toolSelectorItem1, Mockito.times(1)).setSelected(true);
    }

    @Test
    public void setStateWithADifferentGroupMustSetLink() {
        setToolNames();
        toolSelector.addTool(toolSelectorItem1);
        toolSelector.addTool(toolSelectorItem2);
        toolSelector.onGroupChanged(GROUP1_NAME);
        Mockito.verify(toolSelectorItem1, Mockito.times(1)).setGroupShortName(GROUP1_NAME);
        Mockito.verify(toolSelectorItem2, Mockito.times(1)).setGroupShortName(GROUP1_NAME);
    }

    @Test
    public void setStateWithADifferentToolNameMustSelectAndUnSelectTheOlder() {
        setToolNames();
        toolSelector.addTool(toolSelectorItem1);
        toolSelector.addTool(toolSelectorItem2);
        toolSelector.onToolChanged(TOOL1_NAME, TOOL2_NAME);
        Mockito.verify(toolSelectorItem2, Mockito.times(1)).setSelected(false);
        Mockito.verify(toolSelectorItem1, Mockito.times(2)).setSelected(false);
        Mockito.verify(toolSelectorItem2, Mockito.times(1)).setSelected(true);
    }

    @Test
    public void setStateWithNoToolNameMustUnSelectTheOlder() {
        setToolNames();
        toolSelector.addTool(toolSelectorItem1);
        toolSelector.addTool(toolSelectorItem2);
        toolSelector.onToolChanged(TOOL1_NAME, "");
        Mockito.verify(toolSelectorItem2, Mockito.times(1)).setSelected(false);
        Mockito.verify(toolSelectorItem1, Mockito.times(2)).setSelected(false);
        Mockito.verify(toolSelectorItem2, Mockito.never()).setSelected(true);
    }

    public void setToolNames() {
        Mockito.when(toolSelectorItem1.getShortName()).thenReturn(TOOL1_NAME);
        Mockito.when(toolSelectorItem2.getShortName()).thenReturn(TOOL2_NAME);
    }

}
