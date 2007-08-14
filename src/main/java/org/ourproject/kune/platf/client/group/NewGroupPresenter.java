package org.ourproject.kune.platf.client.group;

import java.util.ArrayList;
import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.license.LicenseChangeListener;
import org.ourproject.kune.platf.client.license.LicenseChoosePanel;
import org.ourproject.kune.platf.client.license.LicenseChoosePresenter;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.client.rpc.KuneServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;

public class NewGroupPresenter implements NewGroupForm {

    private final NewGroupListener listener;
    private NewGroupView view;
    private int groupType;
    private LicenseChoosePanel licensePanel;
    private DialogBox licenseDialog;
    private LicenseDTO license;

    public NewGroupPresenter(final NewGroupListener listener) {
	this.listener = listener;
	groupType = GroupDTO.TYPE_ORGANIZATION;
    }

    public void init(final NewGroupView view) {
	this.view = view;
	reset();
    }

    private void reset() {
	view.clearData();
    }

    void doCreateNewGroup(final String shortName, final String longName, final String publicDesc) {
	KuneServiceAsync kuneService = KuneService.App.getInstance();
	// TODO
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

    void doCancel() {
	licensePanel.reset();
	reset();
	listener.onNewGroupCancel();
    }

    public void selectType(final int type) {
	groupType = type;
    }

    protected void doChooseLicense() {
	if (licensePanel == null) {
	    List licensesNotCCList = new ArrayList();
	    licensesNotCCList.add(new LicenseDTO("gfdl", "GNU Free Documentation License", "",
		    "http://www.gnu.org/copyleft/fdl.html", false, true, false, "", ""));
	    List licensesList = new ArrayList();
	    licensesList.add(new LicenseDTO("by", "Creative Commons Attribution", "",
		    "http://creativecommons.org/licenses/by/3.0/", true, false, false, "", ""));
	    licensesList.add(new LicenseDTO("by-sa", "Creative Commons Attribution-ShareAlike", "",
		    "http://creativecommons.org/licenses/by-sa/3.0/", true, true, false, "", ""));
	    licensesList.add(new LicenseDTO("by-nd", "Creative Commons Attribution-NoDerivs", "",
		    "http://creativecommons.org/licenses/by-nd/3.0/", true, false, false, "", ""));
	    licensesList.add(new LicenseDTO("by-nc", "Creative Commons Attribution-NonCommercial", "",
		    "http://creativecommons.org/licenses/by-nc/3.0/", true, false, false, "", ""));
	    licensesList.add(new LicenseDTO("by-nc-sa", "Creative Commons Attribution-NonCommercial-ShareAlike", "",
		    "http://creativecommons.org/licenses/by-nc-sa/3.0/", true, false, false, "", ""));
	    licensesList.add(new LicenseDTO("by-nc-nd", "Creative Commons Attribution-NonCommercial-NoDerivs", "",
		    "http://creativecommons.org/licenses/by-nc-nd/3.0/", true, false, false, "", ""));
	    licensesList.add(new LicenseDTO("gfdl", "GNU Free Documentation License", "",
		    "http://www.gnu.org/copyleft/fdl.html", false, true, false, "", ""));
	    LicenseChoosePresenter licensePresenter = new LicenseChoosePresenter();
	    licensePanel = new LicenseChoosePanel(licensePresenter, licensesNotCCList);
	    licensePresenter.init(licensePanel, licensesList, licensesNotCCList, new LicenseChangeListener() {
		public void onCancel() {
		    licenseDialog.hide();
		    licensePanel.reset();
		}

		public void onLicenseChange(final LicenseDTO licenseDTO) {
		    view.setLicense(licenseDTO.getLongName());
		    license = licenseDTO;
		    licenseDialog.hide();
		}
	    });
	}

	licenseDialog = new DialogBox();
	licenseDialog.setWidget(licensePanel);
	licenseDialog.setText("LALALAA"); // TODO: Better description
	licenseDialog.show();
	licenseDialog.center();

    }

    public View getView() {
	return view;
    }

}
