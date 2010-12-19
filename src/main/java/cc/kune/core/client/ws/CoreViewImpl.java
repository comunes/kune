package cc.kune.core.client.ws;

import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.ws.armor.client.WsArmorImpl;
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

    private final WsArmorImpl armor;

    /**
     * Instantiates a new core view.
     * 
     * @param armor
     *            the body
     */
    @Inject
    public CoreViewImpl(final WsArmorImpl armor) {
        this.armor = armor;
        GWT.<CoreResources> create(CoreResources.class).css().ensureInjected();
        GWT.<WsArmorResources> create(WsArmorResources.class).style().ensureInjected();
        armor.getEntityHeader().add(new InlineLabel("Test - a, e, i, o, u"));
        armor.getDocHeader().add(new InlineLabel("doc title"));
    }

    @Override
    public Widget asWidget() {
        return armor;
    }
}
