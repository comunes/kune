package org.ourproject.kune.workspace.client.sitebar.sitepublic;

import org.ourproject.kune.platf.client.View;

public interface SitePublicSpaceLinkView extends View {

    void setContentGotoPublicUrl(String publicUrl);

    void setContentPublic(boolean visible);

    void setVisible(boolean visible);
}
