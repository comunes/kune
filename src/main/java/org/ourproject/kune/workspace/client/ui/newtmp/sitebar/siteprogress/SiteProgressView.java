package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.siteprogress;

import org.ourproject.kune.platf.client.View;

public interface SiteProgressView extends View {

    void hideProgress();

    void showProgress(String text);
}
