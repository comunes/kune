package org.ourproject.kune.platf.client.group;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.license.LicenseChooseFormPanel;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.client.rpc.KuneServiceAsync;
import org.ourproject.kune.sitebar.client.Site;

import to.tipit.gwtlib.FireLog;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class NewGroupFormPresenter implements NewGroupForm {
    private final NewGroupListener listener;
    private NewGroupFormView view;
    private LicenseChooseFormPanel licensePanel;
    private LicenseDTO license;

    public NewGroupFormPresenter(final NewGroupListener listener) {
	this.listener = listener;
    }

    public void init(final NewGroupFormView view) {
	this.view = view;
	reset();
    }

    private void reset() {
	view.clearData();
    }

    public void doCreateNewGroup() {
	KuneServiceAsync kuneService = KuneService.App.getInstance();
	String shortName = view.getShortName();
	String longName = view.getLongName();
	String publicDesc = view.getPublicDesc();

	// TODO: without license you can't create a group
	GroupDTO group = new GroupDTO(shortName, longName, publicDesc, null, getTypeOfGroup());
	kuneService.createNewGroup(group, new AsyncCallback() {
	    public void onFailure(final Throwable arg0) {
		// TODO
		Site.error("Error creating group");
	    }

	    public void onSuccess(final Object arg0) {
		listener.onNewGroupCreated();
		reset();
	    }
	});
    }

    public void doCancel() {
	licensePanel.reset();
	reset();
	listener.onNewGroupCancel();
    }

    public View getView() {
	return view;
    }

    private int getTypeOfGroup() {
	if (view.isProject()) {
	    FireLog.debug("Proyecto");
	    return GroupDTO.TYPE_PROJECT;
	} else if (view.isOrganization()) {
	    FireLog.debug("Org");
	    return GroupDTO.TYPE_ORGANIZATION;
	} else {
	    FireLog.debug("Comm");
	    return GroupDTO.TYPE_COMNUNITY;
	}
    }
}
