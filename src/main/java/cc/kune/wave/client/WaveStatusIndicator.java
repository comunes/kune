package cc.kune.wave.client;

import org.waveprotocol.box.webclient.client.ClientEvents;
import org.waveprotocol.box.webclient.client.events.NetworkStatusEvent;
import org.waveprotocol.box.webclient.client.events.NetworkStatusEventHandler;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.IconLabelDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.sitebar.SitebarActionsPresenter;
import cc.kune.core.client.sn.actions.SessionAction;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class WaveStatusIndicator {

    public static class WaveStatusAction extends SessionAction {

        @Inject
        public WaveStatusAction(final Session session, final I18nTranslationService i18n) {
            super(session, true);
            ClientEvents.get().addNetworkStatusEventHandler(new NetworkStatusEventHandler() {
                @Override
                public void onNetworkStatus(final NetworkStatusEvent event) {
                    switch (event.getStatus()) {
                    case CONNECTED:
                    case RECONNECTED:
                        putValue(Action.NAME, i18n.t("Online"));
                        putValue(AbstractAction.STYLES, "k-sitebar-wave-status, k-sitebar-wave-status-online");
                        NotifyUser.hideProgress();
                        break;
                    case DISCONNECTED:
                        NotifyUser.showProgress(i18n.t("Connecting"));
                    case RECONNECTING:
                        putValue(Action.NAME, i18n.t("Offline"));
                        putValue(AbstractAction.STYLES, "k-sitebar-wave-status, k-sitebar-wave-status-offline");
                        break;
                    }
                }
            });
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            // Do nothing
        }

    }

    @Inject
    public WaveStatusIndicator(final SitebarActionsPresenter sitebar, final WaveStatusAction action) {
        final IconLabelDescriptor status = new IconLabelDescriptor(action);
        status.setPosition(0);
        sitebar.getRightToolbar().addAction(status);
    }
}
