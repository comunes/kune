
package org.ourproject.kune.platf.client.ui.form;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AbstractFormPanel extends VerticalPanel implements FormView {
    private final Button btnOk;
    private final Button btnCancel;
    protected final VerticalPanel content;

    public AbstractFormPanel(final FormListener listener) {
	content = new VerticalPanel();
	add(content);

	btnOk = new Button("", new ClickListener() {
	    public void onClick(final Widget sender) {
		listener.onAccept();
	    }
	});
	btnCancel = new Button("", new ClickListener() {
	    public void onClick(final Widget sender) {
		listener.onCancel();
	    }
	});

	FlowPanel controls = new FlowPanel();
	controls.add(btnOk);
	controls.add(btnCancel);
	add(controls);
    }

    public void addRow(final String label, final Widget widget) {
	HorizontalPanel panel = new HorizontalPanel();
	panel.add(new Label(label));
	panel.add(widget);
	content.add(panel);
    }

    public void setCommandLabels(final String acceptLabel, final String cancelLabel) {
	btnOk.setText(acceptLabel);
	btnCancel.setText(cancelLabel);
    }
}
