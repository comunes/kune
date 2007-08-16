package org.ourproject.kune.chat.client.ui.cnt;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public class ChatContentPresenter implements ChatContent {

    private final WorkspaceDeckView view;
    private final ChatContentPanel content;

    public ChatContentPresenter(final WorkspaceDeckView view) {
	this.view = view;
	content = new ChatContentPanel();
    }

    public void attach() {
    }

    public void detach() {
    }

    public View getView() {
	return view;
    }

    public void setState(final StateDTO state) {
	view.show(content);
    }

}
