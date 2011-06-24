package cc.kune.core.client.actions;

import java.util.HashMap;
import java.util.Map;

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.SubMenuDescriptor;
import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.actions.WaveExtension.Builder;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.registry.NewMenusForTypeIdsRegistry;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.actions.ActionGroups;

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

  public static class GadgetAction extends RolAction {

    private final Provider<ContentServiceAsync> contentService;
    private final String gadgetName;

    public GadgetAction(final Provider<ContentServiceAsync> contentService, final AccessRolDTO rok,
        final boolean authNeeded, final String gadgetName, final String iconUrl) {
      super(rok, authNeeded);
      this.contentService = contentService;
      this.gadgetName = gadgetName;
      putValue(Action.SMALL_ICON, iconUrl);
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      NotifyUser.info("Created succesfully");
    }

  }

  private static final String SEP = "»";

  private final ActionRegistryByType actionRegistry;
  private final Provider<ContentServiceAsync> contentService;
  private final ErrorHandler errHandler;
  private final Map<String, WaveExtension> extensionsMap;
  private final NewMenusForTypeIdsRegistry newMenusRegistry;
  private final HashMap<String, SubMenuDescriptor> submenus;

  @Inject
  public XMLActionsParser(final ErrorHandler errHandler, final ActionRegistryByType actionRegistry,
      final Provider<ContentServiceAsync> contentService,
      final NewMenusForTypeIdsRegistry newMenusRegistry) {
    this.errHandler = errHandler;
    this.actionRegistry = actionRegistry;
    this.contentService = contentService;
    this.newMenusRegistry = newMenusRegistry;
    extensionsMap = new HashMap<String, WaveExtension>();
    submenus = new HashMap<String, SubMenuDescriptor>();

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

  private SubMenuDescriptor getSubMenu(final MenuDescriptor menu, final String typeId,
      final String parentS) {
    final String[] path = parentS.split(SEP);
    SubMenuDescriptor current = null;
    for (int i = 0; i < path.length; i++) {
      final String name = path[i];
      final String subpathId = getSubPathId(typeId, path, i);
      SubMenuDescriptor subMenuDescriptor = submenus.get(subpathId);
      if (subMenuDescriptor == null) {
        final GuiActionDescrip parent = (i == 0 ? menu : submenus.get(getSubPathId(typeId, path, i - 1)));
        assert parent != null;
        subMenuDescriptor = new SubMenuDescriptor(parent, name);
        // subMenuDescriptor.setVisible(false);
        submenus.put(subpathId, subMenuDescriptor);
        actionRegistry.addAction(ActionGroups.TOOLBAR, subMenuDescriptor, typeId);
      }
      current = subMenuDescriptor;
    }
    assert current != null;
    return current;
  }

  private String getSubPathId(final String typeId, final String[] path, final int i) {
    final StringBuffer id = new StringBuffer().append(typeId);
    for (int j = 0; j <= i; j++) {
      id.append(SEP).append(path[j]);
    }
    return id.toString();
  }

  private void onFailed(final Throwable ex) {
    errHandler.process(ex);
  }

  private void parseXML(final String text) {
    final Document parse = XMLParser.parse(text);
    final NodeList extensions = ((Element) parse.getElementsByTagName("extensions").item(0)).getElementsByTagName("extension");
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
    final NodeList guiDescriptors = ((Element) parse.getElementsByTagName("guiActionDescriptors").item(0)).getElementsByTagName("guiActionDescriptor");
    for (int i = 0; i < guiDescriptors.getLength(); i++) {
      final Element guiDescriptor = (Element) guiDescriptors.item(i);
      if (Boolean.parseBoolean(get(guiDescriptor, "enabled"))) {
        final String type = get(guiDescriptor, "type");
        if (type.equals("wave-gadget")) {
          final String extensionName = get(guiDescriptor, "extensionName");
          final WaveExtension extension = extensionsMap.get(extensionName);
          if (extension == null) {
            throw new UIException("Undefined extension " + extensionName);
          }
          final String name = get(guiDescriptor, "name");
          final String description = get(guiDescriptor, "description");
          final AccessRolDTO rol = AccessRolDTO.valueOf(get(guiDescriptor, "rolRequired"));
          final GadgetAction action = new GadgetAction(contentService, rol, Boolean.parseBoolean(get(
              guiDescriptor, "authNeed")), extension.getGadgetUrl(), extension.getIconUrl());
          final NodeList typeIds = ((Element) guiDescriptor.getElementsByTagName("typeIds").item(0)).getElementsByTagName("typeId");
          final int length = typeIds.getLength();

          for (int j = 0; j < length; j++) {
            final Element typeIdElem = (Element) typeIds.item(j);
            final String typeId = typeIdElem.getFirstChild().getNodeValue();
            final MenuDescriptor menu = newMenusRegistry.get(typeId);
            assert menu != null;
            final String parent = get(guiDescriptor, "parent");
            final SubMenuDescriptor submenu = getSubMenu(menu, typeId, parent);
            final Provider<GuiActionDescrip> menuItemProvider = new Provider<GuiActionDescrip>() {
              @Override
              public GuiActionDescrip get() {
                final MenuItemDescriptor menuItem = new MenuItemDescriptor(action);
                menuItem.withText(name).withToolTip(description);
                menuItem.setParent(TextUtils.notEmpty(parent) ? submenu : menu);
                return menuItem;
              }
            };
            actionRegistry.addAction(ActionGroups.TOOLBAR, menuItemProvider, typeId);
          }
        }
      }
    }
  }
}
