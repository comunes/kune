package cc.kune.core.client.actions;

import java.util.HashMap;
import java.util.Map;

import cc.kune.core.client.actions.WaveExtension.Builder;
import cc.kune.core.client.errors.ErrorHandler;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.Inject;

public class XMLActionsParser {

  private final ErrorHandler errHandler;
  private final Map<String, WaveExtension> extensionsMap;

  @Inject
  public XMLActionsParser(final ErrorHandler errHandler) {
    this.errHandler = errHandler;
    extensionsMap = new HashMap<String, WaveExtension>();

    // Based on:
    // http://www.roseindia.net/tutorials/gwt/retrieving-xml-data.shtml
    final RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET,
        "/others/kune-wave-extensions.xml");
    try {
      requestBuilder.sendRequest(null, new RequestCallback() {
        @Override
        public void onError(final Request request, final Throwable ex) {
          onFailed(ex);
        }

        @Override
        public void onResponseReceived(final Request request, final Response response) {
          parseXML(response.getText());
        }
      });
    } catch (final RequestException ex) {
      onFailed(ex);
    }
  }

  private String get(final Element extension, final String id) {
    final Node child = extension.getElementsByTagName(id).item(0).getFirstChild();
    return child != null ? child.getNodeValue() : "";
  }

  private void onFailed(final Throwable ex) {
    errHandler.process(ex);
  }

  private void parseXML(final String text) {
    final Document parse = XMLParser.parse(text);
    final NodeList extensions = parse.getElementsByTagName("extensions");
    for (int i = 0; i < extensions.getLength(); i++) {
      final Element extension = (Element) extensions.item(i);
      final Builder builder = new WaveExtension.Builder();
      final String name = get(extension, "name");
      builder.name(name);
      builder.gadgetUrl(get(extension, "gadgetUrl"));
      builder.iconUrl(get(extension, "iconUrl"));
      builder.iconCss(get(extension, "iconCss"));
      builder.installerUrl(get(extension, "installerUrl"));
      extensionsMap.put(name, builder.build());
    }

  }
}
