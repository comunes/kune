package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.gspace.client.share.items.ShareItemDescriptor;
import cc.kune.lists.client.rpc.ListsServiceHelper;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ListNoPublicShareItemDescriptor extends ShareItemDescriptor {
  public static class MakeListPublicAction extends AbstractMakeListPublicAction {
    @Inject
    MakeListPublicAction(final Provider<ListsServiceHelper> helper) {
      super(true, helper);
    }
  }

  @Singleton
  public static class MakeListPublicMenuItem extends AbstractToggleMenuItem {
    @Inject
    public MakeListPublicMenuItem(final MakeListPublicAction action, final IconicResources icons) {
      super(action);
      withIcon(icons.world()).withText(I18n.t("Make this list public"));
    }
  }

  @Inject
  public ListNoPublicShareItemDescriptor(final IconicResources icons,
      final MakeListPublicMenuItem makeListPublic) {
    super(icons.del(), I18n.t("Nobody else"), I18n.t("can't be member"), makeListPublic);
  }
}