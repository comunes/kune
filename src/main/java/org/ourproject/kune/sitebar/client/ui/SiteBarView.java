package org.ourproject.kune.sitebar.client.ui;

import com.google.gwt.user.client.ui.Image;

public interface SiteBarView {

        void showLoggedUserName(String user);

        void clearUserName();

        void setLogo(Image logo);

        void setProgressText(String text);

        void showProgress(boolean show);

        void clearSearchText();

        void setSearchText(String text);

        void showLoginDialog();

        void setLogoutLinkVisible(boolean visible);

        public void hideLoginDialog();

        void restoreLoginLink();

}
