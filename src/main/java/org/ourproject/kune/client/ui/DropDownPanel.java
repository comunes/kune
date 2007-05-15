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

import org.ourproject.kune.client.Img;
import org.ourproject.kune.client.Trans;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * <p>This panel opens when you click on the arrow or the title (gmail
 *  style) </p>
 *  
 *  TODO: pagination (using DeckPanel)
 *  
 * @author Vicente J. Ruiz Jurado (vjrj@ourproject.org)
 *
 */
public class DropDownPanel extends Composite implements ClickListener {
    private RoundedPanel outerRP = null;
	private VerticalPanel dropDownPanelVP = null;
	private HorizontalPanel titleHP = null;
	private RolloverImage arrowImage = null;
	private Label titleLabel = null;
	private DeckPanel contentDeckP = null;
	private SimplePanel cleanPanel = null;

	public DropDownPanel() {
		super();
		initialize();
		layout();
		setProperties();
		this.setContentVisible(false);
		arrowImage.addClickListener(this);
		titleLabel.addClickListener(this);
	}

	public DropDownPanel(boolean visible) {
		this();
		this.setContentVisible(visible);
	}
	
	protected void initialize() {
		outerRP = new RoundedPanel();
		dropDownPanelVP = new VerticalPanel();
		titleHP = new HorizontalPanel();
		arrowImage = new RolloverImage();
		titleLabel = new Label();
		contentDeckP = new DeckPanel();
		cleanPanel = new SimplePanel();
	}

	protected void layout() {
		initWidget(outerRP);
		outerRP.add(dropDownPanelVP);
		dropDownPanelVP.add(titleHP);
		dropDownPanelVP.add(contentDeckP);
		titleHP.add(new BorderPanel(arrowImage, 0, 0, 0, 3));
		titleHP.add(new BorderPanel(titleLabel, 0, 0, 0, 3));
		contentDeckP.add(cleanPanel);
	}

	protected void setProperties() {
		outerRP.setCornerStyleName("drop-down-outer");

		dropDownPanelVP.setBorderWidth(0);
		dropDownPanelVP.setSpacing(0);
		dropDownPanelVP.addStyleName("drop-down-outer");
		dropDownPanelVP.setStyleName("drop-down-outer");

		titleHP.setBorderWidth(0);
		titleHP.setSpacing(0);
		titleHP.addStyleName("drop-down-label");
		titleHP.setStyleName("drop-down-label");

		arrowImage.setImages(Img.ref().arrowDownBlack(), Img.ref().arrowDownWhite());
		arrowImage.setHeight("16");
		arrowImage.setWidth("16");

		titleLabel.setText(Trans.constants().Text());
		titleLabel.setWidth("100%");

		contentDeckP.addStyleName("drop-down-inner");
		contentDeckP.setStyleName("drop-down-inner");
		//contentDeckP.setWidth("100%");
	}

    public boolean contentEmpty() {
        return (contentDeckP.getWidgetCount() == 1);
    }
    
	public void setContent(Widget widget) {
		if (!contentEmpty()) {
            contentDeckP.remove(1);
		}
		contentDeckP.add(widget);
	}
	
    public void setContentVisible(boolean visible) {
    	if (visible) {
    		if (!contentEmpty()) {
    			arrowImage.setImages(Img.ref().arrowDownBlack(), Img.ref().arrowDownWhite());
                contentDeckP.showWidget(1);
        		contentDeckP.setVisible(true);
    		}
    	}
    	else {
    		arrowImage.setImages(Img.ref().arrowRightBlack(), Img.ref().arrowRightWhite());    		
            contentDeckP.showWidget(0);
       		contentDeckP.setVisible(false);
    	}
    }
    
    public boolean isVisible() {
    	return (contentDeckP.getVisibleWidget() == 1);
    }
    
    public void setTitle(String title) {
    	titleLabel.setText(title);
    }

    public void onClick(Widget sender) {
    	if  ((sender == titleHP) | (sender == arrowImage) | (sender == titleLabel)) {
            setContentVisible(!isVisible());
    	}
    }
    
    public void setColor(String color) {
    	outerRP.setColor(color);
        DOM.setStyleAttribute(arrowImage.getElement(), "backgroundColor", color);
    	DOM.setStyleAttribute(contentDeckP.getElement(), "background", color); 
    	DOM.setStyleAttribute(dropDownPanelVP.getElement(), "backgroundColor", color);
    	DOM.setStyleAttribute(titleLabel.getElement(), "backgroundColor", color);
    }
}
