package cc.kune.core.client.ui.dialogs;

import cc.kune.common.client.noti.NotifyLevel;

import com.google.gwt.user.client.ui.IsWidget;

public interface MessagePanelView {

    IsWidget getPanel();

    void hideErrorMessage();

    void setErrorMessage(String message, NotifyLevel level);

}
