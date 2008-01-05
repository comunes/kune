package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.MouseListener;

public interface AbstractLabel {

    public void addClickListener(final ClickListener listener);

    public void addMouseListener(final MouseListener listener);

    public String getText();

    public void onBrowserEvent(final Event event);

    public void removeClickListener(final ClickListener listener);

    public void removeMouseListener(final MouseListener listener);

    public void setText(final String text);

    public void setColor(final String color);

    public void setTitle(final String title);

    public void addStyleDependentName(String string);

    public void removeStyleDependentName(String string);

    public void setStylePrimaryName(String string);

    public void addDoubleClickListener(ClickListener listener);

    public void removeDoubleClickListener(ClickListener listener);

}