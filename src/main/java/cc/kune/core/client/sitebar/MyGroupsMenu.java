package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuSeparatorDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuShowAction;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.MyGroupsChangedEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.sitebar.SitebarGroupsLink.SitebarNewGroupAction;
import cc.kune.core.client.sn.actions.GotoGroupAction;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class MyGroupsMenu extends MenuDescriptor {
  public static final String MENU_ID = "k-sitebar-my-group";
  private final Provider<ClientFileDownloadUtils> downloadProvider;
  private final GotoGroupAction gotoGroupAction;
  private final SitebarNewGroupAction newGroupAction;
  private final Session session;
  private final SitebarActions siteOptions;

  @Inject
  public MyGroupsMenu(final I18nTranslationService i18n,
      final Provider<ClientFileDownloadUtils> downloadProvider, final CoreResources res,
      final Session session, final GotoGroupAction gotoGroupAction,
      final SitebarNewGroupAction newGroupAction, final SitebarActions siteOptions,
      final GlobalShortcutRegister global, final MenuShowAction menuShowAction, final EventBus eventBus) {
    super(menuShowAction);
    this.session = session;
    this.newGroupAction = newGroupAction;
    this.siteOptions = siteOptions;
    // menuShowAction.setMenu(this);
    setId(MENU_ID);
    setParent(siteOptions.getRightToolbar());
    setStyles("k-no-backimage, k-btn-sitebar");
    this.downloadProvider = downloadProvider;
    this.gotoGroupAction = gotoGroupAction;
    withText(i18n.t("Your groups"));
    withToolTip(i18n.t("See your groups or create a new one"));
    withIcon(res.arrowdownsitebar());
    withShortcut("Alt+G", global);
    eventBus.addHandler(MyGroupsChangedEvent.getType(),
        new MyGroupsChangedEvent.MyGroupsChangedHandler() {
          @Override
          public void onMyGroupsChanged(final MyGroupsChangedEvent event) {
            regenerateMenu(session.isLogged());
          }
        });

    session.onUserSignInOrSignOut(true, new UserSignInOrSignOutHandler() {
      @Override
      public void onUserSignInOrSignOut(final UserSignInOrSignOutEvent event) {
        regenerateMenu(event.isLogged());
      }
    });

  }

  private void addPartipationToMenu(final GroupDTO group) {
    // FIXME, better user URL in GuiDescritors...
    final String logoImageUrl = session.getSiteUrl() + "/" + downloadProvider.get().getGroupLogo(group);
    final MenuItemDescriptor participant = new MenuItemDescriptor(gotoGroupAction);
    participant.setTarget(group);
    participant.withText(group.getLongName()).withIcon(logoImageUrl).setParent(this, true);
  }

  private void regenerateMenu(final boolean isLogged) {
    if (isLogged) {
      if (session.userIsJoiningGroups()) {
        MyGroupsMenu.this.clear();
        setVisible(true);
        final UserInfoDTO userInfoDTO = session.getCurrentUserInfo();
        for (final GroupDTO group : userInfoDTO.getGroupsIsAdmin()) {
          addPartipationToMenu(group);
        }
        for (final GroupDTO group : userInfoDTO.getGroupsIsCollab()) {
          addPartipationToMenu(group);
        }
        new MenuSeparatorDescriptor(MyGroupsMenu.this);
        new MenuItemDescriptor(MyGroupsMenu.this, newGroupAction);
        siteOptions.refreshActions();
      } else {
        setVisible(false);
      }
    } else {
      setVisible(false);
    }
  }
}
