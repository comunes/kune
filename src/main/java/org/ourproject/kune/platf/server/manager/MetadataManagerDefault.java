package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.Data;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.model.AccessRights;
import org.ourproject.kune.platf.server.model.Content;

import com.google.inject.Singleton;

@Singleton
public class MetadataManagerDefault implements MetadataManager {
    public Content fill(final ContentDescriptor descriptor, final AccessLists accessList,
	    final AccessRights contentRights, final AccessRights folderRights) {
	Folder folder = descriptor.getFolder();
	Content content = new Content();

	Long documentId = descriptor.getId();
	if (documentId != null) {
	    content.setDocumentId(documentId.toString());
	} else {
	    content.setDocumentId(null);
	}
	Data data = descriptor.getLastRevision().getData();
	char[] text = data.getContent();
	content.setContent(text == null ? null : new String(text));
	content.setTitle(data.getTitle());
	content.setToolName(folder.getToolName());
	content.setGroup(folder.getOwner());
	content.setFolder(folder);
	content.setAccessLists(accessList);
	content.setContentRights(contentRights);
	content.setFolderRights(folderRights);
	content.setRate(descriptor.calculateRate(descriptor));
	content.setRateByUsers(descriptor.calculateRateNumberOfUsers(descriptor));
	return content;
    }
}
