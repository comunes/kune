package org.ourproject.kune.platf.client.actions;

import java.util.ArrayList;

public class DragDropContentRegistry {

    private final ArrayList<String> draggables;
    private final ArrayList<String> droppables;

    public DragDropContentRegistry() {
        draggables = new ArrayList<String>();
        droppables = new ArrayList<String>();
    }

    public boolean isDraggable(final String typeId, final boolean administrable) {
        return administrable && draggables.contains(typeId);
    }

    public boolean isDroppable(final String typeId, final boolean administrable) {
        return administrable && droppables.contains(typeId);
    }

    public void registerDraggableType(final String type) {
        draggables.add(type);
    }

    public void registerDroppableType(final String type) {
        droppables.add(type);
    }

}
