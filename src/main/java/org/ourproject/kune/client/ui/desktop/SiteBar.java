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
import org.ourproject.kune.client.ui.SimpleRoundedPanel;
import org.ourproject.kune.client.ui.SiteBarView;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.TextBox;

public class SiteBar extends Composite implements SiteBarView {

	private HorizontalPanel siteBarHP = null;

	private Image siteBarSpinProcessing = null;

	private Label sitebarTextProcessingLabel = null;

	private HTML siteBarSpaceExpand = null;

	private Hyperlink siteBarNewGroupHyperlink = null;

	private HTML siteBarSpaceHtml1 = null;

	private HTML siteBarPipeSeparatorHtml = null;

	private HTML siteBarSpaceHtml2 = null;

	private Hyperlink LoginHyperlink = null;

	private HTML siteBarSpaceHtml3 = null;

	private Image siteBarSearchIconImage = null;

	private HTML siteBarSpaceHtml4 = null;

	private TextBox siteBarSearchTextBox = null;

	private HTML siteBarSpaceHtml5 = null;
	
	private SimpleRoundedPanel siteBarOptionsRP = null;
	
	private MenuBar siteBarOptionsMenu = null;
	
	private MenuBar siteBarOptionsSubMenu = null;

	private HorizontalPanel siteBarHelpTransHP = null;

	private Image siteBarHelpTransImage = null;

	private HTML siteBarHelpTransSpaceHtml = null;

	private Hyperlink siteBarHelpTransHyperlink = null;

	private HTML siteBarSpaceHtml6 = null;

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
		siteBarSpaceHtml1 = new HTML();
		siteBarPipeSeparatorHtml = new HTML();
		siteBarSpaceHtml2 = new HTML();
		LoginHyperlink = new Hyperlink();
		siteBarSpaceHtml3 = new HTML();
		siteBarSearchIconImage = new Image();
		Img.ref().kuneSearchIco().applyTo(siteBarSearchIconImage);
		siteBarSpaceHtml4 = new HTML();
		siteBarSearchTextBox = new TextBox();
		siteBarSpaceHtml5 = new HTML();
		siteBarOptionsRP = new SimpleRoundedPanel();
		siteBarOptionsMenu = new MenuBar();
		siteBarOptionsSubMenu = new MenuBar(true);
		inDevCmd = new Command() {
		      public void execute() {
		          Window.alert("In development!");
		        }
		      };
		siteBarHelpTransHP = new HorizontalPanel();
		siteBarHelpTransHP
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		siteBarHelpTransImage = new Image();
		Img.ref().kuneHelpTranslationIcon().applyTo(siteBarHelpTransImage);
		siteBarHelpTransSpaceHtml = new HTML();
		siteBarHelpTransHyperlink = new Hyperlink();
		siteBarSpaceHtml6 = new HTML();
		siteBarLogoImage = new Image();
		Img.ref().kuneLogo16px().applyTo(siteBarLogoImage);
		
	}

	protected void layout() {
        siteBarHP.add(siteBarSpinProcessing);
		siteBarHP.add(sitebarTextProcessingLabel);
		siteBarHP.add(siteBarSpaceExpand);
		siteBarHP.add(siteBarNewGroupHyperlink);
		siteBarHP.add(siteBarSpaceHtml1);
		siteBarHP.add(siteBarPipeSeparatorHtml);
		siteBarHP.add(siteBarSpaceHtml2);
		siteBarHP.add(LoginHyperlink);
		siteBarHP.add(siteBarSpaceHtml3);
		siteBarHP.add(siteBarOptionsRP);
		siteBarOptionsRP.add(siteBarOptionsMenu);
		siteBarHP.add(siteBarSpaceHtml4);
		siteBarHP.add(siteBarSearchIconImage);
		siteBarHP.add(siteBarSpaceHtml5);
		siteBarHP.add(siteBarSearchTextBox);
        siteBarHP.add(siteBarSpaceHtml6);
		siteBarHP.add(siteBarLogoImage);

        siteBarOptionsMenu.addItem("<b>&nbsp;&nbsp;" + Trans.constants().Options() + "</b>", true, siteBarOptionsSubMenu);
        //siteBarOptionsMenu.addItem(Trans.constants().Options(), siteBarOptionsSubMenu);
        siteBarOptionsSubMenu.addItem(Trans.constants().HelpWithTranslation(), inDevCmd);
        siteBarOptionsSubMenu.addItem(Trans.constants().ContactUs(), inDevCmd);
		siteBarOptionsSubMenu.addItem(Trans.constants().Help(), inDevCmd);
		siteBarHelpTransHP.add(siteBarHelpTransImage);
		siteBarHelpTransHP.add(siteBarHelpTransSpaceHtml);
		siteBarHelpTransHP.add(siteBarHelpTransHyperlink);
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

		siteBarSpaceHtml1.setHTML("&nbsp;");
		siteBarSpaceHtml1.setWidth("3");

		siteBarPipeSeparatorHtml.setHTML("|");
		siteBarPipeSeparatorHtml.setStyleName("kune-sitebar-color");

		siteBarSpaceHtml2.setHTML("&nbsp;");
		siteBarSpaceHtml2.setWidth("3");

		LoginHyperlink.setText(Trans.constants().Login());

		siteBarSpaceHtml3.setHTML("&nbsp;");
		siteBarSpaceHtml3.setWidth("15");

		siteBarSearchIconImage.setStyleName("kune-search-icon");
		
		siteBarSpaceHtml4.setHTML("&nbsp;");
		siteBarSpaceHtml4.setWidth("15");

		siteBarSearchTextBox.setStyleName("kune-search-box");
		siteBarSearchTextBox.setWidth("180");
		siteBarSearchTextBox.setTitle(Trans.constants().Search());

		siteBarSpaceHtml5.setHTML("&nbsp;");
		siteBarSpaceHtml5.setWidth("3");
		
		siteBarOptionsRP.setCornerStyleName("kune-sitebar-options-rp");
		siteBarOptionsMenu.setStyleName("kune-sitebar-options");
		//siteBarOptionsMenu.setHeight("16");
		siteBarOptionsMenu.setTitle(Trans.constants().GlobalSiteOptions());
		siteBarOptionsSubMenu.setStyleName("kune-sitebar-sub-options");
		
		siteBarHelpTransHP.setBorderWidth(0);
		siteBarHelpTransHP.setSpacing(0);
		
		siteBarHelpTransSpaceHtml.setHTML("&nbsp;");
		siteBarHelpTransSpaceHtml.setWidth("3");

		siteBarHelpTransHyperlink
				.setText(Trans.constants().HelpWithTranslation());
		siteBarHelpTransHyperlink.setStyleName("kune-sitebar");

		siteBarSpaceHtml6.setHTML("&nbsp;");
		siteBarSpaceHtml6.setWidth("15");

		siteBarLogoImage.setStyleName("kune-sitebar-logo");
		
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
	
	public void showHelpInTranslation(boolean show) {
		siteBarHelpTransHP.setVisible(show);
	}
}
