package cc.kune.core.client.ws;

import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.StateManagerDefault;
import cc.kune.gspace.client.WsArmorImpl;
import cc.kune.gspace.client.resources.WsArmorResources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
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
    public CoreViewImpl(final WsArmorImpl armor, StateManagerDefault stateManager) {
        this.armor = armor;
        GWT.<CoreResources> create(CoreResources.class).css().ensureInjected();
        GWT.<WsArmorResources> create(WsArmorResources.class).style().ensureInjected();
        History.addValueChangeHandler(stateManager);
    }

    @Override
    public Widget asWidget() {
        return armor;
    }
}
