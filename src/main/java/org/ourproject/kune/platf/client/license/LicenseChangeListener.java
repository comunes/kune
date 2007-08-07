package org.ourproject.kune.platf.client.license;

public interface LicenseChangeListener {
    void onLicenseChange(String licenseShortName);

    void onCancel();
}
