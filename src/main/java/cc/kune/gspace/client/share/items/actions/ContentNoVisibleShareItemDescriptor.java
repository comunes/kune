package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.gspace.client.share.items.ShareItemDescriptor;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ContentNoVisibleShareItemDescriptor extends ShareItemDescriptor {
  public static class MakeContentVisibleAction extends AbstractMakeContentVisibleAction {
    @Inject
    MakeContentVisibleAction(final Provider<ContentServiceHelper> helper) {
      super(true, helper);
    }
  }

  @Singleton
  public static class MakeContentVisibleMenuItem extends AbstractToggleShareMenuItem {
    @Inject
    public MakeContentVisibleMenuItem(final MakeContentVisibleAction action, final IconicResources icons) {
      super(action);
      withIcon(icons.world()).withText(I18n.t("Do this visible to anyone"));
    }
  }

  @Inject
  public ContentNoVisibleShareItemDescriptor(final IconicResources icons,
      final MakeContentVisibleMenuItem makeListPublic) {
    super(icons.del(), I18n.t("Nobody else"), I18n.t("can't view"), makeListPublic);
  }
}