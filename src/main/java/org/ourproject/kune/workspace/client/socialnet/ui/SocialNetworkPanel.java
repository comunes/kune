package org.ourproject.kune.workspace.client.socialnet.ui;

import java.util.Iterator;

import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.GroupListDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.platf.client.ui.IconHyperlink;
import org.ourproject.kune.platf.client.ui.IconLabel;
import org.ourproject.kune.workspace.client.socialnet.SocialNetworkPresenter;
import org.ourproject.kune.workspace.client.socialnet.SocialNetworkView;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SocialNetworkPanel extends DropDownPanel implements SocialNetworkView {

    private final VerticalPanel adminsVP;
    private final VerticalPanel collaboratorsVP;
    private final Label adminsLabel;
    private final Label collaboratorsLabel;
    private final IconLabel pendingCollaboratorsLabel;
    private final VerticalPanel pendingCollaboratorsVP;
    private final Hyperlink moreLink;
    private final IconHyperlink joinLink;
    private final SocialNetworkPresenter presenter;

    public SocialNetworkPanel(final SocialNetworkPresenter presenter) {
	this.presenter = presenter;
	final Images img = Images.App.getInstance();
	final VerticalPanel vp = new VerticalPanel();
	adminsLabel = new Label("Admins");
	adminsVP = new VerticalPanel();
	collaboratorsLabel = new Label("Collaborators");
	collaboratorsVP = new VerticalPanel();
	pendingCollaboratorsLabel = new IconLabel(img.bulletRed(), "Pending Collaborators");
	pendingCollaboratorsVP = new VerticalPanel();
	moreLink = new Hyperlink("more", "fixme");
	joinLink = new IconHyperlink(img.addGreen(), "Request to join", "fixme");
	// Layout
	vp.add(adminsLabel);
	vp.add(adminsVP);
	vp.add(collaboratorsLabel);
	vp.add(collaboratorsVP);
	vp.add(pendingCollaboratorsLabel);
	vp.add(pendingCollaboratorsLabel);

	vp.add(moreLink);
	vp.add(joinLink);
	setContent(vp);

	// Set properties
	setAdminsVisible(false);
	setCollabVisible(false);
	setPendingCollaboratorsLabelVisible(false);
	setContentVisible(true); // DropDown
	// i18n
	setHeaderText("Group members");
	setHeaderTitle("People collaborating in this group");
	addStyleName("kune-SocialNet");
	addStyleName("kune-Margin-Medium-t");

	adminsLabel.addStyleName("kune-SocialNetSubLabel");
	collaboratorsLabel.addStyleName("kune-SocialNetSubLabel");
	pendingCollaboratorsLabel.addStyleName("kune-SocialNetSubLabel");

	vp.setCellHorizontalAlignment(moreLink, HorizontalPanel.ALIGN_RIGHT);
	moreLink.addStyleName("kune-SocialNetMoreLink");
	vp.setCellHorizontalAlignment(joinLink, HorizontalPanel.ALIGN_CENTER);
	joinLink.addStyleName("kune-SocialNetJoinLink");

	joinLink.addClickListener(new ClickListener() {
	    public void onClick(final Widget sender) {
		presenter.onMore();
	    }
	});

	joinLink.addClickListener(new ClickListener() {
	    public void onClick(final Widget sender) {
		presenter.onJoin();
	    }
	});
    }

    public void setSocialNetwork(final SocialNetworkDTO socialNetwork) {
	final GroupListDTO pendingCollaborators = socialNetwork.getPendingCollaborators();
	setAccessLists(socialNetwork.getAccessLists());
	groupsShow(pendingCollaborators, pendingCollaboratorsVP);
    }

    public void setAdminsVisible(final boolean visible) {
	adminsLabel.setVisible(visible);
	adminsVP.setVisible(visible);
    }

    public void setCollabVisible(final boolean visible) {
	collaboratorsLabel.setVisible(visible);
	collaboratorsVP.setVisible(visible);
    }

    public void setPendingCollaboratorsLabelVisible(final boolean visible) {
	pendingCollaboratorsLabel.setVisible(visible);
	pendingCollaboratorsVP.setVisible(visible);
    }

    public void setVisibleJoinLink(final boolean visible) {
	joinLink.setVisible(visible);
    }

    public void setVisibleMoreLink(final boolean visible) {
	moreLink.setVisible(visible);
    }

    private void setAccessLists(final AccessListsDTO accessLists) {
	final GroupListDTO admins = accessLists.getAdmins();
	final GroupListDTO editors = accessLists.getEditors();
	groupsShow(admins, adminsVP);
	groupsShow(editors, collaboratorsVP);
    }

    private void groupsShow(final GroupListDTO groupList, final VerticalPanel groupVP) {
	final Images img = Images.App.getInstance();
	groupVP.clear();
	final Iterator iter = groupList.getList().iterator();
	while (iter.hasNext()) {
	    final GroupDTO group = (GroupDTO) iter.next();
	    final HorizontalPanel hp = new HorizontalPanel();
	    // Until user upload icons/images ...
	    AbstractImagePrototype icon;
	    if (group.getType() == GroupDTO.TYPE_PERSONAL) {
		icon = img.personDef();
	    } else {
		icon = img.groupDefIcon();
	    }
	    final IconLabel groupLabel = new IconLabel(icon, group.getLongName());
	    hp.add(groupLabel);
	    final Image deleteImg = img.del().createImage();
	    deleteImg.setVisible(false);
	    deleteImg.addClickListener(new ClickListener() {
		public void onClick(final Widget arg0) {
		    presenter.onDelGroup(group);
		}
	    });
	    hp.add(deleteImg);
	    final MouseListenerAdapter mouseListenerAdapter = new MouseListenerAdapter() {
		public void onMouseEnter(final Widget arg0) {
		    deleteImg.setVisible(true);
		}

		public void onMouseLeave(final Widget arg0) {
		    deleteImg.setVisible(false);
		}
	    };
	    deleteImg.addMouseListener(mouseListenerAdapter);
	    groupLabel.addMouseListener(mouseListenerAdapter);
	    groupVP.add(hp);
	}
    }
}
