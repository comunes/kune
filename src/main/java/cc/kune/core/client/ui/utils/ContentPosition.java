/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.client.ui.utils;

import org.cobogw.gwt.user.client.CSS;

import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.inject.Inject;

public class ContentPosition {

    public static final String CENTER = "center";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    private static I18nTranslationService i18n;
    private static Object[][] positionObjs;
    public static final String[][] positions = { new String[] { LEFT }, new String[] { CENTER }, new String[] { RIGHT } };

    public synchronized static Object[][] getPositions() {
        if (positionObjs == null) {
            final String[][] values = positions;
            positionObjs = new Object[values.length][1];
            int i = 0;
            for (final String[] position : values) {
                final Object[] obj = new Object[] { position[0], i18n.t(position[0]) };
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
            final com.google.gwt.user.client.Element divEl = DOM.createDiv();
            CSS.setProperty(divEl, CSS.A.TEXT_ALIGN, position);
            divEl.setInnerHTML(element.getString());
            return divEl;
        } else {
            CSS.setProperty(element, CSS.A.FLOAT, position);
            return element;
        }
    }

    public static String setPosition(final String elementCode, final boolean wraptext, final String position) {
        final HTML html = new HTML(elementCode);
        html.removeStyleName("gwt-HTML");
        return setPosition(html.getElement(), wraptext, position).getString();
    }

    @Inject
    public ContentPosition(final I18nTranslationService i18n) {
        ContentPosition.i18n = i18n;
    }
}
