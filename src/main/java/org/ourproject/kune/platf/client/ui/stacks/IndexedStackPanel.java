package org.ourproject.kune.platf.client.ui.stacks;

import java.util.ArrayList;
import java.util.Iterator;

import org.ourproject.kune.platf.client.ui.AbstractLabel;
import org.ourproject.kune.platf.client.ui.IconLabel;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.platf.client.ui.LabelWrapper;
import org.ourproject.kune.platf.client.ui.UIConstants;
import org.ourproject.kune.platf.client.ui.gwtcustom.CustomStackPanel;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class IndexedStackPanel extends CustomStackPanel {
    private final ArrayList<StackItem> stackList;

    public IndexedStackPanel() {
        stackList = new ArrayList<StackItem>();
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

    public boolean containsItem(final String name) {
        return indexOf(name) == -1 ? false : true;
    }

    public StackItem getItem(final int indexOfStackItem) {
        return stackList.get(indexOfStackItem);
    }

    public StackItem getItem(final String name) {
        return stackList.get(indexOf(name));
    }

    public void clear() {
        for (Iterator<StackItem> iterator = stackList.iterator(); iterator.hasNext();) {
            StackItem item = iterator.next();
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
            boolean insertIcon = icon != null && iconAlign != null;
            String textWithCount = text + (countVisible ? " (" + subItems.size() + ")" : "");
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
    }

}
