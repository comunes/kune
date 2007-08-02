package org.ourproject.kune.platf.client.workspace;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.View;

public class WorkspaceTest {
    private WorkspaceView view;
    private WorkspacePresenter presenter;

    @Before
    public void create() {
	view = EasyMock.createStrictMock(WorkspaceView.class);
	presenter = new WorkspacePresenter(view);
    }

    @Test
    public void contextShouldCache() {
	// estos no los vamos a probar...
	// por eso, son niceMocks
	// estos mocks nos ayudan... ¿cómo? :
	// 1. no tengo que escribir clases vacías que implementan los métodos
	// 2. respondiendo el widget cuando le piden el view!!!
	WorkspaceComponent contextComponent = EasyMock.createNiceMock(WorkspaceComponent.class);
	View contextView = EasyMock.createNiceMock(View.class);
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
	presenter.setContext(contextComponent);
	presenter.setContext(contextComponent);
	EasyMock.verify(view);
    }
}
