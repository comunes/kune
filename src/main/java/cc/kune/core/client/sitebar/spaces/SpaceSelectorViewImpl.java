package cc.kune.core.client.sitebar.spaces;

import cc.kune.core.client.sitebar.spaces.SpaceSelectorPresenter.SpaceSelectorView;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.wspace.client.WsArmor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class SpaceSelectorViewImpl extends ViewWithUiHandlers<SpaceSelectorUiHandlers> implements SpaceSelectorView {
    private static SpaceSelectorViewImplUiBinder uiBinder = GWT.create(SpaceSelectorViewImplUiBinder.class);
    @UiField
    ToggleButton homeButton;
    @UiField
    ToggleButton userButton;
    @UiField
    ToggleButton groupButton;
    @UiField
    ToggleButton publicButton;
    @UiField
    HorizontalPanel panel;

    interface SpaceSelectorViewImplUiBinder extends UiBinder<Widget, SpaceSelectorViewImpl> {
    }

    @Inject
    public SpaceSelectorViewImpl(WsArmor armor, I18nTranslationService i18n) {
        armor.getSitebar().insert(uiBinder.createAndBindUi(this), 1);
        homeButton.setTitle(i18n.t("Home page of this site"));
        userButton.setTitle(i18n.t("User space: Waves (aka docs) in which you participate"));
        groupButton.setTitle(i18n.t("Group & personal space: Where you can create and publish contents"));
        publicButton.setTitle(i18n.t("Public space: This is how the rest of public see your published works"));
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setHomeBtnDown(boolean down) {
        homeButton.setDown(down);
    }

    @Override
    public void setUserBtnDown(boolean down) {
        userButton.setDown(down);
    }

    @Override
    public void setGroupBtnDown(boolean down) {
        groupButton.setDown(down);
    }

    @Override
    public void setPublicBtnDown(boolean down) {
        publicButton.setDown(down);
    }

    @UiHandler("homeButton")
    void onHomeSpaceClick(ClickEvent event) {
        getUiHandlers().onHomeSpaceSelect();
    }

    @UiHandler("userButton")
    void onUserSpaceClick(ClickEvent event) {
        getUiHandlers().onUserSpaceSelect();
    }

    @UiHandler("groupButton")
    void onGroupSpaceClick(ClickEvent event) {
        getUiHandlers().onGroupSpaceSelect();
    }

    @UiHandler("publicButton")
    void onPublicSpaceClick(ClickEvent event) {
        getUiHandlers().onPublicSpaceClick();
    }

}
