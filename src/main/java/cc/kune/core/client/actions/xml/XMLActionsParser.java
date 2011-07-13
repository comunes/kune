package cc.kune.core.client.actions.xml;

import java.util.HashMap;
import java.util.Map;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.SubMenuDescriptor;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.registry.NewMenusForTypeIdsRegistry;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.actions.ActionGroups;
import cc.kune.gspace.client.viewers.ContentViewerPresenter;

import com.calclab.emite.core.client.services.Services;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class XMLActionsParser {

  public static final String ACTIONS_XML_LOCATION = "/others/kune-client-actions.xml";

  private static final String SEP = "»";

  private final ActionRegistryByType actionRegistry;
  private final Provider<ContentServiceAsync> contentService;
  private final ContentViewerPresenter contentViewer;
  private final ErrorHandler errHandler;
  private final I18nTranslationService i18n;

  private final NewMenusForTypeIdsRegistry newMenusRegistry;

  private final Session session;

  private final StateManager stateManager;

  private final HashMap<String, SubMenuDescriptor> submenus;

  @Inject
  public XMLActionsParser(final ErrorHandler errHandler, final ContentViewerPresenter contentViewer,
      final ActionRegistryByType actionRegistry, final Provider<ContentServiceAsync> contentService,
      final Session session, final StateManager stateManager, final I18nTranslationService i18n,
      final NewMenusForTypeIdsRegistry newMenusRegistry, final Services services) {
    this.errHandler = errHandler;
    this.contentViewer = contentViewer;
    this.actionRegistry = actionRegistry;
    this.contentService = contentService;
    this.session = session;
    this.stateManager = stateManager;
    this.i18n = i18n;
    this.newMenusRegistry = newMenusRegistry;
    submenus = new HashMap<String, SubMenuDescriptor>();

    // Based on:
    // http://www.roseindia.net/tutorials/gwt/retrieving-xml-data.shtml
    final RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, ACTIONS_XML_LOCATION);
    try {
      requestBuilder.sendRequest(null, new RequestCallback() {
        @Override
        public void onError(final Request request, final Throwable ex) {
          onFailed(ex);
        }

        @Override
        public void onResponseReceived(final Request request, final Response response) {
          parse(new XMLKuneClientActions(services, response.getText()));
        }
      });
    } catch (final RequestException ex) {
      onFailed(ex);
    }
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
        subMenuDescriptor = new SubMenuDescriptor(parent, i18n.t(name));
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

  private void parse(final XMLKuneClientActions xml) {
    final Map<String, XMLWaveExtension> extensions = xml.getExtensions();
    for (final XMLGuiActionDescriptor descrip : xml.getGuiActionDescriptors()) {
      if (descrip.isEnabled()) {
        final XMLWaveExtension extension = extensions.get(descrip.getExtensionName());
        assert extension != null;
        for (final XMLTypeId typeId : descrip.getTypeIds()) {
          final String origTypeId = typeId.getOrigTypeId();
          final String contentIntro = descrip.getNewContentTextIntro();
          final NewGadgetAction action = new NewGadgetAction(contentService, contentViewer,
              stateManager, session, i18n, descrip.getRol().getRolRequired(),
              descrip.getRol().isAuthNeed(), extension.getExtName(), typeId.getDestTypeId(),
              extension.getIconUrl(), descrip.getNewContentTitle(), TextUtils.empty(contentIntro) ? ""
                  : contentIntro);
          final String path = descrip.getPath();
          final MenuDescriptor menu = newMenusRegistry.get(origTypeId);
          assert menu != null;
          final SubMenuDescriptor submenu = getSubMenu(menu, origTypeId, path);
          final Provider<GuiActionDescrip> menuItemProvider = new Provider<GuiActionDescrip>() {
            @Override
            public GuiActionDescrip get() {
              final MenuItemDescriptor menuItem = new MenuItemDescriptor(action);
              menuItem.withText(descrip.getDescName()).withToolTip(descrip.getDescription());
              menuItem.setParent(TextUtils.notEmpty(path) ? submenu : menu);
              return menuItem;
            }
          };
          actionRegistry.addAction(ActionGroups.TOOLBAR, menuItemProvider, origTypeId);
        }
      }
    }
  }
}
