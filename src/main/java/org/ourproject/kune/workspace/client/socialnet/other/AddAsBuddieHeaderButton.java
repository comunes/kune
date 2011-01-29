package org.ourproject.kune.workspace.client.socialnet.other;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.AbstractExtendedAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.PropertyChangeEvent;
import org.ourproject.kune.platf.client.actions.PropertyChangeListener;
import org.ourproject.kune.platf.client.actions.ui.ButtonDescriptor;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;

import cc.kune.core.client.resources.icons.IconConstants;
import cc.kune.core.client.resources.icons.IconResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;

public class AddAsBuddieHeaderButton {

    public static class AddAsBuddieAction extends AbstractExtendedAction {
        private final Provider<ChatEngine> chatEngine;
        private final Session session;

        public AddAsBuddieAction(final Provider<ChatEngine> chatEngine, final Session session,
                final StateManager stateManager, final I18nTranslationService i18n, final IconResources img) {
            super();
            this.chatEngine = chatEngine;
            this.session = session;
            stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
                public void onEvent(final StateAbstractDTO state) {
                    setState(state);
                }
            });
            chatEngine.get().addOnRosterChanged(new Listener0() {
                public void onEvent() {
                    setState(session.getCurrentState());
                }
            });
            putValue(Action.NAME, i18n.t("Add as a buddie"));
            putValue(Action.SMALL_ICON, IconConstants.toPath(img.addGreen()));
        }

        public void actionPerformed(final ActionEvent event) {
            chatEngine.get().addNewBuddie(session.getCurrentState().getGroup().getShortName());
            NotifyUser.info("Added as buddie. Waiting buddie response");
            setEnabled(false);
        }

        private void setState(final StateAbstractDTO state) {
            final String groupName = state.getGroup().getShortName();
            final boolean isPersonal = state.getGroup().isPersonal();
            final boolean isLogged = session.isLogged();
            if (isLogged && isPersonal && (!chatEngine.get().isBuddie(groupName))
                    && (!session.getCurrentUser().getShortName().equals(groupName))) {
                setEnabled(true);
            } else {
                setEnabled(false);
            }
        }
    }

    public AddAsBuddieHeaderButton(final Provider<ChatEngine> chatEngine, final Session session,
            final StateManager stateManager, final I18nTranslationService i18n, final IconResources img,
            final EntityHeader entityHeader) {
        final AddAsBuddieAction buddieAction = new AddAsBuddieAction(chatEngine, session, stateManager, i18n, img);
        final ButtonDescriptor button = new ButtonDescriptor(buddieAction);
        button.setVisible(false);
        buddieAction.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(AbstractAction.ENABLED)) {
                    button.setVisible((Boolean) event.getNewValue());
                }
            }
        });
        entityHeader.addAction(button);
    }
}