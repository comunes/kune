package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.Data;
import org.ourproject.kune.platf.server.model.AccessRights;
import org.ourproject.kune.platf.server.model.Content;

import com.google.inject.Singleton;

@Singleton
public class MetadataManagerDefault implements MetadataManager {
    public void fill(final Content content, final AccessLists accessList, final AccessRights accessRights) {
	ContentDescriptor descriptor = content.getDescriptor();
	Data data = descriptor.getLastRevision().getData();
	content.setDocRef(descriptor.getId().toString());
	content.setTitle(data.getTitle());
	char[] text = data.getContent();
	content.setContent(text == null ? null : new String(text));
	content.setToolName(descriptor.getFolder().getToolName());
	content.setAccessLists(accessList);
	content.setAccessRights(accessRights);
	content.setRate(descriptor.calculateRate(descriptor));
	content.setRateByUsers(descriptor.calculateRateNumberOfUsers(descriptor));
    }
}
