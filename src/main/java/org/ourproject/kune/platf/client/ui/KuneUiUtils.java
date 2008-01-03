package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class KuneUiUtils {

    public static void setQuickTip(final Widget widget, final String tip) {
        setQuickTip(widget.getElement(), tip);
    }

    public static void setQuickTip(final Element element, final String tip) {
        // If we don't want to use ext, use 'title' instead (limited to 64
        // chars)
        DOM.setElementAttribute(element, "ext:qtip", tip);
    }

}
