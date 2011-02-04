package cc.kune.common.client;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
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
import cc.kune.common.client.notify.SimpleUserMessage;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.client.ui.IconLabel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SampleEntryPoint implements EntryPoint {
    public interface ISampleView {
        void addAll(GuiActionDescCollection actions);
    }

    public class TestAction extends AbstractExtendedAction {
        public TestAction(final String text) {
            super(text);
        }

        public TestAction(final String text, final String tooltip, final String icon) {
            super(text, tooltip, icon);
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            final String message = "Testing: " + super.getValue(Action.NAME);
            // Log.info(message);
            userMsg.show(message);
        }
    }
    SimpleUserMessage userMsg = new SimpleUserMessage();

    @Override
    public void onModuleLoad() {

        final SampleGinjector ginjector = GWT.create(SampleGinjector.class);
        ginjector.getGxtGuiProvider();
        ginjector.getGuiProvider();
        final GlobalShortcutRegister shortcutRegister = ginjector.getGlobalShortcutRegister();

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
        pushBtn.putValue(Action.NAME, "Push btn");

        final ToolbarDescriptor toolbar = new ToolbarDescriptor();

        final ToolbarSeparatorDescriptor tsepFill = new ToolbarSeparatorDescriptor(Type.fill, toolbar);
        final ToolbarSeparatorDescriptor toolbarSpace = new ToolbarSeparatorDescriptor(Type.spacer, toolbar);

        simpleBtn.setParent(toolbar);
        pushBtn.setParent(toolbar);

        final MenuDescriptor menu = new MenuDescriptor(action);
        menu.putValue(Action.NAME, "Menu");

        final MenuDescriptor menu2 = new MenuDescriptor(action);
        menu2.putValue(Action.NAME, "Menu2");

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

        actions.add(toolbar, simpleBtn, tsepFill, pushBtn, toolbarSpace, menu, tsepFill, menuItem, menuItem2, menuSep,
                menuItem2, menuItem, iconLabelDescr, submenu, menuItem3, menuItem4, menu2, iconLabelNoAct, menuItem5);

        final ActionFlowPanel view = new ActionFlowPanel(ginjector.getGuiProvider());
        view.addActions(actions);

        final IconLabel simpleIconLabel = new IconLabel("IconLabel (no action)");
        simpleIconLabel.setIcon("oc-testico");
        simpleIconLabel.setTitle("tooltip");

        final VerticalPanel panel = new VerticalPanel();
        panel.setWidth("100%");
        panel.add(view);
        panel.add(simpleIconLabel);

        RootPanel.get().add(view);
    }
}
