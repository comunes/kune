package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.gspace.client.share.items.ShareItemDescriptor;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ContentVisibleShareItemDescriptor extends ShareItemDescriptor {
  public static class MakeContentNoVisibleAction extends AbstractMakeContentVisibleAction {
    @Inject
    MakeContentNoVisibleAction(final Provider<ContentServiceHelper> helper) {
      super(false, helper);
    }
  }

  @Singleton
  public static class MakeContentNoVisibleMenuItem extends AbstractToggleShareMenuItem {
    @Inject
    public MakeContentNoVisibleMenuItem(final MakeContentNoVisibleAction action,
        final IconicResources icons) {
      super(action);
      withIcon(icons.world()).withText(I18n.t("Don't do this public"));
    }
  }

  @Inject
  public ContentVisibleShareItemDescriptor(final IconicResources icons,
      final MakeContentNoVisibleMenuItem makeListPublic) {
    super(icons.del(), I18n.tWithNT("Anyone", "with initial uppercase"), I18n.t("can view"),
        makeListPublic);
  }
}