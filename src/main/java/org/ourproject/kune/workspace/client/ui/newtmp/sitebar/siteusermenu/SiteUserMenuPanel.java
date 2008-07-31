package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.siteusermenu;

import java.util.List;

import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.menu.Menu;

public class SiteUserMenuPanel implements SiteUserMenuView {

    private final PushButton loggedUserMenu;
    private final Widget separator;
    private final Menu userMenu;

    public SiteUserMenuPanel(final SiteUserMenuPresenter presenter, final WorkspaceSkeleton ws) {
	loggedUserMenu = new PushButton("");
	ws.getSiteBar().add(loggedUserMenu);
	separator = ws.getSiteBar().addSeparator();
	userMenu = new Menu();
	loggedUserMenu.addClickListener(new ClickListener() {
	    public void onClick(final Widget sender) {
		userMenu.showAt(sender.getAbsoluteLeft(), sender.getAbsoluteTop());
	    }
	});
    }

    public void setLoggedUserName(final String name) {
	loggedUserMenu.setText(name);
    }

    public void setUseGroupsIsMember(final List<LinkDTO> groupsIsAdmin, final List<LinkDTO> groupsIsCollab) {
	// TODO Auto-generated method stub
    }

    public void setVisible(final boolean visible) {
	loggedUserMenu.setVisible(visible);
	separator.setVisible(visible);
    }
}
