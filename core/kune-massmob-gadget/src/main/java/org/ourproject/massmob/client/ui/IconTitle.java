package org.ourproject.massmob.client.ui;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class IconTitle extends Composite implements HasWidgets {

    interface IconTitleUiBinder extends UiBinder<Widget, IconTitle> {
    }

    private static IconTitleUiBinder uiBinder = GWT.create(IconTitleUiBinder.class);

    @UiField
    Image icon;
    @UiField
    Label label;
    @UiField
    HorizontalPanel hp;
    @UiField
    FocusPanel self;

    public IconTitle(final String text) {
        initWidget(uiBinder.createAndBindUi(this));
        label.setText(text);
    }

    @Override
    public void add(final Widget w) {
        hp.add(w);
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

    public void setIcon(final ImageResource imgRes) {
        AbstractImagePrototype.create(imgRes).applyTo(icon);
    }

    public void setText(final String text) {
        label.setText(text);
    }
}
