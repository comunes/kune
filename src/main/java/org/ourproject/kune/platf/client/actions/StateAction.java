package org.ourproject.kune.platf.client.actions;

import org.ourproject.kune.platf.client.dispatch.DefaultAction;
import org.ourproject.kune.platf.client.dispatch.HistoryToken;

public class StateAction extends DefaultAction {
    public static final String NAME = "group";

    public void execute(Object value) {
	HistoryToken token = new HistoryToken(value.toString());
	String groupName = token.getParam(0);
	if (!state.isCurrentGroup(groupName)) {
	    changeGroup();
	}
	String toolName = token.getParam(1);
	dispatcher.fire(toolName, token.reencodeFrom(2));
    }

    private void changeGroup() {
	// TODO Auto-generated method stub
    }
}
