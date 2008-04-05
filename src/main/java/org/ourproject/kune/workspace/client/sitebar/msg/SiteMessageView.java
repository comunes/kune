package org.ourproject.kune.workspace.client.sitebar.msg;

import org.ourproject.kune.platf.client.View;

public interface SiteMessageView extends View {

    public void setMessage(String message);

    public void setMessage(String message, int oldStyle, int newStyle);

    public void adjustWidth(int windowWidth);

    public void show();

    public void hide();

}
