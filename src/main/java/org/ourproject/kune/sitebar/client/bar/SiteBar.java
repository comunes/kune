package org.ourproject.kune.sitebar.client.bar;

import org.ourproject.kune.platf.client.View;

public interface SiteBar {

    void showProgress(String text);

    void hideProgress();

    View getView();

}
