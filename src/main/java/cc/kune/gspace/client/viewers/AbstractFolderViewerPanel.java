package cc.kune.gspace.client.viewers;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.client.ui.UiUtils;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.registry.IconsRegistry;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.GSpaceArmor;
import cc.kune.gspace.client.viewers.FolderViewerPresenter.FolderViewerView;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public abstract class AbstractFolderViewerPanel extends ViewImpl implements FolderViewerView {
  private final InlineLabel emptyLabel;
  protected final GSpaceArmor gsArmor;
  protected final I18nTranslationService i18n;
  private final IconsRegistry iconRegistry;
  protected Widget widget;

  public AbstractFolderViewerPanel(final GSpaceArmor gsArmor, final I18nTranslationService i18n,
      final ContentCapabilitiesRegistry capabilitiesRegistry) {
    this.gsArmor = gsArmor;
    this.i18n = i18n;
    this.iconRegistry = capabilitiesRegistry.getIconsRegistry();
    emptyLabel = new InlineLabel(i18n.t("This folder is empty."));
    emptyLabel.setStyleName("k-empty-msg");
  }

  @Override
  public Widget asWidget() {
    return widget;
  }

  @Override
  public void attach() {
    final ForIsWidget docContainer = gsArmor.getDocContainer();
    docContainer.add(widget);
  }

  @Override
  public void clear() {
    gsArmor.getSubheaderToolbar().clear();
    UiUtils.clear(gsArmor.getDocContainer());
    UiUtils.clear(gsArmor.getDocHeader());
  }

  @Override
  public void detach() {
    clear();
  }

  @Override
  public void setActions(final GuiActionDescCollection actions) {
    gsArmor.getSubheaderToolbar().clear();
    gsArmor.getSubheaderToolbar().addAll(actions);
  }

  @Override
  public void setContainer(final StateContainerDTO state) {
    // gsArmor.getDocHeader().add(new InlineLabel(state.getTitle()));
    final IconLabel label = new IconLabel(
        (ImageResource) iconRegistry.getContentTypeIcon(state.getTypeId()), state.getTitle());
    label.setStyleName("k-fl");
    gsArmor.getDocHeader().add(label);
  }

  @Override
  public void showEmptyMsg() {
    gsArmor.getDocContainer().add(emptyLabel);
  }
}
