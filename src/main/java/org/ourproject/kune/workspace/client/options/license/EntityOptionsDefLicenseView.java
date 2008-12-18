package org.ourproject.kune.workspace.client.options.license;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LicenseDTO;

public interface EntityOptionsDefLicenseView extends View {

    void openWindow(String url);

    void setLicense(LicenseDTO defaultLicense);

}
