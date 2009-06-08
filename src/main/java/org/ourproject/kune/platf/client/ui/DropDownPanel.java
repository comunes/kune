/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
public class DropDownPanel extends Composite implements ClickHandler {
    private final VerticalPanel vpanel;
    private final HorizontalPanel titleHP;
    private final Label titleLabel;
    private final SimplePanel contentPanel;
    private final Images img;
    private final Image arrowImage;
    private final RoundedPanel outerBorder;
    private String stylePrimaryName;

    public DropDownPanel(final Images img) {
        super();
        this.img = img;
        vpanel = new VerticalPanel();
        outerBorder = new RoundedPanel(vpanel, RoundedPanel.ALL);
        titleHP = new HorizontalPanel();
        arrowImage = new Image();
        titleLabel = new Label();
        contentPanel = new SimplePanel();

        initWidget(outerBorder);
        vpanel.add(titleHP);
        vpanel.add(contentPanel);
        titleHP.add(arrowImage);
        titleHP.add(titleLabel);

        this.stylePrimaryName = "k-dropdownouter";
        outerBorder.setCornerStyleName(stylePrimaryName);
        vpanel.setStylePrimaryName(stylePrimaryName);
        vpanel.setWidth("100%");
        vpanel.setCellWidth(contentPanel, "100%");
        vpanel.setCellWidth(titleHP, "100%");
        titleHP.setStylePrimaryName("k-dropdownlabel");
        img.arrowDownWhite().applyTo(arrowImage);
        titleLabel.setText("");
        contentPanel.setStylePrimaryName("k-dropdowninner");

        setContentVisibleImpl(false);
        arrowImage.addClickHandler(this);
        titleLabel.addClickHandler(this);
    }

    public DropDownPanel(final Images img, final boolean visible) {
        this(img);
        setContentVisibleImpl(visible);
    }

    public DropDownPanel(final Images img, final String headerText, final boolean visible) {
        this(img);
        setContentVisibleImpl(visible);
        setHeaderTextImpl(headerText);
    }

    public boolean isContentVisible() {
        return contentPanel.isVisible();
    }

    public void onClick(final ClickEvent event) {
        setContentVisible(!isContentVisible());
    }

    public void setBorderStylePrimaryName(final String stylePrimaryName) {
        this.stylePrimaryName = stylePrimaryName;
        outerBorder.setCornerStyleName(stylePrimaryName);
        vpanel.setStylePrimaryName(stylePrimaryName);
    }

    public void setContent(final Widget widget) {
        contentPanel.setWidget(widget);
        // refresh panel
        setContentVisible(isContentVisible());
    }

    public void setContentHeight(final String height) {
        contentPanel.setHeight(height);
    }

    public void setContentVisible(final boolean visible) {
        setContentVisibleImpl(visible);
    }

    public void setHeaderText(final String text) {
        setHeaderTextImpl(text);
    }

    public void setHeaderTitle(final String title) {
        // QuickTips size problems with images
        KuneUiUtils.setQuickTip(arrowImage, title);
        KuneUiUtils.setQuickTip(titleLabel, title);
    }

    @Override
    public void setHeight(final String height) {
        super.setHeight(height);
        outerBorder.setHeight(height);
    }

    public void setTheme(final String oldTheme, final String newTheme) {
        if (oldTheme != null) {
            // outerBorder.removeStyleDependentName(oldThemeS);
            vpanel.removeStyleDependentName(oldTheme);
            titleHP.removeStyleDependentName(oldTheme);
            contentPanel.removeStyleDependentName(oldTheme);
        }
        outerBorder.setCornerStyleName(stylePrimaryName + "-" + newTheme);
        // outerBorder.addStyleDependentName(newThemeS);
        vpanel.addStyleDependentName(newTheme);
        titleHP.addStyleDependentName(newTheme);
        contentPanel.addStyleDependentName(newTheme);
    }

    @Override
    public void setWidth(final String width) {
        super.setWidth(width);
        outerBorder.setWidth(width);
    }

    private void setContentVisibleImpl(final boolean visible) {
        if (visible) {
            img.arrowDownWhite().applyTo(arrowImage);
            contentPanel.setVisible(true);

        } else {
            img.arrowRightWhite().applyTo(arrowImage);
            contentPanel.setVisible(false);
        }
    }

    private void setHeaderTextImpl(final String text) {
        titleLabel.setText(text);
    }

}
