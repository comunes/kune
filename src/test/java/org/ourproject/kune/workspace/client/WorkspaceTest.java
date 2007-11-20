package org.ourproject.kune.workspace.client;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;
import org.ourproject.kune.workspace.client.workspace.WorkspacePresenter;
import org.ourproject.kune.workspace.client.workspace.WorkspaceView;

public class WorkspaceTest {
    private WorkspaceView view;
    private WorkspacePresenter presenter;

    @Before
    public void create() {
        presenter = new WorkspacePresenter();
        view = EasyMock.createStrictMock(WorkspaceView.class);
        presenter.init(view);
    }

    @Test
    public void contextShouldCache() {
        // estos no los vamos a probar...
        // por eso, son niceMocks
        // estos mocks nos ayudan... ¿cómo? :
        // 1. no tengo que escribir clases vacías que implementan los metodos
        // 2. respondiendo el widget cuando le piden el view!!!
        final WorkspaceComponent contextComponent = EasyMock.createNiceMock(WorkspaceComponent.class);
        final View contextView = EasyMock.createNiceMock(View.class);
        EasyMock.expect(contextComponent.getView()).andReturn(contextView);

        // de esta manera
        // podemos comprobar que en view
        // llega el contextView que hemos creado antes
        view.setContext(contextView);
        EasyMock.replay(contextComponent, contextView, view);

        // y por fin probamos que el presenter
        // (que es el objeto que queremos probar)
        // sólo llama setContext en la vista cuando
        // el contexto REALMENTE cambia
        presenter.setContext(contextComponent);
        EasyMock.verify(view);
    }
}
