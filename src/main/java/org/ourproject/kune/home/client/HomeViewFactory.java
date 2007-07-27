package org.ourproject.kune.home.client;

import org.ourproject.kune.platf.client.dto.ContentTreeDTO;
import org.ourproject.kune.platf.client.extend.ViewFactory;


public class HomeViewFactory implements ViewFactory {
    private static HomeMainPanel mainView;
    private static HomeMenuPanel menuView;

    public Object getContentView(Object content) {
        if (mainView == null) {
            mainView = new HomeMainPanel();
        }
        return mainView;
    }
    
    public Object getContextView(ContentTreeDTO tree) {
        if (menuView == null) {
            menuView = new HomeMenuPanel();
        }
        return menuView;
    }


}
