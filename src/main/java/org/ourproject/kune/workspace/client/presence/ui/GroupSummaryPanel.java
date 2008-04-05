package org.ourproject.kune.workspace.client.presence.ui;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.stacks.StackedDropDownPanel;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.summary.GroupSummaryView;

public class GroupSummaryPanel extends StackedDropDownPanel implements GroupSummaryView {

    public GroupSummaryPanel(final AbstractPresenter presenter) {
        super(presenter, Kune.getInstance().theme.getSummaryDD(), Kune.I18N.t("Group Summary"), Kune.I18N
                .t("Some summarized information about current project" + Site.IN_DEVELOPMENT), false);
        setDropDownContentVisible(true);
        setVisible(true);
    }

    public void setComment(final String comment) {
        super.clear();
        super.addComment(comment);
    }

}
