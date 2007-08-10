package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.dto.GroupDTO;

import com.google.gwt.user.client.HistoryListener;

public interface StateManager extends HistoryListener {
    String getUser();

    void setState(String groupShortName, String toolName, Long folderId, Long contentId);

    void setState(GroupDTO group);
}
