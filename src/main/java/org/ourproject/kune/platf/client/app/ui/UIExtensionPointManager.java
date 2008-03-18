package org.ourproject.kune.platf.client.app.ui;

import java.util.HashMap;

import org.ourproject.kune.platf.client.View;

import com.google.gwt.user.client.ui.Widget;

public class UIExtensionPointManager {

    private final HashMap<String, UIExtensionPoint> uiExtPoints;

    public UIExtensionPointManager() {
        uiExtPoints = new HashMap<String, UIExtensionPoint>();
    }

    public void addUIExtensionPoint(final UIExtensionPoint extPoint) {
        uiExtPoints.put(extPoint.getId(), extPoint);
    }

    public void attachToExtensionPoint(final String id, final View viewToAttach) {
        UIExtensionPoint extPoint = getExtPoint(id);
        extPoint.getPanel().add((Widget) viewToAttach);
    }

    public void insertToExtensionPoint(final String id, final View viewToAttach) {
        UIExtensionPoint extPoint = getExtPoint(id);
        extPoint.getPanel().add((Widget) viewToAttach);
    }

    public void detachFromExtensionPoint(final String id, final View viewToDetach) {
        UIExtensionPoint extPoint = getExtPoint(id);
        extPoint.getPanel().remove((Widget) viewToDetach);
    }

    private UIExtensionPoint getExtPoint(final String id) {
        UIExtensionPoint extPoint = this.uiExtPoints.get(id);
        return extPoint;
    }

    public void clearExtensionPoint(final String id) {
        UIExtensionPoint extPoint = getExtPoint(id);
        extPoint.getPanel().clear();
    }

    public void addUIExtensionPoints(final HashMap<String, UIExtensionPoint> extensionPoints) {
        uiExtPoints.putAll(extensionPoints);
    }

}
