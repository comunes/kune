package cc.kune.core.client.ws;

import cc.kune.core.ws.armor.client.Body;
import cc.kune.core.ws.armor.client.resources.WsArmorResources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * The Class CoreView is where the general armor of Kune it created/attached.
 */
public class CoreViewImpl extends ViewImpl implements CorePresenter.CoreView {

    private final Body body;

    /**
     * Instantiates a new core view.
     * 
     * @param body
     *            the body
     */
    @Inject
    public CoreViewImpl(final Body body) {
        this.body = body;
        GWT.<WsArmorResources> create(WsArmorResources.class).style().ensureInjected();
        body.getEntityHeader().add(new InlineLabel("Test - a, e, i, o, u"));
        body.getDocHeader().add(new InlineLabel("doc title"));
    }

    @Override
    public Widget asWidget() {
        return body;
    }
}
