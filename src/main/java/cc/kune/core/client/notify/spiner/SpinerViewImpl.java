package cc.kune.core.client.notify.spiner;

import cc.kune.core.client.notify.spiner.SpinerPresenter.SpinerView;
import cc.kune.wspace.client.WsArmor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import com.gwtplatform.mvp.client.UiHandlers;

public class SpinerViewImpl extends PopupViewWithUiHandlers<UiHandlers> implements SpinerView {

    private final PopupPanel popup;

    @Inject
    protected SpinerViewImpl(EventBus eventBus, WsArmor armor) {
        super(eventBus);
        widget = uiBinder.createAndBindUi(this);
        popup = new PopupPanel(false, false);
        popup.add(widget);
        popup.setPopupPosition(0, 0);
        popup.setStyleName("k-spiner-popup");
        popup.show();
    }

    private static SpinerViewImplUiBinder uiBinder = GWT.create(SpinerViewImplUiBinder.class);

    interface SpinerViewImplUiBinder extends UiBinder<Widget, SpinerViewImpl> {
    }

    @UiField
    HorizontalPanel panel;
    @UiField
    InlineLabel label;
    @UiField
    Image img;
    Widget widget;

    @Override
    public Widget asWidget() {
        return popup;
    }

    @Override
    public void fade() {
        // popup.hide();
    }

    @Override
    public void show(String message) {
        if (message == null || message.isEmpty()) {
            label.setText("");
        } else {
            label.setText(message);
        }
        popup.show();
    }
}
