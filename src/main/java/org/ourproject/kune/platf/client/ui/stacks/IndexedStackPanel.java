/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.ui.stacks;

import java.util.ArrayList;
import java.util.Iterator;

import org.ourproject.kune.platf.client.ui.CustomStackPanel;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.platf.client.ui.UIConstants;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class IndexedStackPanel extends CustomStackPanel {
    private final ArrayList stackList;

    public IndexedStackPanel() {
        stackList = new ArrayList();
    }

    /* Stack items */

    public VerticalPanel addStackItem(final String name, final String title, final boolean countVisible) {
        return addStackItem(name, title, null, null, countVisible);
    }

    public VerticalPanel addStackItem(final String name, final String title, final AbstractImagePrototype icon,
            final String iconAlign, final boolean countVisible) {
        ScrollPanel siSP = new ScrollPanel();
        VerticalPanel siVP = new VerticalPanel();
        siSP.add(siVP);
        StackItem stackItem = new StackItem(name, title, icon, iconAlign, countVisible);
        add(siSP, stackItem.getHtml(), true);
        stackList.add(stackItem);
        return siVP;
    }

    public void removeStackItem(final String name) {
        int idx = indexOf(name);
        remove(idx);
        stackList.remove(idx);
    }

    public void showStackItem(final String name) {
        int idx = indexOf(name);
        showStack(idx);
    }

    public int indexOf(final String name) {
        final Iterator iter = stackList.iterator();
        int i = 0;
        while (iter.hasNext()) {
            final StackItem stackItem = (StackItem) iter.next();
            if (stackItem.getName() == name) {
                return i;
            } else {
                i++;
            }
        }
        return -1;
    }

    public boolean containsItem(final String name) {
        return indexOf(name) == -1 ? false : true;
    }

    public StackItem getItem(final int indexOfStackItem) {
        return (StackItem) stackList.get(indexOfStackItem);
    }

    public StackItem getItem(final String name) {
        return (StackItem) stackList.get(indexOf(name));
    }

    public void clear() {
        for (Iterator iterator = stackList.iterator(); iterator.hasNext();) {
            StackItem item = (StackItem) iterator.next();
            item.clear();
        }
        stackList.clear();
        super.clear();
    }

    /*
     * Unattached stack item. We update the stack using this object and
     * generating the Html with getHtml
     */

    public class StackItem {
        private String text;
        private String title;
        private AbstractImagePrototype icon;
        private String iconAlign;
        private boolean countVisible;
        private final ArrayList subItems;

        public StackItem(final String text, final String title, final AbstractImagePrototype icon,
                final String iconAlign, final boolean countVisible) {
            this.text = text;
            this.title = title;
            this.icon = icon;
            this.iconAlign = iconAlign;
            this.countVisible = countVisible;
            subItems = new ArrayList();
        }

        public int getCount() {
            return subItems.size();
        }

        public int indexOfSubItem(final String name) {
            return subItems.indexOf(name);
        }

        public void addSubItem(final String name) {
            subItems.add(name);
        }

        public void removeSubItem(final String name) {
            subItems.remove(name);
        }

        public void clear() {
            subItems.clear();
        }

        public String getName() {
            return text;
        }

        public void setText(final String text) {
            this.text = text;
        }

        public void setTitle(final String title) {
            this.title = title;
        }

        public void setIcon(final AbstractImagePrototype icon) {
            this.icon = icon;
        }

        public void setIconAlign(final String iconAlign) {
            this.iconAlign = iconAlign;
        }

        public boolean isCountVisible() {
            return countVisible;
        }

        public void setCountVisible(final boolean visible) {
            this.countVisible = visible;
        }

        public String getHtml() {
            Element div = DOM.createDiv();
            Element labelElem = DOM.createSpan();
            Element iconElement = null;
            KuneUiUtils.setQuickTip(labelElem, title);
            boolean insertIcon = icon != null && iconAlign != null;
            if (insertIcon) {
                iconElement = icon.createImage().getElement();
                KuneUiUtils.setQuickTip(iconElement, title);
                if (iconAlign == UIConstants.ICON_HORIZ_ALIGN_LEFT) {
                    DOM.appendChild(div, iconElement);
                }
            }
            DOM.setInnerText(labelElem, text + (countVisible ? " (" + subItems.size() + ")" : ""));
            DOM.appendChild(div, labelElem);
            if (insertIcon) {
                if (iconAlign == UIConstants.ICON_HORIZ_ALIGN_RIGHT) {
                    DOM.appendChild(div, iconElement);
                }
            }
            return div.toString();
        }
    }

}
