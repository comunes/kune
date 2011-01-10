package cc.kune.core.client.ws;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

public class CorePlaceManager extends PlaceManagerImpl {

    @Inject
    public CorePlaceManager(final EventBus eventBus, final TokenFormatter tokenFormatter) {
        super(eventBus, tokenFormatter);
    }

    @Override
    public void revealDefaultPlace() {
        revealPlace(new PlaceRequest(CorePresenter.HOME_TOKEN));
    }
}
