package org.ourproject.kune.platf.client.workspace.ui;

public interface SiteMessageView {

    public void setMessageError(String message);

    public void setMessageVeryImp(String message);

    public void setMessageImp(String message);

    public void setMessageInfo(String message);

    public void adjustWidth(int windowWidth);

    public void show();

    public void hide();

    public void reset();

}
