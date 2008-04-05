
package org.ourproject.kune.workspace.client.sitebar.msg;

public interface MessagePresenter {

    public abstract void resetMessage();

    public abstract void setMessage(final String message, final int type);

}