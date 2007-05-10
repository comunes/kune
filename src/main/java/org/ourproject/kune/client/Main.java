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
import org.ourproject.kune.client.model.Rate;
import org.ourproject.kune.client.model.User;
import org.ourproject.kune.client.ui.KuneDefaultFrame;
import org.ourproject.kune.client.ui.RateItDialog;
import org.ourproject.kune.client.ui.RateDialog;
import org.ourproject.kune.client.ui.Wizard;
import org.ourproject.kune.client.ui.chat.ChatroomDialog;
import org.ourproject.kune.client.ui.chat.ChatroomUser;
import org.ourproject.kune.client.ui.desktop.KuneDesktop;
import org.ourproject.kune.client.ui.desktop.SiteMessageDialog;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Main extends AbsolutePanel implements EntryPoint,
		WindowResizeListener {

	private VerticalPanel generalVP = null;
	
	private KuneDesktop kuneDesktopPanel = null;
	
	private SiteMessageDialog siteMessage = null;

	// Sandbox variables
	
	private ChatroomDialog chatroom1 = null;

	private GDesktopPane desktop;
	
	public Main() {
		super();
		initialize();
		layout();
		setProperties();
		sandbox();
	}

	protected void initialize() {
		desktop = new DefaultGDesktopPane();
        generalVP = new VerticalPanel();
        kuneDesktopPanel = new KuneDesktop();
        siteMessage = new SiteMessageDialog();
	}

	protected void layout() {
		generalVP.add(kuneDesktopPanel);
		add((Widget) desktop, 0, 0);
		desktop.setWidgetLocation(generalVP, 0, 0);
		desktop.setWidgetLocation(siteMessage, Window.getClientWidth() * 40 / 100, 23);
	}

	public void onModuleLoad() {
		Element elem = DOM.getElementById("initialstatusbar");
		setVisible(elem, false);
		RootPanel.get().add(this);
	}
	
	public void onWindowResized(int width, int height) {
		kuneDesktopPanel.contextContents.adjustSize(width, height);
		siteMessage.adjustWidth(width);
		desktop.setWidgetLocation(siteMessage, Window.getClientWidth() * 40 / 100, 23);
	}

	protected void setProperties() {
		Gwm.setOverlayLayerDisplayOnDragAction(false);
		desktop.setTheme("alphacubecustom");

		generalVP.setBorderWidth(0);
		generalVP.setSpacing(0);
		generalVP.setHeight("100%");
		generalVP.setWidth("100%");
		
		kuneDesktopPanel.entityLogo.setDefaultText("Foo Organization");
		kuneDesktopPanel.localNavBar.addItem(Trans.constants().Home(), "home");
		kuneDesktopPanel.localNavBar.addItem(Trans.constants().Blogs(), "blogs");
		kuneDesktopPanel.localNavBar.addItem(Trans.constants().Forums(), "forums");
		kuneDesktopPanel.localNavBar.selectItem(0);
		
        kuneDesktopPanel.contextDropDowns.addDropDown("Members", new HTML("Lorem ipsum dolor sit amet,<br>consectetuer adipiscing elit."), true, "87DECD");
        kuneDesktopPanel.contextContents.add(new HTML("<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. Nunc sit amet neque. Ut id dui. Integer viverra feugiat sem. Morbi aliquam turpis rhoncus sapien volutpat condimentum. Vestibulum dignissim, risus et ullamcorper sollicitudin, risus mi molestie lectus, ut aliquam nulla dolor eu nisl. Duis volutpat. Sed eget lectus lacinia lacus interdum facilisis. Aliquam tincidunt sem at mi. Duis a ipsum vel turpis volutpat adipiscing. Sed at libero sit amet lacus elementum tempus. Vestibulum sit amet tellus. Duis dolor. Praesent convallis lorem ac metus. Curabitur malesuada pede id dui. Vivamus tincidunt risus vehicula enim. Nulla fermentum. Sed placerat lacus eget erat. Proin dolor enim, aliquam ut, vehicula sit amet, blandit non, arcu. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.\n</p><p>\nLorem ipsum dolor sit amet, consectetuer adipiscing elit. Duis sapien. Suspendisse potenti. Sed imperdiet pulvinar tortor. Ut vel nisi. Nam commodo, mauris vitae congue placerat, mauris eros vulputate odio, ac facilisis erat quam at enim. Cras iaculis pede sit amet dui. Cras arcu. Fusce non orci vitae lacus hendrerit auctor. Aliquam leo.\n</p><p>\nVestibulum orci dolor, hendrerit et, dapibus vel, congue ac, velit. Maecenas est. Nam in velit eget ante consequat vulputate. Nam posuere. Nunc lectus. Vestibulum facilisis. Aliquam elit nunc, facilisis eget, bibendum at, dignissim at, nulla. Sed ullamcorper, mi a eleifend tincidunt, metus tortor ultricies mi, in tempor arcu tellus nec erat. Quisque semper, turpis in gravida suscipit, elit leo sollicitudin risus, vel laoreet velit mi a massa. Aliquam non nulla a sapien dapibus bibendum. Sed auctor neque vel justo. Etiam cursus. Nunc eget lectus. In euismod urna vitae dui luctus consequat. Nunc cursus vulputate erat. Duis vel justo vel ante imperdiet rutrum. Curabitur eget turpis ac pede interdum accumsan. Ut velit.\n</p><p>\nProin vitae eros ut pede lacinia aliquam. Praesent in metus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aenean sed eros. Phasellus id risus. Vivamus non nunc eget purus feugiat sagittis. Mauris id tortor ut lectus mollis porttitor. Fusce lobortis leo quis augue suscipit tincidunt. Ut tristique, nunc at egestas blandit, sem leo tincidunt nibh, id tempor neque elit vitae tortor. Nulla sapien est, suscipit sed, aliquam eget, viverra suscipit, justo.\n</p>"));
        
    	kuneDesktopPanel.contextTitle.setText(Trans.constants().Text());
    	kuneDesktopPanel.contextBottomBar.setText("(c) Foo organization, contents under some free/open Commons license");
    	kuneDesktopPanel.contextNavBar.add(new HTML("<b>Menu</b>"));
    	siteMessage.setMessageImp("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. Nunc sit amet neque. Ut id dui. Integer viverra feugiat sem. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. Nunc sit amet neque. Ut id dui. Integer viverra feugiat sem. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. Nunc sit amet neque. Ut id dui. Integer viverra feugiat sem. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. Nunc sit amet neque. Ut id dui. Integer viverra feugiat sem.");

		setSize("100%", "100%");
        
		Window.addWindowResizeListener(this);
		Window.enableScrolling(false);

		DeferredCommand.addCommand(new Command() {
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
			
        KuneDefaultFrame chatroomFrame = new KuneDefaultFrame(); 
        
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
		chatroomFrame.setFrame(true, false, true, true, true);
        chatroomFrame.setSize(500,250);
        chatroomFrame.setContent(chatroom1);
        desktop.addFrame(chatroomFrame);
        chatroomFrame.setVisible(true);
        //chatroomFrame.setTitleIcon(new Image("images/chat.png"));
        GwmUtilities.diplayAtScreenCenter(chatroomFrame);
        chatroomFrame.setLocation(100, 100);
        
		//LoginPanel loginPanel = new LoginPanel();
		//public LoginDialogBox(final LoginPanel.LoginListener loginListener) {

        final Wizard wizard = new Wizard();
        wizard.add("New Project", (Widget) new HTML("Create here a project"), false, true, true, false);
        wizard.add("New Project", (Widget) new HTML("bla, bla, bla"), true, true, true, false);
        wizard.add("New Project", (Widget) new HTML("End"), true, true, true, true);
        
        final KuneDefaultFrame wizardFrame = new KuneDefaultFrame("Wizard example");
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
        
        Rate rate = new Rate();
        rate.addRate(4);
        rate.addRate(3);
        RateDialog rateTestWidget = new RateDialog(rate);
        
        kuneDesktopPanel.contextContents.add(rateTestWidget);
        rate.addRate(3);
        rate.addRate(2);
        rate.addRate(2);
        
        Rate rate2 = new Rate();
        RateDialog rateTestWidget2 = new RateDialog(rate2);
        kuneDesktopPanel.contextContents.add(rateTestWidget2);

        
        RateItDialog rateItTestWidget = new RateItDialog(rate2);
        kuneDesktopPanel.contextContents.add(rateItTestWidget);   
        
	}
}
