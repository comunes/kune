package org.ourproject.kune.workspace.client.summary;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.workspace.client.workspace.GroupSummaryComponent;

public class GroupSummaryPresenter extends AbstractPresenter implements GroupSummaryComponent {

    private GroupSummaryView view;

    public void setGroupSummary(final StateDTO state) {
        view.setComment("Summary about this group (in development)");
    }

    public View getView() {
        return view;
    }

    public void init(final GroupSummaryView view) {
        this.view = view;
    }

}
