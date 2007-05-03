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

package org.ourproject.kune.client;

import org.gwm.client.GDesktopPane;
import org.gwm.client.event.GFrameAdapter;
import org.gwm.client.event.GFrameEvent;
import org.gwm.client.impl.DefaultGDesktopPane;
import org.gwm.client.util.Gwm;
import org.gwm.client.util.GwmUtilities;
import org.gwtwidgets.client.ui.PNGImage;
import org.ourproject.kune.client.model.Rate;
import org.ourproject.kune.client.model.User;
import org.ourproject.kune.client.ui.BorderPanel;
import org.ourproject.kune.client.ui.ChatroomDialog;
import org.ourproject.kune.client.ui.DropDownPanel;
import org.ourproject.kune.client.ui.KuneFrame;
import org.ourproject.kune.client.ui.RateItDialog;
import org.ourproject.kune.client.ui.RateDialog;
import org.ourproject.kune.client.ui.RoundedPanel;
import org.ourproject.kune.client.ui.SiteBar;
import org.ourproject.kune.client.ui.Wizard;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Main extends AbsolutePanel implements EntryPoint,
		WindowResizeListener {

	private DockPanel generalDP = null;
	
	private SiteBar siteBar = null;

	private VerticalPanel generalDPEastVP = null;

	private HTML generalDPEastSpaceCellHtml1 = null;

	private PNGImage generalDPEastRssIconImage = null;

	private HTML generalDPEastSpaceCellHtml2 = null;

	private VerticalPanel generalMenuItemVP = null;

	private HTML generalMenuSpaceHtml1 = null;

	private HTML generalMenuSpaceHtml2 = null;

	private RoundedPanel menuItem1 = null;
	
	private RoundedPanel menuItem2 = null;
    
	private RoundedPanel menuItem3 = null;
	
	private BorderPanel contextNav = null;
	
	private VerticalPanel generalDPCenterVP = null;

	private PNGImage groupLogoImage = null;

	private HTML groupTopBarCornerSpace1Html = null;

	private HTML groupTopBarCornerSpace2Html = null;

	private HTML groupTopBarSpaceHtml = null;

	private Label groupTitleLabel = null;

	private ScrollPanel groupCenterScrollPanel = null;

	private HorizontalPanel groupCenterHP = null;

	private VerticalPanel groupCenterVP = null;

	private HTML groupCenterHtml = null;

	private VerticalPanel groupCenterMenuVP = null;

	private HTML groupCenterMenuHtml = null;

	private Label groupBottomBarLabel = null;

	private HTML groupBottomBarCornerSpaceHtml1 = null;

	private HTML groupBottomBarCornerSpaceHtml2 = null;

	// Sandbox variables
	
	private ChatroomDialog chatroom1 = null;

	private GDesktopPane desktop;
	
	private DropDownPanel dropDownPanel = null;
	
	public Main() {
		super();
		initialize();
		layout();
		setProperties();
		sandbox();
	}

	public void adjustSize(int windowWidth, int windowHeight) {
		int scrollWidth = windowWidth
				- groupCenterScrollPanel.getAbsoluteLeft() - 278;
		if (scrollWidth < 1) {
			scrollWidth = 1;
		}

		int scrollHeight = windowHeight
				- groupCenterScrollPanel.getAbsoluteTop() - 30 - 24;
		if (scrollHeight < 1) {
			scrollHeight = 1;
		}

		groupCenterScrollPanel.setSize("" + scrollWidth, "" + scrollHeight);
	}

	protected void initialize() {
		desktop = new DefaultGDesktopPane();
        generalDP = new DockPanel();
        siteBar = new SiteBar();
		generalDPEastVP = new VerticalPanel();
		generalDPEastSpaceCellHtml1 = new HTML();
		generalDPEastRssIconImage = new PNGImage("images/rss-icon.png", 16, 16);
		generalDPEastSpaceCellHtml2 = new HTML();
		generalMenuItemVP = new VerticalPanel();
		menuItem1 = new RoundedPanel(new Label(Trans.constants().Home()), RoundedPanel.RIGHT, "kune-menu-item-selected");
		menuItem2 = new RoundedPanel(new Label(Trans.constants().Blogs()), RoundedPanel.RIGHT, "kune-menu-item-not-selected");
		menuItem3 = new RoundedPanel(new Label(Trans.constants().Forums()), RoundedPanel.RIGHT, "kune-menu-item-not-selected");
		contextNav = new BorderPanel();
		generalMenuSpaceHtml1 = new HTML();
		generalMenuSpaceHtml2 = new HTML();
		generalDPCenterVP = new VerticalPanel();
		groupLogoImage = new PNGImage("images/foo-org-logo.png", 294, 54);
		groupTopBarCornerSpace1Html = new HTML();
		groupTopBarCornerSpace2Html = new HTML();
		groupTopBarSpaceHtml = new HTML();
		groupTitleLabel = new Label();
		groupCenterScrollPanel = new ScrollPanel();
		groupCenterHP = new HorizontalPanel();
		groupCenterVP = new VerticalPanel();
		groupCenterHtml = new HTML();
		groupCenterMenuVP = new VerticalPanel();
		groupCenterMenuHtml = new HTML();
		groupBottomBarLabel = new Label();
		groupBottomBarCornerSpaceHtml1 = new HTML();
		groupBottomBarCornerSpaceHtml2 = new HTML();
		dropDownPanel = new DropDownPanel(true);
	}

	protected void layout() {
		generalDP.add(siteBar, DockPanel.NORTH);
		generalDP.add(generalDPEastVP, DockPanel.EAST);
		generalDP.add(generalDPCenterVP, DockPanel.CENTER);

		generalDPEastVP.add(generalDPEastSpaceCellHtml1);
		generalDPEastVP.add(generalDPEastRssIconImage);
		generalDPEastVP.add(generalDPEastSpaceCellHtml2);
		generalDPEastVP.add(generalMenuItemVP);

        generalMenuItemVP.add(menuItem1);
		generalMenuItemVP.add(generalMenuSpaceHtml1);
        generalMenuItemVP.add(menuItem2);
		generalMenuItemVP.add(generalMenuSpaceHtml2);
        generalMenuItemVP.add(menuItem3);

        generalMenuItemVP.add(contextNav);
		contextNav.setWidgetMargin(dropDownPanel, 5, 0, 0, 5);

		generalDPCenterVP.add(groupLogoImage);
		generalDPCenterVP.add(groupTopBarCornerSpace1Html);
		generalDPCenterVP.add(groupTopBarCornerSpace2Html);
		generalDPCenterVP.add(groupTopBarSpaceHtml);
		generalDPCenterVP.add(groupTitleLabel);
		generalDPCenterVP.add(groupCenterHP);
		generalDPCenterVP.add(groupBottomBarLabel);
		generalDPCenterVP.add(groupBottomBarCornerSpaceHtml1);
		generalDPCenterVP.add(groupBottomBarCornerSpaceHtml2);

		groupCenterHP.add(groupCenterScrollPanel);
		groupCenterHP.add(groupCenterMenuVP);

		groupCenterScrollPanel.add(groupCenterVP);

		groupCenterVP.add(groupCenterHtml);

		groupCenterMenuVP.add(groupCenterMenuHtml);
		
		add((Widget) desktop, 0, 0);
		desktop.setWidgetLocation(generalDP, 0, 0);		
	}

	public void onModuleLoad() {
		Element elem = DOM.getElementById("initialstatusbar");
		setVisible(elem, false);
		RootPanel.get().add(this);
	}
	
	public void onWindowResized(int width, int height) {
		adjustSize(width, height);
	}

	protected void setProperties() {
		Gwm.setOverlayLayerDisplayOnDragAction(false);
		desktop.setTheme("alphacubecustom");

		generalDP.setBorderWidth(0);
		generalDP.setSpacing(0);
		generalDP.setHeight("100%");
		generalDP.setWidth("100%");

		generalDPEastVP.setCellHorizontalAlignment(generalDPEastRssIconImage,
				HasHorizontalAlignment.ALIGN_RIGHT);
		generalDPEastVP.setCellWidth(generalMenuItemVP, "130");
		generalDPEastVP.setBorderWidth(0);
		generalDPEastVP.setSpacing(0);

		generalDPEastSpaceCellHtml1.setHTML("<b></b>");
		generalDPEastSpaceCellHtml1.setHeight("54");

		generalDPEastSpaceCellHtml2.setHTML("<b></b>");
		generalDPEastSpaceCellHtml2.setHeight("11");

		generalMenuItemVP.setBorderWidth(0);
		generalMenuItemVP.setSpacing(0);
		generalMenuItemVP.setWidth("130");

		generalMenuSpaceHtml1.setHTML("&nbsp;");
		generalMenuSpaceHtml1.setHeight("5");

		generalMenuSpaceHtml2.setHTML("&nbsp;");
		generalMenuSpaceHtml2.setHeight("5");

		generalDPCenterVP.setCellVerticalAlignment(groupTitleLabel,
				HasVerticalAlignment.ALIGN_MIDDLE);
		generalDPCenterVP.setCellVerticalAlignment(groupBottomBarLabel,
				HasVerticalAlignment.ALIGN_MIDDLE);
		generalDPCenterVP.setBorderWidth(0);
		generalDPCenterVP.setSpacing(0);

		groupTopBarCornerSpace1Html.setHTML("<b></b>");
		groupTopBarCornerSpace1Html.setStyleName("kune-group-topbar-rc-2l");

		groupTopBarCornerSpace2Html.setHTML("<b></b>");
		groupTopBarCornerSpace2Html.setStyleName("kune-group-topbar-rc-1l");

		groupTopBarSpaceHtml.setHTML("<b></b>");
		groupTopBarSpaceHtml.setStyleName("kune-group-topbar");
		groupTopBarSpaceHtml.setHeight("25");

		groupTitleLabel.setText(Trans.constants().Text());
		groupTitleLabel.setStyleName("kune-group-title");
		groupTitleLabel.setHeight("23");

		groupCenterHP.setCellHeight(groupCenterMenuVP, "100%");
		groupCenterHP.setCellWidth(groupCenterMenuVP, "135"); // 95 in original draft
		groupCenterHP.setBorderWidth(0);
		groupCenterHP.setSpacing(0);
		groupCenterHP.addStyleName("kune-group-center");
		groupCenterHP.setStyleName("kune-group-center");
		groupCenterHP.setHeight("100%");
		groupCenterHP.setWidth("100%");

		groupCenterVP.setBorderWidth(0);
		groupCenterVP.setSpacing(0);
		groupCenterVP.addStyleName("kune-group-center-area");
		groupCenterVP.setStyleName("kune-group-center-area");
		groupCenterVP.setHeight("100%");
		groupCenterVP.setWidth("100%");

		groupCenterHtml
				.setHTML("<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. Nunc sit amet neque. Ut id dui. Integer viverra feugiat sem. Morbi aliquam turpis rhoncus sapien volutpat condimentum. Vestibulum dignissim, risus et ullamcorper sollicitudin, risus mi molestie lectus, ut aliquam nulla dolor eu nisl. Duis volutpat. Sed eget lectus lacinia lacus interdum facilisis. Aliquam tincidunt sem at mi. Duis a ipsum vel turpis volutpat adipiscing. Sed at libero sit amet lacus elementum tempus. Vestibulum sit amet tellus. Duis dolor. Praesent convallis lorem ac metus. Curabitur malesuada pede id dui. Vivamus tincidunt risus vehicula enim. Nulla fermentum. Sed placerat lacus eget erat. Proin dolor enim, aliquam ut, vehicula sit amet, blandit non, arcu. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.\n</p><p>\nLorem ipsum dolor sit amet, consectetuer adipiscing elit. Duis sapien. Suspendisse potenti. Sed imperdiet pulvinar tortor. Ut vel nisi. Nam commodo, mauris vitae congue placerat, mauris eros vulputate odio, ac facilisis erat quam at enim. Cras iaculis pede sit amet dui. Cras arcu. Fusce non orci vitae lacus hendrerit auctor. Aliquam leo.\n</p><p>\nVestibulum orci dolor, hendrerit et, dapibus vel, congue ac, velit. Maecenas est. Nam in velit eget ante consequat vulputate. Nam posuere. Nunc lectus. Vestibulum facilisis. Aliquam elit nunc, facilisis eget, bibendum at, dignissim at, nulla. Sed ullamcorper, mi a eleifend tincidunt, metus tortor ultricies mi, in tempor arcu tellus nec erat. Quisque semper, turpis in gravida suscipit, elit leo sollicitudin risus, vel laoreet velit mi a massa. Aliquam non nulla a sapien dapibus bibendum. Sed auctor neque vel justo. Etiam cursus. Nunc eget lectus. In euismod urna vitae dui luctus consequat. Nunc cursus vulputate erat. Duis vel justo vel ante imperdiet rutrum. Curabitur eget turpis ac pede interdum accumsan. Ut velit.\n</p><p>\nProin vitae eros ut pede lacinia aliquam. Praesent in metus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aenean sed eros. Phasellus id risus. Vivamus non nunc eget purus feugiat sagittis. Mauris id tortor ut lectus mollis porttitor. Fusce lobortis leo quis augue suscipit tincidunt. Ut tristique, nunc at egestas blandit, sem leo tincidunt nibh, id tempor neque elit vitae tortor. Nulla sapien est, suscipit sed, aliquam eget, viverra suscipit, justo.\n</p>");

		groupCenterMenuVP.setCellHeight(groupCenterMenuHtml, "100%");
		groupCenterMenuVP.setBorderWidth(0);
		groupCenterMenuVP.setSpacing(0);
		groupCenterMenuVP.addStyleName("kune-group-center-menubar");
		groupCenterMenuVP.setStyleName("kune-group-center-menubar");
		groupCenterMenuVP.setHeight("100%");
		groupCenterMenuVP.setWidth("135"); // 95 in original draft

		groupCenterMenuHtml.setHTML("<b>Menu</b>");

		groupBottomBarLabel.setText("(c) Foo organization, contents under some free/open Commons license");
		groupBottomBarLabel.setStyleName("kune-group-bottombar");
		groupBottomBarLabel.setHeight("24");

		groupBottomBarCornerSpaceHtml1.setHTML("<b></b>");
		groupBottomBarCornerSpaceHtml1.setStyleName("kune-group-topbar-rc-1l");
		groupBottomBarCornerSpaceHtml1.setHeight("1");

		groupBottomBarCornerSpaceHtml2.setHTML("<b></b>");
		groupBottomBarCornerSpaceHtml2.setStyleName("kune-group-topbar-rc-2l");
		groupBottomBarCornerSpaceHtml2.setHeight("1");
		setSize("100%", "100%");
        
		Window.addWindowResizeListener(this);
		Window.enableScrolling(false);

		DeferredCommand.add(new Command() {
			public void execute() {
				onWindowResized(Window.getClientWidth(), Window
						.getClientHeight());
			}
		});
	}
	
	
	public void sandbox() {
		
		User user = new User("luther.b");
		user.setId((long) 1);
		Session session = new Session();
		session.currentUser = user;
			
        KuneFrame chatroomFrame = new KuneFrame(); 
        
		chatroom1 = new ChatroomDialog();
        chatroom1.setSubject("Welcome to sometopic-foorganization chat room");
        ChatroomUser luthorb = new ChatroomUser("luthor.b", true);
        ChatroomUser anneh = new ChatroomUser("anne.h", false);
        ChatroomUser anneh1 = new ChatroomUser("anne.h1", false);
        ChatroomUser anneh2 = new ChatroomUser("anne.h2", false);
        ChatroomUser anneh3 = new ChatroomUser("anne.h3", false);
        ChatroomUser anneh4 = new ChatroomUser("anne.h4", false);
        ChatroomUser anneh5 = new ChatroomUser("anne.h5", false);
        ChatroomUser anneh6 = new ChatroomUser("anne.h6", false);
        ChatroomUser anneh7 = new ChatroomUser("anne.h7", false);
        ChatroomUser anneh8 = new ChatroomUser("anne.h8", false);
        ChatroomUser anneh9 = new ChatroomUser("anne.h9", false);
        ChatroomUser anneh10 = new ChatroomUser("anne.h10", false);
        ChatroomUser anneh11 = new ChatroomUser("anne.h11", false);
        ChatroomUser anneh12 = new ChatroomUser("anne.h12", false);
        ChatroomUser anneh13 = new ChatroomUser("anne.h13", false);
        ChatroomUser anneh14 = new ChatroomUser("anne.h14", false);
        chatroom1.addUser(luthorb);
        chatroom1.addUser(anneh);
        chatroom1.addUser(anneh1);
        chatroom1.addUser(anneh2);
        chatroom1.addUser(anneh3);
        chatroom1.addUser(anneh4);
        chatroom1.addUser(anneh5);
        chatroom1.addUser(anneh6);
        chatroom1.addUser(anneh7);
        chatroom1.addUser(anneh8);
        chatroom1.addUser(anneh9);
        chatroom1.addUser(anneh10);
        chatroom1.addUser(anneh11);
        chatroom1.addUser(anneh12);
        chatroom1.addUser(anneh13);
        chatroom1.addUser(anneh14);
        chatroom1.addToConversation(luthorb, new HTML("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. Nunc sit amet neque. Ut id dui."));
        chatroom1.addToConversation(anneh, new HTML("Lorem ipsum dolor sit amet?"));
        chatroom1.addToConversation(luthorb, new HTML("yes, lorem ipsum dolor sit amet"));
        chatroom1.addTimeDelimiter();
        chatroom1.addToConversation(luthorb, new HTML("Lorem ipsum dolor sit amet"));
        chatroom1.addToConversation(anneh, new HTML("Lorem ipsum dolor sit amet?"));
        chatroom1.addToConversation(anneh, new HTML("Lorem ipsum dolor sit amet?"));
        chatroom1.addToConversation(anneh, new HTML("Lorem ipsum dolor sit amet?"));
        chatroom1.addToConversation(anneh, new HTML("Lorem ipsum dolor sit amet?"));
        chatroom1.addToConversation(luthorb, new HTML("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. Nunc sit amet neque. Ut id dui."));
        chatroom1.addToConversation(anneh, new HTML("Lorem ipsum dolor sit amet?"));
        
        chatroomFrame.setCaption(Trans.constants().Chatroom() + " " + "sometopic-foorganization@kune.ourproject.org");
		chatroomFrame.setFrame(true, false, true, true, false);
        chatroomFrame.setSize(500,250);
        chatroomFrame.setContent(chatroom1);
        desktop.addFrame(chatroomFrame);
        chatroomFrame.setVisible(true);
        chatroomFrame.setTitleIcon(new Image("images/chat.png"));
        GwmUtilities.diplayAtScreenCenter(chatroomFrame);
        chatroomFrame.setLocation(100, 100);
        
		//LoginPanel loginPanel = new LoginPanel();
		//public LoginDialogBox(final LoginPanel.LoginListener loginListener) {

        final Wizard wizard = new Wizard();
        wizard.add("New Project", (Widget) new HTML("Create here a project"), false, true, true, false);
        wizard.add("New Project", (Widget) new HTML("bla, bla, bla"), true, true, true, false);
        wizard.add("New Project", (Widget) new HTML("End"), true, true, true, true);
        
        final KuneFrame wizardFrame = new KuneFrame("Wizard example");
        wizardFrame.setFrame(true, true, false, true, true);
        wizardFrame.setContent(wizard);
        //desktop.addFrame(wizardFrame);
        //wizardFrame.setVisible(true);
        //GwmUtilities.diplayAtScreenCenter(wizardFrame);
        wizardFrame.addFrameListener(new GFrameAdapter() {   	
               public void frameResized(GFrameEvent evt) {
            	   wizard.setSize(wizardFrame.getWidth(), wizardFrame.getHeight());
            	   }
        });
        
        dropDownPanel.setContent(new HTML("Lorem ipsum dolor sit amet,<br>consectetuer adipiscing elit."));
        dropDownPanel.setContentVisible(true);
        dropDownPanel.setTitle("Members");
        dropDownPanel.setColor("87DECD");
        
        Rate rate = new Rate();
        rate.addRate(4);
        rate.addRate(3);
        RateDialog rateTestWidget = new RateDialog(rate);
        
        this.groupCenterVP.add(rateTestWidget);
        rate.addRate(3);
        rate.addRate(2);
        rate.addRate(2);
        
        Rate rate2 = new Rate();
        RateDialog rateTestWidget2 = new RateDialog(rate2);
        this.groupCenterVP.add(rateTestWidget2);

        
        RateItDialog rateItTestWidget = new RateItDialog(rate2);
        this.groupCenterVP.add(rateItTestWidget);   
        
	}
}
