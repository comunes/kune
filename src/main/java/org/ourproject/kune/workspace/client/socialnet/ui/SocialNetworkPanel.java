package org.ourproject.kune.workspace.client.socialnet.ui;

import java.util.Iterator;

import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.GroupListDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.workspace.client.socialnet.SocialNetworkPresenter;
import org.ourproject.kune.workspace.client.socialnet.SocialNetworkView;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SocialNetworkPanel extends DropDownPanel implements SocialNetworkView {

    private final VerticalPanel adminsVP;
    private final VerticalPanel collaboratorsVP;
    private final Label adminsLabel;
    private final Label collaboratorsLabel;

    public SocialNetworkPanel(final SocialNetworkPresenter presenter) {
	// i18n
	setHeaderText("Members");
	setContentVisible(true);
	VerticalPanel vp = new VerticalPanel();
	adminsLabel = new Label("Admins:");
	adminsVP = new VerticalPanel();
	collaboratorsLabel = new Label("Collaborators:");
	collaboratorsVP = new VerticalPanel();
	vp.add(adminsLabel);
	vp.add(adminsVP);
	vp.add(collaboratorsLabel);
	vp.add(collaboratorsVP);
	setContent(vp);
    }

    public void setSocialNetwork(final SocialNetworkDTO socialNetwork) {
	setAccessLists(socialNetwork.getAccessLists());
    }

    public void setAccessLists(final AccessListsDTO accessLists) {
	groupsShow(accessLists.getAdmins(), adminsVP);
	groupsShow(accessLists.getEditors(), collaboratorsVP);
	collaboratorsLabel.setVisible(accessLists.getEditors().getList().size() > 0);
    }

    private void groupsShow(final GroupListDTO groupList, final VerticalPanel groupVP) {
	groupVP.clear();
	Iterator iter = groupList.getList().iterator();
	while (iter.hasNext()) {
	    GroupDTO next = (GroupDTO) iter.next();
	    groupVP.add(new Label(next.getLongName()));
	}
    }
}
