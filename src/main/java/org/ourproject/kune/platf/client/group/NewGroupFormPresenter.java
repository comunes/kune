package org.ourproject.kune.platf.client.group;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.license.LicenseChooseFormPanel;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.client.rpc.KuneServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class NewGroupFormPresenter implements NewGroupForm {
    private final NewGroupListener listener;
    private NewGroupFormView view;
    private int groupType;
    private LicenseChooseFormPanel licensePanel;
    private LicenseDTO license;

    public NewGroupFormPresenter(final NewGroupListener listener) {
	this.listener = listener;
	groupType = GroupDTO.TYPE_ORGANIZATION;
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
	// TODO: without license you can't create a group
	String shortName = view.getShortName();
	String longName = view.getLongName();
	String publicDesc = view.getPublicDesc();
	GroupDTO group = new GroupDTO(shortName, longName, publicDesc, license.getShortName(), groupType);
	kuneService.createNewGroup(group, new AsyncCallback() {
	    public void onFailure(final Throwable arg0) {
		// TODO
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

    public void selectType(final int type) {
	groupType = type;
    }

    public View getView() {
	return view;
    }

}
