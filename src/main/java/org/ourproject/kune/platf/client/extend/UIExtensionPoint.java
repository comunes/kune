package org.ourproject.kune.platf.client.extend;

import com.google.gwt.user.client.ui.CellPanel;

public class UIExtensionPoint {
    // Not yet implemented EP:
    // public static final String CONTENT_TOOLBAR_RIGHT =
    // "ws.entity.content.toolbar.right";
    // public static final String CONTENT_BOTTOM_TOOLBAR_RIGHT =
    // "ws.entity.content.bottomtb.right";
    // public static final String CONTENT_BOTTOM_TOOLBAR_LEFT =
    // "ws.entity.content.bottomtb.left";
    public static final String CONTENT_TOOLBAR_LEFT = "ws.entity.content.toolbar.left";
    public static final String CONTENT_BOTTOM_ICONBAR = "ws.site.bottom.iconbar";

    private final String id;
    private final CellPanel panel;

    public UIExtensionPoint(final String id, final CellPanel panel) {
        this.id = id;
        this.panel = panel;
    }

    public String getId() {
        return id;
    }

    public CellPanel getPanel() {
        return panel;
    }

}
