
package org.ourproject.kune.platf.server.content;

import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.manager.impl.SearchResult;

import com.google.gwt.user.client.rpc.SerializableException;

public interface ContainerManager {

    Container find(Long id);

    Container createRootFolder(Group group, String toolName, String name, String type);

    Container createFolder(Group group, Container parent, String name, I18nLanguage language);

    String renameFolder(Group group, Container container, String newName) throws SerializableException;

    SearchResult<Container> search(String search);

    SearchResult<Container> search(String search, Integer firstResult, Integer maxResults);
}
