package cc.kune.common.client.ui;

import javax.annotation.Nonnull;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class MaskWidget extends PopupPanel implements MaskWidgetView {

    interface MaskWidgetUiBinder extends UiBinder<Widget, MaskWidget> {
    }
    private static MaskWidgetUiBinder uiBinder = GWT.create(MaskWidgetUiBinder.class);
    @UiField
    FlowPanel flow;
    @UiField
    Image icon;
    @UiField
    Label label;

    @UiField
    SimplePanel mainPanel;

    public MaskWidget() {
        super(false, false);
        add(uiBinder.createAndBindUi(this));
        setStyleName("k-mask");
    }

    @Override
    @Nonnull
    public void mask(final IsWidget widget) {
        mask(widget, "");
    }

    @Override
    @Nonnull
    public void mask(final IsWidget widget, final String message) {
        label.setText(message);
        setPopupPositionAndShow(new PositionCallback() {
            @Override
            public void setPosition(final int offsetWidth, final int offsetHeight) {
                final Widget asWidget = widget.asWidget();
                final int w = asWidget.getOffsetWidth();
                final int h = asWidget.getOffsetHeight();
                MaskWidget.this.setPopupPosition(asWidget.getAbsoluteLeft(), asWidget.getAbsoluteTop());
                getElement().getStyle().setWidth(w, Unit.PX);
                getElement().getStyle().setHeight(h, Unit.PX);
                flow.getElement().getStyle().setTop((h - flow.getOffsetHeight()) / 2d, Unit.PX);
                flow.getElement().getStyle().setLeft((w - flow.getOffsetWidth()) / 2d, Unit.PX);
            }
        });
    }

    @Override
    public void unMask() {
        hide();
    }

}
