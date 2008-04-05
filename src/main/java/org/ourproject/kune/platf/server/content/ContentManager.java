
package org.ourproject.kune.platf.server.content;

import java.util.Date;

import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.Manager;
import org.ourproject.kune.platf.server.manager.impl.SearchResult;

import com.google.gwt.user.client.rpc.SerializableException;

public interface ContentManager extends Manager<Content, Long> {

    public Content createContent(String title, String body, User user, Container container);

    public Content save(User editor, Content descriptor, String content);

    public void rateContent(User rater, Long contentId, Double value) throws SerializableException;

    public Double getRateContent(User user, Content content);

    public Long getRateByUsers(Content content);

    public Double getRateAvg(Content content);

    public String renameContent(User user, Long contentId, String newName) throws SerializableException;

    public I18nLanguage setLanguage(User user, Long contentId, String languageCode) throws SerializableException;

    public void setPublishedOn(User user, Long contentId, Date publishedOn) throws SerializableException;

    public void setTags(User user, Long contentId, String tags) throws SerializableException;

    public void addAuthor(User user, Long contentId, String authorShortName) throws SerializableException;

    public void removeAuthor(User user, Long contentId, String authorShortName) throws SerializableException;

    public void delContent(User user, Long contentId) throws SerializableException;

    SearchResult<Content> search(String search);

    SearchResult<Content> search(String search, Integer firstResult, Integer maxResults);

}
