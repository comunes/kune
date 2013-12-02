package cc.kune.gspace.client.viewers;

import cc.kune.common.client.log.Log;
import cc.kune.core.shared.dto.InitDataDTO;
import cc.kune.core.shared.dto.InitDataDTOJs;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateAbstractDTOJs;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.dto.UserInfoDTO;
import cc.kune.core.shared.dto.UserInfoDTOJs;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.jsonp.client.JsonpRequest;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class EmbedHelper {
  public static InitDataDTO parse(final InitDataDTOJs initJ) {
    final InitDataDTO init = new InitDataDTO();
    init.setStoreUntranslatedStrings(initJ.getStoreUntranslatedStrings());
    init.setSiteLogoUrl(initJ.getSiteLogoUrl());
    init.setSiteLogoUrlOnOver(initJ.getsiteLogoUrlOnOver());
    return init;
  }

  public static StateAbstractDTO parse(final StateAbstractDTOJs stateJs) {
    final StateContentDTO state = new StateContentDTO();
    state.setContent(stateJs.getContent());
    state.setWaveRef(stateJs.getWaveRef());
    state.setTitle(stateJs.getTitle());
    state.setIsParticipant(stateJs.isParticipant());
    // state.setStateToken(new StateToken((StateTokenJs)
    // stateJs.getStateToken()).getEncoded());
    return state;
  }

  public static UserInfoDTO parse(final UserInfoDTOJs userInfo) {
    final String userHash = userInfo.getUserHash();
    if (userHash == null || userHash.equals("null")) {
      Log.info("We are NOT logged");
      return null;
    } else {
      final UserInfoDTO info = new UserInfoDTO();
      info.setUserHash(userHash);
      Log.info("We are logged, userhash: " + userHash);
      final String waveSession = userInfo.getSessionJSON();
      info.setSessionJSON(waveSession);
      Log.info("wave session: " + waveSession);
      info.setWebsocketAddress(userInfo.getWebsocketAddress());
      info.setClientFlags(userInfo.getClientFlags());
      return info;
    }
  }

  static void processJSONRequest(final String url, final Callback<JavaScriptObject, Void> callback) {
    final JsonpRequestBuilder builder = new JsonpRequestBuilder();
    builder.setTimeout(60000);
    @SuppressWarnings("unused")
    final JsonpRequest<JavaScriptObject> request = builder.requestObject(url,
        new AsyncCallback<JavaScriptObject>() {
          @Override
          public void onFailure(final Throwable exception) {
            Log.error("JSON exception: ", exception);
            callback.onFailure(null);
          }

          @Override
          public void onSuccess(final JavaScriptObject result) {
            callback.onSuccess(result);
          }
        });

  }

  static void processRequest(final String url, final Callback<Response, Void> callback) {
    try {
      final RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
      // Needed for CORS
      builder.setIncludeCredentials(true);
      @SuppressWarnings("unused")
      final Request request = builder.sendRequest(null, new RequestCallback() {
        @Override
        public void onError(final Request request, final Throwable exception) {
          Log.error("CORS exception: ", exception);
          callback.onFailure(null);
        }

        @Override
        public void onResponseReceived(final Request request, final Response response) {
          if (200 == response.getStatusCode()) {
            callback.onSuccess(response);
          } else {
            Log.error("Couldn't retrieve CORS (" + response.getStatusText() + ")");
            callback.onFailure(null);
          }
        }
      });
    } catch (final RequestException exception) {
      Log.error("CORS exception: ", exception);
      callback.onFailure(null);
    }
  }
}
