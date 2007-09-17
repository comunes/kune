package org.ourproject.kune.workspace.client.socialnet.ui;

import java.util.Iterator;

import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.GroupListDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.platf.client.ui.IconHyperlink;
import org.ourproject.kune.workspace.client.socialnet.SocialNetworkPresenter;
import org.ourproject.kune.workspace.client.socialnet.SocialNetworkView;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SocialNetworkPanel extends DropDownPanel implements SocialNetworkView {

    private final VerticalPanel adminsVP;
    private final VerticalPanel collaboratorsVP;
    private final Label adminsLabel;
    private final Label collaboratorsLabel;

    public SocialNetworkPanel(final SocialNetworkPresenter presenter) {
	VerticalPanel vp = new VerticalPanel();
	adminsLabel = new Label("Admins");
	adminsVP = new VerticalPanel();
	collaboratorsLabel = new Label("Collaborators");
	collaboratorsVP = new VerticalPanel();
	// i18n
	IconHyperlink joinLink = new IconHyperlink(Images.App.getInstance().addGreen(), "Request to join", "fixme");
	vp.add(adminsLabel);
	vp.add(adminsVP);
	vp.add(collaboratorsLabel);
	vp.add(collaboratorsVP);
	vp.add(joinLink);

	setContent(vp);
	// i18n
	setHeaderText("Group members");
	setContentVisible(true);
	addStyleName("kune-SocialNet");
	addStyleName("kune-Margin-Medium-t");
	adminsLabel.addStyleName("kune-SocialNetSubLabel");
	collaboratorsLabel.addStyleName("kune-SocialNetSubLabel");
	vp.setCellHorizontalAlignment(joinLink, HorizontalPanel.ALIGN_CENTER);
	vp.setCellVerticalAlignment(joinLink, VerticalPanel.ALIGN_MIDDLE);

	joinLink.addClickListener(new ClickListener() {
	    public void onClick(final Widget sender) {
		presenter.onJoin();
	    }
	});
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
	    groupVP.add(new IconHyperlink(Images.App.getInstance().groupDefIcon(), next.getLongName(),
		    "linkToGroupDefContent"));
	}
    }
}
