package org.ourproject.kune.sitebar.client.msg;

public interface MessagePresenter {

    public abstract void resetMessage();

    public abstract void setMessage(final String message, final int type);

    public abstract void onMessageClose();

}