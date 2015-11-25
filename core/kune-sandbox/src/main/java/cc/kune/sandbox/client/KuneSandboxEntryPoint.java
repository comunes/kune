/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.sandbox.client;

import java.util.Date;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.Badge;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.DropDown;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.ListDropDown;
import org.gwtbootstrap3.client.ui.Navbar;
import org.gwtbootstrap3.client.ui.NavbarCollapse;
import org.gwtbootstrap3.client.ui.NavbarCollapseButton;
import org.gwtbootstrap3.client.ui.NavbarHeader;
import org.gwtbootstrap3.client.ui.NavbarNav;
import org.gwtbootstrap3.client.ui.base.button.CustomButton;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.NavbarPosition;
import org.gwtbootstrap3.client.ui.constants.Toggle;
import org.gwtbootstrap3.client.ui.html.Text;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.WhiteSpace;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import br.com.rpa.client._paperelements.PaperFab;
import br.com.rpa.client.api.widgets.paper.PaperButton;
import cc.kune.bootstrap.client.ui.CheckListItem;
import cc.kune.bootstrap.client.ui.ComplexAnchorListItem;
import cc.kune.bootstrap.client.ui.ComplexDropDownMenu;
import cc.kune.bootstrap.client.ui.DropDownSubmenu;
import cc.kune.bootstrap.client.ui.RadioListItem;
import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.BaseAction;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.Shortcut;
import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.FlowActionExtensible;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.IconLabelDescriptor;
import cc.kune.common.client.actions.ui.descrip.LabelDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuCheckItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuTitleItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.PushButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.SubMenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarMenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.common.client.actions.ui.descrip.WidgetMenuDescriptor;
import cc.kune.common.client.notify.NotifyLevelImages;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.notify.SimpleUserMessage;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.BasicThumb;
import cc.kune.common.client.ui.BlinkAnimation;
import cc.kune.common.client.ui.DottedTab;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.client.ui.PopupTopPanel;
import cc.kune.common.client.ui.WrappedFlowPanel;
import cc.kune.common.client.ui.dialogs.BSBasicDialog;
import cc.kune.common.client.ui.dialogs.BasicDialog;
import cc.kune.common.client.ui.dialogs.BasicTopDialog;
import cc.kune.common.client.ui.dialogs.MessagePanel;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.res.KuneIcon;
import cc.kune.common.shared.utils.LoremUtils;
import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.ui.UploaderPanel;
import cc.kune.core.client.ui.dialogs.PromptTopDialog;
import cc.kune.core.client.ui.dialogs.PromptTopDialog.Builder;
import cc.kune.core.client.ui.dialogs.PromptTopDialog.OnEnter;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneSandboxEntryPoint implements EntryPoint {
  /**
   * The Class TestAction.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public class TestAction extends AbstractExtendedAction {

    /**
     * Instantiates a new test action.
     *
     * @param text
     *          the text
     */
    public TestAction(final String text) {
      super(text);
    }

    /**
     * Instantiates a new test action.
     *
     * @param text
     *          the text
     * @param tooltip
     *          the tooltip
     * @param icon
     *          the icon
     */
    public TestAction(final String text, final String tooltip, final String icon) {
      super(text, tooltip, icon);
    }

    /*
     * (non-Javadoc)
     *
     * @see cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune
     * .common.client.actions.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event) {
      // We test two types of dialogs
      if (((new Date().getTime()) & 1) == 0) {
        testDialog();
      } else {
        testPromptDialog();
      }
    }

  }

  /** The absolute panel. */
  private AbsolutePanel absolutePanel;

  final AbstractExtendedAction action1 = new AbstractExtendedAction() {
    @Override
    public void actionPerformed(final ActionEvent event) {
      // final SimpleUserMessage simpleMes = new SimpleUserMessage();
      // simpleMes.show("Hellow world!");
      NotifyUser.showProgress("Savingggg");
      NotifyUser.askConfirmation("Some title", "Some message", "Yeah!", "Nein",
          new SimpleResponseCallback() {
        @Override
        public void onCancel() {
          NotifyUser.error("Cancel");
        }

        @Override
        public void onSuccess() {
          NotifyUser.info("Success");
        }
      });
    }
  };

  final AbstractExtendedAction action2 = new AbstractExtendedAction() {
    @Override
    public void actionPerformed(final ActionEvent event) {
      final String title = "Some title";
      final String message = "Lorem <a href='/'>ipsum</a> dolor sit amet, consectetuer adipiscing elit. Lorem ipsum dolor sit amet, consectetuer adipiscing elit";
      NotifyUser.showProgress("Saving......" + new Date().getTime());
      NotifyUser.avatar("http://lorempixel.com/200/200", message, new ClickHandler() {
        @Override
        public void onClick(final ClickEvent event) {
          NotifyUser.info("On click");
        }
      });
      NotifyUser.error(title, message, true);
      NotifyUser.important(message);
      NotifyUser.info(message);
      NotifyUser.success(title, message);
    }
  };

  /** The ginjector. */
  private KuneSampleGinjector ginjector;

  private MainContainer mainContainer;

  /** The res. */
  private CommonResources res;
  /** The shortcut register. */
  private GlobalShortcutRegister shortcutRegister;

  /** The user msg. */
  SimpleUserMessage userMsg = new SimpleUserMessage();

  public void addNotifyUserTests(final FlowPanel flow) {

    flow.add(new CustomButton("Show progress", IconType.PLAY_CIRCLE, new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        NotifyUser.showProgressLoading();
      }
    }));

    flow.add(new CustomButton("Hide progress", IconType.STOP, new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        NotifyUser.hideProgress();
      }
    }));

    flow.add(new CustomButton("Show date", IconType.CLOCK_O, new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        NotifyUser.showProgress("Date : " + new Date().getTime());
      }
    }));

    flow.add(new CustomButton("Important message", IconType.WARNING, new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        NotifyUser.important("Alert!");
      }
    }));

    flow.add(new CustomButton("Error message", IconType.QUESTION_CIRCLE, new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        NotifyUser.error("Title", "Error!", false);
      }
    }));
  }

  private void addSimpleActions(final FlowPanel flow) {
    final ButtonDescriptor button1 = new ButtonDescriptor("button desc 1", action1);
    final ButtonDescriptor button2 = new ButtonDescriptor("button desc 2 but bigger bigger", action2);
    final ButtonDescriptor button3 = new ButtonDescriptor(action1);

    button1.withIcon(res.info()).withToolTip("Some tooltip");
    button2.withIcon(res.info()).withToolTip("Some tooltip");
    button3.withIcon(IconType.TWITTER).withToolTip("Some tooltip"); // .withStyles("k-button,gwt-Button,k-btn-min");
    final IconLabelDescriptor iconLabel = new IconLabelDescriptor("This does not work?");
    iconLabel.setAction(action1);
    iconLabel.withText("Icon Label 1");
    iconLabel.withIcon(KuneIcon.FOLDER);
    final FlowActionExtensible flowactions = new FlowActionExtensible();
    flowactions.add(iconLabel, button1, button2, button3);
    flow.add(flowactions);
  }

  public void basicTests() {

    // final Growl growl = UserNotifierGrowl.showProgress("Loading...");

    NotifyUser.showProgress("Starting...");

    testTooltips();

    // absolutePanel.add(testActionToolbar(), 200, 200);

    final String defLocale = "en";

    final String locale = WindowUtils.getParameter("locale");
    final String[] ids = new String[] { "summary", "ini", "footer", "kuneloading-msg" };

    for (final String id : ids) {
      final RootPanel someId = RootPanel.get("k-home-" + id + "-" + locale);
      final RootPanel defId = RootPanel.get("k-home-" + id + "-" + defLocale);
      if (someId != null) {
        someId.setVisible(true);
      } else if (defId != null) {
        defId.setVisible(true);
      }
    }

    // testToolpanel();
    // toolSelector.addWidget(new Label("Test"));
    // testPromptDialog();

    testSubWidget();

    final ActionFlowPanel view = makeFlowToolbar();

    final BasicThumb thumb = testThumbs();

    absolutePanel.add(thumb, 200, 10);
    absolutePanel.add(view, 5, 150);

    final DottedTab tab = new DottedTab();
    absolutePanel.add(tab, 400, 400);
    absolutePanel.add(tab, 400, 400);
    absolutePanel.add(makeFileUpload(), 520, 0);
    // absolutePanel.add(new InboxTutorial(), 0, 300);

    new BlinkAnimation(tab, 350).animate(5);

    RootPanel.get().add(absolutePanel);

  }

  public void completeTests() {
    NotifyUser.info("Started");

    final Navbar simpleNavbar = new Navbar();
    final NavbarHeader header = new NavbarHeader();
    final NavbarCollapseButton navbarCollapseButton = new NavbarCollapseButton();
    navbarCollapseButton.setDataTarget("#test");
    header.add(navbarCollapseButton);
    simpleNavbar.add(header);
    final NavbarCollapse navbarCollapse = new NavbarCollapse();
    navbarCollapse.setId("test");
    simpleNavbar.add(navbarCollapse);

    final NavbarNav navbarNav = new NavbarNav();
    final ComplexDropDownMenu<ListDropDown> listDropDown = new ComplexDropDownMenu<ListDropDown>(
        new ListDropDown());
    listDropDown.setMenuText("Plain menu");
    listDropDown.setIcon(IconType.GEAR);
    navbarCollapse.add(navbarNav);
    navbarNav.add(listDropDown.getWidget());

    final AnchorListItem simpleAnchor = new AnchorListItem("Anchor 1");
    simpleAnchor.setIcon(IconType.TWITTER_SQUARE);
    navbarNav.add(simpleAnchor);

    navbarNav.add(new AnchorListItem("Anchor 2"));
    navbarNav.add(new AnchorListItem("Anchor 3"));
    final AnchorListItem menuitem1 = new AnchorListItem("Anchor menuitem 1");
    menuitem1.setIcon(IconType.RANDOM);

    listDropDown.add(menuitem1);

    final CheckListItem checkitem = new CheckListItem("Check item");
    checkitem.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        checkitem.setChecked(!checkitem.isChecked());
      }
    });
    listDropDown.add(checkitem);

    final RadioListItem radioitem = new RadioListItem("Check item");
    radioitem.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        radioitem.setChecked(!radioitem.isChecked());
      }
    });
    listDropDown.add(radioitem);

    final CheckListItem checkitem2 = new CheckListItem("Check anchor");
    checkitem2.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        checkitem2.setChecked(!checkitem2.isChecked());
      }
    });

    navbarNav.add(checkitem2);
    listDropDown.add(new AnchorListItem("Anchor menuitem 2"));
    final ComplexAnchorListItem menuitem3 = new ComplexAnchorListItem("Anchor menuitem 3");
    menuitem3.setIconUrl("http://lorempixel.com/100/100");
    listDropDown.add(menuitem3);
    final AnchorListItem item1 = new AnchorListItem("Testingggg");
    item1.setIcon(IconType.HEART);
    listDropDown.add(item1);

    for (int i = 1; i < 50; i++) {
      listDropDown.add(new AnchorListItem("Anchor " + i));
    }

    final ComplexAnchorListItem itemSubmenu = new ComplexAnchorListItem("Testing submenu");
    itemSubmenu.setIcon(KuneIcon.FOLDER);
    final DropDownSubmenu submenu = new DropDownSubmenu();
    submenu.add(new AnchorListItem("sub item 1"));
    submenu.add(new AnchorListItem("sub item 2"));
    submenu.add(new AnchorListItem("sub item 3"));
    itemSubmenu.add(submenu);
    listDropDown.add(itemSubmenu);

    final DropDown dropDown = new DropDown();

    final Anchor dropDownAnchor = new Anchor();
    // FIXME This fails:
    // final BasicThumb thumb = testThumbs();
    final Image thumb = new Image("http://lorempixel.com/30/30");
    dropDownAnchor.add(thumb);
    dropDownAnchor.setDataToggle(Toggle.DROPDOWN);
    dropDown.add(dropDownAnchor);

    final DropDownMenu dropDownMenu = new DropDownMenu();
    dropDown.add(dropDownMenu);
    final ComplexAnchorListItem dditem1 = new ComplexAnchorListItem("Test 1");
    final ComplexAnchorListItem dditem2 = new ComplexAnchorListItem("Test 2");
    dditem1.setIcon(KuneIcon.BARTER);
    dditem2.setIconUrl("http://lorempixel.com/101/101");
    dropDownMenu.add(dditem1);
    dropDownMenu.add(dditem2);

    // The same with descriptors

    final Image thumb2 = new Image("http://lorempixel.com/30/30");
    final WidgetMenuDescriptor widgetMenu = new WidgetMenuDescriptor(thumb2);
    new MenuItemDescriptor(widgetMenu, new BaseAction("Menuitem desc in widget 1", "Some tooltip"));
    new MenuItemDescriptor(widgetMenu, new BaseAction("Menuitem desc in widget 2", "Some tooltip"));

    // Now with a button

    final MenuDescriptor classicMenu = new MenuDescriptor("Button classic menu");
    classicMenu.withIcon("http://lorempixel.com/30/30");
    new MenuItemDescriptor(classicMenu, new BaseAction("Menuitem desc in widget 1", "Some tooltip"));
    new MenuItemDescriptor(classicMenu, new BaseAction("Menuitem desc in widget 2", "Some tooltip"));

    final MenuDescriptor classicMenu2 = new MenuDescriptor("Button classic2 menu");
    classicMenu2.withIcon("http://lorempixel.com/30/30");
    new MenuItemDescriptor(classicMenu2, new BaseAction("Menuitem desc in widget 3", "Some tooltip"));
    new MenuItemDescriptor(classicMenu2, new BaseAction("Menuitem desc in widget 4", "Some tooltip"));

    final CustomButton btn = new CustomButton("Text custom button");
    Tooltip.to(btn, "Show the dropdown at 100,100 position (or hide it)");
    btn.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        if (listDropDown.isMenuVisible()) {
          listDropDown.hide();
        } else {
          listDropDown.show(100, 100);
        }
      }
    });
    btn.setIcon(KuneIcon.FOLDER);

    // final cc.kune.bootstrap.client.ui.IconLabel iconLabel = new
    // cc.kune.bootstrap.client.ui.IconLabel(
    // "Text");
    // iconLabel.setIcon(new KuneIcon('g'));

    final FlowPanel flow = mainContainer.getFlow();
    flow.insert(btn, 0);
    flow.insert(new HTML("<br>"), 1);
    flow.insert(dropDown, 2);
    flow.insert(new HTML("<br>"), 3);

    final ToolbarDescriptor bottomToolbar = new ToolbarDescriptor();
    bottomToolbar.setPosition(NavbarPosition.FIXED_BOTTOM);

    final ButtonDescriptor btnDesc = new ButtonDescriptor(new TestAction("Button desc"));
    btnDesc.withIcon(KuneIcon.SETTINGS).withToolTip("Show me a tooltip");
    btnDesc.setParent(bottomToolbar);

    final IconLabelDescriptor labelDesc = new IconLabelDescriptor(new TestAction("IconLabel desc"));
    labelDesc.withIcon(KuneIcon.TASKS).withToolTip("Show me a tooltip");
    labelDesc.setParent(bottomToolbar);

    final MenuDescriptor menu = new MenuDescriptor("Menu btn");
    menu.setParent(bottomToolbar);
    final MenuItemDescriptor menuItemDescriptor = new MenuItemDescriptor(menu,
        new TestAction("Menu item"));
    menuItemDescriptor.withIcon(IconType.MAGIC);

    final SubMenuDescriptor submenuDesc = new SubMenuDescriptor(menu, "Some submenu");
    submenuDesc.withIcon(KuneIcon.BARTER);
    new MenuItemDescriptor(submenuDesc, new TestAction("Submenu item 1")).withIcon(IconType.FACEBOOK);
    new MenuItemDescriptor(submenuDesc, new TestAction("Submenu item 2")).withIcon(
        IconType.FAST_BACKWARD);

    // TODO Add more tests here

    final FlowActionExtensible sitebar = new FlowActionExtensible();
    sitebar.add(createSitebarActions());

    final FlowActionExtensible bottombar = new FlowActionExtensible();
    bottombar.add(bottomToolbar);

    // RootPanel.get().add(testActionToolbar());

    final FlowActionExtensible mainFlow = new FlowActionExtensible();
    mainFlow.add(widgetMenu, classicMenu, classicMenu2);

    final PaperFab paperFab = new PaperFab();
    paperFab.setIcon("polymer");

    mainContainer.getSitebar().add(sitebar);
    mainContainer.getFooter().add(bottombar);
    mainContainer.getFooter().add(paperFab);
    final PaperButton paperBtn = new PaperButton();
    mainContainer.getFooter().add(paperBtn);
    flow.add(mainFlow);
    flow.insert(simpleNavbar, 0);

    testBarButtons(flow);
    addNotifyUserTests(flow);
    addSimpleActions(flow);
    flow.add(new Text(LoremUtils.LOREM_MULTI + LoremUtils.LOREM_MULTI));

    RootLayoutPanel.get().add(mainContainer);
  }

  /**
   * Test action toolbar.
   *
   * @return
   */
  @SuppressWarnings("unused")
  public ToolbarDescriptor createSitebarActions() {

    final ToolbarDescriptor sitebarDesc = new ToolbarDescriptor();

    final ToolbarItemDescriptor anchorItem = new ToolbarItemDescriptor();
    anchorItem.setAction(new AbstractExtendedAction("Item descr") {
      @Override
      public void actionPerformed(final ActionEvent event) {
        mainContainer.next();
      }
    });
    anchorItem.withStyles(ActionStyles.CURRENT_TOOLBAR_ITEM);
    sitebarDesc.setPosition(NavbarPosition.FIXED_TOP);
    anchorItem.withIcon(KuneIcon.KUNE);
    anchorItem.setParent(sitebarDesc);

    final KeyStroke shortcut1 = Shortcut.getShortcut(false, true, false, false, Character.valueOf('C'));
    shortcutRegister.put(shortcut1, action1);
    final KeyStroke shortcut2 = Shortcut.getShortcut(false, true, false, false, Character.valueOf('D'));
    shortcutRegister.put(shortcut2, action2);

    final ToolbarMenuDescriptor menuDesc = new ToolbarMenuDescriptor("Menu descr");
    final ToolbarMenuDescriptor menuDesc2 = new ToolbarMenuDescriptor("Menu descr 2");
    menuDesc.setParent(sitebarDesc);
    menuDesc.withIcon(KuneIcon.GROUP);

    final ToolbarSeparatorDescriptor separator = new ToolbarSeparatorDescriptor(Type.separator,
        sitebarDesc);
    menuDesc2.setParent(sitebarDesc);
    menuDesc2.withIcon(KuneIcon.CHAT);

    new ToolbarSeparatorDescriptor(Type.fill, sitebarDesc);

    final ToolbarMenuDescriptor moreItem = new ToolbarMenuDescriptor("More");
    moreItem.withParent(sitebarDesc).withIcon(IconType.NAVICON);

    final MenuItemDescriptor kunemenuitem = new MenuItemDescriptor(moreItem, action1);
    kunemenuitem.withIcon(KuneIcon.KUNE).withText("About kune");

    menuDesc.withToolTip("Some menu tooltip").withShortcut(shortcut1, shortcutRegister);
    final MenuItemDescriptor menuItem1 = new MenuItemDescriptor(menuDesc, new AbstractAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        NotifyUser.success("Setting separator visibility to false");
        separator.setVisible(false);
      }
    });

    menuItem1.withIcon(res.locationBlack()).withText("Some menu item 1");
    final MenuItemDescriptor menuItem2 = new MenuItemDescriptor(menuDesc, action1);
    menuItem2.withIcon(res.info()).withText("Some menu item 2").withShortcut(shortcut2,
        shortcutRegister);

    final MenuItemDescriptor menuItem3 = new MenuItemDescriptor(menuDesc2, action1);
    menuItem3.withIcon(res.locationBlack()).withText("Some other menu item 1");
    final MenuItemDescriptor menuItem4 = new MenuItemDescriptor(menuDesc2, action1);
    menuItem4.withIcon(res.info()).withText("Some other menu item 2").withShortcut(shortcut2,
        shortcutRegister);

    final MenuCheckItemDescriptor menuCheckItem1 = new MenuCheckItemDescriptor(menuDesc, action1);
    menuCheckItem1.withText("Check item");

    final SubMenuDescriptor submenuDesc = new SubMenuDescriptor("Some Submenu", "tip", "oc-testico");
    submenuDesc.setParent(menuDesc);

    final TestAction action3 = new TestAction("Some menu item 3", "Some tooltip", "oc-testico");
    final TestAction action4 = new TestAction("Action 4");

    final MenuSeparatorDescriptor menuSep = new MenuSeparatorDescriptor(menuDesc);
    final MenuItemDescriptor menuHeaderItem = new MenuTitleItemDescriptor(menuDesc, "Header");
    final MenuItemDescriptor menuItemDesc1 = new MenuItemDescriptor(menuDesc, action3);
    final MenuItemDescriptor menuItemDesc2 = new MenuItemDescriptor(menuDesc, action4);
    final MenuRadioItemDescriptor menuItemDesc3 = new MenuRadioItemDescriptor(submenuDesc, action1,
        "somegroup");
    menuItemDesc3.withText("This text is setted").withShortcut(shortcut1, shortcutRegister);
    final MenuRadioItemDescriptor menuItemDesc4 = new MenuRadioItemDescriptor(submenuDesc, action2,
        "somegroup");
    menuItemDesc4.withText("This text is setted").withShortcut(shortcut2, shortcutRegister);

    return sitebarDesc;
  }

  private void initializeInjector() {
    GWT.setUncaughtExceptionHandler(new SuperDevModeUncaughtExceptionHandler());

    ginjector = GWT.create(KuneSampleGinjector.class);
    res = CommonResources.INSTANCE;
    res.commonStyle().ensureInjected();
    ginjector.getUserNotifierGrowl();
    absolutePanel = new AbsolutePanel();
    shortcutRegister = ginjector.getGlobalShortcutRegister();
    shortcutRegister.enable();
    mainContainer = new MainContainer();
  }

  private Widget makeFileUpload() {
    final UploaderPanel uploadPanel = new UploaderPanel(I18n.t("choose an image to upload"),
        "image/png,image/gif,image/jpeg");
    uploadPanel.setSize("250px", "150px");
    return uploadPanel;
  }

  /**
   * Make flow toolbar.
   *
   * @return the action flow panel
   */
  private ActionFlowPanel makeFlowToolbar() {
    final GuiActionDescCollection actions = new GuiActionDescCollection();

    final TestAction action = new TestAction("Action 1");
    action.withToolTip("Some tooltip").withIcon(res.chainClosedGrey());

    final TestAction action2 = new TestAction("Some action 2");
    action2.withIcon("mail");

    final KeyStroke shortcut = Shortcut.getShortcut(false, true, false, false, Character.valueOf('C'));
    shortcutRegister.put(shortcut, action);
    final KeyStroke shortcut2 = Shortcut.getShortcut(false, true, false, false, Character.valueOf('D'));
    shortcutRegister.put(shortcut2, action2);

    final ButtonDescriptor simpleBtn = new ButtonDescriptor(action);

    // Same action but different text
    simpleBtn.putValue(Action.NAME, "Action 1 diff name");

    final ButtonDescriptor iconBtn = new ButtonDescriptor(new AbstractExtendedAction() {

      @Override
      public void actionPerformed(final ActionEvent event) {
        NotifyUser.info("Click!");
      }
    });
    iconBtn.withIcon("polymer");

    // Same action but different text
    simpleBtn.putValue(Action.NAME, "Action 1 diff name");

    final PushButtonDescriptor pushBtn = new PushButtonDescriptor(action2);
    pushBtn.setPushed(true);
    pushBtn.withText("Push btn");

    final ToolbarDescriptor toolbar = new ToolbarDescriptor();
    final ToolbarSeparatorDescriptor tsepFill = new ToolbarSeparatorDescriptor(Type.fill, toolbar);
    final ToolbarSeparatorDescriptor toolbarSpace = new ToolbarSeparatorDescriptor(Type.spacer, toolbar);

    simpleBtn.setParent(toolbar);
    pushBtn.setParent(toolbar);

    final MenuDescriptor menu = new MenuDescriptor(action);
    menu.withText("Menu 1").withIcon("http://lorempixel.com/100/100");

    final MenuDescriptor menu2 = new MenuDescriptor(action);
    menu2.withText("Menu 2").withIcon(IconType.TREE);

    menu.setParent(toolbar);
    final SubMenuDescriptor submenu = new SubMenuDescriptor("Some Submenu", "tip", "oc-testico");
    submenu.setParent(menu);
    final MenuSeparatorDescriptor menuSep = new MenuSeparatorDescriptor(menu);

    final TestAction action3 = new TestAction("Action 3", "Some tooltip", "oc-testico");
    final TestAction action4 = new TestAction("Action 4");

    final MenuItemDescriptor menuItem = new MenuItemDescriptor(menu, action3);
    final MenuItemDescriptor menuItem2 = new MenuItemDescriptor(menu, action4);
    final MenuItemDescriptor menuItem3 = new MenuItemDescriptor(submenu, action);

    final MenuItemDescriptor menuItem4 = new MenuItemDescriptor(submenu, action2);
    menuItem4.withText("Submenu item 2");
    final IconLabelDescriptor iconLabelDescr = new IconLabelDescriptor(action);
    final IconLabelDescriptor iconLabelNoAct = new IconLabelDescriptor(action4);
    final MenuItemDescriptor menuItem5 = new MenuItemDescriptor(menu2, action);

    action.setShortcut(shortcut);
    action2.setShortcut(shortcut2);

    final LabelDescriptor label = new LabelDescriptor("Some label");

    actions.add(toolbar, simpleBtn, tsepFill, pushBtn, iconBtn, toolbarSpace, menu, tsepFill, menuItem,
        menuItem2, menuSep, menuItem2, menuItem, iconLabelDescr, submenu, menuItem3, menuItem4, menu2,
        iconLabelNoAct, menuItem5, label);

    final ActionFlowPanel view = new ActionFlowPanel(ginjector.getGuiProvider(), ginjector.getHasRTL());
    view.addAll(actions);

    final IconLabel simpleIconLabel = new IconLabel("IconLabel (no action)");
    simpleIconLabel.setRightIcon("oc-testico");
    simpleIconLabel.setTitle("tooltip");

    final VerticalPanel panel = new VerticalPanel();
    panel.setWidth("100%");
    panel.add(view);
    panel.add(simpleIconLabel);
    return view;
  }

  @Override
  public void onModuleLoad() {
    initializeInjector();
    basicTests();
    // completeTests();
  }

  public void onModuleLoadPolymer() {
    initializeInjector();
    NotifyUser.info("Started");
    final WrappedFlowPanel docContent = WrappedFlowPanel.wrap("doc_content");

    final CustomButton btn = new CustomButton("Sign in");
    btn.setIcon(KuneIcon.KUNE);
    btn.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        NotifyUser.info("Tested");

      }
    });
    docContent.insert(btn, 0);

    final PaperFab fab = PaperFab.wrap(DOM.getElementById("edit_fab"));

    fab.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        NotifyUser.success("New message");
      }
    });
    final Badge inboxBadge = new Badge();
    final Badge chatsBadge = new Badge();
    inboxBadge.setText("99");
    chatsBadge.setText("9");
    // PolymerUtils.wrap(PolymerId.CHAT_SITEBAR_ICON_GROUP).add(inboxBadge);
    // PolymerUtils.wrapDiv("sitebar_chat_icon_group").add(chatsBadge);
    inboxBadge.addStyleName("btn_green");
    chatsBadge.addStyleName("btn_green");
    inboxBadge.addStyleName("sitebar_inbox_badge");
    chatsBadge.addStyleName("sitebar_chats_badge");
    NotifyUser.hideProgress();
  }

  /**
   * Test bar buttons.
   */
  private void testBarButtons(final FlowPanel flow) {
    final Button btn1 = new Button("Btn 1");
    final Button btn2 = new Button("Btn 2");
    final Button btn3 = new Button("Btn 3");
    final ButtonGroup buttonGroup = new ButtonGroup();
    buttonGroup.add(btn1);
    buttonGroup.add(btn2);
    buttonGroup.add(btn3);

    flow.add(buttonGroup);
  }

  /**
   * Test dialogs.
   */

  private void testDialog() {

    final cc.kune.common.client.ui.dialogs.BasicTopDialog.Builder builder = new BasicTopDialog.Builder(
        "dialogId", true, true, Direction.DEFAULT);
    final BSBasicDialog dialog = builder.build();
    dialog.getTitleText().setText("Title");
    dialog.setFirstBtnText("Accept");
    dialog.setSecondBtnText("Cancel");
    dialog.getMainPanel().add(new Image("http://lorempixel.com/600/400"));
    dialog.getBottomPanel().add(new Image("http://lorempixel.com/40/40"));
    dialog.show();
  }

  @SuppressWarnings("unused")
  private void testOldDialog() {
    final PopupTopPanel pop2 = new PopupTopPanel(false, true);
    pop2.setGlassEnabled(true);
    final BasicDialog dialog = new BasicDialog();
    pop2.add(dialog);

    dialog.getTitleText().setText("Title");
    dialog.setFirstBtnText("Accept");
    dialog.setSecondBtnText("Cancel");
    dialog.setFirstBtnTabIndex(2);
    dialog.setSecondBtnTabIndex(3);
    final ClickHandler clickHandler = new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        pop2.hide();
      }
    };
    dialog.getFirstBtn().addClickHandler(clickHandler);
    dialog.getSecondBtn().addClickHandler(clickHandler);
    final VerticalPanel sPanel = new VerticalPanel();
    sPanel.add(new Label("Someother thing"));
    final Button btn = new Button("Something");
    btn.setTabIndex(1);
    btn.setFocus(true);
    sPanel.add(btn);
    sPanel.setSize("300px", "150px");
    dialog.getInnerPanel().add((IsWidget) sPanel);
    final Label label = new Label("Test label");
    final NotifyLevelImages levelImg = new NotifyLevelImages(CommonResources.INSTANCE);
    // GWT.create(NotifyLevelImages.class);
    dialog.getBottomPanel().add(new MessagePanel(levelImg, "some message"));

    // final MaskWidget mask = new MaskWidget();

    absolutePanel.add(label, 130, 130);

    pop2.showNear(label);

    // DOM.setStyleAttribute(pop.getElement(), "zIndex", "10000");
    // mask.mask(pop2, "JAarrrrr!");
  }

  private void testPromptDialog() {
    final Builder builder = new PromptTopDialog.Builder("kkj-kk", "Some ask text?", false, true,
        Direction.LTR, new OnEnter() {

          @Override
          public void onEnter() {
            NotifyUser.info("On Enter");

          }
        });
    builder.width("200px").height("200px").firstButtonTitle("Create").sndButtonTitle("Cancel");
    builder.regex(TextUtils.UNIX_NAME).regexText(
        "The name must contain only characters, numbers and dashes");
    final PromptTopDialog diag = builder.build();
    diag.showCentered();
    diag.focusOnTextBox();
    diag.getFirstBtn().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(final ClickEvent event) {
        Window.alert("ok");
      }
    });
  }

  //
  // private void testToolpanel() {
  // ToolSelectorPanel toolSelector = new ToolSelectorPanel(new
  // GSpaceArmorImpl(null), null);
  // ToolSelectorItemPanel toolItem1 = new ToolSelectorItemPanel();
  // toolItem1.getLabel().setText("documents");
  // ToolSelectorItemPanel toolItem2 = new ToolSelectorItemPanel();
  // toolItem2.getLabel().setText("something very longgggggg");
  // ToolSelectorItemPanel toolItem3 = new ToolSelectorItemPanel();
  // toolItem3.getLabel().setText("media");
  // toolSelector.addItem(toolItem1);
  // toolSelector.addItem(toolItem2);
  // toolSelector.addItem(toolItem3);
  // toolItem1.setSelected(true);
  // toolItem2.setSelected(false);
  // toolItem3.setSelected(false);
  // toolSelector.asWidget().setWidth("200px");
  // RootPanel.get().add(toolSelector.asWidget());
  // }
  //

  /**
   * Test sub widget.
   */
  private void testSubWidget() {
    // String title = new String("Some big title áéíóú!");
    // String bTitle = Base64Utils.toBase64(title.getBytes());
    // String descr = new String("Some big description áéíóú");
    // String bDescr = Base64Utils.toBase64(descr.getBytes());
    //
    // final SubtitlesWidget sub = new SubtitlesWidget();
    //
    // sub.setTitleText(new String(Base64Utils.fromBase64(bTitle)));
    // sub.setDescription(new String(Base64Utils.fromBase64(bDescr)));
    // sub.setSize(Window.getClientWidth() + "px", Window.getClientHeight()
    // +
    // "px");
    // Button btn = new Button("Click me");
    // btn.addClickHandler(new ClickHandler() {
    // @Override
    // public void onClick(ClickEvent event) {
    //
    // sub.show();
    // }
    // });
    // RootPanel.get().add(btn);
    // RootPanel.get().add(sub);
  }

  /**
   * Test thumbs.
   *
   * @return the basic thumb
   */
  private BasicThumb testThumbs() {
    final BasicThumb thumb = new BasicThumb("http://kune.cc/ws/images/unknown.jpg", 60, "fooo", 5, false,
        new ClickHandler() {

          @Override
          public void onClick(final ClickEvent event) {
            userMsg.show("Testing");
          }
        });
    thumb.setTooltip("Some thumb tooltip");
    thumb.setOnOverLabel(true);
    return thumb;
  }

  //
  /**
   * Test tooltips.
   */
  private void testTooltips() {

    final Button button = new Button("Btn 1 biiggggggg");
    final Button button2 = new Button("Btn 2 also biggggg");
    button2.getElement().getStyle().setWhiteSpace(WhiteSpace.NORMAL);

    final IconLabel button3 = new IconLabel(res.info(), "Btn 3");
    final Button button4 = new Button("Btn 4");

    final int clientWidth = Window.getClientWidth();
    final int clientHeight = Window.getClientHeight();
    absolutePanel.setSize(String.valueOf(clientWidth - 10) + "px",
        String.valueOf(clientHeight - 10) + "px");
    absolutePanel.add(button, 5, 5);
    absolutePanel.add(button2, clientWidth - 90, 5);
    absolutePanel.add(button3, 5, clientHeight - 50);
    absolutePanel.add(button4, clientWidth - 90, clientHeight - 60);
    Tooltip.to(button,
        "Some tooltip, Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. ");
    Tooltip.to(button2,
        "Some tooltip, Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. ");
    Tooltip.to(button3,
        "Some tooltip, Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. ");
    Tooltip.to(button4,
        "Some tooltip, Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. ");

  }
}
