package org.ourproject.kune.platf.client.license;

import org.ourproject.kune.platf.client.View;

public interface LicenseChooseView extends View {

    boolean isCCselected();

    boolean isAllowModif();

    boolean isAllowModifShareAlike();

    int getSelectedNonCCLicenseIndex();

    boolean permitComercial();

    void reset();

    void showNotCCoptiones();

    void showCCoptions();
}
