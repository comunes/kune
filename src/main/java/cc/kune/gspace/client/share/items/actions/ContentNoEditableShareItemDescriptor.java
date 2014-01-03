package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.gspace.client.share.items.ShareItemDescriptor;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ContentNoEditableShareItemDescriptor extends ShareItemDescriptor {
  public static class MakeContentEditableAction extends AbstractMakeContentEditableAction {
    @Inject
    MakeContentEditableAction(final Provider<ContentServiceHelper> helper) {
      super(true, helper);
    }
  }

  @Singleton
  public static class MakeContentEditableMenuItem extends AbstractToggleShareMenuItem {
    @Inject
    public MakeContentEditableMenuItem(final MakeContentEditableAction action,
        final IconicResources icons) {
      super(action);
      withIcon(icons.world()).withText(I18n.t("Allow any person to edit this"));
    }
  }

  @Inject
  public ContentNoEditableShareItemDescriptor(final IconicResources icons,
      final MakeContentEditableMenuItem makeListPublic) {
    super(icons.del(), I18n.t("Nobody else"), I18n.t("can't edit"), makeListPublic);
  }
}