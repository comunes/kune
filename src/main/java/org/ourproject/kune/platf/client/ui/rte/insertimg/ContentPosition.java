package org.ourproject.kune.platf.client.ui.rte.insertimg;

import org.cobogw.gwt.user.client.CSS;
import org.ourproject.kune.platf.client.i18n.Resources;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;

public class ContentPosition {

    public static final String LEFT = "left";
    public static final String CENTER = "center";
    public static final String RIGHT = "right";

    public static String[][] positions = { new String[] { LEFT }, new String[] { CENTER }, new String[] { RIGHT } };

    private static Object[][] positionObjs;

    public synchronized static Object[][] getPositions() {
        if (positionObjs == null) {
            String[][] values = positions;
            positionObjs = new Object[values.length][1];
            int i = 0;
            for (String[] position : values) {
                final Object[] obj = new Object[] { position[0], Resources.i18n.t(position[0]) };
                positionObjs[i++] = obj;
            }
        }
        return positionObjs;
    }

    public static String setCenterPosition(final String elementCode) {
        return setPosition(elementCode, false, CENTER);
    }

    public static Element setPosition(final Element element, final boolean wraptext, final String position) {
        if (!wraptext || position.equals(ContentPosition.CENTER)) {
            com.google.gwt.user.client.Element divEl = DOM.createDiv();
            CSS.setProperty(divEl, CSS.A.TEXT_ALIGN, position);
            divEl.setInnerHTML(element.getString());
            return divEl;
        } else {
            CSS.setProperty(element, CSS.A.FLOAT, position);
            return element;
        }
    }

    public static String setPosition(final String elementCode, final boolean wraptext, final String position) {
        HTML html = new HTML(elementCode);
        html.removeStyleName("gwt-HTML");
        return setPosition(html.getElement(), wraptext, position).getString();
    }
}
