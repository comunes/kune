package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.gspace.client.share.items.ShareItemDescriptor;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ContentEditableShareItemDescriptor extends ShareItemDescriptor {
  public static class MakeContentNoEditableAction extends AbstractMakeContentEditableAction {
    @Inject
    MakeContentNoEditableAction(final Provider<ContentServiceHelper> helper) {
      super(false, helper);
    }
  }

  @Singleton
  public static class MakeContentNoEditableMenuItem extends AbstractToggleShareMenuItem {
    @Inject
    public MakeContentNoEditableMenuItem(final MakeContentNoEditableAction action,
        final IconicResources icons) {
      super(action);
      withIcon(icons.world()).withText(I18n.t("Don't allow edit by everyone"));
    }
  }

  @Inject
  public ContentEditableShareItemDescriptor(final IconicResources icons,
      final MakeContentNoEditableMenuItem makeListPublic) {
    super(icons.del(), I18n.tWithNT("Anyone", "with initial uppercase"), I18n.t("can edit"),
        makeListPublic);
  }
}