
package org.ourproject.kune.platf.server.state;

import org.ourproject.kune.platf.server.access.Access;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.Revision;

import com.google.inject.Singleton;

@Singleton
public class StateServiceDefault implements StateService {
    public State create(final Access access) {
        final Content content = access.getContent();
        final Container container = content.getContainer();
        final State state = new State();

        final Long documentId = content.getId();
        if (documentId != null) {
            state.setTypeId(content.getTypeId());
            state.setDocumentId(documentId.toString());
            state.setIsRateable(true);
            state.setLanguage(content.getLanguage());
            state.setPublishedOn(content.getPublishedOn());
            state.setAuthors(content.getAuthors());
            state.setTags(content.getTagsAsString());
        } else {
            state.setTypeId(container.getTypeId());
            state.setDocumentId(null);
            state.setIsRateable(false);
            state.setLanguage(container.getLanguage());
        }
        final Revision revision = content.getLastRevision();
        final char[] text = revision.getBody();
        state.setContent(text == null ? null : new String(text));
        if (documentId != null) {
            state.setTitle(revision.getTitle());
        } else {
            state.setTitle(container.getName());
        }
        state.setToolName(container.getToolName());
        state.setGroup(container.getOwner());
        state.setFolder(container);
        state.setAccessLists(access.getContentAccessLists());
        state.setContentRights(access.getContentRights());
        state.setFolderRights(access.getFolderRights());
        state.setGroupRights(access.getGroupRights());
        License contentLicense = content.getLicense();
        if (contentLicense == null) {
            contentLicense = container.getOwner().getDefaultLicense();
        }
        state.setLicense(contentLicense);
        return state;
    }
}
