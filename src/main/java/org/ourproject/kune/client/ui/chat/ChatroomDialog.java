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

package org.ourproject.kune.client.ui.chat;

import java.util.Vector;

//import org.gwtwidgets.client.ui.EditableLabel;
import org.ourproject.kune.client.Img;
import org.ourproject.kune.client.Trans;
import org.ourproject.kune.client.rpc.ServiceXmppMucIResponse;
import org.ourproject.kune.client.rpc.ServiceXmppMucServiceManager;
import org.ourproject.kune.client.ui.HorizontalLine;
import org.ourproject.kune.client.ui.desktop.SiteMessageDialog;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.Widget;

public class ChatroomDialog extends Composite implements ChatroomView {
	
	private Vector userList = null;

	private VerticalPanel chatroomVP = null;

	private HorizontalPanel subjectHP = null;
	
	private String chatroomSubject = null;
	
	//private EditableLabel subjectLabel = null;
	private Label subjectLabel = null;

	private VerticalSplitPanel contentSP = null;

	private HorizontalSplitPanel conversationUsersSP = null;

	private ScrollPanel conversationSP = null;

	private VerticalPanel conversationVP = null;

	private ScrollPanel usersSP = null;
	
	private VerticalPanel usersVP = null;

	private VerticalPanel inputOptionsVP = null;

	private TextArea inputTextArea = null;

	private HorizontalPanel optionsHP = null;

	private Image optionsImage = null;

	private HTML optionsSpaceHtml1 = null;

	private Hyperlink optionsHyperlink = null;

	private HTML optionsSpaceExpandHtml = null;

	private Hyperlink maximizeHyperlink = null;

	private HTML optionsSpaceHtml2 = null;

	private Image maximizeImage = null;
	
	private ServiceXmppMucIResponse serviceResponse = null;

	public ChatroomDialog() {
		super();
		initialize();
		layout();
		setProperties();
	}
		
	public void addUser(ChatroomUser user) {
		Grid userGrid = new Grid(1, 2);
		Label userNameLabel = new Label(user.getNickName());
		Image userModeratorIcon = new Image();
		
		userList.add(user);
		if (user.getIsModerator()) {
			Img.ref().bulletStar().applyTo(userModeratorIcon);
		}
		else {
			Img.ref().bulletBlack().applyTo(userModeratorIcon);
		}
		userGrid.setWidget(0, 0, userModeratorIcon);	
		userGrid.setWidget(0, 1, userNameLabel);
		userGrid.setBorderWidth(0);
		userGrid.setCellSpacing(0);
		usersVP.add(userGrid);
	}

	public void addToConversation(ChatroomUser user, HTML chat) {
		HorizontalPanel userChat = new HorizontalPanel();

		userChat.setBorderWidth(0);
		userChat.setSpacing(0);
		userChat.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		
		userChat.add(new HTML("<b>" + user.getNickName() + "</b>: "));
		userChat.add(chat);
		
		conversationVP.add(userChat);
		conversationSP.setScrollPosition(151);
	}
	
	public void addTimeDelimiter() {
		conversationVP.add(new HorizontalLine());
	}
	
	public void clearConversation() {
		conversationVP.clear();
	}

	public void delUser(ChatroomUser user) {
		// TODO
	}
	
	protected void initialize() {
		userList = new Vector();
		chatroomVP = new VerticalPanel();
		subjectHP = new HorizontalPanel();
		subjectLabel = new Label();
//		subjectLabel = new EditableLabel ("", new ChangeListener() {
//			public void onChange(Widget sender) {
//				// TODO Only for tests (put this out of ChatroomDialog) ...
//				ServiceXmppMucServiceManager.INSTANCE.requestChangeSubject(subjectLabel.getText(), new ServiceXmppMucIResponse() {
//					public void accept(Object result) {
//						//TODO
//						SiteMessageDialog.get().setMessageInfo("Success in subject change");
//					}
//					public void failed(Throwable caught) {
//						//TODO
//						SiteMessageDialog.get().setMessageError("Error on subject change");
//					}
//				});
//			}
//		}, Trans.constants().Change(), Trans.constants().Cancel());
		contentSP = new VerticalSplitPanel();
		conversationUsersSP = new HorizontalSplitPanel();
		conversationSP = new ScrollPanel();
		conversationVP = new VerticalPanel();
		conversationVP
			.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		usersSP = new ScrollPanel();
		usersVP = new VerticalPanel();
		inputOptionsVP = new VerticalPanel();
		inputTextArea = new TextArea();
		optionsHP = new HorizontalPanel();
        optionsHP
            .setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		optionsImage = new Image();
		optionsSpaceHtml1 = new HTML();
		optionsHyperlink = new Hyperlink();
		optionsSpaceExpandHtml = new HTML();
		maximizeHyperlink = new Hyperlink();
		optionsSpaceHtml2 = new HTML();
		maximizeImage = new Image();
	}

	protected void layout() {
		initWidget(chatroomVP);

		chatroomVP.add(subjectHP);
		chatroomVP.add(contentSP);
		subjectHP.add(subjectLabel);

		contentSP.setTopWidget(conversationUsersSP);
		contentSP.setBottomWidget(inputOptionsVP);

		conversationUsersSP.setLeftWidget(conversationSP);
		conversationUsersSP.setRightWidget(usersSP);

		conversationSP.add(conversationVP);
		usersSP.add(usersVP);

		inputOptionsVP.add(inputTextArea);
		inputOptionsVP.add(optionsHP);

		optionsHP.add(optionsImage);
		optionsHP.add(optionsSpaceHtml1);
		optionsHP.add(optionsHyperlink);
		optionsHP.add(optionsSpaceExpandHtml);
		optionsHP.add(maximizeHyperlink);
		optionsHP.add(optionsSpaceHtml2);
		optionsHP.add(maximizeImage);
	}
	
	public void permitSubjectChange(boolean permit) {
		// subjectLabel.setEditable(permit);
	}
	
	protected void setProperties() {

		chatroomVP.setBorderWidth(0);
		chatroomVP.setSpacing(5);

		subjectHP.setBorderWidth(0);
		subjectHP.setSpacing(0);
		subjectHP.setStyleName("kune-chatroom-subject");
		subjectHP.setWidth("99%");
		subjectHP.setHeight("20");
		subjectLabel.setWidth("100%");
		// subjectLabel.setVisibleLength(65);

		//contentVP.setBorderWidth(0);
		//contentVP.setSpacing(0);
		contentSP.addStyleName("kune-chatroom-content-outter");
		contentSP.setStyleName("kune-chatroom-content-outter");

		//conversationUsersSP.setBorderWidth(0);
		//conversationUsersSP.setSpacing(0);
		conversationUsersSP
				.addStyleName("kune-chatroom-content-inner-up");
		conversationUsersSP
				.setStyleName("kune-chatroom-content-inner-up");
		conversationUsersSP.setSplitPosition("80%");

		conversationSP
				.addStyleName("kune-chatroom-content-conversation");
		conversationSP
				.setStyleName("kune-chatroom-content-conversation");
		conversationSP.setHeight("100%");
		conversationSP.setWidth("100%");
		
		conversationVP.setBorderWidth(0);
		conversationVP.setSpacing(0);
		
		usersSP.setWidth("100%");
		usersSP.setHeight("100%");
		usersVP.setBorderWidth(0);
		usersVP.setSpacing(0);
		usersVP.addStyleName("kune-chatroom-content-users");
		usersVP.setStyleName("kune-chatroom-content-users");

		inputOptionsVP.setBorderWidth(0);
		inputOptionsVP.setSpacing(0);
		inputOptionsVP.addStyleName("kune-chatroom-content-inner-down");
		inputOptionsVP.setStyleName("kune-chatroom-content-inner-down");
		inputOptionsVP.setWidth("100%");

		inputTextArea.setStyleName("kune-chatroom-content-inner");
		inputTextArea.setHeight("100%");
		inputTextArea.setWidth("100%");

		optionsHP.setCellWidth(optionsSpaceExpandHtml, "100%");
		optionsHP.setBorderWidth(0);
		optionsHP.setSpacing(0);

		Img.ref().kunePreferences().applyTo(optionsImage);
		
		optionsSpaceHtml1.setHTML("<b></b>");
		optionsSpaceHtml1.setWidth("5");

		optionsHyperlink.setText(Trans.constants().Options());

		optionsSpaceExpandHtml.setHTML("&nbsp;");
		optionsSpaceExpandHtml.setWidth("100%");

		maximizeHyperlink.setText(Trans.constants().ExternalWindow());

		optionsSpaceHtml2.setHTML("<b></b>");
		optionsSpaceHtml2.setWidth("5");
        
		Img.ref().tangoGtkFullscreen().applyTo(maximizeImage);
					
	}
	
	public void setSubject(String chatroomSubject) {
		this.chatroomSubject = chatroomSubject;
		subjectLabel.setText(this.chatroomSubject);
	}
	
	public void setUsers(Vector userList) {
		// TODO
	}
	
	public void showActivityInTitle(boolean activity) {
		if (activity) {
			subjectLabel.setText("(*) " + this.chatroomSubject);	
		}
		else {
			subjectLabel.setText(this.chatroomSubject);
		}
	}
}
