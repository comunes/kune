package org.ourproject.kune.workspace.client.newgroup;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LicenseDTO;

public interface NewGroupView extends View {

    void clearData();

    String getPublicDesc();

    String getLongName();

    String getShortName();

    boolean isProject();

    boolean isOrphanedProject();

    boolean isOrganization();

    boolean isCommunity();

    void hide();

    void setEnabledNextButton(boolean enabled);

    void setEnabledFinishButton(boolean enabled);

    void setEnabledBackButton(boolean enabled);

    void showNewGroupInitialDataForm();

    void showLicenseForm();

    LicenseDTO getLicense();

    boolean isFormValid();

    void hideMessage();

    void setMessage(String message, int type);

    void unMask();

    void maskProcessing();

}
