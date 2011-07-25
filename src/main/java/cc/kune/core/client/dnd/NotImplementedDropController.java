package cc.kune.core.client.dnd;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.ui.Widget;

public class NotImplementedDropController extends SimpleDropController {

  private final I18nTranslationService i18n;

  public NotImplementedDropController(final Widget dropTarget, final I18nTranslationService i18n) {
    super(dropTarget);
    this.i18n = i18n;
  }

  @Override
  public void onLeave(final DragContext context) {
    super.onLeave(context);
  }

  @Override
  public void onPreviewDrop(final DragContext context) throws VetoDragException {
    // This cancel the drop
    NotifyUser.info(i18n.t(TextUtils.IN_DEVELOPMENT));
    throw new VetoDragException();
  }
}
