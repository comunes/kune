/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 dated June, 1991.
 * 
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */

package org.ourproject.kune.client.ui;

import org.ourproject.kune.client.Trans;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
//import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Widget;

public class WizardPage extends Composite {

	private DockPanel kWizardDP = null;

	private VerticalPanel northVP = null;

	private Label titleLabel = null;

	private KuneHR hr1 = null;

	private VerticalPanel southVP = null;

	private KuneHR hr2 = null;

	private HorizontalPanel buttonsHP = null;

	private HTML buttonsSpaceExpandHtml = null;

	public Button arrowBackButton = null;

	private HTML buttonSpaceHtml1 = null;
	
	public Button arrowNextFinishButton = null;

	private HTML buttonSpaceHtml2 = null;
	
	public Button cancelButton = null;

	public VerticalPanel centerContentVP = null;
		
	private boolean finish = false;

	public WizardPage() {
		super();
		initialize();
		layout();
		setProperties();
	}
	
	public WizardPage(String title, Widget contentWidget) {
		this();
		setTitle(title);
		centerContentVP.add(contentWidget);
		//centerContentVP.setCellVerticalAlignment(contentWidget, HasVerticalAlignment.ALIGN_TOP);
    }
	
	public WizardPage(String title, Widget contentWidget, boolean backActive, boolean nextFinishActive, boolean cancelActive, boolean setButtonFinish) {
		this(title, contentWidget);
		setBackActive(backActive);
		setNextFinishActive(nextFinishActive);
		setCancelActive(cancelActive);
		if (setButtonFinish) {
            setButtonFinish();
		}
		else {
			setButtonNext();
		}
	}
		
	protected void initialize() {
		kWizardDP = new DockPanel();
		northVP = new VerticalPanel();
		titleLabel = new Label();
		hr1 = new KuneHR(); 
    	//centerContentVP.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		southVP = new VerticalPanel();
		hr2 = new KuneHR();
		buttonsHP = new HorizontalPanel();
		buttonsHP.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		buttonsSpaceExpandHtml = new HTML();
		arrowBackButton = new Button();
		arrowNextFinishButton = new Button();
		buttonSpaceHtml1 = new HTML();
		buttonSpaceHtml2 = new HTML();
		cancelButton = new Button();
		centerContentVP = new VerticalPanel();
		this.initWidget(kWizardDP);
	}

	protected void layout() {
		kWizardDP.add(northVP, DockPanel.NORTH);
		kWizardDP.add(southVP, DockPanel.SOUTH);
		kWizardDP.add(centerContentVP, DockPanel.CENTER);

		northVP.add(titleLabel);
		northVP.add(hr1);

		buttonsHP.add(buttonsSpaceExpandHtml);
		buttonsHP.add(arrowBackButton);
		buttonsHP.add(buttonSpaceHtml1);
		buttonsHP.add(arrowNextFinishButton);
		buttonsHP.add(buttonSpaceHtml2);
		buttonsHP.add(cancelButton);
		
		southVP.add(hr2);
		southVP.add(buttonsHP);

	}

	protected void setProperties() {
		kWizardDP.setBorderWidth(0);
		kWizardDP.setSpacing(2);

		northVP.setBorderWidth(0);
		northVP.setSpacing(0);
		northVP.setWidth("100%");

		hr1.setWidth("100%");
		
		centerContentVP.setHeight("100%");
		centerContentVP.setWidth("100%");
				
        southVP.setBorderWidth(0);
		southVP.setSpacing(0);
		southVP.setWidth("100%");

		hr2.setWidth("100%");
        
		buttonsHP.setBorderWidth(0);
		buttonsHP.setSpacing(0);
		buttonsHP.setWidth("100%");
		buttonsHP.setCellWidth(buttonsSpaceExpandHtml, "100%");
		
		buttonsSpaceExpandHtml.setHTML("&nbsp;");
		buttonsSpaceExpandHtml.setWidth("100%");		

		buttonSpaceHtml1.setHTML("&nbsp;");
		buttonSpaceHtml1.setWidth("14");
		buttonSpaceHtml2.setHTML("&nbsp;");
		buttonSpaceHtml2.setWidth("20");
		
		arrowBackButton.setText(Trans.constants().ArrowBack());

		arrowNextFinishButton.setText(Trans.constants().NextArrow());

		cancelButton.setText(Trans.constants().Cancel());
	}
	
	public void setTitle(String title) {
		titleLabel.setText(title);
	}
	
    public void setBackActive(boolean active) {
    	arrowBackButton.setEnabled(active);
    }
    
    public void setNextFinishActive(boolean active) {
    	arrowNextFinishButton.setEnabled(active);
    }
    
    public void setCancelActive(boolean active) {
    	cancelButton.setEnabled(active);
    }
    
    public void setButtonNext() {
    	arrowNextFinishButton.setText(Trans.constants().NextArrow());
    	finish = false;
    }
 
    public void setButtonFinish() {
    	arrowNextFinishButton.setText(Trans.constants().Finish());
    	finish = true;
    }
    
    public boolean isButtonFinishActive() {
    	return finish;
    }
    
    public void addClickListener(ClickListener listener) {
    	arrowBackButton.addClickListener(listener);
    	arrowNextFinishButton.addClickListener(listener);
    	cancelButton.addClickListener(listener);
    }
    
    public int getTopHeigth() {
    	return this.northVP.getOffsetHeight();
    }
    
    public int getBottomHeigth() {
        return this.southVP.getOffsetHeight();
    }
    
}