package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.dto.AccessRolDTO;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.listener.Listener;

public class ActionToolbarMenuDescriptor<T> extends ActionToolbarDescriptor<T> {

    private String parentMenuTitle;
    private String parentMenuIconUrl;
    private String parentSubMenuTitle;

    public ActionToolbarMenuDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener<T> onPerformCall) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall);
    }

    public ActionToolbarMenuDescriptor(final AccessRolDTO accessRolDTO,
            final ActionToolbarPosition actionToolbarPosition, final Listener<T> onPerformCall,
            final ActionEnableCondition<T> enableCondition) {
        super(accessRolDTO, actionToolbarPosition, onPerformCall, enableCondition);
    }

    public String getParentMenuIconUrl() {
        return parentMenuIconUrl;
    }

    public String getParentMenuTitle() {
        return parentMenuTitle;
    }

    public String getParentSubMenuTitle() {
        return parentSubMenuTitle;
    }

    public void setParentMenuIconUrl(final String parentMenuIconUrl) {
        this.parentMenuIconUrl = parentMenuIconUrl;
    }

    public void setParentMenuTitle(final String parentMenuTitle) {
        this.parentMenuTitle = parentMenuTitle;
    }

    public void setParentSubMenuTitle(final String parentSubMenuTitle) {
        if (parentMenuTitle == null) {
            Log.warn("Please set parentMenuTitle before");
        }
        this.parentSubMenuTitle = parentSubMenuTitle;
    }
}
