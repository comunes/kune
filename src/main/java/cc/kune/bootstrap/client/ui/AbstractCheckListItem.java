package cc.kune.bootstrap.client.ui;

import org.gwtbootstrap3.client.ui.constants.IconType;

public abstract class AbstractCheckListItem extends ComplexAnchorListItem {

  private final IconType checkedIcon;
  private final IconType uncheckedIcon;

  public AbstractCheckListItem(final IconType checked, final IconType unchecked) {
    this.checkedIcon = checked;
    this.uncheckedIcon = unchecked;
    super.setIcon(unchecked);
  }

  public AbstractCheckListItem(final IconType checked, final IconType unchecked, final String text) {
    this(checked, unchecked);
    setText(text);
  }

  public boolean isChecked() {
    return getIcon().equals(checkedIcon);
  }

  public void setChecked(final boolean checked) {
    super.setIcon(checked ? checkedIcon : uncheckedIcon);
  }

  @Override
  public void setIcon(final IconType iconType) {
    // We override this to avoid the setting of another icons
  }
}
