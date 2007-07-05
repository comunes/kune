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

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.gwm.client.GDesktopPane;
import org.gwm.client.event.GFrameAdapter;
import org.gwm.client.event.GFrameEvent;
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
import org.ourproject.kune.client.ui.chat.ConferenceRoomDialogImpl;
import org.ourproject.kune.client.ui.chat.ChatroomUser;
import org.ourproject.kune.client.ui.chat.ConferenceRoomImpl;
import org.ourproject.kune.client.ui.desktop.KuneDesktop;
import org.ourproject.kune.client.ui.desktop.SiteMessageDialog;
import org.ourproject.kune.client.ui.ed.CustomRichTextArea;
import org.ourproject.kune.client.ui.ed.CustomRichTextAreaModel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class Main extends AbsolutePanel implements EntryPoint,
WindowResizeListener {

    private KuneFactory factory;

    private GDesktopPane desktop;

    private VerticalPanel generalVP = null;

    private KuneDesktop kuneDesktopPanel = null;

    private SiteMessageDialog siteMessage = null;

    Tree docTree;

    private CustomRichTextArea area;

    private CustomRichTextAreaModel areaController;

    public Main() {
        super();
        initialize();
        layout();
        setProperties();
        initTest();
    }

    protected void initialize() {
        factory = KuneFactory.get();
        desktop = new DefaultGDesktopPane();
        generalVP = new VerticalPanel();
        kuneDesktopPanel = new KuneDesktop();
        siteMessage = factory.getSiteMessageDialog();
    }

    protected void layout() {
        generalVP.add(kuneDesktopPanel);
        add((Widget) desktop, 0, 0);
        desktop.addWidget(generalVP, 0, 0);
        desktop.addWidget(siteMessage, Window.getClientWidth() * 40 / 100 - 10, 23);
//      add(generalVP, 0, 0);
//      add(siteMessage, Window.getClientWidth() * 40 / 100 - 10, 23);
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
            }

            public void onSuccess(Object result) {
                final KuneDoc doc = (KuneDoc) result;
                final Hyperlink item = new Hyperlink(doc.getName(), false, doc.getName());
                final TreeItem rootItem = new TreeItem(item);
                docTree.addItem(rootItem);
                item.addClickListener(new ClickListener() {
                    public void onClick(Widget sender) {
                        if (sender == item) {
                            showDoc(doc, rootItem);
                        }
                    }
                } );
                getChildren(doc, rootItem);
            }
        });
    }

    private void addChild(KuneDoc parent, TreeItem item) {
        KuneDocumentServiceAsync docService = KuneDocumentService.App.getInstance();
        final TreeItem parentItem = item;
        // FIXME
        docService.createDocument(parent, "test", new AsyncCallback () {

            public void onFailure(Throwable exception) {
                SiteMessageDialog.get().setMessageError("No se ha podido crear hijo en el servidor: " + exception.toString());
            }

            public void onSuccess(Object result) {
                addDocTree((KuneDoc) result, parentItem);
            }

        });
    }

    private void saveRootDocument(String content) {
        KuneDocumentServiceAsync docService = KuneDocumentService.App.getInstance();
        //FIXME
        KuneDoc doc = new KuneDoc();
        doc.setContent(content);
        docService.setRootDocument("yellow submarine", doc, new AsyncCallback () {

            public void onFailure(Throwable exception) {
                SiteMessageDialog.get().setMessageError("No se ha podido salvar el contenido en el servidor (se reintentará): " + exception.toString());
                areaController.afterFailedSave();
            }

            public void onSuccess(Object result) {
                SiteMessageDialog.get().setMessageInfo("Document saved");
                areaController.afterSaved();
            }

        });
    }

    private void showDoc(KuneDoc doc, TreeItem item) {
        final KuneDoc docShow = doc;
        final TreeItem parentItem = item;
        kuneDesktopPanel.contextContents.clear();
        kuneDesktopPanel.contextContents.add(new HTML(docShow.getContent()));

        CustomPushButton editButton = new CustomPushButton("Edit", CustomPushButton.SMALL, new ClickListener() {
            public void onClick(Widget sender) {
                editDoc(docShow);
            }
        });
        CustomPushButton addChildButton = new CustomPushButton("AddChild", CustomPushButton.SMALL, new ClickListener() {
            public void onClick(Widget sender) {
                addChild(docShow, parentItem);
            }
        });

        kuneDesktopPanel.contextContents.add(editButton);
        kuneDesktopPanel.contextContents.add(addChildButton);
    }

    private void editDoc(KuneDoc doc) {
        final KuneDoc docEdit = doc;

        kuneDesktopPanel.contextContents.clear();
        areaController = new CustomRichTextAreaModel();
        area = new CustomRichTextArea(areaController);
        kuneDesktopPanel.contextContents.add(new BorderPanel(area, 0, 5, 0, 0));
        areaController.init(docEdit.getContent(), area, true, new Command() {
            public void execute() {
                saveDoc(docEdit, area.getHTML());
            }
        });
    }

    private void saveDoc(KuneDoc doc, String content) {
        // FIXME: save any other doc
        saveRootDocument(content);
    }

    public void editTest() {
        kuneDesktopPanel.contextContents.clear();
        loadRootDocument();
    }

    public void styleTest() {
        kuneDesktopPanel.contextContents.clear();
        kuneDesktopPanel.contextContents.add(new HTML("<h1>Some tests</h1>"));
        kuneDesktopPanel.contextTitle.setText(Trans.constants().Text());

        // RTE Test
        kuneDesktopPanel.contextContents.add(new HTML("<p><b>Rich Text Editor:</b></p>"));
        CustomRichTextAreaModel areaController = new CustomRichTextAreaModel();
        CustomRichTextArea area = new CustomRichTextArea(areaController);
        kuneDesktopPanel.contextContents.add(new BorderPanel(area, 0, 5, 0, 0));
        areaController.init("Hard coded in main.java only for tests", area, true, new Command() {
            public void execute() {
                // do nothing
            }
        });

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

        siteMessage.setMessageImp("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. Nunc sit amet neque. Ut id dui. Integer viverra feugiat sem. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. Nunc sit amet neque. Ut id dui. Integer viverra feugiat sem. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. Nunc sit amet neque. Ut id dui. Integer viverra feugiat sem. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. Nunc sit amet neque. Ut id dui. Integer viverra feugiat sem.");

    }

    public void sandbox() {
        kuneDesktopPanel.contextContents.clear();
        KuneDefaultFrame chatroomFrame = new KuneDefaultFrame();
        ConferenceRoomImpl chatroomControler = new ConferenceRoomImpl();
        ConferenceRoomDialogImpl chatroom1 = new ConferenceRoomDialogImpl(chatroomControler);
        chatroomControler.init(chatroom1);
        chatroom1.setSubject("Welcome to sometopic-foorganization chat room");
        ChatroomUser lutherb = new ChatroomUser("luther.b", true);
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
        chatroom1.addUser(lutherb);
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
        chatroom1.delUser(anneh13);
        chatroom1.addToConversation("luther.b", new HTML("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. Nunc sit amet neque. Ut id dui."));
        chatroom1.addToConversation("anne.h", new HTML("Lorem ipsum dolor sit amet?"));
        chatroom1.addToConversation("luther.b", new HTML("yes, lorem ipsum dolor sit amet"));
        Date date = new Date(2007, 07, 04, 0, 52);
        chatroom1.addTimeDelimiter(date.toLocaleString());
        chatroom1.addToConversation("luther.b", new HTML("Lorem ipsum dolor sit amet"));
        chatroom1.addToConversation("anne.h2", new HTML("Lorem ipsum dolor sit amet?"));
        chatroom1.addToConversation("anne.h3", new HTML("Lorem ipsum dolor sit amet?"));
        chatroom1.addToConversation("anne.h9", new HTML("Lorem ipsum dolor sit amet?"));
        chatroom1.addToConversation("anne.h8", new HTML("Lorem ipsum dolor sit amet?"));
        chatroom1.addToConversation("luther.b", new HTML("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. Nunc sit amet neque. Ut id dui."));
        chatroom1.addToConversation("anne.h", new HTML("Lorem ipsum dolor sit amet?"));
//        DialogBox chatDialog = new DialogBox();
//        chatDialog.setText(Trans.constants().Chatroom() + " " + "sometopic-foorganization@kune.ourproject.org");
//        chatDialog.setWidget(chatroom1);
//        desktop.addWidget(chatDialog, 20, 20);

        chatroomFrame.setCaption(Trans.constants().Chatroom() + " " + "sometopic-foorganization@kune.ourproject.org");
        chatroomFrame.setFrame(true, true, true, true, true);
        chatroomFrame.setSize(500, 250);
        chatroomFrame.setContent(chatroom1);
        desktop.addFrame(chatroomFrame);
        chatroomFrame.setVisible(true);
        chatroomFrame.setTitleIcon(new Image("images/chat.png"));
        GwmUtilities.diplayAtScreenCenter(chatroomFrame);
        chatroomFrame.setLocation(100, 100);
        //chatroomFrame.setMaximumHeight(250);
        chatroom1.adjustSize(500, 250);
        chatroomFrame.addFrameListener(new GFrameAdapter() {
            public void frameClosed(GFrameEvent evt) {
                // TODO Auto-generated method stub

            }

            public void frameMaximized(GFrameEvent evt) {
                // TODO Auto-generated method stub
                //chatroom1.adjustSize(500, 250);
            }

            public void frameResized(GFrameEvent evt) {
                // TODO Auto-generated method stub

            }

        } );



//      final Wizard wizard = new Wizard();
//      wizard.add("New Project", (Widget) new HTML("Create here a project"), false, true, true, false);
//      wizard.add("New Project", (Widget) new HTML("bla, bla, bla"), true, true, true, false);
//      wizard.add("New Project", (Widget) new HTML("End"), true, true, true, true);

//      final KuneDefaultFrame wizardFrame = new KuneDefaultFrame("Wizard example");
//      wizardFrame.setFrame(true, true, false, true, true);
//      wizardFrame.setContent(wizard);
//      desktop.addFrame(wizardFrame);
//      wizardFrame.setVisible(true);
//      GwmUtilities.diplayAtScreenCenter(wizardFrame);
//      wizardFrame.addFrameListener(new GFrameAdapter() {
//      public void frameResized(GFrameEvent evt) {
//      wizard.setSize(wizardFrame.getWidth(), wizardFrame.getHeight());
//      }
//      });

        kuneDesktopPanel.contextContents.add(new HTML(Trans.t("Cancel")));
        kuneDesktopPanel.contextContents.add(new HTML(Trans.t("Prueba de algo inexistente")));

    }

    private void initTest() {
        Group group = new Group("yellowsub", "The Yellow Submarine Environmental Initiative");
        User user = new User("luther.b");
        user.setId((long) 1);
        Session session = new Session();
        session.currentUser = user;
        session.currentGroup = group;
        kuneDesktopPanel.contextBottomBar.setGroup(group);
        kuneDesktopPanel.entityLogo.setDefaultText(group.getLongName());

        kuneDesktopPanel.localNavBar.addItem(Trans.constants().Home(), "home");
        kuneDesktopPanel.localNavBar.addItem(Trans.constants().Blogs(), "blogs");
        kuneDesktopPanel.localNavBar.addItem(Trans.constants().Forums(), "forums");
        kuneDesktopPanel.localNavBar.selectItem(0);

        kuneDesktopPanel.contextDropDowns.addDropDown("Members", new HTML("Lorem ipsum dolor sit amet,<br>consectetuer adipiscing elit."), true, "87DECD");

        final Hyperlink sandboxLink = new Hyperlink("Sandbox", false, "sandbox");
        sandboxLink.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                if (sender == sandboxLink) {
                    sandbox();
                }
            }
        } );

        final Hyperlink styleTestLink = new Hyperlink("Style tests", false, "styletest");
        styleTestLink.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                if (sender == styleTestLink) {
                    styleTest();
                }
            }
        } );

        this.kuneDesktopPanel.contextNavBar.add(sandboxLink);
        this.kuneDesktopPanel.contextNavBar.add(styleTestLink);
        docTree = new Tree();
        this.kuneDesktopPanel.contextNavBar.add(docTree);
        loadRootDocument();
    }

    private void getChildren(KuneDoc parent, TreeItem item) {
        KuneDocumentServiceAsync docService = KuneDocumentService.App.getInstance();
        final TreeItem parentItem = item;
        docService.getChildren(parent, new AsyncCallback() {
            public void onFailure(Throwable exception) {
                SiteMessageDialog.get().setMessageError("No se ha podido recuperar el contenido del servidor: " + exception.toString());
            }

            public void onSuccess(Object result) {
                List childrenDocs = (List) result;
                for (Iterator it = childrenDocs.iterator(); it.hasNext();) {
                    KuneDoc currentDoc = ((KuneDoc) it.next());
                    TreeItem currentItem = addDocTree(currentDoc, parentItem);
                    getChildren(currentDoc, currentItem);
                }
            }
        });
    }

    private TreeItem addDocTree(KuneDoc doc, TreeItem parentItem) {
        final KuneDoc currentDoc = doc;
        final Hyperlink itemLabel = new Hyperlink(currentDoc.getName(), false, currentDoc.getName());
        final TreeItem currentItem = new TreeItem(itemLabel);
        parentItem.addItem(currentItem);
        itemLabel.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                if (sender == itemLabel) {
                    showDoc(currentDoc, currentItem);
                }
            }
        } );
        return currentItem;
    }

}
