package org.ourproject.kune.workspace.client.workspace.ui;

import java.util.HashMap;

import org.ourproject.kune.platf.client.extend.UIExtensionPoint;
import org.ourproject.kune.workspace.client.workspace.ContentToolBarPresenter;
import org.ourproject.kune.workspace.client.workspace.ContentToolBarView;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class ContentToolBarPanel extends HorizontalPanel implements ContentToolBarView {

    private final HorizontalPanel leftHP;

    public ContentToolBarPanel(final ContentToolBarPresenter presenter) {
        leftHP = new HorizontalPanel();
        add(leftHP);
        add(new Label(""));
        this.setWidth("100%");
        this.addStyleName("kune-ContentToolBarPanel");
        leftHP.addStyleName("kune-Margin-Large-l");
    }

    public HashMap<String, UIExtensionPoint> getExtensionPoints() {
        HashMap<String, UIExtensionPoint> extPoints = new HashMap<String, UIExtensionPoint>();
        String leftId = UIExtensionPoint.CONTENT_TOOLBAR_LEFT;
        extPoints.put(leftId, new UIExtensionPoint(leftId, leftHP));
        return extPoints;
    }
}
