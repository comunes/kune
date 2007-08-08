package org.ourproject.kune.platf.client.rpc;

import java.util.ArrayList;
import java.util.List;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class KuneServiceMocked extends MockedService implements KuneServiceAsync {
    public void getDefaultGroup(final String userHash, final AsyncCallback callback) {
	delay(new Delayer() {
	    public void run() {
		callback.onSuccess(new GroupDTO("Kune dev", "kune", "This is the default group", null,
			GroupDTO.TYPE_ORGANIZATION));
	    }
	});
    }

    public void createNewGroup(final GroupDTO group, final AsyncCallback callback) {
	Timer timer = new Timer() {
	    public void run() {
		callback.onSuccess(null);
	    }
	};
	timer.schedule(1000);
    }

    public void getNotCCLicenses(final AsyncCallback callback) {
	List licenseList = new ArrayList();
	licenseList.add(new LicenseDTO("gfdl", "GNU Free Documentation License", "",
		"http://www.gnu.org/copyleft/fdl.html", false, true, false, "", ""));
	callback.onSuccess(licenseList);
    }

    public void getAllLicenses(final AsyncCallback callback) {
	List licenseList = new ArrayList();
	licenseList.add(new LicenseDTO("by", "Creative Commons Attribution", "",
		"http://creativecommons.org/licenses/by/3.0/", true, false, false, "", ""));
	licenseList.add(new LicenseDTO("by-sa", "Creative Commons Attribution-ShareAlike", "",
		"http://creativecommons.org/licenses/by-sa/3.0/", true, true, false, "", ""));
	licenseList.add(new LicenseDTO("by-nd", "Creative Commons Attribution-NoDerivs", "",
		"http://creativecommons.org/licenses/by-nd/3.0/", true, false, false, "", ""));
	licenseList.add(new LicenseDTO("by-nc", "Creative Commons Attribution-NonCommercial", "",
		"http://creativecommons.org/licenses/by-nc/3.0/", true, false, false, "", ""));
	licenseList.add(new LicenseDTO("by-nc-sa", "Creative Commons Attribution-NonCommercial-ShareAlike", "",
		"http://creativecommons.org/licenses/by-nc-sa/3.0/", true, false, false, "", ""));
	licenseList.add(new LicenseDTO("by-nc-nd", "Creative Commons Attribution-NonCommercial-NoDerivs", "",
		"http://creativecommons.org/licenses/by-nc-nd/3.0/", true, false, false, "", ""));
	licenseList.add(new LicenseDTO("gfdl", "GNU Free Documentation License", "",
		"http://www.gnu.org/copyleft/fdl.html", false, true, false, "", ""));
	callback.onSuccess(licenseList);
    }
}
