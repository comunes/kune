package org.ourproject.kune.platf.client.license;

import java.util.List;

import org.ourproject.kune.platf.client.View;

public interface LicenseChooseView extends View {

    boolean isCCselected();

    boolean isAllowModif();

    boolean isAllowModifShareAlike();

    int getSelectedNonCCLicenseIndex();

    boolean permitComercial();

    void setDefaults(List nonCCLicenses);
}
