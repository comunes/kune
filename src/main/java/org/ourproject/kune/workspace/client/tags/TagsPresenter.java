
package org.ourproject.kune.workspace.client.tags;

import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.ShowSearcherActionParams;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.TagResultDTO;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.search.SearchSiteView;
import org.ourproject.kune.workspace.client.workspace.TagsComponent;

public class TagsPresenter implements TagsComponent {

    private TagsView view;
    private final Session session;

    public TagsPresenter(final Session session) {
        this.session = session;
    }

    public void setState(final StateDTO state) {
        view.setTags(state.getGroupTags());
    }

    public void setGroupTags(final List<TagResultDTO> groupTags) {
        view.setTags(groupTags);
    }

    public void init(final TagsView view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void doSearchTag(final String name) {
        DefaultDispatcher.getInstance().fire(
                WorkspaceEvents.SHOW_SEARCHER,
                new ShowSearcherActionParams("group:" + session.getCurrentState().getGroup().getShortName() + " tag:"
                        + name, SearchSiteView.CONTENT_SEARCH));
    }
}
