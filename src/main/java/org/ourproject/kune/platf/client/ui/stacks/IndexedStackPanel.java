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
package org.ourproject.kune.platf.client.ui.stacks;

import java.util.ArrayList;
import java.util.Iterator;

import org.ourproject.kune.platf.client.ui.AbstractLabel;
import org.ourproject.kune.platf.client.ui.IconLabel;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.platf.client.ui.LabelWrapper;
import org.ourproject.kune.platf.client.ui.UIConstants;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class IndexedStackPanel extends StackPanel {
    public class StackItem {
	private String text;
	private String title;
	private AbstractImagePrototype icon;
	private String iconAlign;
	private boolean countVisible;
	private final ArrayList<String> subItems;

	public StackItem(final String text, final String title, final AbstractImagePrototype icon,
		final String iconAlign, final boolean countVisible) {
	    this.text = text;
	    this.title = title;
	    this.icon = icon;
	    this.iconAlign = iconAlign;
	    this.countVisible = countVisible;
	    subItems = new ArrayList<String>();
	}

	public void addSubItem(final String name) {
	    subItems.add(name);
	}

	public void clear() {
	    subItems.clear();
	}

	public int getCount() {
	    return subItems.size();
	}

	public String getHtml() {
	    final boolean insertIcon = icon != null && iconAlign != null;
	    final String textWithCount = text + (countVisible ? " (" + subItems.size() + ")" : "");
	    AbstractLabel label;
	    if (insertIcon) {
		if (iconAlign == UIConstants.ICON_HORIZ_ALIGN_RIGHT) {
		    label = new IconLabel(textWithCount, icon);
		} else {
		    label = new IconLabel(textWithCount, icon);
		}
	    } else {
		label = new LabelWrapper(textWithCount);
	    }
	    KuneUiUtils.setQuickTip(label.getElement(), title);
	    return label.toString();
	}

	public String getName() {
	    return text;
	}

	public int indexOfSubItem(final String name) {
	    return subItems.indexOf(name);
	}

	public boolean isCountVisible() {
	    return countVisible;
	}

	public void removeSubItem(final String name) {
	    subItems.remove(name);
	}

	public void setCountVisible(final boolean visible) {
	    this.countVisible = visible;
	}

	public void setIcon(final AbstractImagePrototype icon) {
	    this.icon = icon;
	}

	public void setIconAlign(final String iconAlign) {
	    this.iconAlign = iconAlign;
	}

	public void setText(final String text) {
	    this.text = text;
	}

	public void setTitle(final String title) {
	    this.title = title;
	}
    }

    private final ArrayList<StackItem> stackList;

    /* Stack items */

    public IndexedStackPanel() {
	stackList = new ArrayList<StackItem>();
    }

    public VerticalPanel addStackItem(final String name, final String title, final AbstractImagePrototype icon,
	    final String iconAlign, final boolean countVisible) {
	final ScrollPanel siSP = new ScrollPanel();
	final VerticalPanel siVP = new VerticalPanel();
	siSP.add(siVP);
	final StackItem stackItem = new StackItem(name, title, icon, iconAlign, countVisible);
	add(siSP, stackItem.getHtml(), true);
	stackList.add(stackItem);
	return siVP;
    }

    public VerticalPanel addStackItem(final String name, final String title, final boolean countVisible) {
	return addStackItem(name, title, null, null, countVisible);
    }

    public void clear() {
	for (final Iterator<StackItem> iterator = stackList.iterator(); iterator.hasNext();) {
	    final StackItem item = iterator.next();
	    item.clear();
	}
	stackList.clear();
	super.clear();
    }

    public boolean containsItem(final String name) {
	return indexOf(name) == -1 ? false : true;
    }

    public StackItem getItem(final int indexOfStackItem) {
	return stackList.get(indexOfStackItem);
    }

    public StackItem getItem(final String name) {
	return stackList.get(indexOf(name));
    }

    public int indexOf(final String name) {
	final Iterator<StackItem> iter = stackList.iterator();
	int i = 0;
	while (iter.hasNext()) {
	    final StackItem stackItem = iter.next();
	    if (stackItem.getName() == name) {
		return i;
	    } else {
		i++;
	    }
	}
	return -1;
    }

    public void removeStackItem(final String name) {
	final int idx = indexOf(name);
	remove(idx);
	stackList.remove(idx);
    }

    /*
     * Unattached stack item. We update the stack using this object and
     * generating the Html with getHtml
     */

    public void showStackItem(final String name) {
	final int idx = indexOf(name);
	showStack(idx);
    }

}
