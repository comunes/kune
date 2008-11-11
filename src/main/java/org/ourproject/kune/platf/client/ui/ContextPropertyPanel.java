package org.ourproject.kune.platf.client.ui;

import org.ourproject.kune.platf.client.services.Images;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DisclosurePanelImages;
import com.google.gwt.user.client.ui.Widget;

public class ContextPropertyPanel extends Composite {

    public ContextPropertyPanel(AbstractImagePrototype headerIcon, String tooltip, String headerText, boolean isOpen,
            String debugId, Widget content) {
        this(new IconLabel(headerIcon, headerText), tooltip, isOpen, debugId, content);
    }

    public ContextPropertyPanel(String headerText, String tooltip, boolean isOpen, String debugId, Widget content) {
        DisclosurePanelImages images = new DisclosurePanelImages() {
            public AbstractImagePrototype disclosurePanelClosed() {
                return Images.App.getInstance().arrowRightWhite();
            }

            public AbstractImagePrototype disclosurePanelOpen() {
                return Images.App.getInstance().arrowDownWhite();
            }
        };
        DisclosurePanel disclosure = new DisclosurePanel(images, headerText, isOpen);
        init(disclosure, tooltip, content, debugId);
    }

    public ContextPropertyPanel(Widget header, String tooltip, boolean isOpen, String debugId, Widget content) {
        DisclosurePanel disclosure = new DisclosurePanel(header, isOpen);
        init(disclosure, tooltip, content, debugId);
    }

    private void init(DisclosurePanel disclosure, String tooltip, Widget content, String debugId) {
        disclosure.setAnimationEnabled(true);
        disclosure.ensureDebugId(debugId);
        disclosure.setContent(content);
        KuneUiUtils.setQuickTip(disclosure, tooltip);
        initWidget(disclosure);
    }
}
