package cc.kune.core.client.sitebar.logo;

import cc.kune.core.client.sitebar.logo.SiteLogoPresenter.SiteLogoView;
import cc.kune.wspace.client.WsArmor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class SiteLogoViewImpl extends ViewWithUiHandlers<SiteLogoUiHandlers> implements SiteLogoView {

    interface SiteLogoViewImplUiBinder extends UiBinder<Widget, SiteLogoViewImpl> {
    }

    private static SiteLogoViewImplUiBinder uiBinder = GWT.create(SiteLogoViewImplUiBinder.class);

    @UiField
    Image logo;

    @Inject
    public SiteLogoViewImpl(final WsArmor armor) {
        armor.getSitebar().insert(uiBinder.createAndBindUi(this), 0);
    }

    @Override
    public Widget asWidget() {
        return logo;
    }

    @UiHandler("logo")
    void onLogoClick(final ClickEvent event) {
        if (getUiHandlers() != null) {
            getUiHandlers().onClick();
        }
    }

    @Override
    public void setSiteLogoUrl(final String siteLogoUrl) {
        logo.setUrl(siteLogoUrl);
    }
}
