package org.ourproject.kune.platf.client.actions;

/**
 * And action description and a item (for instance a StateToken, a XmmpURI) over
 * the action takes place
 * 
 * @param <T>
 */
public class ActionItem<T> {

    ActionDescriptor<T> action;
    T item;

    public ActionItem(final ActionDescriptor<T> action, final T item) {
	this.action = action;
	this.item = item;
    }

    public ActionDescriptor<T> getAction() {
	return action;
    }

    public T getItem() {
	return item;
    }
}
