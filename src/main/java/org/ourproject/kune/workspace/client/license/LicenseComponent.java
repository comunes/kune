package org.ourproject.kune.workspace.client.license;

import org.ourproject.kune.platf.client.Component;
import org.ourproject.kune.platf.client.dto.LicenseDTO;

public interface LicenseComponent extends Component {

    void setLicense(String groupName, LicenseDTO licenseDTO);

}
