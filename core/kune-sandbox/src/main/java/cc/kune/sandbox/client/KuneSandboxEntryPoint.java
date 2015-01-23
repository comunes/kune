/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.DropDown;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.Navbar;
import org.gwtbootstrap3.client.ui.NavbarCollapse;
import org.gwtbootstrap3.client.ui.NavbarCollapseButton;
import org.gwtbootstrap3.client.ui.NavbarHeader;
import org.gwtbootstrap3.client.ui.NavbarNav;
import org.gwtbootstrap3.client.ui.base.button.CustomButton;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.Toggle;

import cc.kune.bootstrap.client.actions.gwtui.FlowActionExtensible;
import cc.kune.bootstrap.client.ui.CheckListItem;
import cc.kune.bootstrap.client.ui.ComplexAnchorListItem;
import cc.kune.bootstrap.client.ui.ComplexListDropDown;
import cc.kune.bootstrap.client.ui.DropDownSubmenu;
import cc.kune.bootstrap.client.ui.RadioListItem;
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.Shortcut;
import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.IconLabelDescriptor;
import cc.kune.common.client.actions.ui.descrip.LabelDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuTitleItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.PushButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.SubMenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
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
import cc.kune.common.client.ui.dialogs.BasicDialog;
import cc.kune.common.client.ui.dialogs.MessagePanel;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.res.KuneIcon;
import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.ui.UploaderPanel;
import cc.kune.core.client.ui.dialogs.PromptTopDialog;
import cc.kune.core.client.ui.dialogs.PromptTopDialog.Builder;
import cc.kune.core.client.ui.dialogs.PromptTopDialog.OnEnter;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.WhiteSpace;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

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
      testDialogs();
    }
  }

  /** The absolute panel. */
  private AbsolutePanel absolutePanel;

  /** The ginjector. */
  private KuneSampleGinjector ginjector;

  /** The res. */
  private CommonResources res;

  /** The shortcut register. */
  private GlobalShortcutRegister shortcutRegister;

  /** The toolbar. */
  private Toolbar toolbar;

  /** The user msg. */
  SimpleUserMessage userMsg = new SimpleUserMessage();

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

    final TestAction action = new TestAction("Action 1", "Some tooltip", "oc-testico");
    final TestAction action2 = new TestAction("Some action 2");

    final KeyStroke shortcut = Shortcut.getShortcut(false, true, false, false, Character.valueOf('C'));
    shortcutRegister.put(shortcut, action);
    final KeyStroke shortcut2 = Shortcut.getShortcut(false, true, false, false, Character.valueOf('D'));
    shortcutRegister.put(shortcut2, action2);

    final ButtonDescriptor simpleBtn = new ButtonDescriptor(action);

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

    actions.add(toolbar, simpleBtn, tsepFill, pushBtn, toolbarSpace, menu, tsepFill, menuItem,
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
    ginjector = GWT.create(KuneSampleGinjector.class);
    toolbar = ginjector.getToolbar();
    res = CommonResources.INSTANCE;
    res.commonStyle().ensureInjected();
    ginjector.getUserNotifierGrowl();
    absolutePanel = new AbsolutePanel();

    NotifyUser.info("Started");

    final Navbar navbar = new Navbar();
    final NavbarHeader header = new NavbarHeader();
    final NavbarCollapseButton navbarCollapseButton = new NavbarCollapseButton();
    navbarCollapseButton.setDataTarget("#test");
    header.add(navbarCollapseButton);
    navbar.add(header);
    final NavbarCollapse navbarCollapse = new NavbarCollapse();
    navbarCollapse.setId("test");
    navbar.add(navbarCollapse);

    final NavbarNav navbarNav = new NavbarNav();

    final ComplexListDropDown listDropDown = new ComplexListDropDown();
    listDropDown.setMenuText("Menu");
    listDropDown.setIcon(IconType.GEAR);
    navbarCollapse.add(navbarNav);
    navbarNav.add(listDropDown);

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
    itemSubmenu.setIcon(new KuneIcon('f'));
    final DropDownSubmenu submenu = new DropDownSubmenu();
    submenu.add(new AnchorListItem("sub item 1"));
    submenu.add(new AnchorListItem("sub item 2"));
    submenu.add(new AnchorListItem("sub item 3"));
    itemSubmenu.add(submenu);
    listDropDown.add(itemSubmenu);
    RootPanel.get().add(navbar);

    final DropDown dropDown = new DropDown();

    final Anchor dropDownAnchor = new Anchor();
    final Image thumb = new Image("http://lorempixel.com/30/30");
    dropDownAnchor.add(thumb);
    // thumb.getElement().setAttribute(Attributes.DATA_TOGGLE,
    // Toggle.DROPDOWN.getToggle());
    // dropDownAnchor.add(thumb);

    // final Anchor dropDownAnchor = new Anchor();
    // dropDownAnchor.setText("Label dropdown");
    dropDownAnchor.setDataToggle(Toggle.DROPDOWN);
    dropDown.add(dropDownAnchor);

    final DropDownMenu dropDownMenu = new DropDownMenu();
    dropDown.add(dropDownMenu);
    final ComplexAnchorListItem dditem1 = new ComplexAnchorListItem("Test 1");
    final ComplexAnchorListItem dditem2 = new ComplexAnchorListItem("Test 2");
    dditem1.setIcon(new KuneIcon('g'));
    dditem2.setIconUrl("http://lorempixel.com/101/101");
    dropDownMenu.add(dditem1);
    dropDownMenu.add(dditem2);

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
    btn.setIcon(new KuneIcon('f'));

    // final cc.kune.bootstrap.client.ui.IconLabel iconLabel = new
    // cc.kune.bootstrap.client.ui.IconLabel(
    // "Text");
    // iconLabel.setIcon(new KuneIcon('g'));

    RootPanel.get().add(btn);
    // RootPanel.get().add(iconLabel);
    RootPanel.get().add(new HTML("<br>"));
    RootPanel.get().add(dropDown);
    RootPanel.get().add(new HTML("<br>"));

    final ToolbarDescriptor toolbarDesc = new ToolbarDescriptor();

    final ButtonDescriptor btnDesc = new ButtonDescriptor(new TestAction("Button desc"));
    btnDesc.withIcon(new KuneIcon('z')).withToolTip("Show me a tooltip");
    btnDesc.setParent(toolbarDesc);

    final IconLabelDescriptor labelDesc = new IconLabelDescriptor(new TestAction("IconLabel desc"));
    labelDesc.withIcon(new KuneIcon('l')).withToolTip("Show me a tooltip");
    labelDesc.setParent(toolbarDesc);

    final MenuDescriptor menu = new MenuDescriptor("Menu btn");
    menu.setParent(toolbarDesc);
    final MenuItemDescriptor menuItemDescriptor = new MenuItemDescriptor(menu, new TestAction(
        "Menu item"));
    menuItemDescriptor.withIcon(IconType.MAGIC);

    final SubMenuDescriptor submenuDesc = new SubMenuDescriptor(menu, "Some submenu");
    submenuDesc.withIcon(new KuneIcon('g'));
    new MenuItemDescriptor(submenuDesc, new TestAction("Submenu item 1")).withIcon(IconType.FACEBOOK);
    new MenuItemDescriptor(submenuDesc, new TestAction("Submenu item 2")).withIcon(IconType.FAST_BACKWARD);

    // TODO Add more tests here

    // Testing
    final FlowActionExtensible flow = new FlowActionExtensible();
    flow.add(toolbarDesc); // , toolbarDesc, menu, menuItemDescriptor);

    RootPanel.get().add(flow);
    // RootPanel.get().add(toolbarDesc);
    RootPanel.get().add(new HTML("<br>"));
    RootPanel.get().add(testActionToolbar());
    // navbarDropDown.setMenuVisible(true);
    RootPanel.get().add(absolutePanel);

  }

  public void onModuleLoad2() {

    ginjector = GWT.create(KuneSampleGinjector.class);
    toolbar = ginjector.getToolbar();
    res = CommonResources.INSTANCE;
    res.commonStyle().ensureInjected();
    ginjector.getUserNotifierGrowl();

    final MainContainer mainPanel = new MainContainer();
    // mainPanel.setWidth("100%");
    // mainPanel.setHeight("100px");

    // final ScrollableDeckPanel scroll = new ScrollableDeckPanel();
    // final FlowPanel flow = new FlowPanel();

    final CustomButton next = new CustomButton("Deck next", IconType.PLAY_CIRCLE, new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        mainPanel.next();
      }
    });
    next.setStyleName(ActionStyles.SITEBAR_STYLE);
    // mainPanel.getSitebar().add(next);
    mainPanel.getFooter().add(new CustomButton("Footer", IconType.DOWNLOAD, new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        mainPanel.prev();
      }
    }));

    final FlowPanel flow = mainPanel.getFlow();

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
    //
    // scroll.insert(flow, 0);
    // // scroll.setHeight("400px");
    // mainPanel.addToMain(scroll);
    // mainPanel.addToMain(new Label("Test panel"));
    // RootLayoutPanel.get().add(mainPanel);

    // final MenuDescriptor menu = new MenuDescriptor("Menu");

    // final MenuItemDescriptor menuItemDescriptor = new
    // MenuItemDescriptor(menu, new TestAction(
    // "Menu item"));
    // toolbar.add(menu);

    // mainPanel.getSitebar().add(toolbar);

    // mainPanel.getSitebar().add(new TestBootstrap());

    // mainPanel.getSitebar().add(navmenu);
  }

  public void onModuleLoadOld() {

    // final Growl growl = UserNotifierGrowl.showProgress("Loading...");

    ginjector = GWT.create(KuneSampleGinjector.class);
    toolbar = ginjector.getToolbar();
    res = CommonResources.INSTANCE;
    res.commonStyle().ensureInjected();
    ginjector.getUserNotifierGrowl();

    NotifyUser.showProgress("Starting");

    absolutePanel = new AbsolutePanel();
    testBarButtons();
    testTooltips();

    absolutePanel.add(testActionToolbar(), 200, 200);

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

    // testPUload();

    shortcutRegister = ginjector.getGlobalShortcutRegister();
    shortcutRegister.enable();

    final ActionFlowPanel view = makeFlowToolbar();

    final BasicThumb thumb = testThumbs();

    absolutePanel.add(thumb, 200, 10);
    absolutePanel.add(view, 5, 150);

    final DottedTab tab = new DottedTab();
    absolutePanel.add(tab, 400, 400);
    absolutePanel.add(tab, 400, 400);
    absolutePanel.add(makeFileUpload(), 520, 0);

    new BlinkAnimation(tab, 350).animate(5);

    RootPanel.get().add(absolutePanel);
    // UserNotifierGrowl.close(growl);
  }

  /**
   * Test action toolbar.
   *
   * @return
   */
  @SuppressWarnings("unused")
  public Widget testActionToolbar() {

    final AbstractExtendedAction action1 = new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        final SimpleUserMessage simpleMes = new SimpleUserMessage();
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

    final IconLabelDescriptor iconLabel = new IconLabelDescriptor("This does not work");
    iconLabel.setAction(action1);
    iconLabel.withText("Icon Label2");
    iconLabel.withIcon(new KuneIcon('f'));

    final ButtonDescriptor button1 = new ButtonDescriptor("button 1", action1);
    final ButtonDescriptor button2 = new ButtonDescriptor("button 2 but bigger bigger", action2);
    final ButtonDescriptor button3 = new ButtonDescriptor(action1);

    button1.withIcon(res.info()).withToolTip("Some tooltip");
    button2.withIcon(res.info()).withToolTip("Some tooltip");
    button3.withIcon(IconType.TWITTER).withToolTip("Some tooltip"); // .withStyles("k-button,gwt-Button,k-btn-min");

    final ToolbarDescriptor toolbarDesc = new ToolbarDescriptor();
    final ToolbarItemDescriptor anchorItem = new ToolbarItemDescriptor();
    anchorItem.setAction(new AbstractExtendedAction("Item descr") {
      @Override
      public void actionPerformed(final ActionEvent event) {
        NotifyUser.info("Clicked");
      }
    });
    anchorItem.withIcon(IconType.MAGNET);
    anchorItem.setParent(toolbarDesc);

    final MenuDescriptor menuDesc = new MenuDescriptor("Menu descr");
    menuDesc.setParent(toolbarDesc);
    menuDesc.setRightIcon(res.world16());
    menuDesc.withIcon(res.info()).withToolTip("Some menu");
    final MenuItemDescriptor menuItem1 = new MenuItemDescriptor(menuDesc, action1);
    menuItem1.withIcon(res.info()).withText("Some menu item");
    final MenuItemDescriptor menuItem2 = new MenuItemDescriptor(menuDesc, action1);
    menuItem2.withIcon(res.info()).withText("Some other menu item");

    final SubMenuDescriptor submenuDesc = new SubMenuDescriptor("Some Submenu", "tip", "oc-testico");
    submenuDesc.setParent(menuDesc);

    final TestAction action3 = new TestAction("Action 3", "Some tooltip", "oc-testico");
    final TestAction action4 = new TestAction("Action 4");

    final MenuSeparatorDescriptor menuSep = new MenuSeparatorDescriptor(menuDesc);
    final MenuItemDescriptor menuHeaderItem = new MenuTitleItemDescriptor(menuDesc, "Header");
    final MenuItemDescriptor menuItemDesc1 = new MenuItemDescriptor(menuDesc, action3);
    final MenuItemDescriptor menuItemDesc2 = new MenuItemDescriptor(menuDesc, action4);
    final MenuItemDescriptor menuItemDesc3 = new MenuItemDescriptor(submenuDesc, action1);
    menuItemDesc3.withText("This text is setted");
    final MenuItemDescriptor menuItemDesc4 = new MenuItemDescriptor(submenuDesc, action2);

    // TODO: add more things here

    final FlowActionExtensible flowActions = new FlowActionExtensible();

    flowActions.add(button1, iconLabel, button2, button3, toolbarDesc);

    return flowActions;
  }

  /**
   * Test bar buttons.
   */
  private void testBarButtons() {
    final Button btn1 = new Button("Btn 1");
    final Button btn2 = new Button("Btn 2");
    final Button btn3 = new Button("Btn 3");
    final ButtonGroup buttonGroup = new ButtonGroup();
    buttonGroup.add(btn1);
    buttonGroup.add(btn2);
    buttonGroup.add(btn3);

    final FlowPanel flow = new FlowPanel();

    flow.add(buttonGroup);
    absolutePanel.add(flow, 5, 60);
  }

  /**
   * Test dialogs.
   */
  private void testDialogs() {
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

  @SuppressWarnings("unused")
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
    final BasicThumb thumb = new BasicThumb("http://kune.cc/ws/images/unknown.jpg", 60, "fooo", 5,
        false, new ClickHandler() {

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
    button.getElement().getStyle().setWhiteSpace(WhiteSpace.NORMAL);

    final IconLabel button3 = new IconLabel(res.info(), "Btn 3");
    final Button button4 = new Button("Btn 4");

    final int clientWidth = Window.getClientWidth();
    final int clientHeight = Window.getClientHeight();
    absolutePanel.setSize(String.valueOf(clientWidth - 10) + "px", String.valueOf(clientHeight - 10)
        + "px");
    absolutePanel.add(button, 5, 5);
    absolutePanel.add(button2, clientWidth - 90, 5);
    absolutePanel.add(button3, 5, clientHeight - 50);
    absolutePanel.add(button4, clientWidth - 90, clientHeight - 60);
    Tooltip.to(button,
        "Some tooltip, Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. ").setWidth(
        100);
    Tooltip.to(button2,
        "Some tooltip, Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. ").setWidth(
        100);
    Tooltip.to(button3,
        "Some tooltip, Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. ").setWidth(
        100);
    Tooltip.to(button4,
        "Some tooltip, Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec vitae eros. ").setWidth(
        100);

  }
}
