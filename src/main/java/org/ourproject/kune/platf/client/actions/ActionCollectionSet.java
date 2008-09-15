package org.ourproject.kune.platf.client.actions;


public class ActionCollectionSet<T> {

    private final ActionCollection<T> toolbarActions;
    private final ActionCollection<T> itemActions;

    public ActionCollectionSet() {
	toolbarActions = new ActionCollection<T>();
	itemActions = new ActionCollection<T>();
    }

    public ActionCollection<T> getItemActions() {
	return itemActions;
    }

    public ActionCollection<T> getToolbarActions() {
	return toolbarActions;
    }
}
