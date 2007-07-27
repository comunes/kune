package org.ourproject.kune.platf.client.workspace;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.*;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.KuneTool;

public class WorkspacePresenterTest {

    private Workspace workspace;
    private WorkspacePresenter presenter;
    private WorkspaceView view;

    @Before public void prepare() {
        workspace = new Workspace();
        presenter = new WorkspacePresenter(workspace);
        view = createStrictMock(WorkspaceView.class);
    }

    @Test public void testViewInitialization() {
        KuneTool tool = createNiceMock(KuneTool.class);
        String toolName = "toolName";
        expect(tool.getName()).andReturn(toolName);

        String groupName = "groupName";
        workspace.setGroupName(groupName);
        
        workspace.setTools(new KuneTool[] { tool });
        
        // mis expectativas
        view.addTab(toolName);
        expectLastCall();
        
        view.setLogo(groupName);
        expectLastCall();
        
        view.setSelectedTab(0);
        expectLastCall();

        replay(tool, view);
        
        presenter.init(view);
        verify(tool, view);
    }

}
