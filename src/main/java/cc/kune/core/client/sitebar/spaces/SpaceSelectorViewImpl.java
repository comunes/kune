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
    interface SpaceSelectorViewImplUiBinder extends UiBinder<Widget, SpaceSelectorViewImpl> {
    }
    private static SpaceSelectorViewImplUiBinder uiBinder = GWT.create(SpaceSelectorViewImplUiBinder.class);
    @UiField
    ToggleButton groupButton;
    @UiField
    ToggleButton homeButton;
    @UiField
    HorizontalPanel panel;
    @UiField
    ToggleButton publicButton;

    @UiField
    ToggleButton userButton;

    @Inject
    public SpaceSelectorViewImpl(final WsArmor armor, final I18nTranslationService i18n) {
        armor.getSitebar().insert(uiBinder.createAndBindUi(this), 0);
        homeButton.setTitle(i18n.t("Home page of this site"));
        userButton.setTitle(i18n.t("User space: Waves (aka docs) in which you participate"));
        groupButton.setTitle(i18n.t("Group and personal space: Where you can create and publish contents"));
        publicButton.setTitle(i18n.t("Public space: This is how the rest of public see your published works"));
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @UiHandler("groupButton")
    void onGroupSpaceClick(final ClickEvent event) {
        getUiHandlers().onGroupSpaceSelect();
    }

    @UiHandler("homeButton")
    void onHomeSpaceClick(final ClickEvent event) {
        getUiHandlers().onHomeSpaceSelect();
    }

    @UiHandler("publicButton")
    void onPublicSpaceClick(final ClickEvent event) {
        getUiHandlers().onPublicSpaceClick();
    }

    @UiHandler("userButton")
    void onUserSpaceClick(final ClickEvent event) {
        getUiHandlers().onUserSpaceSelect();
    }

    @Override
    public void setGroupBtnDown(final boolean down) {
        groupButton.setDown(down);
    }

    @Override
    public void setHomeBtnDown(final boolean down) {
        homeButton.setDown(down);
    }

    @Override
    public void setPublicBtnDown(final boolean down) {
        publicButton.setDown(down);
    }

    @Override
    public void setUserBtnDown(final boolean down) {
        userButton.setDown(down);
    }

}
