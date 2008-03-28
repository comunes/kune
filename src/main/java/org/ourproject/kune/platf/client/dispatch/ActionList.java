package org.ourproject.kune.platf.client.dispatch;

import java.util.ArrayList;
import java.util.Iterator;

public class ActionList<T> {

    private final ArrayList<Action<T>> list;

    public ActionList() {
        list = new ArrayList<Action<T>>();
    }

    public void addAction(final Action<T> action) {
        list.add(action);
    }

    public void removeAction(final Action<T> action) {
        list.remove(action);
    }

    public Iterator<Action<T>> iterator() {
        return list.iterator();
    }
}
