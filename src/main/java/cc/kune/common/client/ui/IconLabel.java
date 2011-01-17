package cc.kune.common.client.ui;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class IconLabel extends Composite implements HasWidgets {

    interface IconTitleUiBinder extends UiBinder<Widget, IconLabel> {
    }

    private static IconTitleUiBinder uiBinder = GWT.create(IconTitleUiBinder.class);

    @UiField
    HTML label;
    @UiField
    HorizontalPanel hp;
    @UiField
    FocusPanel self;

    public IconLabel(final String text) {
        initWidget(uiBinder.createAndBindUi(this));
        label.setText(text);
    }

    @Override
    public void add(final Widget w) {
        hp.add(w);
    }

    public void addTextStyleName(final String style) {
        label.addStyleName(style);
    }

    @Override
    public void clear() {
        hp.clear();
    }

    public HasClickHandlers getFocus() {
        return self;
    }

    @Override
    public Iterator<Widget> iterator() {
        return hp.iterator();
    }

    @Override
    public boolean remove(final Widget w) {
        return hp.remove(w);
    }

    public void setIcon(final String imgCss) {
        label.addStyleName(imgCss);
        label.addStyleName("oc-ico-pad");
    }

    public void setLabelHtml(final String html) {
        label.setHTML(html);
    }

    public void setText(final String text) {
        label.setText(text);
    }
}
