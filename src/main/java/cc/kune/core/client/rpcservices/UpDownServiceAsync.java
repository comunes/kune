package cc.kune.core.client.rpcservices;

import cc.kune.common.shared.ui.UploadFile;
import cc.kune.core.shared.domain.utils.StateToken;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UpDownServiceAsync {

  void uploadBackground(String hash, StateToken token, UploadFile file, AsyncCallback<Void> callback);

  void uploadLogo(String hash, StateToken token, UploadFile file, AsyncCallback<Void> callback);
}
