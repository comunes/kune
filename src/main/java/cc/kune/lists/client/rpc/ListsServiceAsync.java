package cc.kune.lists.client.rpc;

import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ListsServiceAsync {

  void createList(String hash, StateToken parentToken, String name, String description,
      boolean isPublic, AsyncCallback<StateContainerDTO> callback);

  void newPost(String userHash, StateToken parentToken, String title,
      AsyncCallback<StateContentDTO> callback);

  void setPublic(String hash, StateToken token, Boolean isPublic,
      AsyncCallback<StateContainerDTO> callback);

  void subscribeToList(String hash, StateToken token, Boolean subscribe,
      AsyncCallback<StateContainerDTO> callback);

}
