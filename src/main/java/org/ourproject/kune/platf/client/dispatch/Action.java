package org.ourproject.kune.platf.client.dispatch;

public interface Action<T> {

    public void execute(T param);

}