package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;

public interface AbstractToolbar {

    void add(final Widget widget);

    void add(final Widget widget, VerticalAlignmentConstant valign);

    Widget addFill();

    Widget addSeparator();

    Widget addSpacer();

    int getOffsetHeight();

    void insert(final Widget widget, int position);

    boolean isAttached();

    void remove(final Widget widget);

    void removeAll();

    void setBlankStyle();

    void setHeight(String height);

    void setNormalStyle();

}