package org.ourproject.kune.workspace.client.site.msg;

import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;

import com.gwtextux.client.widgets.window.ToastWindow;

public class SiteToastMessagePanel implements SiteToastMessageView {

    public SiteToastMessagePanel() {
    }

    public void showMessage(String title, String message, SiteErrorType type) {
        ToastWindow toastWindow = new ToastWindow(title, message);
        String iconCls = "";
        switch (type) {
        case info:
            iconCls = "k-stm-info-icon";
            break;
        case imp:
            iconCls = "k-stm-imp-icon";
            break;
        case veryimp:
            iconCls = "k-stm-verimp-icon";
            break;
        case error:
            iconCls = "k-stm-error-icon";
            break;
        }
        toastWindow.setIconCls(iconCls);
        toastWindow.show();
    }
}
