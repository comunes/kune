
package org.ourproject.kune.platf.client.rpc;

import java.util.Date;
import java.util.List;

import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.TagResultDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContentServiceAsync {

    void addAuthor(String userHash, String groupShortName, String documentId, String authorShortName,
            AsyncCallback<?> asyncCallback);

    void addContent(String user, String groupShortName, Long parentFolderId, String name,
            AsyncCallback<StateDTO> callback);

    void addFolder(String hash, String groupShortName, Long parentFolderId, String title,
            AsyncCallback<StateDTO> callback);

    void addRoom(String user, String groupShortName, Long parentFolderId, String name, AsyncCallback<StateDTO> callback);

    void delContent(String userHash, String groupShortName, String documentId, AsyncCallback<?> asyncCallback);

    void getContent(String user, String groupShortName, StateToken newState, AsyncCallback<StateDTO> callback);

    void getSummaryTags(String userHash, String groupShortName, AsyncCallback<List<TagResultDTO>> asyncCallback);

    void rateContent(String userHash, String groupShortName, String documentId, Double value,
            AsyncCallback<?> asyncCallback);

    void removeAuthor(String userHash, String groupShortName, String documentId, String authorShortName,
            AsyncCallback<?> asyncCallback);

    void rename(String userHash, String groupShortName, String token, String newName,
            AsyncCallback<String> asyncCallback);

    void save(String user, String groupShortName, String documentId, String content,
            AsyncCallback<Integer> asyncCallback);

    void setLanguage(String userHash, String groupShortName, String documentId, String languageCode,
            AsyncCallback<I18nLanguageDTO> asyncCallback);

    void setPublishedOn(String userHash, String groupShortName, String documentId, Date publishedOn,
            AsyncCallback<?> asyncCallback);

    void setTags(String userHash, String groupShortName, String documentId, String tags,
            AsyncCallback<List<TagResultDTO>> asyncCallback);
}
