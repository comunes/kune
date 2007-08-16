package org.ourproject.kune.platf.client.dispatch;

public interface Action {
    public void execute(Object value, Object extra);

    public String getEventName();

    public String getActionName();
}
