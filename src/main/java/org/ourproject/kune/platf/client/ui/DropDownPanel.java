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
package org.ourproject.kune.platf.client.ui;

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * <p>
 * This panel opens when you click on the arrow or the title (gmail style)
 * </p>
 * 
 */
public class DropDownPanel extends Composite implements ClickListener {
    private final VerticalPanel vp;
    private final HorizontalPanel titleHP;
    private final Label titleLabel;
    private final SimplePanel contentPanel;
    private final Images img;
    private final Image arrowImage;
    private final RoundedPanel outerBorder;
    private String stylePrimaryName;

    public DropDownPanel() {
	vp = new VerticalPanel();
	outerBorder = new RoundedPanel(vp, RoundedPanel.ALL);
	titleHP = new HorizontalPanel();
	arrowImage = new Image();
	titleLabel = new Label();
	contentPanel = new SimplePanel();

	initWidget(outerBorder);
	vp.add(titleHP);
	vp.add(contentPanel);
	titleHP.add(arrowImage);
	titleHP.add(titleLabel);

	this.stylePrimaryName = "k-dropdownouter";
	outerBorder.setCornerStyleName(stylePrimaryName);
	vp.setStylePrimaryName(stylePrimaryName);
	vp.setWidth("100%");
	vp.setCellWidth(contentPanel, "100%");
	vp.setCellWidth(titleHP, "100%");
	titleHP.setStylePrimaryName("k-dropdownlabel");
	img = Images.App.getInstance();
	img.arrowDownWhite().applyTo(arrowImage);
	titleLabel.setText("");
	contentPanel.setStylePrimaryName("k-dropdowninner");

	setContentVisible(false);
	arrowImage.addClickListener(this);
	titleLabel.addClickListener(this);
    }

    public DropDownPanel(final boolean visible) {
	this();
	setContentVisible(visible);
    }

    public DropDownPanel(final String headerText, final boolean visible) {
	this();
	setContentVisible(visible);
	setHeaderText(headerText);
    }

    public boolean isContentVisible() {
	return contentPanel.isVisible();
    }

    public void onClick(final Widget sender) {
	if (sender == titleHP | sender == arrowImage | sender == titleLabel) {
	    setContentVisible(!isContentVisible());
	}
    }

    public void setBorderStylePrimaryName(final String stylePrimaryName) {
	this.stylePrimaryName = stylePrimaryName;
	outerBorder.setCornerStyleName(stylePrimaryName);
	vp.setStylePrimaryName(stylePrimaryName);
    }

    @Deprecated
    public void setColor(final String color) {
	// outerBorder.setColor(color);
	// DOM.setStyleAttribute(arrowImage.getElement(), "backgroundColor",
	// color);
	// DOM.setStyleAttribute(vp.getElement(), "backgroundColor", color);
	// DOM.setStyleAttribute(titleLabel.getElement(), "backgroundColor",
	// color);
    }

    public void setContent(final Widget widget) {
	contentPanel.setWidget(widget);
	// refresh panel
	setContentVisible(isContentVisible());
    }

    public void setContentVisible(final boolean visible) {
	if (visible) {
	    img.arrowDownWhite().applyTo(arrowImage);
	    contentPanel.setVisible(true);

	} else {
	    img.arrowRightWhite().applyTo(arrowImage);
	    contentPanel.setVisible(false);
	}
    }

    public void setHeaderText(final String text) {
	titleLabel.setText(text);
    }

    public void setHeaderTitle(final String title) {
	// QuickTips size problems with images
	KuneUiUtils.setQuickTip(arrowImage, title);
	KuneUiUtils.setQuickTip(titleLabel, title);
    }

    public void setHeight(final String height) {
	super.setHeight(height);
	outerBorder.setHeight(height);
    }

    public void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
	if (oldTheme != null) {
	    final String oldThemeS = oldTheme.toString();
	    // outerBorder.removeStyleDependentName(oldThemeS);
	    vp.removeStyleDependentName(oldThemeS);
	    titleHP.removeStyleDependentName(oldThemeS);
	    contentPanel.removeStyleDependentName(oldThemeS);
	}
	final String newThemeS = newTheme.toString();
	outerBorder.setCornerStyleName(stylePrimaryName + "-" + newThemeS);
	// outerBorder.addStyleDependentName(newThemeS);
	vp.addStyleDependentName(newThemeS);
	titleHP.addStyleDependentName(newThemeS);
	contentPanel.addStyleDependentName(newThemeS);
    }

    public void setWidth(final String width) {
	super.setWidth(width);
	outerBorder.setWidth(width);
    }

}
