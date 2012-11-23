package cc.kune.sandbox.client;

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
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.PushButtonDescriptor;
import cc.kune.common.client.actions.ui.descrip.SubMenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarDescriptor;
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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class KuneSandboxEntryPoint implements EntryPoint {

  public class TestAction extends AbstractExtendedAction {
    public TestAction(final String text) {
      super(text);
    }

    public TestAction(final String text, final String tooltip, final String icon) {
      super(text, tooltip, icon);
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      testDialogs();
    }
  }
  SimpleUserMessage userMsg = new SimpleUserMessage();

  private AbsolutePanel absolutePanel;
  private Toolbar toolbar;
  private CommonResources res;

  private GlobalShortcutRegister shortcutRegister;

  private KuneSampleGinjector ginjector;

  /**
   * This is the entry point method.
   */
  @Override
  public void onModuleLoad() {
    ginjector = GWT.create(KuneSampleGinjector.class);
    toolbar = ginjector.getToolbar();
    res = CommonResources.INSTANCE;
    res.commonStyle().ensureInjected();
    ginjector.getUserNotifierPopup();

    absolutePanel = new AbsolutePanel();
    testBarButtons();
    testTooltips();

    testActionToolbar();

    final String defLocale = "en";

    String locale = WindowUtils.getParameter("locale");
    String[] ids = new String[] { "summary", "ini", "footer", "kuneloading-msg" };

    for (String id : ids) {
      RootPanel someId = RootPanel.get("k-home-" + id + "-" + locale);
      RootPanel defId = RootPanel.get("k-home-" + id + "-" + defLocale);
      if (someId != null)
        someId.setVisible(true);
      else if (defId != null)
        defId.setVisible(true);
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

    absolutePanel.add(thumb, 100, 300);
    absolutePanel.add(view, 200, 300);

    DottedTab tab = new DottedTab();
    absolutePanel.add(tab, 400, 400);

    new BlinkAnimation(tab, 350).animate(5);

    RootPanel.get().add(absolutePanel);
  }

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

  private ActionFlowPanel makeFlowToolbar() {
    final GuiActionDescCollection actions = new GuiActionDescCollection();

    final TestAction action = new TestAction("Action 1", "Some tooltip", "oc-testico");
    final TestAction action2 = new TestAction("Action 2");

    final KeyStroke shortcut = Shortcut.getShortcut(false, true, false, false, Character.valueOf('C'));
    shortcutRegister.put(shortcut, action);

    final ButtonDescriptor simpleBtn = new ButtonDescriptor(action);

    // Same action but different text
    simpleBtn.putValue(Action.NAME, "Action 1 diff name");

    final PushButtonDescriptor pushBtn = new PushButtonDescriptor(action2);
    pushBtn.setPushed(true);
    pushBtn.withText("Push btn").withStyles("k-button");
    // FIXME when fix the style set also in I18nTranslatorForm.ui.xml (currently
    // is set to "none")

    final ToolbarDescriptor toolbar = new ToolbarDescriptor();

    final ToolbarSeparatorDescriptor tsepFill = new ToolbarSeparatorDescriptor(Type.fill, toolbar);
    final ToolbarSeparatorDescriptor toolbarSpace = new ToolbarSeparatorDescriptor(Type.spacer, toolbar);

    simpleBtn.setParent(toolbar);
    pushBtn.setParent(toolbar);

    final MenuDescriptor menu = new MenuDescriptor(action);
    menu.withText("Menu 1").withStyles(ActionStyles.MENU_BTN_STYLE_LEFT);

    final MenuDescriptor menu2 = new MenuDescriptor(action);
    menu2.withText("Menu 2").withStyles(ActionStyles.MENU_BTN_STYLE_LEFT);

    menu.setParent(toolbar);
    final SubMenuDescriptor submenu = new SubMenuDescriptor("Some Submenu", "tip", "oc-testico");
    submenu.setParent(menu);
    final MenuSeparatorDescriptor menuSep = new MenuSeparatorDescriptor(menu);

    final TestAction action3 = new TestAction("Action 3", "Some tooltip", "oc-testico");
    final TestAction action4 = new TestAction("Action 4");

    final MenuItemDescriptor menuItem = new MenuItemDescriptor(menu, action3);
    final MenuItemDescriptor menuItem2 = new MenuItemDescriptor(menu, action4);
    final MenuItemDescriptor menuItem3 = new MenuItemDescriptor(submenu, action);
    final MenuItemDescriptor menuItem4 = new MenuItemDescriptor(submenu, action);
    final IconLabelDescriptor iconLabelDescr = new IconLabelDescriptor(action);
    final IconLabelDescriptor iconLabelNoAct = new IconLabelDescriptor(action4);
    final MenuItemDescriptor menuItem5 = new MenuItemDescriptor(menu2, action);

    action.setShortcut(shortcut);

    actions.add(toolbar, simpleBtn, tsepFill, pushBtn, toolbarSpace, menu, tsepFill, menuItem,
        menuItem2, menuSep, menuItem2, menuItem, iconLabelDescr, submenu, menuItem3, menuItem4, menu2,
        iconLabelNoAct, menuItem5);

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

  public void testActionToolbar() {

    AbstractExtendedAction action1 = new AbstractExtendedAction() {
      @Override
      public void actionPerformed(ActionEvent event) {
        // SimpleUserMessage simpleMes = new SimpleUserMessage();
        // simpleMes.show("Hellow world!");
        NotifyUser.info("Some title", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit");
      }
    };

    AbstractExtendedAction action2 = new AbstractExtendedAction() {
      @Override
      public void actionPerformed(ActionEvent event) {
        NotifyUser.error(
            "Some title",
            "Lorem <a href='/'>ipsum</a> dolor sit amet, consectetuer adipiscing elit. Lorem ipsum dolor sit amet, consectetuer adipiscing elit",
            false);
      }
    };

    IconLabelDescriptor iconLabel = new IconLabelDescriptor("Icon Label", res.info());
    iconLabel.setAction(action1);

    ButtonDescriptor button1 = new ButtonDescriptor("button 1", action1);
    ButtonDescriptor button2 = new ButtonDescriptor("button 2 but bigger bigger", action2);
    ButtonDescriptor button3 = new ButtonDescriptor(action1);

    button1.withIcon(res.info()).withToolTip("Some tooltip");
    button2.withIcon(res.info()).withToolTip("Some tooltip");
    button3.withIcon(res.info()).withToolTip("Some tooltip"); // .withStyles("k-button,gwt-Button,k-btn-min");

    toolbar.add(iconLabel);
    toolbar.add(button1);
    toolbar.add(button2);
    toolbar.add(button3);

    MenuDescriptor menu = new MenuDescriptor("Menu");
    menu.setRightIcon(res.world16());
    menu.withIcon(res.info()).withToolTip("Some menu").withStyles("k-button, gwt-Button");
    MenuItemDescriptor menuItem1 = new MenuItemDescriptor(menu, action1);
    menuItem1.withIcon(res.info()).withText("Some menu item");
    MenuItemDescriptor menuItem2 = new MenuItemDescriptor(menu, action1);
    menuItem2.withIcon(res.info()).withText("Some other menu item");

    toolbar.add(menu);

    absolutePanel.add(toolbar, 200, 200);

  }

  // private void testPUload() {
  // final Button browseButton = new Button();
  // browseButton.getElement().setId("my-browse-button");
  // final PluploadBuilder builder = new PluploadBuilder();
  // // ADD ANY PLUPLOAD PROPERTIES HERE
  // builder.uploadUrl("server/upload.php");
  // builder.browseButton("my-browse-button");
  // final Plupload plupload = builder.create();
  // RootPanel.get().add(browseButton);
  // }

  //
  // private void testPromptDialog() {
  // Builder builder = new PromptTopDialog.Builder("kkj-kk", "Some ask text?",
  // false, true, Direction.LTR);
  // builder.width("200px").height("200px").firstButtonTitle("Create").sndButtonTitle("Cancel");
  // builder.regex(TextUtils.UNIX_NAME).regexText(
  // "The name must contain only characters, numbers and dashes");
  // PromptTopDialog diag = builder.build();
  // diag.showCentered();
  // diag.focusOnTextBox();
  // diag.getFirstBtn().addClickHandler(new ClickHandler() {
  //
  // @Override
  // public void onClick(ClickEvent event) {
  // Window.alert("ok");
  // }
  // });
  // }
  //
  private void testBarButtons() {
    Button btn1 = new Button("Btn 1");
    Button btn2 = new Button("Btn 2");
    Button btn3 = new Button("Btn 3");
    btn1.addStyleName("k-button");
    btn1.addStyleName("k-button-left");
    btn2.addStyleName("k-button");
    btn2.addStyleName("k-button-center");
    btn3.addStyleName("k-button");
    btn3.addStyleName("k-button-right");
    btn1.addStyleName("kune-Margin-Large-t");
    btn2.addStyleName("kune-Margin-Large-t");
    btn3.addStyleName("kune-Margin-Large-t");
    btn1.addStyleName("k-fl");
    btn2.addStyleName("k-fl");
    btn3.addStyleName("k-fl");
    FlowPanel vp = new FlowPanel();
    vp.add(btn1);
    vp.add(btn2);
    vp.add(btn3);
    absolutePanel.add(vp, 100, 100);
  }

  //
  private void testTooltips() {

    final Button button = new Button("Btn 1 biiggggggg");
    final Button button2 = new Button("Btn 2 also biggggg");
    final IconLabel button3 = new IconLabel(res.info(), "Btn 3");
    final Button button4 = new Button("Btn 4");

    int clientWidth = Window.getClientWidth();
    int clientHeight = Window.getClientHeight();
    absolutePanel.setSize(String.valueOf(clientWidth - 10) + "px", String.valueOf(clientHeight - 10)
        + "px");
    absolutePanel.add(button, 5, 5);
    absolutePanel.add(button2, clientWidth - 80, 5);
    absolutePanel.add(button3, 5, clientHeight - 40);
    absolutePanel.add(button4, clientWidth - 80, clientHeight - 40);
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
    ClickHandler clickHandler = new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
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
    // sub.setSize(Window.getClientWidth() + "px", Window.getClientHeight() +
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
}
