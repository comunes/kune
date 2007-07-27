package org.ourproject.kune.home.client;


public class HomeViewFactory {
    private static HomeMainPanel mainView;
    private static HomeMenuPanel menuView;

    public static HomeMainView getMainView() {
        if (mainView == null) {
            mainView = new HomeMainPanel();
        }
        return mainView;
    }

    public static HomeMenuView getContextMenuView() {
        if (menuView == null) {
            menuView = new HomeMenuPanel();
        }
        return menuView;
    }

}
