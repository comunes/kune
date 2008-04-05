package org.ourproject.kune.workspace.client.licensechoose;

import org.ourproject.kune.platf.client.View;

public interface LicenseChooseView extends View {

    boolean isCCselected();

    boolean isAllowModif();

    boolean isAllowModifShareAlike();

    int getSelectedNonCCLicenseIndex();

    boolean permitComercial();

    void reset();

    void showNotCCoptions();

    void showCCoptions();

    void showIsCopyleft();

    void showIsNotCopyleft();
}
