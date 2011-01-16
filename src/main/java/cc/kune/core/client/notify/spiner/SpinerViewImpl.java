package cc.kune.core.client.notify.spiner;

import cc.kune.core.client.notify.spiner.SpinerPresenter.SpinerView;

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

    interface SpinerViewImplUiBinder extends UiBinder<Widget, SpinerViewImpl> {
    }

    private static SpinerViewImplUiBinder uiBinder = GWT.create(SpinerViewImplUiBinder.class);

    @UiField
    Image img;

    @UiField
    InlineLabel label;

    @UiField
    HorizontalPanel panel;
    private final PopupPanel popup;
    Widget widget;

    @Inject
    public SpinerViewImpl(EventBus eventBus) {
        super(eventBus);
        widget = uiBinder.createAndBindUi(this);
        popup = new PopupPanel(false, false);
        popup.add(widget);
        popup.setPopupPosition(0, 0);
        popup.setStyleName("k-spiner-popup");
        popup.show();
    }

    @Override
    public Widget asWidget() {
        return popup;
    }

    @Override
    public void fade() {
        popup.hide();
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
