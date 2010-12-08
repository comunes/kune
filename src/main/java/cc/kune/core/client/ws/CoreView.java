package cc.kune.core.client.ws;

import org.ourproject.kune.ws.armor.client.Body;
import org.ourproject.kune.ws.armor.client.resources.WsArmorResources;

import cc.kune.core.client.ws.CorePresenter.ICoreView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.inject.Inject;

public class CoreView extends Composite implements ICoreView {

    @Inject
    public CoreView(final Body body) {
        GWT.<WsArmorResources> create(WsArmorResources.class).style().ensureInjected();
        initWidget(body);
    }
}
