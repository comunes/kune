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
import org.gwm.client.impl.DefaultGDesktopPane;
import org.gwm.client.util.Gwm;
import org.gwm.client.util.GwmUtilities;
import org.ourproject.kune.client.model.Group;
import org.ourproject.kune.client.model.License;
import org.ourproject.kune.client.model.Rate;
import org.ourproject.kune.client.model.User;
import org.ourproject.kune.client.rpc.KuneDocumentService;
import org.ourproject.kune.client.rpc.KuneDocumentServiceAsync;
import org.ourproject.kune.client.rpc.dto.KuneDoc;
import org.ourproject.kune.client.ui.BorderPanel;
import org.ourproject.kune.client.ui.CustomPushButton;
import org.ourproject.kune.client.ui.KuneDefaultFrame;
import org.ourproject.kune.client.ui.LicenseWidget;
import org.ourproject.kune.client.ui.RateDialog;
import org.ourproject.kune.client.ui.RateItDialog;
import org.ourproject.kune.client.ui.WebSafePaletteDialog;
import org.ourproject.kune.client.ui.chat.ChatroomDialog;
import org.ourproject.kune.client.ui.chat.ChatroomUser;
import org.ourproject.kune.client.ui.desktop.KuneDesktop;
import org.ourproject.kune.client.ui.desktop.SiteMessageDialog;
import org.ourproject.kune.client.ui.ed.RichTextToolbar;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class Main extends AbsolutePanel implements EntryPoint,
		WindowResizeListener {

	private GDesktopPane desktop;
	
	private VerticalPanel generalVP = null;
	
	private KuneDesktop kuneDesktopPanel = null;
	
	private SiteMessageDialog siteMessage = null;
	
	private WebSafePaletteDialog webSafePalette = null;
	
	private KuneDoc doc;
	
	private RichTextArea area;
    
	private RichTextToolbar tb;
	
	private Timer saveTimer;
	
	private boolean savePending = false;
	
	private KeyboardListener areaKbListener;
	
	private ClickListener areaClickListener;
	

	public Main() {
		super();
		initialize();
		layout();
		setProperties();
		sandbox();
		styleTest();
	}

	protected void initialize() {
        desktop = new DefaultGDesktopPane();
        generalVP = new VerticalPanel();
        kuneDesktopPanel = new KuneDesktop();
        siteMessage = new SiteMessageDialog();
        webSafePalette = new WebSafePaletteDialog();
	}

	protected void layout() {
		generalVP.add(kuneDesktopPanel);
        add((Widget) desktop, 0, 0);
        desktop.addWidget(generalVP, 0, 0);
		desktop.addWidget(siteMessage, Window.getClientWidth() * 40 / 100 - 10, 23);
		desktop.addWidget(webSafePalette, 0, 0);
//		add(generalVP, 0, 0);
//		add(siteMessage, Window.getClientWidth() * 40 / 100 - 10, 23);
	}

	public void onModuleLoad() {
		Element elem = DOM.getElementById("initialstatusbar");
		setVisible(elem, false);
		RootPanel.get().add(this);
	}
	
	public void onWindowResized(int width, int height) {
		kuneDesktopPanel.adjustSize(width, height);
		siteMessage.adjustWidth(width);
		//setWidgetPosition(siteMessage, Window.getClientWidth() * 40 / 100 - 10, 23);
        desktop.setWidgetPosition(siteMessage, Window.getClientWidth() * 40 / 100 - 10, 23);
	}

	protected void setProperties() {
		Gwm.setOverlayLayerDisplayOnDragAction(false);
		desktop.setTheme("alphacubecustom");

		generalVP.setBorderWidth(0);
		generalVP.setSpacing(0);
		generalVP.setHeight("100%");
		generalVP.setWidth("100%");
		
		kuneDesktopPanel.localNavBar.addItem(Trans.constants().Home(), "home");
		kuneDesktopPanel.localNavBar.addItem(Trans.constants().Blogs(), "blogs");
		kuneDesktopPanel.localNavBar.addItem(Trans.constants().Forums(), "forums");
		kuneDesktopPanel.localNavBar.selectItem(0);
		
        kuneDesktopPanel.contextDropDowns.addDropDown("Members", new HTML("Lorem ipsum dolor sit amet,<br>consectetuer adipiscing elit."), true, "87DECD");
        
        
        saveTimer = new Timer() {
        	public void run() {
        		saveRootDocument();
        	}
        };

        area = new RichTextArea();
        tb = new RichTextToolbar(area);

        VerticalPanel ed = new VerticalPanel();
        ed.add(tb);
        ed.add(area);

        area.setHeight("20em");
        area.setWidth("100%");
        ed.setWidth("100%");
        
        // TODO: clickListener in the Toolbar() (now not saving after clicks in the toolbar)
        areaClickListener = new ClickListener() {
        	public void onClick(Widget sender) {
        		if (sender == area) {
        			if (!savePending) {
        				saveTimer.schedule(10000);
        				savePending = true;
        				area.removeKeyboardListener(areaKbListener);
        				area.removeClickListener(areaClickListener);
        			}
        		}
        	}
        };
        
        areaKbListener = new KeyboardListener() {
        	public void onKeyDown(Widget sender, char keyCode, int modifiers) {
        	}

        	public void onKeyPress(Widget sender, char keyCode, int modifiers) {
        	}

        	public void onKeyUp(Widget sender, char keyCode, int modifiers) {
        		if (sender == area) {
        			if (!savePending) {
                        saveTimer.schedule(10000);
                        savePending = true;
                        area.removeKeyboardListener(areaKbListener);
                        area.removeClickListener(areaClickListener);
        			}
        		}
        	}
        };
    
        area.addKeyboardListener(areaKbListener);
        area.addClickListener(areaClickListener);
        		
		kuneDesktopPanel.contextContents.add(new BorderPanel(ed, 0, 5));
        
        loadRootDocument();
        
        
        kuneDesktopPanel.contextContents.add(new HTML("<h1>Some tests</h1>")); 
    	kuneDesktopPanel.contextTitle.setText(Trans.constants().Text());
    	    	
    	kuneDesktopPanel.contextNavBar.add(new HTML("<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros.</p>"));
    	siteMessage.setMessageImp("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. Nunc sit amet neque. Ut id dui. Integer viverra feugiat sem. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. Nunc sit amet neque. Ut id dui. Integer viverra feugiat sem. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. Nunc sit amet neque. Ut id dui. Integer viverra feugiat sem. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. Nunc sit amet neque. Ut id dui. Integer viverra feugiat sem.");
    	
		setSize("100%", "100%");
       
		Window.addWindowResizeListener(this);
		Window.enableScrolling(false);
		onWindowResized(Window.getClientWidth(), Window.getClientHeight());
		
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				onWindowResized(Window.getClientWidth(), Window
						.getClientHeight());
			}
		});
	}

	private void loadRootDocument() {
		KuneDocumentServiceAsync docService = KuneDocumentService.App.getInstance();
		docService.getRootDocument("yellow submarine", new AsyncCallback () {

			public void onFailure(Throwable exception) {
				SiteMessageDialog.get().setMessageError("No se ha podido recuperar el contenido desde el servidor: " + exception.toString());
				area.setEnabled(false);
			}

			public void onSuccess(Object result) {
				doc = (KuneDoc) result;
				setContent(doc.getContent());
				area.setEnabled(true);
			}
			
		});
	}
	
	private void saveRootDocument() {
		KuneDocumentServiceAsync docService = KuneDocumentService.App.getInstance();
        doc.setContent(area.getHTML());
        docService.setRootDocument("yellow submarine", doc, new AsyncCallback () {

			public void onFailure(Throwable exception) {
				SiteMessageDialog.get().setMessageError("No se ha podido salvar el contenido en el servidor (se reintentará): " + exception.toString());
                saveTimer.schedule(20000);
                savePending = true;
			}

			public void onSuccess(Object result) {
				SiteMessageDialog.get().setMessageInfo("Document saved");
                saveTimer.cancel();
				savePending = false;
				area.addKeyboardListener(areaKbListener);
				area.addClickListener(areaClickListener);
			}

		});
	}
	
	private void setContent(String content) {
        area.setHTML(content);
	}

    public void styleTest() {
		// Licenses
		kuneDesktopPanel.contextContents.add(new HTML("<p><b>License tests:</b></p>")); 
		License license = new License();
		LicenseWidget licw1 = new LicenseWidget(license);
		LicenseWidget licw2 = new LicenseWidget(license);
		licw1.setView(true, false, true);
		licw2.setView(true, true, false);
		HorizontalPanel licHR = new HorizontalPanel();
		licHR.add(new Label("License: "));
		licHR.add(licw1);
		licHR.setBorderWidth(0);
		licHR.setSpacing(0);
		kuneDesktopPanel.contextContents.add(licHR);
		kuneDesktopPanel.contextBottomBar.setLicense(license);
		
		// Buttons tests
		kuneDesktopPanel.contextContents.add(new HTML("<p><b>Buttons tests:</b></p>")); 
		kuneDesktopPanel.contextContents.add(new BorderPanel(new CustomPushButton("Large font", CustomPushButton.LARGE), CustomPushButton.VERSPACELARGE, 0));
		kuneDesktopPanel.contextContents.add(new BorderPanel(new CustomPushButton("Small font", CustomPushButton.SMALL), 0, 0, CustomPushButton.VERSPACESMALL, 0));
		kuneDesktopPanel.contextContents.add(new BorderPanel(new CustomPushButton("Mini font", CustomPushButton.MINI), 0, 0, CustomPushButton.VERSPACEMINI, 0));
		CustomPushButton disableButton = new CustomPushButton("Large Disabled", CustomPushButton.LARGE);
		disableButton.setEnabled(false);
		kuneDesktopPanel.contextContents.add(new BorderPanel(disableButton, 0, 0, CustomPushButton.VERSPACELARGE, 0));
		
    	Image helpImageUp = new Image();
    	Image helpImageDown = new Image();
    	Img.ref().buttonHelpLight().applyTo(helpImageUp);
    	Img.ref().buttonHelpBlue().applyTo(helpImageDown);
    	PushButton helpTest = new PushButton(helpImageUp, helpImageDown);
    	kuneDesktopPanel.contextContents.add(new BorderPanel(helpTest, 0, 0, CustomPushButton.SPACEHELPBUTTON, 0));
		
    	// Rate
    	kuneDesktopPanel.contextContents.add(new HTML("<p><b>Rate tests:</b></p>")); 
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

        kuneDesktopPanel.contextContents.add(new HTML("<p><b>RateIt dialog tests:</b></p>")); 
        RateItDialog rateItTestWidget = new RateItDialog(rate2);
        kuneDesktopPanel.contextContents.add(rateItTestWidget);
        
        kuneDesktopPanel.entityLogo.setDefaultText(Session.get().currentGroup.getLongName());
	}
	
	public void sandbox() {
		Group group = new Group("yellowsub", "The Yellow Submarine Environmental Initiative");
		User user = new User("luther.b");
		user.setId((long) 1);
		Session session = new Session();
		session.currentUser = user;
        session.currentGroup = group;
        kuneDesktopPanel.contextBottomBar.setGroup(group);
        kuneDesktopPanel.entityLogo.setDefaultText(group.getLongName());
        
        KuneDefaultFrame chatroomFrame = new KuneDefaultFrame();  
        ChatroomDialog chatroom1 = new ChatroomDialog();
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
//        DialogBox chatDialog = new DialogBox();
//        chatDialog.setText(Trans.constants().Chatroom() + " " + "sometopic-foorganization@kune.ourproject.org");
//        chatDialog.add(chatroom1);
//        chatDialog.show();
        chatroomFrame.setCaption(Trans.constants().Chatroom() + " " + "sometopic-foorganization@kune.ourproject.org");
		chatroomFrame.setFrame(true, false, true, true, true);
        chatroomFrame.setSize(500,250);
        chatroomFrame.setContent(chatroom1);
        desktop.addFrame(chatroomFrame);
        chatroomFrame.setVisible(true);
        chatroomFrame.setTitleIcon(new Image("images/chat.png"));
        GwmUtilities.diplayAtScreenCenter(chatroomFrame);
        chatroomFrame.setLocation(100, 100);
        
//        final Wizard wizard = new Wizard();
//        wizard.add("New Project", (Widget) new HTML("Create here a project"), false, true, true, false);
//        wizard.add("New Project", (Widget) new HTML("bla, bla, bla"), true, true, true, false);
//        wizard.add("New Project", (Widget) new HTML("End"), true, true, true, true);
//        
//        final KuneDefaultFrame wizardFrame = new KuneDefaultFrame("Wizard example");
//        wizardFrame.setFrame(true, true, false, true, true);
//        wizardFrame.setContent(wizard);
//        desktop.addFrame(wizardFrame);
//        wizardFrame.setVisible(true);
//        GwmUtilities.diplayAtScreenCenter(wizardFrame);
//        wizardFrame.addFrameListener(new GFrameAdapter() {   	
//               public void frameResized(GFrameEvent evt) {
//            	   wizard.setSize(wizardFrame.getWidth(), wizardFrame.getHeight());
//            	   }
//        });

	}
}
