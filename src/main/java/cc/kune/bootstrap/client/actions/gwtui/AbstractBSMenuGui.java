package cc.kune.bootstrap.client.actions.gwtui;

import org.gwtbootstrap3.client.ui.base.HasIcon;
import org.gwtbootstrap3.client.ui.constants.IconType;

import cc.kune.common.client.actions.gwtui.AbstractGwtMenuGui;

import com.google.gwt.user.client.ui.UIObject;

public abstract class AbstractBSMenuGui extends AbstractGwtMenuGui {
  @Override
  protected void setIcon(final Object icon) {
    if (icon instanceof IconType) {
      final UIObject widget = descriptor.isChild() ? child : getWidget();
      if (widget instanceof HasIcon) {
        ((HasIcon) widget).setIcon((IconType) icon);
      }
    } else {
      super.setIcon(icon);
    }
  }
}
