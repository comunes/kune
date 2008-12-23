/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.ToolTip;
import com.gwtext.client.widgets.form.Field;

public class KuneUiUtils {

    public enum IconPosition {
        left, right
    }

    public static void focusOnField(final Field field) {
        new Timer() {
            @Override
            public void run() {
                field.focus(true);
            }
        }.schedule(50);
    }

    public static String genQuickTipLabel(final String labelText, final String tipTitle, final String tipText) {
        String tipHtml = "<span style=\"vertical-align: middle;\" ext:qtip=\"" + tipText + "\"";
        if (tipTitle != null && tipTitle.length() > 0) {
            tipHtml += " ext:qtitle=\"" + tipTitle + "\"";
        }
        tipHtml += ">";
        tipHtml += labelText;
        tipHtml += "</span>";
        return tipHtml;
    }

    public static String genQuickTipLabel(final String labelText, final String tipTitle, final String tipText,
            final AbstractImagePrototype icon, IconPosition iconPosition) {
        String tipHtml = "<span style=\"vertical-align: middle;\" ext:qtip=\"" + tipText + "\"";
        if (tipTitle != null && tipTitle.length() > 0) {
            tipHtml += " ext:qtitle=\"" + tipTitle + "\"";
        }
        tipHtml += ">";
        final Image iconImg = new Image();
        icon.applyTo(iconImg);
        iconImg.setStyleName("vamiddle");
        // setQuickTip(iconImg, tipText, tipTitle);

        ToolTip tooltip = new ToolTip();
        tooltip.setHtml(tipText);
        tooltip.setWidth(250);
        tooltip.applyTo(iconImg.getElement());

        switch (iconPosition) {
        case left:
            tipHtml += iconImg.toString();
            tipHtml += "&nbsp;";
            tipHtml += labelText;
            break;
        case right:
            tipHtml += labelText;
            tipHtml += "&nbsp;";
            tipHtml += iconImg.toString();
            break;
        }

        tipHtml += "</span>";
        return tipHtml;
    }

    public static String genQuickTipWithImage(String imageResizedUrl, int size) {
        return new Image(imageResizedUrl).toString();
    }

    /**
     * Use widget.setTitle if you don't want to use extjs (but remember that
     * title is limited to 64chars).
     * 
     * Bug: extjs quicktips don't work with images or span inside divs (like
     * pushbutton). See: http://extjs.com/forum/showthread.php?t=22293
     * http://extjs.com/forum/showthread.php?t=5200
     * 
     * @param element
     * @param tip
     */
    public static void setQuickTip(final Element element, final String tipText) {
        setQuickTip(element, tipText, null);
    }

    public static void setQuickTip(final Element element, final String tipText, final String tipTitle) {
        // If we don't want to use ext, use 'title' instead (limited to 64
        // chars)
        if (tipText == null || tipText.length() == 0) {
            DOM.removeElementAttribute(element, "ext:qtip");
        } else {
            ToolTip tip = new ToolTip();
            tip.setHtml(tipText);
            if (tipTitle != null) {
                tip.setTitle(tipTitle);
            }
            tip.applyTo(element);
            // check size here?
            // String newTip = "<div style='min-width: 35px'>" + tip + "</div>";
        }
    }

    public static void setQuickTip(final Widget widget, final String tip) {
        setQuickTip(widget.getElement(), tip);
    }

    public static void setQuickTip(final Widget widget, final String tip, final String tipTitle) {
        setQuickTip(widget.getElement(), tip, tipTitle);
    }

}
