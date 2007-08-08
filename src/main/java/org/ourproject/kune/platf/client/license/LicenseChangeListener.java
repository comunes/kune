package org.ourproject.kune.platf.client.license;

import org.ourproject.kune.platf.client.dto.LicenseDTO;

public interface LicenseChangeListener {

    void onLicenseChange(LicenseDTO licenseDTO);

    void onCancel();

}
