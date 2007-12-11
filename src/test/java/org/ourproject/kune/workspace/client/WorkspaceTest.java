package org.ourproject.kune.workspace.client;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;
import org.ourproject.kune.workspace.client.workspace.WorkspacePresenter;
import org.ourproject.kune.workspace.client.workspace.WorkspaceView;

public class WorkspaceTest {
    private WorkspaceView view;
    private WorkspacePresenter presenter;

    @Before
    public void create() {
        presenter = new WorkspacePresenter(new Session("userHash"));
        view = EasyMock.createStrictMock(WorkspaceView.class);
        presenter.init(view);
    }

    @Test
    public void contextShouldCache() {
        final WorkspaceComponent contextComponent = EasyMock.createNiceMock(WorkspaceComponent.class);
        final View contextView = EasyMock.createNiceMock(View.class);
        EasyMock.expect(contextComponent.getView()).andReturn(contextView);

        view.setContext(contextView);
        EasyMock.replay(contextComponent, contextView, view);

        presenter.setContext(contextComponent);
        EasyMock.verify(view);
    }
}
