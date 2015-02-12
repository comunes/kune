package cc.kune.polymer.client.actions.ui;

import org.gwtbootstrap3.client.ui.constants.IconType;

import br.com.rpa.client._coreelements.HasIcon;
import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;

import com.google.gwt.user.client.ui.UIObject;

public abstract class AbstractPoChildGuiItem extends AbstractChildGuiItem {

  public static final String ICON = "action.icon";

  public AbstractPoChildGuiItem() {
    super();
  }

  public AbstractPoChildGuiItem(final GuiActionDescrip descriptor) {
    super(descriptor);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIcon(java.lang.Object)
   */
  @Override
  protected void setIcon(final Object icon) {
    final UIObject widget = descriptor.isChild() ? child : getWidget();
    final boolean widgetIsIcon = widget instanceof HasIcon;
    if (icon instanceof IconType) {
      if (widgetIsIcon) {
        final IconType iconType = (IconType) icon;
        final String iconS = iconType.getCssName().replaceFirst("-", ":");
        ((HasIcon) widget).setIcon(iconS);
      }
    } else if (icon instanceof String) {
      final String iconS = (String) icon;
      final Object iconWidget = descriptor.getValue(ICON);
      HasIcon hasIcon = null;
      if (iconWidget != null) {
        hasIcon = (HasIcon) iconWidget;
      } else if (widgetIsIcon) {
        hasIcon = (HasIcon) widget;
      }
      if (hasIcon != null) {
        if (iconS.startsWith("http")) {
          hasIcon.setIconSrc(iconS);
        } else if (!iconS.startsWith("#")) {
          hasIcon.setIcon(iconS);
        }
      }
    } else {
      super.setIcon(icon);
    }
  }
}
