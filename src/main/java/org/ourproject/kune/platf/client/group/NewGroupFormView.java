package org.ourproject.kune.platf.client.group;

import org.ourproject.kune.platf.client.View;

public interface NewGroupFormView extends View {
    void clearData();

    void setLicense(String longName);

    String getPublicDesc();

    String getLongName();

    String getShortName();
}
