package cc.kune.gspace.client.viewers;

import cc.kune.common.client.ui.EditableLabel;
import cc.kune.common.client.ui.HasEditHandler;
import cc.kune.common.client.ui.UiUtils;
import cc.kune.core.client.registry.IconsRegistry;
import cc.kune.core.shared.dto.BasicMimeTypeDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.GSpaceArmor;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;

public class ContentTitleWidget extends Composite {
  private final EditableLabel editableTitle;
  private final GSpaceArmor gsArmor;
  private final I18nTranslationService i18n;
  private final IconsRegistry iconRegistry;
  private final Image titleIcon;

  public ContentTitleWidget(final I18nTranslationService i18n, final GSpaceArmor gsArmor,
      final IconsRegistry iconRegistry) {
    this.i18n = i18n;
    this.gsArmor = gsArmor;
    this.iconRegistry = iconRegistry;
    final FlowPanel flow = new FlowPanel();
    titleIcon = new Image();
    editableTitle = new EditableLabel();
    flow.add(titleIcon);
    flow.add(editableTitle);
    initWidget(flow);
  }

  public HasEditHandler getEditableTitle() {
    return editableTitle;
  }

  public void setText(final String text) {
    editableTitle.setText(text);
  }

  public void setTitle(final String title, final String typeId, final BasicMimeTypeDTO mime,
      final boolean editable) {
    final ForIsWidget docHeader = gsArmor.getDocHeader();
    UiUtils.clear(docHeader);

    final ImageResource resource = (mime == null ? (ImageResource) iconRegistry.getContentTypeIcon(typeId)
        : (ImageResource) iconRegistry.getContentTypeIcon(typeId, mime));
    final boolean hasIcon = resource != null;
    if (hasIcon) {
      titleIcon.setResource(resource);
    }
    titleIcon.setVisible(hasIcon);
    editableTitle.setText(title);
    if (editable) {
      editableTitle.setTooltip(i18n.t("Click to edit"));
    } else {
      editableTitle.setTooltip("");
    }
    editableTitle.setEditable(editable);
    docHeader.add(this);
  }

  public void setTitle(final String title, final String typeId, final boolean editable) {
    setTitle(title, typeId, null, editable);
  }

}
