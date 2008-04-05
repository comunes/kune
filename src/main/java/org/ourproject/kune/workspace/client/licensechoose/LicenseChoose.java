package org.ourproject.kune.workspace.client.licensechoose;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LicenseDTO;

public interface LicenseChoose {

    View getView();

    LicenseDTO getLicense();

}