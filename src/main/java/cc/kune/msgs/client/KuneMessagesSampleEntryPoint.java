package cc.kune.msgs.client;

import cc.kune.msgs.client.msgs.UserMessage;
import cc.kune.msgs.client.msgs.UserMessageLevel;
import cc.kune.msgs.client.panel.UserMessagesPanel;
import cc.kune.msgs.client.panel.UserMessagesPresenter;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class KuneMessagesSampleEntryPoint implements EntryPoint {

    /**
     * This is the entry point method.
     */
    @Override
    public void onModuleLoad() {
        UserMessagesPanel panel = new UserMessagesPanel();
        UserMessage.setFadeMills(3000);
        UserMessage.setCloseTitle("Close message");
        panel.setWidth("422px");
        final UserMessagesPresenter presenter = new UserMessagesPresenter();
        presenter.init(panel);
        presenter.add(UserMessageLevel.info, "Lorem ipsum dolor sit amet, consectetuer adipiscing elit", false);
        presenter.add(UserMessageLevel.error, "Lorem ipsum dolor sit amet, consectetuer adipiscing elit", false);
        Timer time = new Timer() {
            @Override
            public void run() {
                presenter.add(
                        UserMessageLevel.error,
                        "Lorem <a href='/'>ipsum</a> dolor sit amet, consectetuer adipiscing elit. Lorem ipsum dolor sit amet, consectetuer adipiscing elit",
                        true);
            }
        };
        Timer time2 = new Timer() {
            @Override
            public void run() {
                presenter.add(UserMessageLevel.important, "Lorem ", false);
                presenter.add(UserMessageLevel.important, "Lorem ", true);
            }
        };
        time.schedule(1000);
        time2.schedule(2000);
        PopupPanel aPanel = new PopupPanel();
        aPanel.add(panel);
        aPanel.setPopupPosition(0, 0);
        aPanel.show();
        // RootPanel.get().add(aPanel);
    }
}
