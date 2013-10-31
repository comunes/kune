package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.gspace.client.share.items.ShareItemDescriptor;
import cc.kune.lists.client.rpc.ListsServiceHelper;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ListPublicShareItemDescriptor extends ShareItemDescriptor {

  public static class MakeListNonPublicAction extends AbstractMakeListPublicAction {
    @Inject
    MakeListNonPublicAction(final Provider<ListsServiceHelper> helper) {
      super(false, helper);
    }
  }

  @Singleton
  public static class MakeListNonPublicMenuItem extends AbstractToggleMenuItem {

    @Inject
    public MakeListNonPublicMenuItem(final MakeListNonPublicAction action, final IconicResources icons) {
      super(action);
      withIcon(icons.del()).withText(I18n.t("Make this list not public"));
    }
  }

  @Inject
  public ListPublicShareItemDescriptor(final IconicResources icons,
      final MakeListNonPublicMenuItem makeListNonPublic) {
    super(icons.world(), I18n.tWithNT("Anyone", "with initial uppercase"), I18n.t("can be member"),
        makeListNonPublic);
  }

}