package cc.kune.core.client.actions;

import java.util.HashMap;
import java.util.Map;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.core.client.actions.WaveExtension.Builder;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.rpcservices.ContentServiceAsync;

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
import com.google.inject.Provider;

public class XMLActionsParser {

  public static class GadgetAction extends AbstractExtendedAction {

    private final Provider<ContentServiceAsync> contentService;

    @Inject
    public GadgetAction(final Provider<ContentServiceAsync> contentService) {
      this.contentService = contentService;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
    }

  }

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
    final NodeList guiDescriptors = parse.getElementsByTagName("guiActionDescriptors");
    for (int i = 0; i < guiDescriptors.getLength(); i++) {
      final Element extension = (Element) extensions.item(i);
      final Provider<GuiActionDescrip> provider = new Provider<GuiActionDescrip>() {
        @Override
        public GuiActionDescrip get() {
          // TODO Auto-generated method stub
          new MenuItemDescriptor(null);
          return null;
        }
      };
    }
  }
}
