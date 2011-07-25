package cc.kune.core.client.dnd;

import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.GSpaceArmor;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class NotImplementedDropManager {

  private final KuneDragController dragController;
  private final I18nTranslationService i18n;

  @Inject
  public NotImplementedDropManager(final I18nTranslationService i18n, final GSpaceArmor gSpaceArmor,
      final KuneDragController dragController) {
    this.i18n = i18n;
    this.dragController = dragController;
    registerImpl((FlowPanel) gSpaceArmor.getEntityHeader());
  }

  public void register(final Widget widget) {
    registerImpl(widget);
  }

  private void registerImpl(final Widget widget) {
    final NotImplementedDropController dropController = new NotImplementedDropController(widget, i18n);
    dragController.registerDropController(dropController);
    widget.addAttachHandler(new Handler() {
      @Override
      public void onAttachOrDetach(final AttachEvent event) {
        if (!event.isAttached()) {
          dragController.unregisterDropController(dropController);
        }
      }
    });
  }
}
