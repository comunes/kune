package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitepublic;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.GroupListDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.StateManager;

import com.calclab.suco.client.signal.Slot;

public class SitePublicSpaceLinkPresenter implements SitePublicSpaceLink {

    private SitePublicSpaceLinkView view;

    public SitePublicSpaceLinkPresenter(final StateManager stateManager) {
	stateManager.onStateChanged(new Slot<StateDTO>() {
	    public void onEvent(final StateDTO state) {
		setState(state);
	    }
	});
    }

    public View getView() {
	return view;
    }

    public void init(final SitePublicSpaceLinkView view) {
	this.view = view;
    }

    public void setVisible(final boolean visible) {
	view.setVisible(visible);
    }

    private void setState(final StateDTO state) {
	final StateToken token = state.getStateToken();
	if (state.getAccessLists().getViewers().getMode().equals(GroupListDTO.EVERYONE)) {
	    final String publicUrl = token.getPublicUrl();
	    view.setContentGotoPublicUrl(publicUrl);
	    view.setContentPublic(true);
	} else {
	    view.setContentPublic(false);
	}
    }

}
