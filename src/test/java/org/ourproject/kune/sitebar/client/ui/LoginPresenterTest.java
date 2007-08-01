package org.ourproject.kune.sitebar.client.ui;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;


public class LoginPresenterTest {
    private LoginPresenter presenter;
    private LoginView view;
    private LoginListener listener;

    @Before
    public void createObjects() {
        listener = EasyMock.createStrictMock(LoginListener.class);
        presenter = new LoginPresenter(listener);
        view = EasyMock.createStrictMock(LoginView.class);
    }
    
//    @Test public void testViewInitialization () {
//        presenter.init(view);
//    }
    
    
    @Test public void noUserInput() {
        view.setEnabledLoginButton(false);
        EasyMock.replay(view);
        
        presenter.onDataChanged("", "");
        EasyMock.verify(view);
    }
}

    