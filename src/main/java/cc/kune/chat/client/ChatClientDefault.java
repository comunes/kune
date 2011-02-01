package cc.kune.chat.client;

import cc.kune.chat.client.ShowChatDialogEvent.ShowChatDialogHandler;
import cc.kune.chat.client.ToggleShowChatDialogEvent.ToggleShowChatDialogHandler;
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.Shortcut;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.IconLabelDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.ToolbarSeparatorDescriptor.Type;
import cc.kune.common.client.ui.PopupTopPanel;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.resources.icons.IconResources;
import cc.kune.core.client.sitebar.SitebarActionsPresenter;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.UserSignInEvent;
import cc.kune.core.client.state.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.state.UserSignOutEvent;
import cc.kune.core.client.state.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.emite.browser.client.PageAssist;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.hablar.HablarComplete;
import com.calclab.hablar.HablarConfig;
import com.calclab.hablar.console.client.HablarConsole;
import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.HablarWidget;
import com.calclab.hablar.html.client.HtmlConfig;
import com.calclab.hablar.icons.alt.client.AltIcons;
import com.calclab.hablar.icons.def.client.DefaultIcons;
import com.calclab.hablar.icons.ie6gif.client.IE6GifIcons;
import com.calclab.hablar.login.client.HablarLogin;
import com.calclab.hablar.login.client.LoginConfig;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ChatClientDefault implements ChatClient {

    public static class ChatClientAction extends AbstractExtendedAction {

        private final EventBus eventBus;

        @Inject
        public ChatClientAction(final EventBus eventBus) {
            super();
            this.eventBus = eventBus;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            eventBus.fireEvent(new ToggleShowChatDialogEvent());
        }

    }

    protected static final String CHAT_CLIENT_ICON_ID = "k-chat-icon-id";

    private final ChatClientAction action;
    protected IconLabelDescriptor chatIcon;
    private final I18nTranslationService i18n;

    private PopupTopPanel popup;

    private final IconResources res;

    private final Session session;

    private final SitebarActionsPresenter siteActions;

    @Inject
    public ChatClientDefault(final EventBus eventBus, final I18nTranslationService i18n, final ChatClientAction action,
            final SitebarActionsPresenter siteActions, final IconResources res, final Session session) {
        this.i18n = i18n;
        this.action = action;
        this.siteActions = siteActions;
        this.res = res;
        this.session = session;
        eventBus.addHandler(AppStartEvent.getType(), new AppStartEvent.AppStartHandler() {
            @Override
            public void onAppStart(final AppStartEvent event) {
                if (session.isLogged()) {
                    createActionIfNeeded();
                }
                eventBus.addHandler(UserSignInEvent.getType(), new UserSignInHandler() {
                    @Override
                    public void onUserSignIn(final UserSignInEvent event) {
                        createActionIfNeeded();
                        createDialogIfNeeded();
                        chatIcon.setVisible(true);
                    }
                });
                eventBus.addHandler(UserSignOutEvent.getType(), new UserSignOutHandler() {
                    @Override
                    public void onUserSignOut(final UserSignOutEvent event) {
                        createActionIfNeeded();
                        chatIcon.setVisible(false);
                    }
                });
                eventBus.addHandler(ShowChatDialogEvent.getType(), new ShowChatDialogHandler() {
                    @Override
                    public void onShowChatDialog(final ShowChatDialogEvent event) {
                        createActionIfNeeded();
                        showDialog(event.show);
                    }
                });
                eventBus.addHandler(ToggleShowChatDialogEvent.getType(), new ToggleShowChatDialogHandler() {
                    @Override
                    public void onToggleShowChatDialog(final ToggleShowChatDialogEvent event) {
                        toggleShowDialog();
                    }
                });
            }
        });
    }

    @Override
    public void addNewBuddie(final String shortName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void chat(final XmppURI jid) {
        // TODO Auto-generated method stub
    }

    private void createActionIfNeeded() {
        if (chatIcon == null) {
            res.css().ensureInjected();
            chatIcon = new IconLabelDescriptor(action);
            chatIcon.setParent(SitebarActionsPresenter.LEFT_TOOLBAR);
            chatIcon.putValue(Action.SMALL_ICON, res.chat());
            chatIcon.putValue(Action.NAME, i18n.t("Chat ;)"));
            chatIcon.setId(CHAT_CLIENT_ICON_ID);
            chatIcon.setStyles("k-no-backimage, k-btn-sitebar, k-chat-icon");
            action.putValue(Action.SHORT_DESCRIPTION, i18n.t("Show/hide the chat window"));
            final KeyStroke shortcut = Shortcut.getShortcut(false, true, true, false, Character.valueOf('C'));
            action.setShortcut(shortcut);
            chatIcon.setVisible(session.isLogged());
            siteActions.getLeftToolbar().addAction(
                    new ToolbarSeparatorDescriptor(Type.spacer, SitebarActionsPresenter.LEFT_TOOLBAR));
            siteActions.getLeftToolbar().addAction(
                    new ToolbarSeparatorDescriptor(Type.spacer, SitebarActionsPresenter.LEFT_TOOLBAR));
            siteActions.getLeftToolbar().addAction(
                    new ToolbarSeparatorDescriptor(Type.spacer, SitebarActionsPresenter.LEFT_TOOLBAR));
            siteActions.getLeftToolbar().addAction(chatIcon);
        }
    }

    private void createDialog(final HablarWidget widget, final HtmlConfig htmlConfig) {
        // popup.setSize(htmlConfig.width, htmlConfig.height);
        widget.addStyleName("k-chat-panel");
        setSize(widget, htmlConfig);
        popup.add(widget);
    }

    private DialogBox createDialog2(final HablarWidget widget, final HtmlConfig htmlConfig) {
        final DialogBox dialog = new DialogBox();
        dialog.setText("Hablar");
        setSize(dialog, htmlConfig);
        dialog.show();
        dialog.center();
        return dialog;
    }

    private void createDialogIfNeeded() {
        if (popup == null) {
            popup = new PopupTopPanel();
            initEmite();
        }
    }

    private void initEmite() {
        final String icons = PageAssist.getMeta("hablar.icons");
        if ("alt".equals(icons)) {
            AltIcons.load();
        } else if ("ie6".equals(icons)) {
            IE6GifIcons.load();
        } else {
            DefaultIcons.load();
        }

        final HablarConfig config = HablarConfig.getFromMeta();
        final HtmlConfig htmlConfig = HtmlConfig.getFromMeta();
        htmlConfig.hasLogger = true;
        final HablarWidget widget = new HablarWidget(config.layout, config.tabHeaderSize);
        final Hablar hablar = widget.getHablar();

        HablarComplete.install(hablar, config);

        if (htmlConfig.hasLogger) {
            new HablarConsole(hablar);
        }

        if (htmlConfig.hasLogin) {
            new HablarLogin(hablar, LoginConfig.getFromMeta());
        }
        createDialog(widget, htmlConfig);
    }

    @Override
    public boolean isBuddie(final String localUserName) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isBuddie(final XmppURI jid) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isLoggedIn() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void joinRoom(final String roomName, final String userAlias) {
        // TODO Auto-generated method stub

    }

    @Override
    public void joinRoom(final String roomName, final String subject, final String userAlias) {
        // TODO Auto-generated method stub

    }

    @Override
    public void login(final String jid, final String passwd) {
        // TODO Auto-generated method stub

    }

    @Override
    public void logout() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAvatar(final String photoBinary) {
        // TODO Auto-generated method stub

    }

    private void setSize(final Widget widget, final HtmlConfig htmlConfig) {
        if (htmlConfig.width != null) {
            widget.setWidth(htmlConfig.width);
        }
        if (htmlConfig.height != null) {
            widget.setHeight(htmlConfig.height);
        }
        widget.setWidth("450px");
        widget.setHeight("300px");
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    private void showDialog(final boolean show) {
        if (session.isLogged()) {
            createDialogIfNeeded();
            if (show) {
                popup.showNear((Widget) chatIcon.getValue(ParentWidget.PARENT_UI));
            } else {
                popup.hide();
            }
        }
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }

    private void toggleShowDialog() {
        showDialog(popup == null ? true : !popup.isShowing());
    }

}
