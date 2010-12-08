package cc.kune.core.client.ws;

import org.ourproject.kune.ws.armor.client.Body;
import org.ourproject.kune.ws.armor.client.resources.WsArmorResources;

import cc.kune.core.client.ws.CorePresenter.ICoreView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.inject.Inject;

/**
 * The Class CoreView is where the general armor of Kune it created/attached.
 */
public class CoreView extends Composite implements ICoreView {

    /**
     * Instantiates a new core view.
     * 
     * @param body
     *            the body
     */
    @Inject
    public CoreView(final Body body) {
        GWT.<WsArmorResources> create(WsArmorResources.class).style().ensureInjected();
        initWidget(body);
        body.getEntityHeader().add(new InlineLabel("Test - a, e, i, o, u"));
        body.getDocHeader().add(new InlineLabel("doc title"));
    }
}
