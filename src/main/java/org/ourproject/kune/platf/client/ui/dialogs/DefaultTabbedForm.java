package org.ourproject.kune.platf.client.ui.dialogs;

import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.layout.FormLayout;

public class DefaultTabbedForm {
    private final FormPanel formPanel;
    private final TabPanel tabPanel;

    public DefaultTabbedForm(final int width, final int labelWidth) {
	formPanel = new FormPanel();
	formPanel.setFrame(true);
	formPanel.setLabelWidth(labelWidth);
	formPanel.setBorder(false);
	formPanel.setWidth(width);
	formPanel.setButtonAlign(Position.RIGHT);

	tabPanel = new TabPanel();
	tabPanel.setActiveTab(0);
	formPanel.add(tabPanel);
    }

    public void addButton(final Button button) {
	formPanel.addButton(button);
    }

    public void addTab(final String title, final Panel tab) {
	tab.setTitle(title);
	tab.setLayout(new FormLayout());
	tab.setAutoHeight(true);
	tab.setPaddings(10);
	tabPanel.add(tab);
	if (formPanel.isRendered()) {
	    formPanel.doLayout();
	}
    }

    public Form getForm() {
	return formPanel.getForm();
    }

    public Component getPanel() {
	return formPanel;
    }

}
