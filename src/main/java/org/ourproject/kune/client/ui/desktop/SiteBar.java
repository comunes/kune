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

package org.ourproject.kune.client.ui.desktop;

import org.ourproject.kune.client.Img;
import org.ourproject.kune.client.Trans;
import org.ourproject.kune.client.model.User;
import org.ourproject.kune.client.ui.BorderPanel;
import org.ourproject.kune.client.ui.SimpleRoundedPanel;
import org.ourproject.kune.client.ui.SiteBarView;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;

public class SiteBar extends Composite implements SiteBarView {

	private HorizontalPanel siteBarHP = null;

	private Image siteBarSpinProcessing = null;

	private Label sitebarTextProcessingLabel = null;

	private HTML siteBarSpaceExpand = null;

	private Hyperlink siteBarNewGroupHyperlink = null;

	private HTML siteBarPipeSeparatorHtml = null;

	private Hyperlink LoginHyperlink = null;

	private PushButton siteBarSearchButton = null;
	
	private TextBox siteBarSearchTextBox = null;
	
	private SimpleRoundedPanel siteBarOptionsRP = null;
	
	private MenuBar siteBarOptionsMenu = null;
	
	private MenuBar siteBarOptionsSubMenu = null;

	private Image siteBarHelpTransImage = null;

	private Hyperlink siteBarHelpTransHyperlink = null;

	private Image siteBarLogoImage = null;
	
	private Command inDevCmd = null;
	
	public SiteBar() {
		super();
		initialize();
		layout();
		setProperties();
	}

	protected void initialize() {
		siteBarHP = new HorizontalPanel();
		initWidget(siteBarHP);
		siteBarSpinProcessing = new Image();
		Img.ref().spinKuneThundGreen().applyTo(siteBarSpinProcessing);
		sitebarTextProcessingLabel = new Label();
		siteBarSpaceExpand = new HTML();
		siteBarNewGroupHyperlink = new Hyperlink();
		siteBarPipeSeparatorHtml = new HTML();
		LoginHyperlink = new Hyperlink();		
		siteBarSearchButton = new PushButton(Img.ref().kuneSearchIco().createImage(), Img.ref().kuneSearchIcoPush().createImage());
		siteBarSearchTextBox = new TextBox();
		siteBarOptionsRP = new SimpleRoundedPanel();
		siteBarOptionsMenu = new MenuBar();
		siteBarOptionsSubMenu = new MenuBar(true);
		inDevCmd = new Command() {
		      public void execute() {
		          Window.alert("In development!");
		        }
		      };
		siteBarHelpTransImage = new Image();
		Img.ref().kuneHelpTranslationIcon().applyTo(siteBarHelpTransImage);
		siteBarHelpTransHyperlink = new Hyperlink();
		siteBarLogoImage = new Image();
		Img.ref().kuneLogo16px().applyTo(siteBarLogoImage);
		
	}

	protected void layout() {
        siteBarHP.add(siteBarSpinProcessing);
		siteBarHP.add(sitebarTextProcessingLabel);
		siteBarHP.add(siteBarSpaceExpand);
		siteBarHP.add(siteBarNewGroupHyperlink);
		siteBarHP.add(new BorderPanel(siteBarPipeSeparatorHtml, 0, 3));
		siteBarHP.add(LoginHyperlink);
		siteBarHP.add(new BorderPanel(siteBarOptionsRP, 0, 15));
		siteBarOptionsRP.add(siteBarOptionsMenu);
		siteBarHP.add(new BorderPanel(siteBarSearchButton, 0, 3, 0, 0));
		siteBarHP.add(siteBarSearchTextBox);
		siteBarHP.add(new BorderPanel(siteBarLogoImage, 0, 0, 0, 15));

		siteBarOptionsMenu.addItem(Img.ref().buttonSitebarArrowDown().getHTML() + "&nbsp;&nbsp;" + Trans.constants().Options(), true, siteBarOptionsSubMenu);
        siteBarOptionsSubMenu.addItem(Trans.constants().HelpWithTranslation(), inDevCmd);
        siteBarOptionsSubMenu.addItem(Trans.constants().ContactUs(), inDevCmd);
		siteBarOptionsSubMenu.addItem(Trans.constants().Help(), inDevCmd);
	}

	protected void setProperties() {
		siteBarHP.setCellHeight(siteBarSpinProcessing, "16");
		siteBarHP.setCellHeight(siteBarSpaceExpand, "16");
		siteBarHP.setCellWidth(siteBarSpaceExpand, "100%");
		siteBarHP.setBorderWidth(0);
		siteBarHP.setSpacing(0);
		siteBarHP.addStyleName("kune-sitebar");
		siteBarHP.setStyleName("kune-sitebar");
		siteBarHP.setHeight("16");
		siteBarHP.setWidth("100%");

		siteBarSpinProcessing.setStyleName("kune-status");

		sitebarTextProcessingLabel.setText(Trans.constants().Processing());
		sitebarTextProcessingLabel.setStyleName("kune-status");

		siteBarSpaceExpand.setHTML("&nbsp;");
		siteBarSpaceExpand.setHeight("16");
		siteBarSpaceExpand.setWidth("100%");

		siteBarNewGroupHyperlink.setText(Trans.constants().NewGroup());

		siteBarPipeSeparatorHtml.setHTML("|");
		siteBarPipeSeparatorHtml.setStyleName("kune-sitebar-color");

		LoginHyperlink.setText(Trans.constants().Login());

		siteBarSearchTextBox.setStyleName("kune-search-box");
		siteBarSearchTextBox.setWidth("180");
		siteBarSearchTextBox.setTitle(Trans.constants().Search());
		
		siteBarOptionsRP.setCornerStyleName("kune-sitebar-options-rp");
		siteBarOptionsMenu.setStyleName("kune-sitebar-options");
		siteBarOptionsMenu.setTitle(Trans.constants().GlobalSiteOptions());
		siteBarOptionsSubMenu.setStyleName("kune-sitebar-sub-options");

		siteBarHelpTransHyperlink
				.setText(Trans.constants().HelpWithTranslation());
		siteBarHelpTransHyperlink.setStyleName("kune-sitebar");
		
		showProgressBar(false);
	}
	
	public void showProgressBar(boolean show) {
		siteBarSpinProcessing.setVisible(show);
		sitebarTextProcessingLabel.setVisible(show);
	}
	
	public void setTextProgressBar(String text) {
		sitebarTextProcessingLabel.setText(text);
	}
	
	public void login(User user) {
		// TODO
		LoginHyperlink.setText(user.getNickName());
	}
	
	public void logout() {
		// TODO
		LoginHyperlink.setText(Trans.constants().Login());
	}
	
	public void clearSearchBox() {
		siteBarSearchTextBox.setText("");
	}
	
	public void setTextSearchBox(String text) {
		siteBarSearchTextBox.setText(text);
	}

}
