package cc.kune.core.client.sitebar;

import org.ourproject.common.client.actions.AbstractExtendedAction;
import org.ourproject.common.client.actions.Action;
import org.ourproject.common.client.actions.ActionEvent;
import org.ourproject.common.client.actions.ui.IsActionExtensible;
import org.ourproject.common.client.actions.ui.descrip.ButtonDescriptor;
import org.ourproject.common.client.actions.ui.descrip.IconLabelDescriptor;

import cc.kune.wspace.client.WsArmor;

import com.google.gwt.user.client.Window;
import com.google.inject.Inject;

public class SiteBarActions {

    @Inject
    public SiteBarActions(WsArmor armor) {
        IconLabelDescriptor icon = new IconLabelDescriptor("test");
        AbstractExtendedAction action = new AbstractExtendedAction() {

            @Override
            public void actionPerformed(ActionEvent event) {
                Window.alert("kk");
            }
        };
        action.putValue(Action.SHORT_DESCRIPTION, "kk");
        action.putValue(Action.LONG_DESCRIPTION, "tooltip");
        ButtonDescriptor signIn = new ButtonDescriptor(action);
        IsActionExtensible toolbar = armor.getSiteActionsToolbar();
        // ToolbarSeparatorDescriptor fill = new
        // ToolbarSeparatorDescriptor(Type.fill, (ToolbarDescriptor) toolbar);
        // toolbar.addAction(fill);
        toolbar.addAction(icon);
        toolbar.addAction(signIn);
    }
}
