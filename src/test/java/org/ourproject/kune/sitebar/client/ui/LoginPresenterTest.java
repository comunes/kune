package org.ourproject.kune.sitebar.client.ui;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class LoginPresenterTest {
    private LoginPresenter presenter;
    private LoginView view;
    private LoginListener listener;

    @Before public void createObjects() {
        listener = EasyMock.createStrictMock(LoginListener.class);
        presenter = new LoginPresenter(listener);
        view = EasyMock.createStrictMock(LoginView.class);
    }

    @Test public void testViewInitialization() {
        view.setEnabledLoginButton(false);
        view.clearData();
        EasyMock.replay(view);
        presenter.init(view);
        EasyMock.verify(view);
    }

    @Test public void noUserInput() {
        view.setEnabledLoginButton(false);
        EasyMock.replay(view);

        presenter.onDataChanged("", "");
        EasyMock.verify(view);
    }

    @Test public void nullInput() {
        view.setEnabledLoginButton(false);
        EasyMock.replay(view);

        presenter.onDataChanged(null, null);
        EasyMock.verify(view);
    }

    @Test public void nickPassTyped() {
        view.setEnabledLoginButton(true);
        EasyMock.replay(view);

        presenter.onDataChanged("luther.b", "somepass");
        EasyMock.verify(view);
    }
}
