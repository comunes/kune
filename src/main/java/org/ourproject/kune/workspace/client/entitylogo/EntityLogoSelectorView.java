package org.ourproject.kune.workspace.client.entitylogo;

import org.ourproject.kune.platf.client.View;

public interface EntityLogoSelectorView extends View {
    void hide();

    void setUploadParams(String userHash, String token);

    void show();
}
