package org.ourproject.kune.workspace.client.site;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.noti.ConfirmationAsk;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser.Level;
import org.ourproject.kune.workspace.client.site.msg.ToastMessage;
import org.ourproject.kune.workspace.client.sitebar.siteprogress.SiteProgress;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.events.Listener2;
import com.calclab.suco.client.ioc.Provider;

public class WorkspaceNotifyUser {

    public WorkspaceNotifyUser(NotifyUser notifyUser, final I18nTranslationService i18n,
            final SiteProgress siteProgress, final Provider<ToastMessage> toaster,
            final Provider<WorkspaceSkeleton> ws) {
        notifyUser.addProgressNotifier(new Listener<String>() {
            public void onEvent(String message) {
                siteProgress.showProgress(message);
            }
        });
        notifyUser.addHideProgressNotifier(new Listener0() {
            public void onEvent() {
                siteProgress.hideProgress();
            }
        });
        notifyUser.addNotifier(new Listener2<Level, String>() {
            public void onEvent(Level level, String msg) {
                String title = "";
                switch (level) {
                case error:
                    title = i18n.t("Error");
                    break;
                case important:
                    title = i18n.t("Important");
                    break;
                case veryImportant:
                    title = i18n.t("Alert");
                    break;
                case info:
                    title = i18n.t("Info");
                    break;
                }
                toaster.get().showMessage(title, msg, level);
            }
        });
        notifyUser.addAlerter(new Listener2<String, String>() {
            public void onEvent(String title, String msg) {
                ws.get().showAlertMessage(title, msg);
            }
        });
        notifyUser.addConfirmationAsker(new Listener<ConfirmationAsk>() {
            public void onEvent(ConfirmationAsk ask) {
                ws.get().askConfirmation(ask.getTitle(), ask.getMessage(), ask.getOnConfirmed(), ask.getOnCancel());
            }
        });
    }

}
