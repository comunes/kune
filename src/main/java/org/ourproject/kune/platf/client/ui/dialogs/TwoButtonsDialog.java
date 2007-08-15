package org.ourproject.kune.platf.client.ui.dialogs;

import org.ourproject.kune.workspace.client.ui.form.FormListener;

import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ButtonConfig;
import com.gwtext.client.widgets.LayoutDialog;
import com.gwtext.client.widgets.LayoutDialogConfig;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;

public class TwoButtonsDialog {

    private final LayoutDialog dialog;

    public TwoButtonsDialog(final String caption, final String firstButton, final String secondButton,
	    final boolean modal, final boolean minimizable, final int width, final int height, final int minWidth,
	    final int minHeight, final FormListener listener) {

	dialog = new LayoutDialog(new LayoutDialogConfig() {
	    {
		// Param values
		setTitle(caption);
		setModal(modal);
		setWidth(width);
		setHeight(height);
		setMinWidth(minWidth);
		setMinHeight(minHeight);
		setCollapsible(minimizable);

		// Def values
		setShadow(true);
	    }
	}, new LayoutRegionConfig());

	dialog.addButton(new Button(firstButton, new ButtonConfig() {
	    {
		setText(firstButton);
		setButtonListener(new ButtonListenerAdapter() {
		    public void onClick(final Button button, final EventObject e) {
			listener.onAccept();
		    }
		});
	    }
	}));

	dialog.addButton(new Button(secondButton, new ButtonConfig() {
	    {
		setText(secondButton);
		setButtonListener(new ButtonListenerAdapter() {
		    public void onClick(final Button button, final EventObject e) {
			listener.onCancel();
		    }
		});
	    }
	}));
    }

    public void add(final Widget widget) {
	BorderLayout layout = dialog.getLayout();
	ContentPanel contentPanel = new ContentPanel();
	contentPanel.add(widget);
	layout.add(contentPanel);
    }

    public void show() {
	dialog.show();
    }

    public void center() {
	dialog.center();
    }

    public void hide() {
	dialog.hide();
    }
}
