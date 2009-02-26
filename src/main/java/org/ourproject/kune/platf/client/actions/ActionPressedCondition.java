package org.ourproject.kune.platf.client.actions;

public interface ActionPressedCondition<T> {
    boolean mustBePressed(T param);
}
