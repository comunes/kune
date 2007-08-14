package org.ourproject.kune.platf.server.manager;

public interface Manager<T, X> {
    public T find(X id);
}
