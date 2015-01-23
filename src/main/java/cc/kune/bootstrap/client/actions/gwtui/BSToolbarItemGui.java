package cc.kune.bootstrap.client.actions.gwtui;

import cc.kune.bootstrap.client.ui.ComplexAnchorListItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.shared.res.KuneIcon;

public class BSToolbarItemGui extends AbstractBSChildGuiItem {

  private ComplexAnchorListItem item;

  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;
    item = new ComplexAnchorListItem();
    item.addClickHandler(defClickHandler);
    child = item;
    super.create(descriptor);
    configureItemFromProperties();
    return this;
  }

  @Override
  protected void setEnabled(final boolean enabled) {
    item.setEnabled(enabled);
  }

  @Override
  public void setIcon(final KuneIcon icon) {
    item.setIcon(icon);
  }

  @Override
  protected void setIconBackColor(final String backgroundColor) {
    item.setIconBackColor(backgroundColor);
  }

  @Override
  protected void setIconStyle(final String style) {
    item.setIconStyle(style);

  }

  @Override
  public void setIconUrl(final String url) {
    item.setIconUrl(url);

  }

  @Override
  protected void setText(final String text) {
    item.setText(text);

  }

  @Override
  public void setVisible(final boolean visible) {
    item.setVisible(visible);
  }

  @Override
  public boolean shouldBeAdded() {
    return false;
  }

}
