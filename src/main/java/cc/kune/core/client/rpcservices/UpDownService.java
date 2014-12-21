package cc.kune.core.client.rpcservices;

import cc.kune.common.shared.ui.UploadFile;
import cc.kune.core.shared.domain.utils.StateToken;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UpDownService")
public interface UpDownService extends RemoteService {

  void uploadBackground(String hash, StateToken token, UploadFile file);

  void uploadLogo(String hash, StateToken token, UploadFile file);
}
