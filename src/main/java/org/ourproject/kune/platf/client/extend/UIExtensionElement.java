package org.ourproject.kune.platf.client.extend;

import org.ourproject.kune.platf.client.View;

public class UIExtensionElement {
    private final String id;
    private final View view;

    public UIExtensionElement(final String id, final View view) {
        this.id = id;
        this.view = view;
    }

    public String getId() {
        return id;
    }

    public View getView() {
        return view;
    }

}
