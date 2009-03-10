package org.ourproject.kune.platf.client.actions.toolbar;

import org.ourproject.kune.platf.client.actions.ActionShortcut;
import org.ourproject.kune.platf.client.actions.ActionToolbarDescriptor;

import com.calclab.suco.client.events.Listener0;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public class ActionToolbarButton<T> extends ToolbarButton {

    public ActionToolbarButton(ActionToolbarDescriptor<T> action, final String id, boolean enabled,
            boolean isPushButton, boolean pressed, final Listener0 onclick) {
        final String text = action.getText();
        final String iconUrl = action.getIconUrl();
        final String iconCls = action.getIconCls();
        if (text != null) {
            super.setText(text);
        }
        if (isPushButton) {
            super.setEnableToggle(true);
            super.setPressed(pressed);
        }
        super.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject e) {
                e.stopEvent();
                onclick.onEvent();
            }
        });
        if (iconUrl != null) {
            super.setIcon(iconUrl);
        }
        if (iconCls != null) {
            super.setIconCls(iconCls);
        }
        String toolTip = action.getToolTip();
        ActionShortcut shortcut = action.getShortcut();
        super.setTooltip((toolTip == null ? "" : toolTip) + (shortcut != null ? shortcut.toString() : ""));
        setEnableButton(enabled);
        // Waring: If you put set Id above click listener fires twice
        super.setId(id);
    }

    private void setEnableButton(final boolean enabled) {
        if (enabled) {
            super.enable();
        } else {
            super.disable();
        }
    }

}
