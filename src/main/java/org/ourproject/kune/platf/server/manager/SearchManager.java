package org.ourproject.kune.platf.server.manager;

import java.util.List;

public interface SearchManager {

    List search(String search, Integer firstResult, Integer maxResults);

    List search(String search);

}