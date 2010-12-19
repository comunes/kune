package cc.kune.core.client.notify;

import cc.kune.core.client.notify.SpinerPresenter.SpinerView;
import cc.kune.core.ws.armor.client.WsArmor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import com.gwtplatform.mvp.client.UiHandlers;

public class SpinerViewImpl extends PopupViewWithUiHandlers<UiHandlers> implements SpinerView {

    @Inject
    protected SpinerViewImpl(EventBus eventBus, WsArmor armor) {
        super(eventBus);
        widget = uiBinder.createAndBindUi(this);
        armor.getSitebar().add(widget);
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
        return widget;
    }

    @Override
    public void fade() {
        panel.setVisible(false);
    }

    @Override
    public void show(String message) {
        if (message == null || message.isEmpty()) {
            label.setText("Fixme");
        } else {
            label.setText(message);
        }
        panel.setVisible(true);
    }
}
