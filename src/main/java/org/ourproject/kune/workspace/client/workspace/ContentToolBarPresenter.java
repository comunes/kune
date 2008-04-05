
package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.View;

public class ContentToolBarPresenter implements ContentToolBarComponent {

    private ContentToolBarView view;

    public void init(final ContentToolBarView view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }
}
