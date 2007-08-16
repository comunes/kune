package org.ourproject.kune.platf.client.license;

import org.ourproject.kune.platf.client.View;

public interface LicenseChooseForm {

    void onCancel();

    void onSelect();

    View getView();

}