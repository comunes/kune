package org.ourproject.kune.workspace.client.licensefoot;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LicenseDTO;

public interface LicenseView extends View {

    void showLicense(String groupName, LicenseDTO licenseDTO);

    void openWindow(String url);

}
