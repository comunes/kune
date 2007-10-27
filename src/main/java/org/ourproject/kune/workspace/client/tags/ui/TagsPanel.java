package org.ourproject.kune.workspace.client.tags.ui;

import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.workspace.client.tags.TagsPresenter;
import org.ourproject.kune.workspace.client.tags.TagsView;

import com.google.gwt.user.client.ui.Label;

public class TagsPanel extends DropDownPanel implements TagsView {

    public TagsPanel(final TagsPresenter presenter) {
        super("Tags", true);
        addStyleName("kune-Margin-Medium-t");
    }

    public void setTags(final String thisIsOnlyForTests) {
        super.setContent(new Label(thisIsOnlyForTests));
    }

}
