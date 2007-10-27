package org.ourproject.kune.workspace.client.tags;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.workspace.TagsComponent;

public class TagsPresenter implements TagsComponent {

    private TagsView view;

    public TagsPresenter() {
    }

    public void setTags(final String thisIsOnlyForTests) {
        view.setTags(thisIsOnlyForTests);
    }

    public void init(final TagsView view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }
}
