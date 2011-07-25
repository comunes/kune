package cc.kune.core.client.dnd;

import cc.kune.core.shared.i18n.I18nTranslationService;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.ui.Widget;

public class NotAllowedDropController extends SimpleDropController {

  private final I18nTranslationService i18n;

  public NotAllowedDropController(final Widget dropTarget, final I18nTranslationService i18n) {
    super(dropTarget);
    this.i18n = i18n;
  }

  @Override
  public void onPreviewDrop(final DragContext context) throws VetoDragException {
    // This cancel the drop
    // NotifyUser.info(i18n.t("You cannot drop this here));
    throw new VetoDragException();
  }
}
