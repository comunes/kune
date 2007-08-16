package org.ourproject.kune.platf.server.state;

import org.ourproject.kune.platf.server.access.Access;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Data;
import org.ourproject.kune.platf.server.domain.Container;

import com.google.inject.Singleton;

@Singleton
public class StateServiceDefault implements StateService {
    public State create(final Access access) {
	Content descriptor = access.getDescriptor();
	Container container = descriptor.getFolder();
	State state = new State();

	Long documentId = descriptor.getId();
	if (documentId != null) {
	    state.setDocumentId(documentId.toString());
	} else {
	    state.setDocumentId(null);
	}
	Data data = descriptor.getLastRevision().getData();
	char[] text = data.getContent();
	state.setContent(text == null ? null : new String(text));
	state.setTitle(data.getTitle());
	state.setToolName(container.getToolName());
	state.setGroup(container.getOwner());
	state.setFolder(container);
	state.setAccessLists(access.getContentAccessLists());
	state.setContentRights(access.getContentRights());
	state.setFolderRights(access.getFolderRights());
	state.setRate(descriptor.calculateRate(descriptor));
	state.setRateByUsers(descriptor.calculateRateNumberOfUsers(descriptor));
	return state;
    }
}
