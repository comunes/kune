
package org.ourproject.kune.platf.client.ui.dialogs;

import com.gwtext.client.widgets.Window;

public class BasicDialog extends Window {

    public BasicDialog(final String caption, final boolean modal, final boolean autoScroll) {
        setAutoWidth(true);
        // Param values
        setTitle(caption);
        setModal(modal);
        setAutoScroll(autoScroll);
        // Def values
        setShadow(true);
        setPlain(true);
        setClosable(true);
        setCollapsible(true);
        setResizable(true);
        setCloseAction(Window.HIDE);
    }

    public BasicDialog(final String caption, final boolean modal) {
        this(caption, modal, false);
    }

    public BasicDialog(final String caption, final boolean modal, final boolean autoScroll, final int width,
            final int height, final int minWidth, final int minHeight) {
        this(caption, modal, autoScroll);
        setAutoWidth(false);
        // Param values
        setWidth(width);
        setHeight(height);
        setMinWidth(minWidth);
        setMinHeight(minHeight);
    }

    public BasicDialog(final String caption, final boolean modal, final boolean autoScroll, final int width,
            final int height) {
        this(caption, modal, autoScroll, width, height, width, height);
    }

}
