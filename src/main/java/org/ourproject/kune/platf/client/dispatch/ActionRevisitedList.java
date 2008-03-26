package org.ourproject.kune.platf.client.dispatch;

import java.util.ArrayList;
import java.util.Iterator;

public class ActionRevisitedList<T> {

    private ArrayList<ActionRevisited<T>> list;

    public ActionRevisitedList() {
        list = new ArrayList<ActionRevisited<<T>>();
    }

    public void addAction(final ActionRevisited<T> action) {
        list.add(action);
    }

    public void removeAction(final ActionRevisited<T> action) {
        list.remove(action);
    }

    public Iterator<ActionRevisited<T>> iterator() {
        return list.iterator();
    }
}
