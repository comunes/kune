package cc.kune.lists.client.rpc;

import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ListsService")
public interface ListsService extends RemoteService {

  StateContainerDTO createList(String hash, StateToken parentToken, String name, String description,
      boolean isPublic);

  public StateContentDTO newPost(final String userHash, final StateToken parentToken, final String title);

  StateContainerDTO setPublic(String hash, StateToken token, Boolean isPublic);

  StateContainerDTO subscribeToList(String hash, StateToken token, Boolean subscribe);

}