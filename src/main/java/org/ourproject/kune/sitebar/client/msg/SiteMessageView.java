package org.ourproject.kune.sitebar.client.msg;

public interface SiteMessageView {

    public void setMessage(String message);

    public void setMessage(String message, int oldStyle, int newStyle);

    public void adjustWidth(int windowWidth);

    public void show();

    public void hide();

    public void reset();

}
