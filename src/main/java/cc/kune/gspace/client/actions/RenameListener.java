package cc.kune.gspace.client.actions;

import cc.kune.core.shared.domain.utils.StateToken;

public interface RenameListener {

  void onFail(StateToken token, String oldTitle);

  void onSuccess(StateToken token, String title);

}
