package cc.kune.common.client.tooltip;

import cc.kune.common.client.utils.TextUtils;
import cc.kune.common.client.utils.TimerWrapper;
import cc.kune.common.client.utils.TimerWrapper.Executer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class Tooltip extends PopupPanel {

    interface TooltipUiBinder extends UiBinder<Widget, Tooltip> {
    }

    private static TooltipUiBinder uiBinder = GWT.create(TooltipUiBinder.class);

    public static Tooltip to(final Widget widget, final String text) {
        if (TextUtils.notEmpty(text)) {
            final Tooltip tip = new Tooltip();
            tip.to(widget);
            tip.setText(text);
            return tip;
        }
        return null;
    }

    public static Tooltip to(final Widget widget, final Widget withContent) {
        final Tooltip tip = new Tooltip();
        tip.to(widget);
        tip.setContent(withContent);
        return tip;
    }
    @UiField
    HTMLPanel arrow;
    @UiField
    HTMLPanel arrowBorder;
    @UiField
    FlowPanel content;
    @UiField
    FlowPanel flow;
    private Widget ofWidget;
    private final TooltipTimers timers;
    @UiField
    InlineLabel title;
    @UiField
    HTMLPanel tooltip;

    public Tooltip() {
        super.add(uiBinder.createAndBindUi(this));
        super.setStyleName("k-tooltip-no-chrome");
        super.setAutoHideEnabled(false);
        super.setAnimationEnabled(false);
        final TimerWrapper overTimer = new TimerWrapper();
        overTimer.configure(new Executer() {
            @Override
            public void execute() {
                show();
            }
        });
        final Executer hideExecuter = new Executer() {
            @Override
            public void execute() {
                hide();
            }
        };
        final TimerWrapper outTimer = new TimerWrapper();
        final TimerWrapper securityTimer = new TimerWrapper();
        outTimer.configure(hideExecuter);
        securityTimer.configure(hideExecuter);
        timers = new TooltipTimers(overTimer, outTimer, securityTimer);
    }

    protected int getHeight() {
        return tooltip.getElement().getOffsetHeight();
    }

    protected int getWidth() {
        return tooltip.getElement().getOffsetWidth();
    }

    private void setContent(final Widget widget) {
        content.clear();
        content.add(widget);
    }

    public void setText(final String text) {
        content.clear();
        content.add(new Label(text));
    }

    @Override
    public void show() {
        if (!Tooltip.this.isShowing()) {
            Tooltip.super.show();
            Tooltip.this.showAt(TooltipPositionCalculator.calculate(Window.getClientWidth(), Window.getClientHeight(),
                    ofWidget.getAbsoluteLeft(), ofWidget.getAbsoluteTop(), ofWidget.getOffsetWidth(),
                    ofWidget.getOffsetHeight(), Tooltip.this.getWidth(), Tooltip.this.getHeight()));
            if (tooltip.getOffsetWidth() > 430) {
                tooltip.getElement().getStyle().setWidth(430, Unit.PX);
            } else {
                tooltip.getElement().getStyle().clearWidth();
            }
        }
    }

    protected void showAt(final TooltipPosition position) {
        this.setPopupPosition(position.getLeft(), position.getTop());
        switch (position.getArrowPosition()) {
        case N:
        case NW:
        case NE:
            arrow.getElement().getStyle().setLeft(position.getArrowLeft(), Unit.PX);
            arrow.getElement().getStyle().setTop(position.getArrowTop() + 3, Unit.PX);
            arrowBorder.getElement().getStyle().setLeft(position.getArrowLeft(), Unit.PX);
            arrowBorder.getElement().getStyle().setTop(position.getArrowTop() + 1, Unit.PX);
            arrow.getElement().removeClassName("k-tooltip-arrow-s");
            arrow.getElement().addClassName("k-tooltip-arrow-n");
            arrowBorder.getElement().removeClassName("k-tooltip-arrow-border-s");
            arrowBorder.getElement().addClassName("k-tooltip-arrow-border-n");
            break;
        case S:
        case SE:
        case SW:
            arrow.getElement().getStyle().setLeft(position.getArrowLeft(), Unit.PX);
            arrow.getElement().getStyle().setBottom(position.getArrowTop() + 2, Unit.PX);
            arrowBorder.getElement().getStyle().setLeft(position.getArrowLeft(), Unit.PX);
            arrowBorder.getElement().getStyle().setBottom(position.getArrowTop(), Unit.PX);
            arrow.getElement().addClassName("k-tooltip-arrow-s");
            arrow.getElement().removeClassName("k-tooltip-arrow-n");
            arrowBorder.getElement().addClassName("k-tooltip-arrow-border-s");
            arrowBorder.getElement().removeClassName("k-tooltip-arrow-border-n");
            break;
        }
    }

    private void to(final Widget ofWidget) {
        this.ofWidget = ofWidget;
        ofWidget.addDomHandler(new MouseOverHandler() {
            @Override
            public void onMouseOver(final MouseOverEvent event) {
                timers.onOver();
            }
        }, MouseOverEvent.getType());
        ofWidget.addDomHandler(new FocusHandler() {
            @Override
            public void onFocus(final FocusEvent event) {
                // timers.onOver();
            }
        }, FocusEvent.getType());
        ofWidget.addDomHandler(new BlurHandler() {
            @Override
            public void onBlur(final BlurEvent event) {
                timers.onOut();
            }
        }, BlurEvent.getType());
        ofWidget.addDomHandler(new MouseOutHandler() {
            @Override
            public void onMouseOut(final MouseOutEvent event) {
                timers.onOut();
            }
        }, MouseOutEvent.getType());
    }
}
