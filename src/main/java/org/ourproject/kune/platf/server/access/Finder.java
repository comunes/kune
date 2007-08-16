package org.ourproject.kune.platf.server.access;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;

public interface Finder {

    Content getContent(Group group, StateToken token) throws ContentNotFoundException;

    Content getContent(Long contentId) throws ContentNotFoundException;

    Folder getFolder(Long folderId) throws ContentNotFoundException;

}
