package org.ourproject.kune.platf.client.actions;

public interface ActionEnableCondition<T> {
    boolean mustBeEnabled(T item);
}
