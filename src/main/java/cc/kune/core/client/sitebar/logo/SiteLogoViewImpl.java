package cc.kune.core.client.sitebar.logo;

import cc.kune.core.client.sitebar.logo.SiteLogoPresenter.SiteLogoView;
import cc.kune.core.ws.armor.client.WsArmor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class SiteLogoViewImpl extends ViewWithUiHandlers<SiteLogoUiHandlers> implements SiteLogoView {

    private static SiteLogoViewImplUiBinder uiBinder = GWT.create(SiteLogoViewImplUiBinder.class);

    interface SiteLogoViewImplUiBinder extends UiBinder<Widget, SiteLogoViewImpl> {
    }

    @Inject
    public SiteLogoViewImpl(WsArmor armor, PlaceManager placeManager) {
        armor.getSitebar().add(uiBinder.createAndBindUi(this));
    }

    @UiField
    Image logo;

    @UiHandler("logo")
    void onLogoClick(ClickEvent event) {
        if (getUiHandlers() != null) {
            getUiHandlers().onClick();
        }
    }

    @Override
    public Widget asWidget() {
        return logo;
    }

    @Override
    public void setSiteLogoUrl(String siteLogoUrl) {
        logo.setUrl(siteLogoUrl);
    }
}
