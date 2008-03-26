package org.ourproject.kune.platf.client.dispatch;

public interface ActionRevisited<T> {

    public <K> void execute(T param, K environment);

}