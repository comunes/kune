package org.ourproject.kune.workspace.client.licensefoot;

import org.ourproject.kune.platf.client.Component;
import org.ourproject.kune.platf.client.dto.StateDTO;

public interface LicenseComponent extends Component {

    void setLicense(StateDTO state);

}
