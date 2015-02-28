package cc.kune.client;

import cc.kune.bootstrap.client.BSGuiProvider;
import cc.kune.chat.client.ChatParts;
import cc.kune.common.client.events.EventBusWithLogging;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.client.utils.MetaUtils;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.core.client.CoreParts;
import cc.kune.core.client.actions.xml.XMLActionsParser;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.sitebar.ErrorsDialog;
import cc.kune.core.client.state.SessionExpirationManager;
import cc.kune.core.client.state.SessionInstance;
import cc.kune.core.client.state.SiteParameters;
import cc.kune.core.client.state.impl.SessionChecker;
import cc.kune.core.client.ws.CorePresenter;
import cc.kune.docs.client.DocsParts;
import cc.kune.events.client.EventsParts;
import cc.kune.gspace.client.GSpaceParts;
import cc.kune.gspace.client.tool.ContentViewerSelector;
import cc.kune.hspace.client.HSpaceParts;
import cc.kune.lists.client.ListsParts;
import cc.kune.polymer.client.PolymerId;
import cc.kune.polymer.client.PolymerUtils;
import cc.kune.trash.client.TrashParts;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Bootstrapper;

public class KuneBootstrapper implements Bootstrapper {

  /** The Constant HOME_IDS_DEF_SUFFIX. */
  protected static final String HOME_IDS_DEF_SUFFIX = "-def";

  /** The Constant HOME_IDS_PREFIX. */
  protected static final String HOME_IDS_PREFIX = "k-home-";

  protected static PolymerId[] unresolvedIdList = new PolymerId[] { PolymerId.GROUP_SPACE,
    PolymerId.USER_SPACE, PolymerId.SITEBAR_RIGHT_EXTENSIONBAR, PolymerId.HOME_TOOLBAR };

  private final ContentViewerSelector contentViewerSelector;

  private final CorePresenter corePresenter;

  private final GlobalShortcutRegister globalShortcutRegister;

  private final SessionChecker sessionChecker;

  @Inject
  public KuneBootstrapper(final SessionChecker sessionChecker,
      final ContentViewerSelector contentViewerSelector,
      final GlobalShortcutRegister globalShortcutRegister,
      final SessionExpirationManager sessionExpirationManager,
      final EventBusWithLogging eventBusWithLogging, final ErrorsDialog errorsDialog,
      final CorePresenter corePresenter, final OnAppStartFactory onAppStartFactory,

      // Here you define the gui ui provider (gwt, gxt, bootstrap, polymer)
      final BSGuiProvider guiProvider,
      // GwtGuiProvider guiProvider

      final DocsParts docs,

      /*
       * final BlogsParts blogs, final WikiParts wiki,
       */
      final EventsParts events,
      /* final TasksParts tasks, */
      final ListsParts lists, final ChatParts chats, /*
       * final BartersParts
       * barters,
       */

      final TrashParts trash,

      final CoreParts coreParts, final GSpaceParts gSpaceParts, /*
       * final
       * PSpaceParts
       * pSpaceParts,
       */
      final HSpaceParts hSpaceParts,

      final XMLActionsParser xmlActionsParser) {

    this.sessionChecker = sessionChecker;
    this.contentViewerSelector = contentViewerSelector;
    this.globalShortcutRegister = globalShortcutRegister;
    this.corePresenter = corePresenter;
  }

  @Override
  public void onBootstrap() {

    corePresenter.forceReveal();

    sessionChecker.check(new AsyncCallbackSimple<Void>() {
      @Override
      public void onSuccess(final Void result) {
        // Do nothing
      }
    });

    contentViewerSelector.init();

    globalShortcutRegister.enable();

    setHomeLocale();

    SessionInstance.get().onUserSignInOrSignOut(true, new UserSignInOrSignOutHandler() {
      @Override
      public void onUserSignInOrSignOut(final UserSignInOrSignOutEvent event) {
        // TODO Auto-generated method stub
        // Polymer preventing FOUC
        // https://www.polymer-project.org/docs/polymer/styling.html#fouc-prevention
        for (final PolymerId id : unresolvedIdList) {
          PolymerUtils.resolved(id);
        }
        PolymerUtils.resolved(RootPanel.getBodyElement());
      }
    });
  }

  /**
   * Home set locale. In ws.html there is some no visible elements with the
   * different locales and we only show the current locale
   */
  private void setHomeLocale() {
    final String currentLocale = WindowUtils.getParameter(SiteParameters.LOCALE);

    final String meta = MetaUtils.get("kune.home.ids");
    if (meta != null) {
      final String[] ids = meta.split(",[ ]*");

      for (final String id : ids) {
        final RootPanel someElement = RootPanel.get(HOME_IDS_PREFIX + id + "-" + currentLocale);
        final RootPanel defElement = RootPanel.get(HOME_IDS_PREFIX + id + HOME_IDS_DEF_SUFFIX);
        if (someElement != null) {
          someElement.setVisible(true);
        } else if (defElement != null) {
          defElement.setVisible(true);
        }
      }
    }
  }

}