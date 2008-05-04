package org.ourproject.kune.platf.client.ui.dialogs;

import org.ourproject.kune.platf.client.services.Kune;

import com.google.gwt.user.client.ui.Label;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public class InfoDialog extends BasicDialog {
    public InfoDialog(final String title, final String header, final String text, final boolean modal,
	    final boolean autoScroll, final int width, final int height) {
	super(title, modal, autoScroll, width, height);
	final Button okButton = new Button(Kune.I18N.t("Ok"));
	okButton.addListener(new ButtonListenerAdapter() {
	    public void onClick(final Button button, final EventObject e) {
		hide();
	    }
	});
	final Panel panel = new Panel();
	panel.setBorder(false);
	panel.setHeader(false);
	panel.setPaddings(20);

	final Label headerLabel = new Label(header);
	final Label textLabel = new Label(text);

	panel.add(headerLabel);
	panel.add(new Label());
	panel.add(textLabel);

	add(okButton);
	show();
    }
}
