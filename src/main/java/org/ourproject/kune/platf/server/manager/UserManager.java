
package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.client.errors.I18nNotFoundException;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.impl.SearchResult;

public interface UserManager {
    User login(String nickOrEmail, String passwd);

    /**
     * CreateUser new method with language country and timezone params
     * 
     * @param shortName
     * @param longName
     * @param email
     * @param passwd
     * @param timezone
     * @param country
     * @param language
     * @param timezone
     * @return User
     * @throws I18nNotFoundException
     */
    User createUser(String shortName, String longName, String email, String passwd, String language, String country,
            String timezone) throws I18nNotFoundException;

    /**
     * IMPORTANT: if userId == null, it returns User.NONE
     * 
     * @param userId
     * @return
     */
    User find(Long userId);

    SearchResult<User> search(String search);

    SearchResult<User> search(String search, Integer firstResult, Integer maxResults);

    void reIndex();

}
